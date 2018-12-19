package com.sinwn.capsule.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinwn.capsule.config.RabbitMQConfig;
import com.sinwn.capsule.domain.response.EmailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class RabbitMQTodayEmailSender {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQTodayEmailSender.class);

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMQTodayEmailSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public void sendAllEmail(List<EmailBean> emailBeans) {
        if (emailBeans != null && emailBeans.size() > 0) {
            for (EmailBean bean : emailBeans) {
                sendMail(bean);
            }
        }
    }

    public void sendMail(EmailBean emailBean) {

        this.rabbitTemplate.setExchange(RabbitMQConfig.EXCHANGE_NAME_EMAIL);
        this.rabbitTemplate.setRoutingKey(RabbitMQConfig.ROUTING_KEY_EMAIL);
        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        try {
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(emailBean))
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            MessageProperties properties = message.getMessageProperties();
            properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,
                    MessageProperties.CONTENT_TYPE_JSON);
            properties.setExpiration(getExpiration(emailBean.getSendDate()));
            this.rabbitTemplate.convertAndSend(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getExpiration(Date sendDate) {
        LocalDateTime LocalSendDate = LocalDateTime.ofInstant(sendDate.toInstant(),
                ZoneId.systemDefault());
        long expiration = ChronoUnit.MILLIS.between(LocalDateTime.now(), LocalSendDate);
        if (expiration < 0) {
            expiration = 0;
        }
        return String.valueOf(expiration);
    }
}
