package ru.made.ex.three;

public class JsonSerializationStrategy implements SerializationStrategy {
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
        return "\"" + objectName + "\": {\n";
    }

    @Override
    public String getObjectSuffix(String objectName) {
        return "},\n";
    }

    @Override
    public String getFiledPrefix(String fieldName) {
        return "\"" + fieldName + "\": ";
    }

    @Override
    public String getFieldSuffix(String fieldName) {
        return ",\n";
    }

    @Override
    public String getScalarValue(String scalar) {
        return "\"" + scalar + "\"";
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
