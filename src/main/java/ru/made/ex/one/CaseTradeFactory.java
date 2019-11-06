package ru.made.ex.one;

class CaseTradeFactory implements TradeFactory {
    public Trade getTrade(String tradeType, double price) {
        Trade trade;
        switch (tradeType) {
            case "BOND":
                trade = new BondTrade(price);
                break;
            case "FX_SPOT":
                trade = new FxSpotTrade(price);
                break;
            case "COMMODITY_SPOT":
                trade = new CommoditySpotTrade(price);
                break;
            case "IR_SWAP":
                trade = new IrSwapTrade(price);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tradeType);
        }
        return trade;
    }
}
