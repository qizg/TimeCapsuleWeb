package com.sinwn.capsule.service.impl;

import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.response.EmailBean;
import com.sinwn.capsule.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Value("${mail.address}")
    private String from;

    @Value("${mail.niceName}")
    private String niceName;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * 发送文本邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }

    }

    /**
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public boolean sendHtmlMail(String to, String receiverNickname, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = getMimeMessageHelper(to, receiverNickname, subject, content, message);

            mailSender.send(message);
            logger.info("html邮件发送成功:" + to);
            return true;
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = getMimeMessageHelper(to, to, subject, content, message);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            //helper.addAttachment("test"+fileName, file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送正文中有静态资源（图片）的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = getMimeMessageHelper(to, to, subject, content, message);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送心愿邮件
     *
     * @param emailBean
     */
    @Override
    public void sendEmailWish(EmailBean emailBean) {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("email", emailBean);
        String emailContent = templateEngine.process("email_wish", context);

        boolean sendFlag = sendHtmlMail(emailBean.getReceiver(), emailBean.getReceiverNickname(),
                emailBean.getSubject(), emailContent);

        // TODO 发送成功更新状态
        if (sendFlag) {

        }
    }

    /**
     * 查询当天需要发送的邮件
     *
     * @param pageNo
     * @param pageCount
     * @return
     */
    @Override
    public ResultListData<EmailBean> loadTodayNeedSendMail(int pageNo, int pageCount) {
        // Todo 查询数据库，完成真实数据返回。

        List<EmailBean> eMailBeans = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            eMailBeans.add(new EmailBean(i, i + "@xx.cn", i + "@xx.cn", "subject" + i,
                    "content" + i, new Date(), new Date(System.currentTimeMillis() + i * 10_000L)));
        }

        return new ResultListData<>(eMailBeans);
    }

    private MimeMessageHelper getMimeMessageHelper(String to, String receiverNickname, String subject,
                                                   String content, MimeMessage message)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress(from, niceName, "UTF-8"));
        helper.setTo(new InternetAddress(to, receiverNickname, "UTF-8"));
        helper.setSubject(subject);
        //true表示需要创建一个multipart message
        helper.setText(content, true);
        return helper;
    }
}
