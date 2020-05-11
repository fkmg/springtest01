package com.sxt.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

public class CglibProxy  implements MethodInterceptor {

    private static Logger log = Logger.getLogger(CglibProxy.class);

    private Object target;

    public Object getProxy(Object target) {
        this.target = target;

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(target.getClass());

        enhancer.setCallback(this);

        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {

        Object result = method.invoke(target, params);
        log.debug("为方法"+method.getName()+"添加日志");
        return result;
    }
}
