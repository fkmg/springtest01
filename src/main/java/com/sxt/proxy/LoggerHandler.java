package com.sxt.proxy;

import com.sxt.aop.Logger;
import com.sxt.aop.impl.ConsoleLogger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LoggerHandler implements InvocationHandler {

    private Object target;

    private Logger log = new ConsoleLogger();

    public Object getTarget(Object target){
        this.target = target;

        return Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //添加日志
        log.log(method);
        //执行方法
        Object result = method.invoke(target, args);
        return result;
    }
}
