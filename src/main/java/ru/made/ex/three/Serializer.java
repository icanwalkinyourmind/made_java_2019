package ru.made.ex.three;

import java.lang.reflect.InvocationTargetException;

public interface Serializer {
    String serialize(Object o) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;
}
