package tdd.training.domain;

import tdd.training.domain.CoinDispenser.ChangeCannotBeReturnedException;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

    private final ProductStorage storage;
    private final CoinDispenser coinDispenser;
    private final ProductFeeder productFeeder;

    private List<Coin> inserted = new ArrayList<Coin>();
    private int selectedShelf;
    private String display = "";

    public VendingMachine(ProductStorage storage, CoinDispenser coinDispenser, ProductFeeder productFeeder) {
        this.storage = storage;
        this.coinDispenser = coinDispenser;
        this.productFeeder = productFeeder;
    }

    public String getDisplay() {

        return display;
    }

    private void updateDisplayedPrice() {
    
        if (selectedProduct() == Product.NO_PRODUCT) {
            updateDisplay("");
            return;
        }
        updateDisplay(String.format("%s: %s PLN", selectedProduct().toString(), remainingAmmount()));
    }

    private Price remainingAmmount() {

        Price remainingPrice = selectedProduct().getPrice();
        return remainingPrice.minus(insertedAmmount());
    }

    private Price insertedAmmount() {

        Price instertedAmmount = Price.of("0,00");

        for (Coin coin : inserted) {
            instertedAmmount = instertedAmmount.plus(coin.asPrice());
        }

        return instertedAmmount;
    }

    public void select(int shelfNumber) {

        selectedShelf = shelfNumber;
        updateDisplayedPrice();
    }

    public void insert(Coin money) {

        inserted.add(money);
        
        if (fullyPaid()) {
            giveProductAndReturnChange();
        } else{
            updateDisplayedPrice();
        }
    }

    private void giveProductAndReturnChange() {

        try {
        
            coinDispenser.accept(inserted.toArray(new Coin[0]));
            if (remainingAmmount().asCents() < 0) {
                coinDispenser.giveBack(Price.fromCents(-remainingAmmount().asCents()));
            }
            storage.takeFromShelf(selectedShelf);
            productFeeder.release(selectedProduct());
            clearSelection();
            updateDisplayedPrice();
            
        } catch (ChangeCannotBeReturnedException e) {
            coinDispenser.giveBack(insertedAmmount());
            updateDisplay("No change!");
        }
        inserted.clear();
    }

    private boolean fullyPaid() {
        return remainingAmmount().asCents() <= 0;
    }


    private void clearSelection() {
        selectedShelf = -1;
    }

    private void updateDisplay(String display) {
        this.display = display;
    }

    private Product selectedProduct() {
        return storage.productOnShelf(selectedShelf).orElse(Product.NO_PRODUCT);
    }

    public void cancel() {
        coinDispenser.accept(inserted.toArray(new Coin[0]));
        coinDispenser.giveBack(insertedAmmount());
        inserted.clear();
        clearSelection();
        updateDisplayedPrice();
    }
}
