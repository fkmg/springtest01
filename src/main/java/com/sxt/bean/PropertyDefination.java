package com.sxt.bean;

public class PropertyDefination {
    private String name;//封装property节点中的name属性值
    private String ref;//封装property节点中的ref属性值

    public PropertyDefination(String name, String ref) {
        this.name = name;
        this.ref = ref;
    }

    public PropertyDefination() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "PropertyDefination{" +
                "name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                '}';
    }
}
