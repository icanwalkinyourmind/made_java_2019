package ru.made.ex.one;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TradeFactoryTest {
    private Trade trade = new BondTrade(10.0);
    private TradeFactory[] factories = {new EnumTradeFactory(), new CaseTradeFactory()};

    @Test
    public void testGetTrade() {
        for (TradeFactory factory : factories) {
            Trade trade = factory.getTrade("BOND", 10.0);
            assert trade instanceof BondTrade;
            assertEquals(this.trade.getPrice(), trade.getPrice(), 0.0001d);
        }
    }
}
