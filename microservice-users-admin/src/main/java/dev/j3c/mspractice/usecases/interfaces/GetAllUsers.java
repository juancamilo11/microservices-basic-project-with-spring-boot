package dev.j3c.mspractice.usecases.interfaces;

import dev.j3c.mspractice.dto.UserDto;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface GetAllUsers {
    Flux<UserDto> get();
}
