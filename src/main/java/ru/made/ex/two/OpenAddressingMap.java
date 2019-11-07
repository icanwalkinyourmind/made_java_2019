package ru.made.ex.two;

import java.util.*;


@SuppressWarnings(value = "unchecked")
public class OpenAddressingMap<K, V> implements SimpleMap<K, V> {
    private int tableSize = 4;
    private int tableCapacity = 0;
    private Node<K, V>[] table = new Node[tableSize];

    private int dealWithCollision(int index) {
        if (index == tableSize - 1) return 0;
        return index + 1;
    }

    private int getKeyPosition(K key) {
        int index = key.hashCode() % tableSize;
        while (table[index] != null && !table[index].getKey().equals(key)) {
            index = dealWithCollision(index);
        }
        return index;
    }

    private Node<K, V>[] tableNodes() {
        Node<K, V>[] nodes = new Node[tableCapacity];
        int j = 0;
        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
                nodes[j] = table[i];
                ++j;
            }
        }
        return nodes;
    }

    private void extendTable() {
        Node<K, V>[] nodes = tableNodes();
        tableSize = tableSize * 2;
        table = new Node[tableSize];
        for (Node<K, V> node : nodes) {
            if (node.isAvailable()) {
                table[getKeyPosition(node.getKey())] = node;
            }
        }
    }

    @Override
    public void put(K key, V value) {
        if ((double) tableCapacity / (double) tableSize > 0.5) extendTable();
        table[getKeyPosition(key)] = new Node<>(key, value);
        ++tableCapacity;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = table[getKeyPosition(key)];
        if (node != null && node.isAvailable()) {
            return node.getValue();
        } else {
            return null;
        }
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        if (value != null) {
            table[getKeyPosition(key)].markDeleted();
        }
        --tableCapacity;
        return value;
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        return tableCapacity;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        for (Node<K, V> node : tableNodes()) {
            keys.add(node.getKey());
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for (Node<K, V> node : tableNodes()) {
            values.add(node.getValue());
        }
        return values;
    }

    private static class Node<K, V> {
        private final K key;
        private final V value;
        private boolean deleted = false;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K getKey() {
            return this.key;
        }

        V getValue() {
            return this.value;
        }

        void markDeleted() {
            deleted = true;
        }

        boolean isAvailable() {
            return !deleted;
        }
    }
}
