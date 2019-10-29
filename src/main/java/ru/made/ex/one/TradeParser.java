package ru.made.ex.one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class TradeParser {
    private static String tradeType;
    private static float tradePrice;
    private final String tradeFilePath;

    TradeParser(String filePath) throws IOException {
        this.tradeFilePath = filePath;
        this.parseFile();
    }

    private static String getTrim(String input, String subString) {
        return input.replaceAll(subString, "").replaceFirst(".$", "").trim();
    }

    String getTradeType() {
        return tradeType;
    }

    float getTradePrice() {
        return tradePrice;
    }

    private void parsePrice(String input) {
        tradePrice = Float.parseFloat(getTrim(input, "price:"));
    }

    private void parseType(String input) {
        tradeType = getTrim(input, "type:");
    }

    private void parseFile() throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader(this.tradeFilePath));
        String nextString;
        while ((nextString = buffer.readLine()) != null) {
            if (nextString.matches(".*type:.*")) {
                parseType(nextString);
            } else if (nextString.matches(".*price:.*")) {
                parsePrice(nextString);
            }
        }
    }
}
