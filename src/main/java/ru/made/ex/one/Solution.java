package ru.made.ex.one;

import java.io.IOException;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();
        FileTradeParser parser = new FileTradeParser(filePath);
        parser.parseFile();
        TradeFactory enumFactory = new EnumTradeFactory();
        TradeFactory caseFactory = new CaseTradeFactory();
        Trade tradeCase = enumFactory.getTrade(parser.getTradeType(), parser.getTradePrice());
        Trade tradeEnum = caseFactory.getTrade(parser.getTradeType(), parser.getTradePrice());
        System.out.println(tradeCase);
        System.out.println(tradeEnum);
    }
}
