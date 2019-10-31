package ru.made.ex.one;

class EnumTradeFactory implements TradeFactory {
    public Trade getTrade(String tradeType, double price) {
        return TradeType.valueOf(tradeType).getInstance(price);
    }
}
