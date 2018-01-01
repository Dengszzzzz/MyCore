package com.dengzh.sample.bean;

/**
 * Created by dengzh on 2018/1/1.
 */

public class Book {
    private boolean isSelect;
    private int id;
    private String name;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Book() {
    }
}
