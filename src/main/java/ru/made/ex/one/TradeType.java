package ru.made.ex.one;

public enum TradeType {
    BOND {
        @Override
        public Trade getInstance(double price) {
            return new BondTrade(price);
        }
    },
    FX_SPOT {
        @Override
        public Trade getInstance(double price) {
            return new FxSpotTrade(price);
        }
    },
    COMMODITY_SPOT {
        @Override
        public Trade getInstance(double price) {
            return new CommoditySpotTrade(price);
        }
    },
    IR_SWAP {
        @Override
        public Trade getInstance(double price) {
            return new IrSwapTrade(price);
        }
    };
    public abstract Trade getInstance(double price);
}
