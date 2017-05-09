package tdd.training.domain;

import java.util.*;

public class MapProductStorage implements ProductStorage{

    private Map<Integer, List<Product>> storage = new HashMap<>();

    @Override
    public Optional<Product> productOnShelf(int shelfNumber) {
        List<Product> list = storage.getOrDefault(shelfNumber, Collections.emptyList());
        return list.stream().findFirst();
    }

    @Override
    public int itemsOnShelf(int shelfNumber) {
        return storage.getOrDefault(shelfNumber, Collections.emptyList()).size();
    }

    @Override
    public void loadOnShelf(int shelfNumber, Product productToLoad) {
        if(!storage.containsKey(shelfNumber)){
            storage.put(shelfNumber, new ArrayList<>());
        }
        storage.get(shelfNumber).add(productToLoad);
    }

    @Override
    public Optional<Product> takeFromShelf(int shelfNumber) {
        List<Product> productList = storage.get(shelfNumber);
        if(productList != null && productList.size() > 0){
            return Optional.of(productList.remove(0));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public void clear() {
        this.storage = new HashMap<>();
    }
}
