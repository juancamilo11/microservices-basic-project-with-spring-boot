package dev.j3c.mspractice.repository;

import dev.j3c.mspractice.collection.TransactionResult;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface TraceabilityRepository extends ReactiveCrudRepository<TransactionResult, String> {
    Flux<TransactionResult> findAllByDateBetween(LocalDate dateStart, LocalDate dateEnd);
    Flux<TransactionResult> findAllByMessage(String message);
    Flux<TransactionResult> findAllByUserId(String userId);
}



