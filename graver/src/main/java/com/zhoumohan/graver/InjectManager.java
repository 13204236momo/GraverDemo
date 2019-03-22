package com.zhoumohan.graver;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectManager {

    /**
     * activity注入
     *
     * @param activity
     */
    public static void inject(Activity activity) {

        injectLayout(activity);

        injectViews(activity);

        injectEvent(activity);
    }

    /**
     * 布局注入
     *
     * @param activity
     */
    private static void injectLayout(Activity activity) {
        Class<?> clazz = activity.getClass();
        ContentView annotation = clazz.getAnnotation(ContentView.class);
        if (annotation != null) {
            int layout = annotation.value();
            try {
                Method method = clazz.getMethod("setContentView", int.class);
                method.invoke(activity, layout);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 组件注入
     *
     * @param activity
     */
    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            BindView annotation = field.getAnnotation(BindView.class);
            if (annotation != null) {
                int viewId = annotation.value();
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, viewId);

                    //设置属性可以访问，若不设置，如果field为private会赋值失败
                    field.setAccessible(true);
                    //属性的值赋值给控件
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 事件注入
     *
     * @param activity
     */
    private static void injectEvent(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(OnClick.class);
            if (annotation != null) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if (eventBase != null) {
                        String listenerSetter = eventBase.listrnerSetter();
                        Class<?> listenerType = eventBase.listenerType();
                        String callBackListener = eventBase.callBackListener();
                        try {
                            //通过annotationType获取onClick注解的value值，拿到R.id.xx
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);
                            //拦截方法，执行自定义方法
                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            handler.addMethod(callBackListener,method);
                            //代理方式完成
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
                            for (int viewId : viewIds) {
                                View view = activity.findViewById(viewId);
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                setter.invoke(view, listener);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        }
    }

    /**
     * fragment注入
     */
    public static void inject(Fragment fragment) {

        injectLayout(fragment);

        injectViews(fragment);

        injectEvent(fragment);
    }


    private static void injectLayout(Fragment fragment) {
    }

    private static void injectViews(Fragment fragment) {
    }

    private static void injectEvent(Fragment fragment) {
    }


}
