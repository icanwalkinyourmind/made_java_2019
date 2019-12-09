package ru.made.ex.three;

public interface SerializationStrategy {
    String getFilePrefix(String objectName);

    String getFileSuffix(String objectName);

    String getObjectPrefix(String objectName);

    String getObjectSuffix(String objectName);

    String getFiledPrefix(String fieldName);

    String getFieldSuffix(String fieldName);

    String getScalarValue(String scalar);

    String getCollectionPrefix();

    String getCollectionElementPrefix(Integer position);

    String getCollectionElementSuffix(Integer position);

    String getCollectionSuffix();

    String trimLastElement(String s);
}
