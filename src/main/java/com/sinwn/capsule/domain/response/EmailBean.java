package com.sinwn.capsule.domain.response;

import java.util.Date;

public class EmailBean {

    private int id;

    private String receiver;

    private String subject;

    private String content;

    private Date sendDate;

    public EmailBean() {
    }

    public EmailBean(int id, String receiver, String subject, String content, Date sendDate) {
        this.id = id;
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.sendDate = sendDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
