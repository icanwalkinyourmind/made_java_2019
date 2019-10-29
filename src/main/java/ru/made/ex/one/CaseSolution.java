package ru.made.ex.one;

import java.io.IOException;
import java.util.Scanner;

public class CaseSolution {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();
        FileTradeParser parser = new FileTradeParser(filePath);
        Trade trade;
        switch (parser.getTradeType()) {
            case "BOND":
                trade = new BondTrade(parser.getTradePrice());
                break;
            case "FX_SPOT":
                trade = new FxSpotTrade(parser.getTradePrice());
                break;
            case "COMMODITY_SPOT":
                trade = new CommoditySpotTrade(parser.getTradePrice());
                break;
            case "IR_SWAP":
                trade = new IrSwapTrade(parser.getTradePrice());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + parser.getTradeType());
        }
        System.out.println(trade);
    }
}
