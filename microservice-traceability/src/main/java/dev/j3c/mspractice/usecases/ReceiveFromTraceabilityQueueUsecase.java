package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.TransactionResultDto;
import dev.j3c.mspractice.mapper.TransactionResultMapper;
import dev.j3c.mspractice.repository.TraceabilityRepository;
import dev.j3c.mspractice.usecases.interfaces.ReceiveFromTraceabilityQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class ReceiveFromTraceabilityQueueUsecase implements ReceiveFromTraceabilityQueue {

    private final Logger logger = LoggerFactory.getLogger(ReceiveFromTraceabilityQueueUsecase.class);
    private final TraceabilityRepository traceabilityRepository;
    private final TransactionResultMapper transactionResultMapper;

    @Autowired
    public ReceiveFromTraceabilityQueueUsecase(TraceabilityRepository traceabilityRepository, TransactionResultMapper transactionResultMapper) {
        this.traceabilityRepository = traceabilityRepository;
        this.transactionResultMapper = transactionResultMapper;
    }

    public Mono<TransactionResultDto> receiveMessage(TransactionResultDto transactionResultDto) {
        logger.info("[MS-TRACEABILITY] Saving traceability message from RabbitMQ");
        return this.traceabilityRepository
                .save(this.transactionResultMapper
                        .mapFromDtoToEntity()
                        .apply(transactionResultDto))
                .map(transactionResult -> this.transactionResultMapper
                        .mapFromEntityToDto()
                        .apply(transactionResult));
    }

}
