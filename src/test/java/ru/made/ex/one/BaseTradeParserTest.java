package ru.made.ex.one;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BaseTradeParserTest {
    private BaseTradeParser parser = new BaseTradeParser();

    @Test
    public void parsePrice() {
        String priceString = "    price: 1623.23,   ";
        double priceAnswer = 1623.23;
        parser.parsePrice(priceString);
        assertEquals(priceAnswer, parser.getTradePrice(), 0.0001f);
    }

    @Test
    public void parseType() {
        String typeString = "    type: BOND,   ";
        String typeAnswer = "BOND";

        parser.parseType(typeString);
        assertEquals(typeAnswer, parser.getTradeType());
    }
}
