package ru.made.ex.one;

class BaseTradeParser {
    private static String tradeType;
    private static float tradePrice;

    private static String getTrim(String input, String subString) {
        return input.replaceAll(subString, "").replaceFirst(".$", "").trim();
    }

    String getTradeType() {
        return tradeType;
    }

    float getTradePrice() {
        return tradePrice;
    }

    void parsePrice(String input) {
        tradePrice = Float.parseFloat(getTrim(input, "price:"));
    }

    void parseType(String input) {
        tradeType = getTrim(input, "type:");
    }
}
