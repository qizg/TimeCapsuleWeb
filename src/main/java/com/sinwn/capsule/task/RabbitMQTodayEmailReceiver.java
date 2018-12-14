package com.sinwn.capsule.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.sinwn.capsule.config.RabbitMQConfig;
import com.sinwn.capsule.domain.response.EmailBean;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_REAL_EMAIL)
public class RabbitMQTodayEmailReceiver {


    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMQTodayEmailReceiver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @RabbitHandler
    public void process(@Payload byte[] emailBytes, Channel channel, Message message) throws IOException {

        EmailBean emailBean = objectMapper.readerFor(EmailBean.class).readValue(emailBytes);

        System.out.println("HelloReceiver收到  : " + emailBean.getSubject() + "收到时间" + new Date());
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了
            // 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("receiver success");
            // TODO 发送邮件操作

        } catch (IOException e) {
            e.printStackTrace();
            // 处理失败，重新压入MQ
            channel.basicRecover();
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
        }

    }
}
