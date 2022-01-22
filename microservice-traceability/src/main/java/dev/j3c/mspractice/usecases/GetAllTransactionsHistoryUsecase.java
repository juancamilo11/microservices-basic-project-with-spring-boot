package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.TransactionResultDto;
import dev.j3c.mspractice.mapper.TransactionResultMapper;
import dev.j3c.mspractice.repository.TraceabilityRepository;
import dev.j3c.mspractice.usecases.interfaces.GetAllTransactionsHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class GetAllTransactionsHistoryUsecase implements GetAllTransactionsHistory {

    private final TraceabilityRepository traceabilityRepository;
    private final TransactionResultMapper transactionResultMapper;

    @Autowired
    public GetAllTransactionsHistoryUsecase(TraceabilityRepository traceabilityRepository, TransactionResultMapper transactionResultMapper) {
        this.traceabilityRepository = traceabilityRepository;
        this.transactionResultMapper = transactionResultMapper;
    }

    @Override
    public Flux<TransactionResultDto> get() {
        return this.traceabilityRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(transactionResult -> this.transactionResultMapper
                        .mapFromEntityToDto()
                        .apply(transactionResult));
    }
}
