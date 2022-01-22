package dev.j3c.mspractice.repository;

import dev.j3c.mspractice.collection.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<Boolean> existsByContactData_Email(String email);
}
