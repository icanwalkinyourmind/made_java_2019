package ru.made.ex.one;

class TradeFactory {
    static Trade getTrade(String tradeType, double price) {
        return TradeType.valueOf(tradeType).getInstance(price);
    }
}
