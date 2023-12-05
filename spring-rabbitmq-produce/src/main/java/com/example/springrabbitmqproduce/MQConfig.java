package com.example.springrabbitmqproduce;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConversionException;

@Configuration
public class MQConfig {

    public static final String QUEUE="message_queue";
    public static final String EXCHANGE="message_exchange";
    public static final String ROUTINGKEY="message_routingKey";
    @Bean
    public Queue queue(){
        return new Queue(QUEUE);
    }


    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }
    @Bean
    public Binding binding(Queue queue,TopicExchange topicExchange){
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(ROUTINGKEY);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template=new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}
