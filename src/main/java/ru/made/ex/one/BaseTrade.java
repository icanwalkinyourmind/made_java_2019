package ru.made.ex.one;

public class BaseTrade implements Trade {
    private final Float price;

    BaseTrade(Float price) {
        this.price = price;
    }

    @Override
    public Float getPrice() {
        return this.price;
    }
}
