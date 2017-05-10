package tdd.training.domain;

import java.util.Optional;

public enum Coin {

    FIVE_DOLLAR,
    TWO_DOLLAR,
    ONE_DOLLAR,
    FIFTY_CENTS,
    TWENTY_CENTS,
    TEN_CENTS;

    public static Optional<Coin> fromString(String name) {
        for (Coin coin : values()) {
            if (coin.name().equals(name)) {
                return Optional.of(coin);
            }
        }
        return Optional.empty();
    }
}
