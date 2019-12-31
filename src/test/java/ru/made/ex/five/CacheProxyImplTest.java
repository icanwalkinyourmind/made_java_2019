package ru.made.ex.five;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

interface DoSomething {
    @Cache
    String doSomething(String s, Integer i);
}

class StupidClazz implements DoSomething {
    public String doSomething(String s, Integer i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s + i;
    }
}

public class CacheProxyImplTest {

    @Test
    public void cache() {
        CacheProxy cacheProxy = new CacheProxyImpl();
        StupidClazz test = new StupidClazz();
        DoSomething testClass = (DoSomething) cacheProxy.cache(test);
        long start = System.currentTimeMillis();
        testClass.doSomething("hello", 15);
        long beforeCache = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();
        testClass.doSomething("hello", 15);
        long afterCache = System.currentTimeMillis() - start;
        assertTrue((beforeCache - afterCache) >= 1000);
    }
}
