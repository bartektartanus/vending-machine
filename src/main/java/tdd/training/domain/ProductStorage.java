package tdd.training.domain;

import java.util.Optional;

public interface ProductStorage {

    Optional<Product> productOnShelf(int shelfNumber);
    int itemsOnShelf(int shelfNumber);

    void loadOnShelf(int shelfNumber, Product productToLoad);
    Optional<Product> takeFromShelf(int shelfNumber);
    
    void clear();

}