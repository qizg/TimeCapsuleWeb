package com.sinwn.capsule.service;

import com.sinwn.capsule.domain.response.EmailBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSimpleMail() throws Exception {
//        mailService.sendSimpleMail("qi@sinwn.com", "test simple mail", " hello this is simple mail");
    }

    @Test
    public void testHtmlMail() throws Exception {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
//        mailService.sendHtmlMail("qi@sinwn.com", "test simple mail", content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath = "e:\\tmp\\application.log";
//        mailService.sendAttachmentsMail("qi@sinwn.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }


    @Test
    public void sendInlineResourceMail() {
        String rscId = "neo006";
        String content = "<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "C:\\Users\\summer\\Pictures\\favicon.png";

//        mailService.sendInlineResourceMail("qi@sinwn.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }

    @Test
    public void sendEmailWish() {
        EmailBean bean = new EmailBean(111, "qi@sinwn.com", "Qi在啊", "模板邮件",
                "我有一个心愿在那里", new Date(), new Date(System.currentTimeMillis() + 10_000L));
        try {
//            mailService.sendEmailWish(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
