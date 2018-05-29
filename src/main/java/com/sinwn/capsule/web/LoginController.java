package com.sinwn.capsule.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestHeader Map<String, Object> header,
                        @RequestBody Map<String, Object> body) {

        return "Hello，《Spring Boot 2.x 核心技术实战 - 上 基础篇》！";
    }


}
