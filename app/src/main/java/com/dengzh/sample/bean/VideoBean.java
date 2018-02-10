package com.dengzh.sample.bean;

import java.io.Serializable;

/**
 * Created by dengzh on 2018/1/29.
 */

public class VideoBean implements Serializable{

    private String title;
    private String url;

    public VideoBean() {
    }

    public VideoBean(String url, String title) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
