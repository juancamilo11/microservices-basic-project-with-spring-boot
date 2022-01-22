package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.TransactionResultDto;
import dev.j3c.mspractice.mapper.TransactionResultMapper;
import dev.j3c.mspractice.repository.TraceabilityRepository;
import dev.j3c.mspractice.usecases.interfaces.GetTransactionById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class GetTransactionByIdUsecase implements GetTransactionById {
    private final TraceabilityRepository traceabilityRepository;
    private final TransactionResultMapper transactionResultMapper;

    @Autowired
    public GetTransactionByIdUsecase(TraceabilityRepository traceabilityRepository, TransactionResultMapper transactionResultMapper) {
        this.traceabilityRepository = traceabilityRepository;
        this.transactionResultMapper = transactionResultMapper;
    }

    @Override
    public Mono<TransactionResultDto> apply(String id) {
        return this.traceabilityRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(transactionResult -> this.transactionResultMapper
                        .mapFromEntityToDto()
                        .apply(transactionResult));
    }
}
