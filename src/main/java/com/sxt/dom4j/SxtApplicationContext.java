package com.sxt.dom4j;

import com.sxt.bean.BeanDefination;

import java.util.HashMap;
import java.util.Map;

public class SxtApplicationContext {

    //用于封装配置文件中所有bean节点的信息，利用bean的Id做键，利用bean节点的信息(BeanDefination对象)做值
    private Map<String, BeanDefination> beansMap = new HashMap<String,BeanDefination>();
    //用于保存动态创建的对象信息，利用bean节点的id属性值做键,利用动态创建的对象做值
    private Map<String,Object> objectsMap = new HashMap<String,Object>();

    public SxtApplicationContext(){

    }



}
