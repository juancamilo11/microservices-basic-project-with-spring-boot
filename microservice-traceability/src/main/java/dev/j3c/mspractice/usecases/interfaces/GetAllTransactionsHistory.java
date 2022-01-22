package dev.j3c.mspractice.usecases.interfaces;

import dev.j3c.mspractice.dto.TransactionResultDto;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface GetAllTransactionsHistory {
    Flux<TransactionResultDto> get();
}
