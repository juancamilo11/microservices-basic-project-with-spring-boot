package dev.j3c.mspractice.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteAccountById {
    Mono<Void> accept(String accountId);
}
