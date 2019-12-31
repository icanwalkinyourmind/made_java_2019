package ru.made.ex.five.storage;

import ru.made.ex.five.Cache;

public interface StorageStrategy {
    void put(String key, Object o, Cache annotation) throws StorageException;

    Object get(String key, Class<?> clazz) throws StorageException;

    boolean contains(String key);
}
