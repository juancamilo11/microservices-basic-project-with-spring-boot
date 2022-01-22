package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.repository.UserRepository;
import dev.j3c.mspractice.usecases.interfaces.DeleteUserById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class DeleteUserByIdUsecase implements DeleteUserById {

    private final UserRepository userRepository;

    @Autowired
    public DeleteUserByIdUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> accept(String id) {
        return userRepository
                .existsById(id)
                .flatMap(exists -> Boolean.TRUE.equals(exists) ?
                        userRepository.deleteById(id).then():
                        Mono.error(new IllegalArgumentException("Error, el usuario con id " + id + " no está registrado en el sistema.")));
    }
}
