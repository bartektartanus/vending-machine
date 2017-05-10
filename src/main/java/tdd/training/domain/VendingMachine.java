package tdd.training.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VendingMachine {

    private Map<Integer, Product> shelfToProduct = new HashMap<>();
    private Map<Integer, Integer> productCounter = new HashMap<>();
    private String display = "";

    public String getDisplay() {
        return display;
    }

    public void pushButton(int i) {
        this.display = Optional.ofNullable(shelfToProduct.get(i)).map(p -> p.toString()).orElse("BRAK PRODUKTU");
    }



    public void putOnShelf(int i, Product product, int count) {
        shelfToProduct.put(i, product);
        productCounter.put(i, count);
    }

    public void insertCoin(String coinString) {
        Optional<Coin> coin = Coin.fromString(coinString);

    }
}
