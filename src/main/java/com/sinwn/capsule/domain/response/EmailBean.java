package com.sinwn.capsule.domain.response;

import java.util.Date;

public class EmailBean {

    private int emailId;

    private String receiver;

    private String receiverNickname;

    private String subject;

    private String content;

    private Date createDate;

    private Date sendDate;

    public EmailBean() {
    }

    public EmailBean(int emailId, String receiver, String receiverNickname,
                     String subject, String content, Date createDate, Date sendDate) {
        this.emailId = emailId;
        this.receiver = receiver;
        this.receiverNickname = receiverNickname;
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
        this.sendDate = sendDate;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public void setReceiverNickname(String receiverNickname) {
        this.receiverNickname = receiverNickname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
