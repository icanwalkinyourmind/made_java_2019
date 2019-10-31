package ru.made.ex.one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class FileTradeParser extends BaseTradeParser {
    private final String tradeFilePath;

    FileTradeParser(String filePath) {
        this.tradeFilePath = filePath;
    }

    public void parseFile() throws IOException {
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
