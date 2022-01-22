package dev.j3c.mspractice.usecases.interfaces;

import dev.j3c.mspractice.dto.TransactionResultDto;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface GetTransactionById {
    Mono<TransactionResultDto> apply(String id);
}
