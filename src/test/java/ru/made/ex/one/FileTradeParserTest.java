package ru.made.ex.one;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileTradeParserTest {
    private FileTradeParser parser = new FileTradeParser("src/test/resources/bond_trade.txt");
    private String tradeType = "BOND";
    private double tradePrice = 1533.32;

    @Test
    public void testParseFile() throws IOException {
        parser.parseFile();
        assertEquals(tradeType, parser.getTradeType());
        assertEquals(tradePrice, parser.getTradePrice(), 0.001);
    }
}
