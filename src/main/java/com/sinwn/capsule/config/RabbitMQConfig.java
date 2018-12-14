package com.sinwn.capsule.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);

    private final static String QUEUE_NAME_EMAIL = "today-email-dead-queue";
    public final static String EXCHANGE_NAME_EMAIL = "today-email-dead-topic-exchange";
    public final static String ROUTING_KEY_EMAIL = "today-email-dead.topic.email";

    public final static String QUEUE_NAME_REAL_EMAIL = "today-email-real-queue";
    private final static String EXCHANGE_NAME_REAL_EMAIL = "today-email-real-topic-exchange";
    private final static String ROUTING_KEY_REAL_EMAIL = "today-email-real.topic.email";

    // 创建发布邮件延时队列， 面向生产端
    @Bean
    public Queue todayEmailQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", EXCHANGE_NAME_REAL_EMAIL);
        arguments.put("x-dead-routing-key", ROUTING_KEY_REAL_EMAIL);
        return new Queue(QUEUE_NAME_EMAIL, true, false, false, arguments);
    }

    // 创建一个 topic 类型的交换器， 面向生产端
    @Bean
    public TopicExchange todayEmailExchange() {
        return new TopicExchange(EXCHANGE_NAME_EMAIL);
    }

    // 使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）， 面向生产端
    @Bean
    public Binding todayEmailBinding() {
        return BindingBuilder.bind(todayEmailQueue()).to(todayEmailExchange()).with(ROUTING_KEY_EMAIL);
    }

    // 创建发布邮件真实队列， 面向消费端
    @Bean
    public Queue todayEmailRealQueue() {
        return new Queue(QUEUE_NAME_REAL_EMAIL);
    }

    // 创建一个 topic 类型的交换器， 面向消费端
    @Bean
    public TopicExchange todayEmailRealExchange() {
        return new TopicExchange(EXCHANGE_NAME_REAL_EMAIL);
    }

    // 使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）， 面向消费端
    @Bean
    public Binding todayEmailRealBinding() {
        return BindingBuilder.bind(todayEmailRealQueue()).to(todayEmailRealExchange())
                .with(ROUTING_KEY_REAL_EMAIL);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {

        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText,
                                        String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
                        exchange, routingKey, replyCode, replyText, message);
            }
        });
        return rabbitTemplate;
    }
}
