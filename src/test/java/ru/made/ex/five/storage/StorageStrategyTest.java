package ru.made.ex.five.storage;


import org.junit.Test;
import ru.made.ex.five.Cache;
import ru.made.ex.five.utils.Defaults;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


public class StorageStrategyTest {
    private final Integer[] arr = new Integer[]{1, 2, 3};
    private final String key = "test";


    private StorageStrategy getDiskStorageStrategy(Cache annotation) throws StorageException {
        StorageStrategy strategy = new DiskStorageStrategy("/tmp/");
        strategy.put(key, arr, annotation);
        return strategy;
    }

    private ArrayList<StorageStrategy> createStrategies() throws StorageException {
        ArrayList<StorageStrategy> storageStrategies = new ArrayList<>();
        Cache annotation = Defaults.of(Cache.class);

        StorageStrategy strategy = getDiskStorageStrategy(annotation);
        storageStrategies.add(strategy);

        strategy = new InMemoryStorageStrategy();
        strategy.put(key, arr, annotation);

        storageStrategies.add(strategy);

        return storageStrategies;
    }

    @Test
    public void get() throws StorageException {
        for (StorageStrategy strategy : createStrategies()) {
            assertArrayEquals(arr, (Integer[]) strategy.get(key, Integer[].class));
        }

    }

    @Test
    public void contains() throws StorageException {
        for (StorageStrategy strategy : createStrategies()) {
            assertTrue(strategy.contains(key));
        }
    }

    @Cache(compress = true)
    public void withCompress() {
    }

    @Test
    public void compress() throws NoSuchMethodException, StorageException {
        Method m = StorageStrategyTest.class.getMethod("withCompress");
        Cache annotation = m.getAnnotation(Cache.class);
        StorageStrategy strategy = getDiskStorageStrategy(annotation);
        assertArrayEquals(arr, (Integer[]) strategy.get(key, Integer[].class));
    }

}
