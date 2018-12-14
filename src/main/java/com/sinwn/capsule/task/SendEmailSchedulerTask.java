package com.sinwn.capsule.task;

import com.sinwn.capsule.domain.PageInfo;
import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.response.EmailBean;
import com.sinwn.capsule.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendEmailSchedulerTask {

    private static final int DEF_PAGE_COUNT = 100;

    private final MailService mailService;

    private final RabbitMQTodayEmailSender mailRabbitSender;

    private int nowPageNo = 0;

    @Autowired
    public SendEmailSchedulerTask(MailService mailService, RabbitMQTodayEmailSender mailRabbitSender) {
        this.mailService = mailService;
        this.mailRabbitSender = mailRabbitSender;
    }

    // 每天0点执行
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendEmail2RabbitMQ() {
        PageInfo pageInfo;
        do {
            ResultListData<EmailBean> emailListData = loadEmail(nowPageNo);
            mailRabbitSender.sendAllEmail(emailListData.getList());
            pageInfo = emailListData.getPageInfo();
            nowPageNo = pageInfo.getPageNum() + 1;
        } while (!pageInfo.isLastPage());
    }

    private ResultListData<EmailBean> loadEmail(int pageNo) {
        return mailService.loadTodayNeedSendMail(pageNo, DEF_PAGE_COUNT);
    }
}
