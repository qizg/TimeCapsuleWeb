package com.sinwn.capsule.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`c_wish`")
public class WishEntity {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "`user_id`")
    private Integer userId;

    /**
     * 接收邮箱
     */
    @Column(name = "`email`")
    private String email;

    /**
     * 接收邮件时间
     */
    @Column(name = "`receive_time`")
    private Date receiveTime;

    /**
     * 公开状态
     */
    @Column(name = "`public_status`")
    private Integer publicStatus;

    /**
     * 匹配状态
     */
    @Column(name = "`match_status`")
    private Integer matchStatus;

    /**
     * 状态 1：正常 2：删除
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    @Column(name = "`content`")
    private String content;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取接收邮箱
     *
     * @return email - 接收邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置接收邮箱
     *
     * @param email 接收邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取接收邮件时间
     *
     * @return receive_time - 接收邮件时间
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * 设置接收邮件时间
     *
     * @param receiveTime 接收邮件时间
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 获取公开状态
     *
     * @return public_status - 公开状态
     */
    public Integer getPublicStatus() {
        return publicStatus;
    }

    /**
     * 设置公开状态
     *
     * @param publicStatus 公开状态
     */
    public void setPublicStatus(Integer publicStatus) {
        this.publicStatus = publicStatus;
    }

    /**
     * 获取匹配状态
     *
     * @return match_status - 匹配状态
     */
    public Integer getMatchStatus() {
        return matchStatus;
    }

    /**
     * 设置匹配状态
     *
     * @param matchStatus 匹配状态
     */
    public void setMatchStatus(Integer matchStatus) {
        this.matchStatus = matchStatus;
    }

    /**
     * 获取状态 1：正常 2：删除
     *
     * @return status - 状态 1：正常 2：删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 1：正常 2：删除
     *
     * @param status 状态 1：正常 2：删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}