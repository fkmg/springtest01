package com.sxt.test;

import com.sxt.dom4j.SxtApplicationContext;
import com.sxt.proxy.CglibProxy;
import com.sxt.proxy.JDKProxy;
import com.sxt.proxy.LoggerHandler;
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


    @Test
    public void testCglibProxy(){
        SxtApplicationContext sxtApplicationContext = new SxtApplicationContext();
        UserService userService = (UserService)sxtApplicationContext.getBean("userService");
        CglibProxy jdkProxy = new CglibProxy();
        UserService proxyUserService =(UserService) jdkProxy.getProxy(userService);
        proxyUserService.save();
    }

    @Test
    public void testLogerProxy(){
        SxtApplicationContext sxtApplicationContext = new SxtApplicationContext();
        UserService userService = (UserService)sxtApplicationContext.getBean("userService");
        //CglibProxy jdkProxy = new CglibProxy();
        LoggerHandler loggerHandler = new LoggerHandler();
        UserService proxyUserService =(UserService) loggerHandler.getTarget(userService);
        proxyUserService.save();
    }
}
