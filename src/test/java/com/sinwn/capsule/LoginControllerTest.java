package com.sinwn.capsule;

import com.sinwn.capsule.utils.JWTUtil;
import org.junit.Test;

public class LoginControllerTest {

    @Test
    public void testSayHello() {
        String str = JWTUtil.sign(1, "666", "888");

        boolean f = JWTUtil.verify(str, 1, "666");

        System.out.println(f);

        System.out.println(JWTUtil.getUserId(str));

        System.out.println(str);
    }


}
