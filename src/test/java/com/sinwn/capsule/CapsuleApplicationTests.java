package com.sinwn.capsule;

import com.sinwn.capsule.entity.AppTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CapsuleApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private AppTest blogProperties;

    @Test
    public void getHello() throws Exception {
        Assert.assertEquals(blogProperties.getName(), "时光胶囊");
        Assert.assertEquals(blogProperties.getTitle(), "时光胶囊");
    }
}
