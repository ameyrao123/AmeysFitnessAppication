package com.Fitness.AIService.Config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class  RabbitMQConfig {

    // spring takes values from applications.properties and inject it in the string
    @Value("${rabbitmq.exchange.name}")
    private  String exchange;
    @Value("${rabbitmq.routing.key}")
    private  String routingKey;
    @Value("${rabbitmq.queue.name}")
    private  String queue;

    @Bean
    public Queue activityQueue(){
        // this is the name of queue
        // true means even rabbit mq restarts it keeps the messages
        return new Queue(queue,true);
    }

    @Bean
    public DirectExchange activityExchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding acitivityBinding(Queue activityQueue,DirectExchange directExchange){
        return BindingBuilder.bind(activityQueue).to(activityExchange()).with(routingKey);
    }
    @Bean
    public MessageConverter jsonMessageConverter(){
        // this is to covert the message to json format
        return new JacksonJsonMessageConverter();
    }
}
