package com.sinwn.capsule.web;

import com.sinwn.capsule.entity.UserEntity;
import com.sinwn.capsule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<UserEntity> getUserList() {

        List<UserEntity> u = new ArrayList<>();
System.out.print("===================================");
        return userService.getUserList("", 0, 10);

//        return u;
    }

}
