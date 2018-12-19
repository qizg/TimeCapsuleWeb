package com.sinwn.capsule.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.sinwn.capsule.config.RabbitMQConfig;
import com.sinwn.capsule.domain.response.EmailBean;
import com.sinwn.capsule.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_REAL_EMAIL)
public class RabbitMQTodayEmailReceiver {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQTodayEmailReceiver.class);

    private final ObjectMapper objectMapper;

    private final MailService mailService;

    @Autowired
    public RabbitMQTodayEmailReceiver(ObjectMapper objectMapper, MailService mailService) {
        this.objectMapper = objectMapper;
        this.mailService = mailService;
    }


    @RabbitHandler
    public void process(@Payload byte[] emailBytes, Channel channel, Message message) throws IOException {
        try {
            EmailBean emailBean = objectMapper.readerFor(EmailBean.class).readValue(emailBytes);
            mailService.sendEmailWish(emailBean);
            // 告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了
            // 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("RabbitMQTodayEmailReceiver", e);
            // 处理失败，重新压入MQ
            channel.basicRecover();
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
        }

    }
}
