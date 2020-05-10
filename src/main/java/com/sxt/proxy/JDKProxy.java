package com.sxt.proxy;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {

    private static Logger log = Logger.getLogger(JDKProxy.class);

    private Object target;

    public JDKProxy() {
    }

    public Object getTarget(Object target) {
        /**
         *      第一个参数:loader - 定义代理类的类加载器
         第二个参数:interfaces - 被代理类要实现的接口列表
         第三个参数:h - 指派方法调用的调用处理程序
         */
        this.target=target;
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.debug("为方法:"+method.getName()+"增加日志功能");
        Object value = method.invoke(target, args);
        return value;
    }
}
