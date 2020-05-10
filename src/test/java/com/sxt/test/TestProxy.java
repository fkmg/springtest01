package com.sxt.test;

import com.sxt.dom4j.SxtApplicationContext;
import com.sxt.proxy.JDKProxy;
import com.sxt.service.UserService;
import org.junit.Test;

public class TestProxy {

    @Test
    public void testJdkProxy(){
        SxtApplicationContext sxtApplicationContext = new SxtApplicationContext();
        UserService userService = (UserService)sxtApplicationContext.getBean("userService");
        JDKProxy jdkProxy = new JDKProxy();
        UserService proxyUserService =(UserService) jdkProxy.getTarget(userService);
        proxyUserService.save();
    }
}
