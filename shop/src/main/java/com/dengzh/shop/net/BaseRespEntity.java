package com.dengzh.shop.net;

/**
 * Created by dengzh on 2017/9/10 0010.
 * 网络接收实体基类
 * 格式如下
 * {
 *     code:100,
 *     msg:"接收成功",
 *     data:{
 *         ...
 *     }
 * }
 */

public class BaseRespEntity<T> {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
