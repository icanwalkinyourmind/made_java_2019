package ru.made.ex.five.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Defaults implements InvocationHandler {
    public static <A extends Annotation> A of(Class<A> annotation) {
        return (A) Proxy.newProxyInstance(annotation.getClassLoader(),
                new Class[]{annotation}, new Defaults());
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        return method.getDefaultValue();
    }
}