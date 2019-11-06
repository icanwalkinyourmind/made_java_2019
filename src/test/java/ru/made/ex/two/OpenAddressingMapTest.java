package ru.made.ex.two;

import com.sun.tools.javac.util.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class OpenAddressingMapTest {
    @SuppressWarnings(value = "unchecked") // Как этого избежать?
    private static Pair<String, Integer>[] testValuesPairs = new Pair[]{
            Pair.of("one", 1),
            Pair.of("two", 2),
            Pair.of("three", 3),
            Pair.of("four", 4),
            Pair.of("five", 5),
    };

    private OpenAddressingMap<String, Integer> initMap() {
        OpenAddressingMap<String, Integer> map = new OpenAddressingMap<>();
        for (Pair<String, Integer> pair : testValuesPairs) {
            map.put(pair.fst, pair.snd);
        }
        return map;
    }

    @Test
    public void putAndGet() {
        OpenAddressingMap<String, Integer> map = initMap();
        for (Pair<String, Integer> pair : testValuesPairs) {
            assertEquals(pair.snd, map.get(pair.fst));
        }
        map.put("one", 5);
        assertEquals((Integer) 5, map.get("one"));
    }

    @Test
    public void remove() {
        OpenAddressingMap<String, Integer> map = initMap();
        for (Pair<String, Integer> pair : testValuesPairs) {
            Integer value = map.remove(pair.fst);
            assertEquals(pair.snd, value);
            assertNull(map.get(pair.fst));
        }
    }

    @Test
    public void contains() {
        OpenAddressingMap<String, Integer> map = initMap();
        for (Pair<String, Integer> pair : testValuesPairs) {
            assertTrue(map.contains(pair.fst));
            assertFalse(map.contains(pair.snd.toString()));
        }
    }

    @Test
    public void size() {
        OpenAddressingMap<String, Integer> map = initMap();
        int size = 5;
        for (Pair<String, Integer> pair : testValuesPairs) {
            map.remove(pair.fst);
            assertEquals(--size, map.size());
        }
    }

    @Test
    public void keySet() {
        OpenAddressingMap<String, Integer> map = initMap();
        HashSet<String> keys = new HashSet<>();
        for (Pair<String, Integer> pair : testValuesPairs) {
            keys.add(pair.fst);
        }
        assertEquals(keys, map.keySet());
    }

    @Test
    public void values() {
        OpenAddressingMap<String, Integer> map = initMap();
        ArrayList<Integer> values = new ArrayList<>();
        for (Pair<String, Integer> pair : testValuesPairs) {
            values.add(pair.snd);
        }
        assertArrayEquals(values.toArray(), map.values().stream().sorted().toArray());
    }
}
