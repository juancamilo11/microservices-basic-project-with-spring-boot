package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.UserDto;
import dev.j3c.mspractice.mapper.UserMapper;
import dev.j3c.mspractice.repository.UserRepository;
import dev.j3c.mspractice.usecases.interfaces.AddUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class AddUserUsecase implements AddUser {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Autowired
    public AddUserUsecase(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<UserDto> apply(UserDto userDto) {
        final String email = userDto.getContactDataDto().getEmail();
        return userRepository.existsByContactData_Email(email)
                .flatMap(result -> Boolean.TRUE.equals(result) ?
                        Mono.error(new IllegalArgumentException("Error, el email " + email + " ya se encuentra registrado, intente con otro.")) :
                        userRepository
                                .save(mapper
                                        .mapFromDtoToEntity()
                                        .apply(userDto))
                                .map(user -> mapper
                                        .mapFromEntityToDto()
                                        .apply(user)));
    }
}
