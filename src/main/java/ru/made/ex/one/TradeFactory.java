package ru.made.ex.one;

class TradeFactory {
    static Trade getTrade(String tradeType, float price) {
        return TradeType.valueOf(tradeType).getInstance(price);
    }
}
