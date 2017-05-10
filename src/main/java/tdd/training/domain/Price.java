package tdd.training.domain;

public class Price {

    private int cents;

    private Price(int cents) {
        this.cents = cents;
    }

    public static Price fromCents(int i) {
        return new Price(i);
    }

    @Override
    public String toString() {
        return cents/100 + "," + String.format("%02d", cents % 100) + "$";
    }
}
