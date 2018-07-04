package com.sinwn.capsule.domain.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SignUpRequest {
    @Length(max = 20, message = "手机号长度错误")
    private String phone;

    @Length(max = 30, message = "邮箱长度错误")
    @Email(message = "邮箱地址输入错误")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不正确")
    private String password;

    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
