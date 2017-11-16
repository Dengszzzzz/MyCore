package com.dengzh.core.rx;

/**
 * Created by dengzh on 2017/9/21 0021.
 * rxBus 的事件
 */

public class RxEvents<T> {

    public static final int TestRxCode = 1; //测试RxBus

    public int code;
    public T content;

    public static <O> RxEvents<O> setContent(O t) {
        RxEvents<O> events = new RxEvents<>();
        events.content = t;
        return events;
    }

    public <T> T getContent() {
        return (T) content;
    }

}
