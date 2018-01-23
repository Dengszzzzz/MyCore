package com.dengzh.sample.bean;

/**
 * Created by dengzh on 2018/1/17.
 * 类
 */

public class ClazzBean {

    private Class clazz;
    private String clazzName;

    public ClazzBean() {
    }

    public ClazzBean(String clazzName,Class clazz) {
        this.clazz = clazz;
        this.clazzName = clazzName;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }
}
