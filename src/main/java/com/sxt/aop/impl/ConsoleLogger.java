package com.sxt.aop.impl;

import com.sxt.aop.Logger;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLogger implements Logger {

    
    @Override
    public void log(Method method) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(now);
        System.out.println(time+"调用了["+method.getName()+"]方法.");
    }
}
