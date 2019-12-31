package ru.made.ex.five;

import ru.made.ex.five.storage.CacheStorage;
import ru.made.ex.five.storage.DiskStorageStrategy;
import ru.made.ex.five.utils.Defaults;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;


class CacheException extends Exception {
}

class CacheHandler implements InvocationHandler {
    private final CacheStorage storage;
    private final Object delegate;

    public CacheHandler(Object delegate, CacheStorage storage) {
        this.storage = storage;
        this.delegate = delegate;
    }

    private String getPrefix(Method m, Cache annotaion) {
        if (!annotaion.keyPrefix().equals("")) {
            return annotaion.keyPrefix();
        } else {
            return delegate.getClass().getName() + '.' + m.getName();
        }
    }

    private int getPartialHashCode(Object[] objects, Cache annotation) throws CacheException {
        int current = 0;
        List<Object> toHashCode = new ArrayList<>();
        for (Class<?> clazz : annotation.storeBy()) {
            for (int i = current; i < objects.length; i++) {
                if (clazz.equals(objects[i].getClass())) {
                    toHashCode.add(objects[i]);
                    current++;
                    break;
                }
            }
        }
        if (toHashCode.size() != objects.length) {
            throw new CacheException();
        }
        return toHashCode.hashCode();
    }

    private int getHashCode(Object[] objects, Cache annotation) throws CacheException {
        if (annotation.storeBy().length != 0) {
            return getPartialHashCode(objects, annotation);
        } else {
            return Arrays.hashCode(objects);
        }
    }

    private String getCallKey(Method m, Object[] objects, Cache annotation) throws CacheException {
        return getPrefix(m, annotation) + '_' + getHashCode(objects, annotation);
    }


    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        try {
            if (method.isAnnotationPresent(Cache.class)) {
                Cache cacheAnnotation = method.getAnnotation(Cache.class);
                String callKey = getCallKey(method, objects, cacheAnnotation);
                if (!storage.contains(callKey, cacheAnnotation)) {
                    storage.put(method.invoke(delegate, objects), callKey, cacheAnnotation);
                }
                return storage.get(callKey, cacheAnnotation, method.getReturnType());
            } else {
                return method.invoke(delegate, objects);
            }
        } catch (InvocationTargetException ite) {
            throw ite.getCause();
        }


    }
}

public class CacheProxyImpl implements CacheProxy {
    private final String root_dir;

    public CacheProxyImpl(String root_dir) {
        this.root_dir = root_dir;

    }

    public CacheProxyImpl() {
        root_dir = DiskStorageStrategy.DEFAULT_ROOT_DIR;
    }

    @Override
    public Object cache(Object delegate) {
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(),
                new CacheHandler(delegate, new CacheStorage(root_dir)));
    }


}
