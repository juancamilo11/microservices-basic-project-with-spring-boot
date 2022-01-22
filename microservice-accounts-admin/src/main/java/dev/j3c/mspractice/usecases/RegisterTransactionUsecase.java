package dev.j3c.mspractice.usecases;

import com.google.gson.Gson;
import dev.j3c.mspractice.collection.Account;
import dev.j3c.mspractice.config.RabbitMQPublisherConfig;
import dev.j3c.mspractice.dto.TransactionDto;
import dev.j3c.mspractice.dto.TransactionResultDto;
import dev.j3c.mspractice.dto.helpers.EnumTransactionTypeDto;
import dev.j3c.mspractice.repository.AccountRepository;
import dev.j3c.mspractice.usecases.interfaces.RegisterTransaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@Validated
public class RegisterTransactionUsecase implements RegisterTransaction {

    private final AccountRepository accountRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();

    @Autowired
    public RegisterTransactionUsecase(AccountRepository accountRepository, RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<TransactionResultDto> apply(TransactionDto transactionDto) {
        String transactionId = UUID.randomUUID().toString();
        this.rabbitTemplate
                .convertAndSend(RabbitMQPublisherConfig.EXCHANGE,
                        RabbitMQPublisherConfig.ROUTING_KEY,
                        gson.toJson("Starting transaction with Id: " + transactionId));
        if(EnumTransactionTypeDto.valueOf(transactionDto.getType()).equals(EnumTransactionTypeDto.DEPOSIT)) {
            return this.executeDeposit(transactionId, transactionDto)
                    .doOnNext(emitDepositTransactionToTraceabilityQueue());
        }
        return this.executeWithdrawal(transactionId, transactionDto)
                .doOnNext(emitWithdrawalTransactionToTraceabilityQueue());
    }

    private Mono<TransactionResultDto> executeDeposit(String transactionId, TransactionDto transactionDto) {
        return this.accountRepository
                .findById(transactionDto.getAccountId())
                .map(account -> Account.builder()
                        .accountId(account.getAccountId())
                        .userId(account.getUserId())
                        .currentValue(account.getCurrentValue() + transactionDto.getValue())
                        .lastModificationDate(LocalDate.now())
                        .build())
                .flatMap(this.accountRepository::save)
                .flatMap(account -> Mono.just(TransactionResultDto.builder()
                        .id(transactionId)
                        .userId(account.getUserId())
                        .hasErrors(false)
                        .message("Deposit Completed")
                        .date(LocalDate.now())
                        .build()));
    }

    private Mono<TransactionResultDto> executeWithdrawal(String transactionId, TransactionDto transactionDto) {
        return this.accountRepository
                .findById(transactionDto.getAccountId())
                .map(account -> account.getCurrentValue() >= transactionDto.getValue() ? Account.builder()
                        .accountId(account.getAccountId())
                        .userId(account.getUserId())
                        .currentValue(account.getCurrentValue() - transactionDto.getValue())
                        .lastModificationDate(LocalDate.now())
                        .build() : account)
                .flatMap(this.accountRepository::save)
                .flatMap(account -> Mono.just(TransactionResultDto.builder()
                        .id(transactionId)
                        .userId(account.getUserId())
                        .hasErrors(false)
                        .message("Transaction Completed")
                        .date(LocalDate.now())
                        .build()));
    }

    private Consumer<TransactionResultDto> emitDepositTransactionToTraceabilityQueue() {
        return transactionResultDto -> this.rabbitTemplate
                .convertAndSend(RabbitMQPublisherConfig.EXCHANGE,
                        RabbitMQPublisherConfig.ROUTING_KEY,
                        gson.toJson(transactionResultDto));
    }

    private Consumer<TransactionResultDto> emitWithdrawalTransactionToTraceabilityQueue() {
        return transactionResultDto -> this.rabbitTemplate
                .convertAndSend(RabbitMQPublisherConfig.EXCHANGE,
                        RabbitMQPublisherConfig.ROUTING_KEY,
                        gson.toJson(transactionResultDto));
    }

}
