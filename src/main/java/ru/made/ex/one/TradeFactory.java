package ru.made.ex.one;

public interface TradeFactory {
    Trade getTrade(String tradeType, double price);
}
