package com.sinwn.capsule.service;

import com.sinwn.capsule.domain.response.WishBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishServiceTest {
    @Autowired
    private WishService wishService;
    @Autowired
    private UserService userService;

    @Test
    public void getDetail() {

        WishBean bean = wishService.getWishById(2);
        if (bean != null) {
            System.out.println(bean.getContent());
        }
    }

    @Test
    public void delete() {
        wishService.deleteWishById(14);
    }

    @Test
    public void tt(){
        userService.findByUserId(4);
    }
}
