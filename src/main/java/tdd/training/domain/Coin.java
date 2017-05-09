package tdd.training.domain;

import java.util.Optional;

public enum Coin {
    
    FIVE_DOLLAR(Price.of("5,00")), 
    TWO_DOLLAR(Price.of("2,00")), 
    ONE_DOLLAR(Price.of("1,00")), 
    FIFTY_CENTS(Price.of("0,50")), 
    TWENTY_CENTS(Price.of("0,20")), 
    TEN_CENTS(Price.of("0,10"));

    private final Price price;

    private Coin(Price price) {
        this.price = price;
    }

    public static Optional<Coin> fromString(String name){
        for (Coin coin : values()) {
            if(coin.name().equals(name)){
                return Optional.of(coin);
            }
        }
        return Optional.empty();
    }

    public String getPrice(){
        return price.toString();
    }
    
    public Price asPrice() {
        return price;
    }
}
