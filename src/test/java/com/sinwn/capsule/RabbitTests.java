package com.sinwn.capsule;

import com.sinwn.capsule.domain.response.EmailBean;
import com.sinwn.capsule.service.MailService;
import com.sinwn.capsule.task.RabbitMQTodayEmailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTests {

    @Autowired
    private RabbitMQTodayEmailSender helloSender;


    @Autowired
    private MailService mailService;

    @Test
    public void hello() throws Exception {
    }


    @Test
    public void checkMail(){
        List<EmailBean> emailBeans = mailService.loadTodayNeedSendMail(1, 100).getList();

        helloSender.sendAllEmail(emailBeans);


        try {
            Thread.sleep(10_000_000_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
