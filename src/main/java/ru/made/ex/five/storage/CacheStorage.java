package ru.made.ex.five.storage;

import ru.made.ex.five.Cache;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class CacheStorage {
    private final HashMap<StorageType, StorageStrategy> cachePolicies = new HashMap<>();


    private void initialize(String root_dir) {
        cachePolicies.put(StorageType.IN_MEMORY, new InMemoryStorageStrategy());
        cachePolicies.put(StorageType.FILE, new DiskStorageStrategy(root_dir));
    }

    public CacheStorage(String root_dir) {
        initialize(root_dir);
    }

    public CacheStorage() {
        initialize(DiskStorageStrategy.DEFAULT_ROOT_DIR);
    }

    private StorageStrategy policyByType(Cache annotation) {
        return cachePolicies.get(annotation.storageType());
    }


    private Object processList(Object o, Cache annotation) {
        List<?> sl = ((List<?>) o);
        return sl.stream().limit(annotation.maxListSize()).collect(Collectors.toList());
    }


    public void put(Object o, String key, Cache annotation) {
        if (o instanceof List) {
            o = processList(o, annotation);
        }
        try {
            policyByType(annotation).put(key, o, annotation);
        } catch (StorageException e) {
            e.printStackTrace(); // ToDo make it right
        }
    }


    public Object get(String key, Cache annotation, Class<?> clazz) throws StorageException {
        return policyByType(annotation).get(key, clazz);
    }

    public boolean contains(String key, Cache annotation) {
        return policyByType(annotation).contains(key);
    }

}
