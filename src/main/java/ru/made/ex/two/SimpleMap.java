package ru.made.ex.two;


import java.util.Collection;
import java.util.Set;

public interface SimpleMap<K, V> {
    void put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean contains(K key);

    int size();

    Set<K> keySet();

    Collection<V> values();
}