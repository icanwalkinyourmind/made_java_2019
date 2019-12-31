package ru.made.ex.five.storage;

import ru.made.ex.five.Cache;

import java.util.HashMap;

public class InMemoryStorageStrategy implements StorageStrategy {
    private final HashMap<String, Object> storage = new HashMap<>();

    @Override
    public void put(String key, Object o, Cache annotation) {
        storage.put(key, o);
    }


    @Override
    public Object get(String key, Class<?> clazz) {
        return storage.get(key);
    }

    @Override
    public boolean contains(String key) {
        return storage.containsKey(key);
    }
}
