package ru.made.ex.three;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class SimpleSerializer implements Serializer {
    private SerializationStrategy strategy;
    private StringBuilder builder;
    private Integer indent;

    SimpleSerializer(SerializationStrategy strategy) {
        this.strategy = strategy;
        builder = new StringBuilder();
        indent = 0;
    }

    private void appendWithIndent(String s) {
        builder.append(" ".repeat(indent));
        builder.append(s);
    }

    private String trimSuffix(String s, Integer i, Integer n_elements) {
        if (i.equals(n_elements - 1)) {
            return strategy.trimLastElement(s);
        }
        return s;
    }

    private void increaseIndent() {
        indent += 2;
    }

    private void decreaseIndent() {
        indent -= 2;
    }

    private boolean isObject(Object o) {
        Class<?> clazz = o.getClass();
        return !(clazz.isPrimitive() || o instanceof String || clazz.isArray() || Collection.class.isAssignableFrom(clazz));
    }

    private void _serialize(Object o) throws IllegalAccessException {
        Class<?> clazz = o.getClass();
        if (clazz.isPrimitive() || o instanceof String) {
            builder.append(strategy.getScalarValue(o.toString()));
        } else if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
            builder.append(strategy.getCollectionPrefix());
            increaseIndent();
            int array_length = Array.getLength(o);
            for (int i = 0; i < array_length; i++) {
                appendWithIndent(strategy.getCollectionElementPrefix(i + 1));
                _serialize(Array.get(o, i));
                builder.append(trimSuffix(strategy.getCollectionElementSuffix(i + 1), i, array_length));
            }
            decreaseIndent();
            appendWithIndent(strategy.getCollectionSuffix());
        } else {
            increaseIndent();
            Field[] fields = clazz.getDeclaredFields();
            int array_length = fields.length;
            for (int i = 0; i < array_length; i++) {
                fields[i].setAccessible(true);
                if (isObject(fields[i].get(o))) {
                    appendWithIndent(strategy.getObjectPrefix(fields[i].getName()));
                    _serialize(fields[i].get(o));
                    appendWithIndent(trimSuffix(strategy.getObjectSuffix(fields[i].getName()), i, array_length));
                } else {
                    appendWithIndent(strategy.getFiledPrefix(fields[i].getName()));
                    _serialize(fields[i].get(o));
                    builder.append(trimSuffix(strategy.getFieldSuffix(fields[i].getName()), i, array_length));
                }
            }
            decreaseIndent();
        }
    }

    @Override
    public String serialize(Object o) throws IllegalAccessException {
        builder = new StringBuilder();
        appendWithIndent(strategy.getFilePrefix(o.getClass().getName()));
        _serialize(o);
        appendWithIndent(strategy.getFileSuffix(o.getClass().getName()));
        return builder.toString();
    }
}
