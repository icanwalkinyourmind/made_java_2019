package ru.made.ex.three;

public class JsonSerializationStrategy implements SerializationStrategy {
    private String addBracers(String s) {
        return "\"" + s + "\"";
    }

    @Override
    public String getFilePrefix(String objectName) {
        return "{\n";
    }

    @Override
    public String getFileSuffix(String objectName) {
        return "}\n";
    }

    @Override
    public String getObjectPrefix(String objectName) {
        return addBracers(objectName) + ": {\n";
    }

    @Override
    public String getObjectSuffix(String objectName) {
        return "},\n";
    }

    @Override
    public String getFiledPrefix(String fieldName) {
        return addBracers(fieldName) + ": ";
    }

    @Override
    public String getFieldSuffix(String fieldName) {
        return ",\n";
    }

    @Override
    public String getScalarValue(String scalar) {
        return addBracers(scalar);
    }

    @Override
    public String getCollectionPrefix() {
        return "[\n";
    }

    @Override
    public String getCollectionElementPrefix(Integer position) {
        return "";
    }

    @Override
    public String getCollectionElementSuffix(Integer position) {
        return ",\n";
    }


    @Override
    public String getCollectionSuffix() {
        return "]";
    }

    @Override
    public String trimLastElement(String s) {
        return s.replace(",", "");
    }
}
