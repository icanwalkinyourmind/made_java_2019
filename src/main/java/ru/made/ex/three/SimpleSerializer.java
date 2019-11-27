package ru.made.ex.three;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class SimpleSerializer implements Serializer {
    private final SerializationStrategy strategy;
    private int indent;

    SimpleSerializer(SerializationStrategy strategy) {
        this.strategy = strategy;
        indent = 0;
    }

    private void appendWithIndent(StringBuilder builder, String s) {
        builder.append(" ".repeat(indent));
        builder.append(s);
    }

    private String trimSuffix(String s, Integer i, Integer nElements) {
        if (i.equals(nElements - 1)) {
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

    private void serializeCollectionElement(StringBuilder builder, Object o, Integer i, Integer arrayLength) throws IllegalAccessException {
        appendWithIndent(builder, strategy.getCollectionElementPrefix(i + 1));
        doSerialize(builder, Array.get(o, i));
        builder.append(trimSuffix(strategy.getCollectionElementSuffix(i + 1), i, arrayLength));
    }

    private void serializeCollection(StringBuilder builder, Object o) throws IllegalAccessException {
        builder.append(strategy.getCollectionPrefix());
        increaseIndent();
        int arrayLength = Array.getLength(o);
        for (int i = 0; i < arrayLength; i++) {
            serializeCollectionElement(builder, o, i, arrayLength);
        }
        decreaseIndent();
        appendWithIndent(builder, strategy.getCollectionSuffix());
    }

    private void serializeFieldAsObject(StringBuilder builder, Object o, Field field, String s) throws IllegalAccessException {
        appendWithIndent(builder, strategy.getObjectPrefix(field.getName()));
        doSerialize(builder, field.get(o));
        appendWithIndent(builder, s);
    }

    private void serializeField(StringBuilder builder, Object o, Field field, String str) throws IllegalAccessException {
        appendWithIndent(builder, strategy.getFiledPrefix(field.getName()));
        doSerialize(builder, field.get(o));
        builder.append(str);
    }


    private void serializeObject(StringBuilder builder, Object o, Class<?> clazz) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        int arrayLength = fields.length;
        for (int i = 0; i < arrayLength; i++) {
            fields[i].setAccessible(true);
            if (isObject(fields[i].get(o))) {
                serializeFieldAsObject(builder, o, fields[i], trimSuffix(strategy.getObjectSuffix(fields[i].getName()), i, arrayLength));
            } else {
                serializeField(builder, o, fields[i], trimSuffix(strategy.getFieldSuffix(fields[i].getName()), i, arrayLength));
            }
        }
    }

    private void doSerialize(StringBuilder builder, Object o) throws IllegalAccessException {
        Class<?> clazz = o.getClass();
        if (clazz.isPrimitive() || o instanceof String) {
            builder.append(strategy.getScalarValue(o.toString()));
        } else if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
            serializeCollection(builder, o);
        } else {
            increaseIndent();
            serializeObject(builder, o, clazz);
            decreaseIndent();
        }
    }

    @Override
    public String serialize(Object o) {
        StringBuilder builder = new StringBuilder();
        appendWithIndent(builder, strategy.getFilePrefix(o.getClass().getName()));
        try {
            doSerialize(builder, o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        appendWithIndent(builder, strategy.getFileSuffix(o.getClass().getName()));
        return builder.toString();
    }
}
