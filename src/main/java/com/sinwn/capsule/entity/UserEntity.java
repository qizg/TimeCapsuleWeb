package com.sinwn.capsule.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "`t_user`")
public class UserEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    private Integer id;

    /**
     * 邮箱
     */
    @Column(name = "`email`")
    private String email;

    @Column(name = "`phone`")
    private String phone;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`token`")
    private String token;

    @Column(name = "`nick_name`")
    private String nickName;

    @Column(name = "`avatar`")
    private String avatar;

    @Column(name = "`state`")
    private Integer state;

    @Column(name = "`create_time`")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}