package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.UserDto;
import dev.j3c.mspractice.mapper.UserMapper;
import dev.j3c.mspractice.repository.UserRepository;
import dev.j3c.mspractice.usecases.interfaces.GetUserById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class GetUserByIdUsecase implements GetUserById {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public GetUserByIdUsecase(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Mono<UserDto> apply(String id) {
        return userRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Error, el usuario con id " + id + " no está registrado en el sistema.")))
                .map(user -> userMapper
                        .mapFromEntityToDto()
                        .apply(user));
    }
}
