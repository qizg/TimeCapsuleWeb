package com.sinwn.capsule;

import com.sinwn.capsule.domain.response.WishBean;
import com.sinwn.capsule.service.WishService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> objRedisTemplate;

    @Autowired
    private WishService wishService;

    @Test
    public void test() throws Exception {

        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));

    }

    @Test
    public void getWishDetail() {
        WishBean bean1 = wishService.getWishById(1);

        objRedisTemplate.opsForValue().set("wishBean1", bean1);

        WishBean bean = (WishBean) objRedisTemplate.opsForValue().get("wishBean1");
        if (bean != null) {
            System.out.println(bean.getId());
        }

    }

}
