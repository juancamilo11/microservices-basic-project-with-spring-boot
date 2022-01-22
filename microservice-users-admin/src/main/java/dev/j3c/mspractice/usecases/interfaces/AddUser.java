package dev.j3c.mspractice.usecases.interfaces;

import dev.j3c.mspractice.dto.UserDto;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface AddUser {
    Mono<UserDto> apply(@Valid UserDto userDto);
}
