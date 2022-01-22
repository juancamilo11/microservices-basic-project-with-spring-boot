package dev.j3c.mspractice.usecases.interfaces;

import dev.j3c.mspractice.dto.AccountDto;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface GetAccountsByUserId {
    Flux<AccountDto> apply(String userId);
}
