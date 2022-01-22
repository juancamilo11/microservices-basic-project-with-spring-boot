package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.UserDto;
import dev.j3c.mspractice.mapper.UserMapper;
import dev.j3c.mspractice.repository.UserRepository;
import dev.j3c.mspractice.usecases.interfaces.UpdateUserById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class UpdateUserByIdUsecase implements UpdateUserById {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UpdateUserByIdUsecase(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Mono<UserDto> apply(UserDto userDto) {
        return userRepository
                .existsById(userDto.getId())
                .flatMap(result -> Boolean.TRUE.equals(result) ? userRepository
                        .save(userMapper.mapFromDtoToEntity().apply(userDto))
                        .map(user -> userMapper.mapFromEntityToDto().apply(user)):
                        Mono.error(new IllegalArgumentException("Error, el usuario con id" + userDto.getId() + " no se encuentra ingresado en el sistema.")));
    }
}
