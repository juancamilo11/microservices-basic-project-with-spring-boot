package dev.j3c.mspractice.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface VerifyUserExistenceById {
    Mono<Boolean> apply(String id);
}
