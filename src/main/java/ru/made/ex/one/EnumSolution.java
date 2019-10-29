package ru.made.ex.one;

import java.io.IOException;
import java.util.Scanner;

public class EnumSolution {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();
        FileTradeParser parser = new FileTradeParser(filePath);
        Trade trade = TradeFactory.getTrade(parser.getTradeType(), parser.getTradePrice());
        System.out.println(trade);
    }
}
