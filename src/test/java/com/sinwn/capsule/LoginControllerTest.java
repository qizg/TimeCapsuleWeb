package com.sinwn.capsule;

import com.sinwn.capsule.web.LoginController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginControllerTest {

    @Test
    public void testSayHello() {
        assertEquals("Hello,World!", new LoginController().sayHello());
    }


}
