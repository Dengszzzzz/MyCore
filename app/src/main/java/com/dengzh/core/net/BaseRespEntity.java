package com.dengzh.core.net;

/**
 * Created by dengzh on 2017/9/10 0010.
 * 网络接收实体基类
 * 一般返回格式都是 {"code":"001","msg":"success","data":{T}}
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
