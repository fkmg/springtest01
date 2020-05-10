package com.sxt.dom4j;

import com.sxt.bean.BeanDefination;
import com.sxt.bean.PropertyDefination;
import com.sxt.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SxtApplicationContext {
    
    private static Logger log = Logger.getLogger(SxtApplicationContext.class);
    //用于封装配置文件中所有bean节点的信息，利用bean的Id做键，利用bean节点的信息(BeanDefination对象)做值
    private Map<String, BeanDefination> beansMap = new HashMap<String,BeanDefination>();
    //用于保存动态创建的对象信息，利用bean节点的id属性值做键,利用动态创建的对象做值
    private Map<String,Object> objectsMap = new HashMap<String,Object>();

    public SxtApplicationContext(){
        parseXML();
        createObject();
        injectObject();
    }

    /**
     * 使用dom4j解析配置文件
     */
    public  void parseXML(){
        InputStream in = this.getClass().getResourceAsStream("/applicationContext.xml");

        //创建Sxt对象
        SAXReader saxReader = new SAXReader();

        try {
            //创建document对象
            Document document  = saxReader.read(in);
            //获取根节点
            Element element = document.getRootElement();
            //获取bean节点
            Iterator<Element> beanIter = element.elementIterator();
            while (beanIter.hasNext()){
                Element beanNode = beanIter.next();
                //获取id与class
                String id = beanNode.attributeValue("id");
                String beanClass = beanNode.attributeValue("class");
                BeanDefination bean = new BeanDefination(id, beanClass);
                //获取property节点
                Iterator<Element> propertyIter  = beanNode.elementIterator();
                if(propertyIter  != null){
                    while (propertyIter.hasNext()){
                        Element property = propertyIter.next();
                        String ref = property.attributeValue("ref");
                        String name = property.attributeValue("name");
                        PropertyDefination propertyDefination = new PropertyDefination(name, ref);
                        //将对象放到bena的map中
                        bean.getPropsMap().put(name,propertyDefination);
                    }
                }
                beansMap.put(id,bean);
            }
            
            log.debug(beansMap);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据配置解析出的bean节点信息动态创建对象:只要见到一个bean节点，就利用反射机制动态创建一个对象，
     *
     */
    public void createObject(){
        //遍历beansMap

        Set<Map.Entry<String, BeanDefination>> beanMapsSet = beansMap.entrySet();
        Iterator<Map.Entry<String, BeanDefination>> beansIter = beanMapsSet.iterator();
        if(beansIter != null){
            while (beansIter.hasNext()){
                Map.Entry<String, BeanDefination> beansMap = beansIter.next();
                String id = beansMap.getKey();
                BeanDefination beans = beansMap.getValue();
                String beanClass = beans.getBeanClass();
                try {
                    Object obj = Class.forName(beanClass).newInstance();
                    objectsMap.put(id,obj);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 为对象的属性注入值:回调属性的set方法为属性赋值
     * 1.遍历beansMap(保存配置文件信息)，获取bean节点及对应的property节点
     * 2.获取property节点的name属性,然后根据name属性获取对应的setter方法
     * 3.回调setter方法,将property节点的ref所引用的对象赋值给属性。
     */
    public void injectObject(){
        //遍历已创建的map对象
        if(beansMap != null && beansMap.size()>0){
            for (Map.Entry<String, BeanDefination> beanMap : beansMap.entrySet()){
                BeanDefination bean = beanMap.getValue();
                String beanId = bean.getBeanId();
                Map<String, PropertyDefination> propsMap = bean.getPropsMap();
                if(propsMap != null && propsMap.size()>0){
                    for (Map.Entry<String, PropertyDefination> propMaps : propsMap.entrySet()){
                        PropertyDefination prop = propMaps.getValue();
                        //获取属性名称及引用的键值
                        String refName = prop.getName();
                        String refId = prop.getRef();
                        //根据refName获取set方法
                        String setMethodName = makeSetter(refName);
                        //获取需注入的对象
                        Object refObject = objectsMap.get(refId);
                        //获取beanObect
                        Object beanObject = objectsMap.get(beanId);
                        //将对象注入到bean中

                        //1、获取对象的反射对象
                        Class beanClass = beanObject.getClass();

                        try {
                            //Method method = beanClass.getMethod(setMethodName, refObject.getClass());
                            //method.invoke(beanObject,refObject);
                            for (Method method : beanClass.getMethods()){
                                if(method.getName().equals(setMethodName)){
                                    method.invoke(beanObject,refObject);
                                }
                            }
                        }  catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

        }
    }


    /**
     * 根据属性名称获取对应的setter方法名:set+属性的第一个字母大写+其余字母
     * @param fieldName
     * @return
     */

    public String makeSetter(String fieldName){

        return "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
    }


    /**
     * 根据beanId查找bean节点的信息
     * @param beanId
     * @return
     */
    public BeanDefination getBeanDefinationById(String beanId){
        return beansMap.get(beanId);
    }

    /**
     * 根据beanId查找对应bean的对象
     * @param beanId
     * @return
     */
    public Object getBean(String beanId){
        return objectsMap.get(beanId);
    }
    
    
    public static void main( String[] args ) throws ClassNotFoundException {
        Class<?> cls = Class.forName("com.sxt.service.impl.UserServiceImpl");
        //判断cls是否为空
        if (cls != null){
            System.out.println("cls不为空");
        }

        SxtApplicationContext sxtApplicationContext = new SxtApplicationContext();
        UserService userService = (UserService)sxtApplicationContext.getBean("userService");
        userService.save();
    }
    
    




}
