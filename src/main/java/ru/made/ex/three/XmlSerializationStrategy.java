package ru.made.ex.three;

public class XmlSerializationStrategy implements SerializationStrategy {
    private String getOpenTag(String s) {
        return "<" + s + ">";
    }

    private String getCloseTag(String s) {
        return "</" + s + ">\n";
    }

    private String trimPackagePath(String packageName) {
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

    @Override
    public String getObjectPrefix(String objectName) {
        return getOpenTag(objectName) + "\n";
    }

    @Override
    public String getObjectSuffix(String objectName) {
        return getCloseTag(objectName);
    }

    @Override
    public String getFilePrefix(String objectName) {
        return getObjectPrefix(trimPackagePath(objectName));
    }

    @Override
    public String getFileSuffix(String objectName) {
        return getObjectSuffix(trimPackagePath(objectName));
    }

    @Override
    public String getFiledPrefix(String fieldName) {
        return getOpenTag(fieldName);
    }

    @Override
    public String getFieldSuffix(String fieldName) {
        return getCloseTag(fieldName);
    }

    @Override
    public String getScalarValue(String scalar) {
        return scalar;
    }

    @Override
    public String getCollectionPrefix() {
        return "\n";
    }

    @Override
    public String getCollectionElementPrefix(Integer position) {
        return getOpenTag(position.toString());
    }

    @Override
    public String getCollectionElementSuffix(Integer position) {
        return getCloseTag(position.toString());
    }


    @Override
    public String getCollectionSuffix() {
        return "";
    }

    @Override
    public String trimLastElement(String s) {
        return s;
    }
}
