package com.dengzh.sample.bean;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by dengzh on 2017/11/4.
 * Realm 数据库实体类
 * 注意：
 * 1.如果创建Model并运行过，然后修改了Model。那么就需要升级数据库，否则会抛异常。
 * 2.Realm数据库的主键字段不是自动增长的，并且不支持设置数据的自增。
 */

public class RealmUserBean extends RealmObject{

    @PrimaryKey
    private int id;   //@PrimaryKey——表示该字段是主键
    @Required
    private String name; //@Required——表示该字段非空，基本数据类型不需要使用注解 @Required，因为他们本身就不可为空。
    @Ignore
    private int age;     //@Ignore——表示忽略该字段
    private String email;
    private String phone;  //新加的字段，测试数据库升级

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "RealmUserBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
