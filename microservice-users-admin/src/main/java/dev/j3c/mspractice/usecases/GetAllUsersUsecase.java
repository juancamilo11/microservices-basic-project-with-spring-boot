package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.UserDto;
import dev.j3c.mspractice.mapper.UserMapper;
import dev.j3c.mspractice.repository.UserRepository;
import dev.j3c.mspractice.usecases.interfaces.GetAllUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class GetAllUsersUsecase implements GetAllUsers {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public GetAllUsersUsecase(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Flux<UserDto> get() {
        return userRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(user -> userMapper.mapFromEntityToDto().apply(user));
    }
}
