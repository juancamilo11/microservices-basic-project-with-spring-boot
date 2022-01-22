package dev.j3c.mspractice.usecases.interfaces;

import dev.j3c.mspractice.dto.AccountDto;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface AddAccount {
    Mono<AccountDto> apply(AccountDto accountDto);
}
