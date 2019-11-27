package ru.made.ex.three;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class SimpleSerializer implements Serializer {
    private final SerializationStrategy strategy;

    SimpleSerializer(SerializationStrategy strategy) {
        this.strategy = strategy;
    }

    private void appendWithIndent(StringBuilder builder, Integer indent, String s) {
        builder.append(" ".repeat(indent));
        builder.append(s);
    }

    private String trimSuffix(String s, Integer i, Integer nElements) {
        if (i.equals(nElements - 1)) {
            return strategy.trimLastElement(s);
        }
        return s;
    }

    private Integer increaseIndent(Integer indent) {
        return indent + 2;
    }

    private Integer decreaseIndent(Integer indent) {

        return indent - 2;
    }

    private boolean isObject(Object o) {
        Class<?> clazz = o.getClass();
        return !(clazz.isPrimitive() || o instanceof String || clazz.isArray() || Collection.class.isAssignableFrom(clazz));
    }

    private void serializeCollectionElement(StringBuilder builder, Integer indent, Object o, Integer i, Integer arrayLength) throws IllegalAccessException {
        appendWithIndent(builder, indent, strategy.getCollectionElementPrefix(i + 1));
        doSerialize(builder, indent, Array.get(o, i));
        builder.append(trimSuffix(strategy.getCollectionElementSuffix(i + 1), i, arrayLength));
    }

    private void serializeCollection(StringBuilder builder, Integer indent, Object o) throws IllegalAccessException {
        builder.append(strategy.getCollectionPrefix());
        indent = increaseIndent(indent);
        int arrayLength = Array.getLength(o);
        for (int i = 0; i < arrayLength; i++) {
            serializeCollectionElement(builder, indent, o, i, arrayLength);
        }
        indent = decreaseIndent(indent);
        appendWithIndent(builder, indent, strategy.getCollectionSuffix());
    }

    private void serializeFieldAsObject(StringBuilder builder, Integer indent, Object o, Field field, Integer i, Integer arrayLength) throws IllegalAccessException {
        appendWithIndent(builder, indent, strategy.getObjectPrefix(field.getName()));
        doSerialize(builder, indent, field.get(o));
        appendWithIndent(builder, indent, trimSuffix(strategy.getObjectSuffix(field.getName()), i, arrayLength));
    }

    private void serializeField(StringBuilder builder, Integer indent, Object o, Field field, Integer i, Integer arrayLength) throws IllegalAccessException {
        appendWithIndent(builder, indent, strategy.getFiledPrefix(field.getName()));
        doSerialize(builder, indent, field.get(o));
        builder.append(trimSuffix(strategy.getFieldSuffix(field.getName()), i, arrayLength));
    }


    private void serializeObject(StringBuilder builder, Integer indent, Object o, Class<?> clazz) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        int arrayLength = fields.length;
        for (int i = 0; i < arrayLength; i++) {
            fields[i].setAccessible(true);
            if (isObject(fields[i].get(o))) {
                serializeFieldAsObject(builder, indent, o, fields[i], i, arrayLength);
            } else {
                serializeField(builder, indent, o, fields[i], i, arrayLength);
            }
        }
    }

    private void doSerialize(StringBuilder builder, Integer indent, Object o) throws IllegalAccessException {
        Class<?> clazz = o.getClass();
        if (clazz.isPrimitive() || o instanceof String) {
            builder.append(strategy.getScalarValue(o.toString()));
        } else if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
            serializeCollection(builder, indent, o);
        } else {
            indent = increaseIndent(indent);
            serializeObject(builder, indent, o, clazz);
        }
    }

    @Override
    public String serialize(Object o) {
        StringBuilder builder = new StringBuilder();
        Integer indent = 0;
        builder.append(strategy.getFilePrefix(o.getClass().getName()));
        try {
            doSerialize(builder, indent, o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        builder.append(strategy.getFileSuffix(o.getClass().getName()));
        return builder.toString();
    }
}
