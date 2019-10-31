package ru.made.ex.one;

class BaseTradeParser {
    private String tradeType;
    private double tradePrice;

    private String getTrim(String input, String subString) {
        return input.replaceAll(subString, "").replaceFirst(",\\s+$", "").trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public double getTradePrice() {
        return tradePrice;
    }

    protected void parsePrice(String input) {
        tradePrice = Float.parseFloat(getTrim(input, "price:"));
    }

    protected void parseType(String input) {
        tradeType = getTrim(input, "type:");
    }
}
