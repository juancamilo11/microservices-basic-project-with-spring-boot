package dev.j3c.mspractice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPublisherConfig {

    public static final String TRACEABILITY_QUEUE = "bank.traceability.queue";

    public static final String EXCHANGE = "bank.traceability.exchange";

    public static final String ROUTING_KEY = "bank.traceability.routing_key";

    @Bean
    public Queue queue() {
        return new Queue(TRACEABILITY_QUEUE, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(this.queue())
                .to(this.topicExchange())
                .with(ROUTING_KEY);
    }

}
