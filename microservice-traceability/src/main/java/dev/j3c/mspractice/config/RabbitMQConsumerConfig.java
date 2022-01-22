package dev.j3c.mspractice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import dev.j3c.mspractice.dto.TransactionResultDto;
import dev.j3c.mspractice.usecases.ReceiveFromTraceabilityQueueUsecase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitMQConsumerConfig {

    public static final String TRACEABILITY_QUEUE = "bank.traceability.queue";
    private final ReceiveFromTraceabilityQueueUsecase receiveFromTraceabilityQueueUsecase;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumerConfig.class);

    @Autowired
    public RabbitMQConsumerConfig(ReceiveFromTraceabilityQueueUsecase receiveFromTraceabilityQueueUsecase) {
        this.receiveFromTraceabilityQueueUsecase = receiveFromTraceabilityQueueUsecase;
    }

    @RabbitListener(queues = {TRACEABILITY_QUEUE})
    public void listenerOfNewStockQueue(String messageReceived) throws JsonProcessingException {
        final Gson gson = new Gson();
        logger.info("[MS-TRACEABILITY] Listening to " + TRACEABILITY_QUEUE + " Queue");
        receiveFromTraceabilityQueueUsecase.receiveMessage(gson.fromJson(messageReceived, TransactionResultDto.class)).subscribe();
    }
}
