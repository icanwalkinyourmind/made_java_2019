package ru.made.ex.one;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BaseTradeTest {
    private Trade trade = new BaseTrade(10.0);

    @Test
    public void testToString() {
        assertEquals("ru.made.ex.one.BaseTrade 10.0", trade.toString());
    }
}
