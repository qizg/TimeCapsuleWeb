package com.sinwn.capsule.domain.response;

public class LoginResponse {
    private long userId;

    private String email;

    private String phone;

    private String nickName;

    private String superToken;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSuperToken() {
        return superToken;
    }

    public void setSuperToken(String superToken) {
        this.superToken = superToken;
    }
}
