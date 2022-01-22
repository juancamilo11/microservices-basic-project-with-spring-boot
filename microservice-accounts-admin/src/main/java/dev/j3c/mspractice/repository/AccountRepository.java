package dev.j3c.mspractice.repository;

import dev.j3c.mspractice.collection.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, String> {
    Flux<Account> findAllByUserId(String id);
}
