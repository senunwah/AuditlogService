package com.jumiapay.interview.auditlogservice.config;

import com.jumiapay.interview.auditlogservice.messaging.IListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;
    @Value("${app.rabbitmq.routingkey}")
    private String routingKey;
    @Value("${app.rabbitmq.queue}")
    private String queue;

    @Bean
    public Exchange exchange() {
        return new TopicExchange(exchange,true,false);
    }

    @Bean
    public Queue queue() {
        return new Queue(queue,true);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(IListener listener) {
        return new MessageListenerAdapter(listener,"onListen");
    }
}
