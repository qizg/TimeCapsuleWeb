package com.sinwn.capsule.service;

import com.sinwn.capsule.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;


    @Test
    public void test() {

        UserEntity entity = userService.findByUserId(4);
        if (entity != null) {
            System.out.println(entity.getId());
        }
    }

    @Test
    public void update() {
        UserEntity entity = userService.findByUserId(4);
        if (entity != null) {
            entity.setPhone("12121212666");
            userService.updateUser(entity);
        }
    }

    @Test
    public void delete() {
        userService.deleteUser(4);

    }

}
