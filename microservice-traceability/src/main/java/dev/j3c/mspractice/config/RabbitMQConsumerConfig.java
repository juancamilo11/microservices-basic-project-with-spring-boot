package dev.j3c.mspractice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dev.j3c.mspractice.dto.TransactionResultDto;
import dev.j3c.mspractice.usecases.ReceiveFromTraceabilityQueueUsecase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Map;

@Configuration
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
        //System.out.println(messageReceived);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(messageReceived, Map.class);
        logger.info("[MS-TRACEABILITY] Listening to " + TRACEABILITY_QUEUE + " Queue");

        receiveFromTraceabilityQueueUsecase.receiveMessage(
                TransactionResultDto.builder()
                        .id(map.get("id").toString())
                        .userId(map.get("userId").toString())
                        .hasErrors(Boolean.parseBoolean(map.get("hasErrors").toString()))
                        .message(map.get("message").toString())
                        .date(LocalDate.now())
                        .build())
                .subscribe();
    }
}
