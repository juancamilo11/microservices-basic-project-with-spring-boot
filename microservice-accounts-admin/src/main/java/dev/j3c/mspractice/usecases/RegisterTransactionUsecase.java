package dev.j3c.mspractice.usecases;

import com.google.gson.Gson;
import dev.j3c.mspractice.collection.Account;
import dev.j3c.mspractice.config.RabbitMQPublisherConfig;
import dev.j3c.mspractice.dto.TransactionDto;
import dev.j3c.mspractice.dto.TransactionResultDto;
import dev.j3c.mspractice.dto.helpers.EnumTransactionTypeDto;
import dev.j3c.mspractice.repository.AccountRepository;
import dev.j3c.mspractice.router.AccountRouter;
import dev.j3c.mspractice.usecases.interfaces.RegisterTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@Validated
public class RegisterTransactionUsecase implements RegisterTransaction {

    private final AccountRepository accountRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();
    private final VerifyUserExistenceUsecase verifyUserExistenceUsecase;

    @Autowired
    public RegisterTransactionUsecase(AccountRepository accountRepository, RabbitTemplate rabbitTemplate, VerifyUserExistenceUsecase verifyUserExistenceUsecase) {
        this.rabbitTemplate = rabbitTemplate;
        this.accountRepository = accountRepository;
        this.verifyUserExistenceUsecase = verifyUserExistenceUsecase;
    }

    @Override
    public Mono<TransactionResultDto> apply(TransactionDto transactionDto) {
        String transactionId = UUID.randomUUID().toString();
        if(Boolean.FALSE.equals(this.verifyUserExistenceUsecase.apply(transactionDto.getUserId())))
            return Mono.error(new IllegalArgumentException("Error, el usuario con Id " + transactionDto.getUserId() + " no existe en el sistema."));

        if(transactionDto.getType().equals("Deposit")) {
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
                        .message("Withdrawal Completed")
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
