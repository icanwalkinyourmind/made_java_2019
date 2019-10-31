package ru.made.ex.one;

public class BaseTrade implements Trade {
    private final double price;

    BaseTrade(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " " + this.price;
    }
}
