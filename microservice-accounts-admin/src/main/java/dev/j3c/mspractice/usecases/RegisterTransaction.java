package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.TransactionDto;
import dev.j3c.mspractice.dto.TransactionResultDto;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface RegisterTransaction {
    Mono<TransactionResultDto> apply(@Valid TransactionDto transactionDto);
}
