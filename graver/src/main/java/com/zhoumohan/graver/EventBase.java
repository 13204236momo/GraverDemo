package com.zhoumohan.graver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    /**
     *方法名
     * setOn-xxx-listener
     */
    String listrnerSetter();

    /**
     * 监听对象
     * 如：View.OnClickListener();
     */
    Class<?> listenerType();

    /**
     * 要执行的回调方法
     * 如：onClick（）
     */
    String callBackListener();

}
