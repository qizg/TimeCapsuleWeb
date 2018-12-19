package com.sinwn.capsule.service;

import com.sinwn.capsule.domain.ResultListData;
import com.sinwn.capsule.domain.response.EmailBean;

public interface MailService {
    void sendSimpleMail(String to, String subject, String content);

    boolean sendHtmlMail(String to, String receiverNickname, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

    void sendEmailWish(EmailBean emailBean) throws Exception;

    ResultListData<EmailBean> loadTodayNeedSendMail(int pageNo, int pageCount);
}
