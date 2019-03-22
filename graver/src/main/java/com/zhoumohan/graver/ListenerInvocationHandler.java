package com.zhoumohan.graver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class ListenerInvocationHandler implements InvocationHandler {

    /**
     * 拦截MainActivity中的某些方法
     */
    private Object target;
    /**
     * 拦截的键值对
     */
    private HashMap<String, Method> methodmap = new HashMap<>();


    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        if (target != null) {
            String methodName = method.getName();  //假如是onClick()方法
            method = methodmap.get(methodName); //如果找到了，就执行自定义的方法
            if (method !=null){
               return method.invoke(target,args);
            }
        }
        return null;
    }

    /**
     * 拦截的添加
     *
     * @param methodName 本应该执行的方法，拦截
     * @param method     执行自定义的方法
     */
    public void addMethod(String methodName, Method method) {
        methodmap.put(methodName, method);
    }
}
