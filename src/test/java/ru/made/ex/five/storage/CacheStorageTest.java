package ru.made.ex.five.storage;

import org.junit.Test;
import ru.made.ex.five.Cache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CacheStorageTest {
    private final List<Integer> list = Arrays.asList(1, 2, 3);
    private final CacheStorage storage = new CacheStorage();

    @Cache(maxListSize = 1)
    public List<Integer> constructList() {
        return list;
    }

    @Test
    public void listLimit() throws NoSuchMethodException, StorageException {
        Method m = CacheStorageTest.class.getMethod("constructList");
        Cache annotation = m.getAnnotation(Cache.class);
        String key = "test";
        storage.put(list, key, annotation);
        List<Integer> o = (List<Integer>) storage.get(key, annotation, List.class);
        assertEquals(1, o.size());
    }
}
