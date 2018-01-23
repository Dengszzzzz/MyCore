package com.dengzh.sample.adapter;

import java.io.Serializable;

/**
 * Created by dengzh on 2018/1/18.
 */

public class LocationBean implements Serializable{

    private int left;
    private int top;
    private int height;
    private int width;

    public LocationBean() {
    }

    public LocationBean(int left, int top, int height, int width) {
        this.left = left;
        this.top = top;
        this.height = height;
        this.width = width;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
