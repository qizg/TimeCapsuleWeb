package com.sinwn.capsule.domain.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class WishAddRequest {

    @NotNull(message = "用户Id不能为空")
    private Integer userId;

    @Length(min = 1, max = 30, message = "邮箱地址长度错误")
    @Email(message = "邮箱地址输入错误")
    private String email;

    @Future(message = "接收邮件时间错误")
    @NotNull(message = "接收邮件时间不能为空")
    private Date receiveTime;

    @NotBlank(message = "内容不能为空")
    private String content;

    private Integer publicStatus;

    private Integer matchStatus;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(Integer publicStatus) {
        this.publicStatus = publicStatus;
    }

    public Integer getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(Integer matchStatus) {
        this.matchStatus = matchStatus;
    }
}
