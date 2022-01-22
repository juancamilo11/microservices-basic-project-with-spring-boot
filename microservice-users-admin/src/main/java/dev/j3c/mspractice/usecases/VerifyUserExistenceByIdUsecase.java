package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.repository.UserRepository;
import dev.j3c.mspractice.usecases.interfaces.VerifyUserExistenceById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class VerifyUserExistenceByIdUsecase implements VerifyUserExistenceById {
    private static final Logger logger = LoggerFactory.getLogger(VerifyUserExistenceByIdUsecase.class);
    private final UserRepository userRepository;

    @Autowired
    public VerifyUserExistenceByIdUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Boolean> apply(String id) {
        return this.userRepository
                .existsById(id)
                .flatMap(result -> Boolean.FALSE.equals(result) ?
                        Mono.error(new IllegalArgumentException("El usuario con Id " + id + " no existe")) :
                        Mono.just(result)).doOnNext(result -> logger.info("[MS-ADMIN_USERS] Verifying user existence for a transaction"));
    }
}
