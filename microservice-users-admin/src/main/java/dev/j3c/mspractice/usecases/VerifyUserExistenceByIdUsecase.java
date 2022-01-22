package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.repository.UserRepository;
import dev.j3c.mspractice.usecases.interfaces.VerifyUserExistenceById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class VerifyUserExistenceByIdUsecase implements VerifyUserExistenceById {

    private final UserRepository userRepository;

    @Autowired
    public VerifyUserExistenceByIdUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Boolean> apply(String id) {
        return this.userRepository.existsById(id);
    }
}
