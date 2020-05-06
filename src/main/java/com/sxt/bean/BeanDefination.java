package com.sxt.bean;

import java.util.HashMap;
import java.util.Map;

public class BeanDefination {

    private String beanId;//封装bean节点的id属性值
    private String beanClass;//封装bean节点class的属性值
    //用于封装bean节点下的所有property子节点信息,利用property的name做键，利用property节点信息(封装PropertyDefination对象)做值
    private Map<String,PropertyDefination> propsMap = new HashMap<String,PropertyDefination>();

    @Override
    public String toString() {
        return "BeanDefination{" +
                "beanId='" + beanId + '\'' +
                ", beanClass='" + beanClass + '\'' +
                ", propsMap=" + propsMap +
                '}';
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public Map<String, PropertyDefination> getPropsMap() {
        return propsMap;
    }

    public void setPropsMap(Map<String, PropertyDefination> propsMap) {
        this.propsMap = propsMap;
    }
}
