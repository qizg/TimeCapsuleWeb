package com.sinwn.capsule.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/book/hello",method = RequestMethod.GET)
    public String sayHello() {
        return "Hello，《Spring Boot 2.x 核心技术实战 - 上 基础篇》！";
    }


}
