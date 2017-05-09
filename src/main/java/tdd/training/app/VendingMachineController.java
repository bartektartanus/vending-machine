package tdd.training.app;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tdd.training.domain.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class VendingMachineController {

    private static final int MAX_SHELF_NUMBER = 9;

    private final VendingMachine vendingMachine;
    private final ProductStorage productStorage;

    @RequestMapping("/")
    public String index() {
        return "redirect:vending-machine";
    }

    @RequestMapping("/vending-machine")
    public ModelAndView page() {
        ModelAndView model = new ModelAndView("vending-machine");
        model.getModelMap().put("coins", Coin.values());
        return model;
    }


    @Bean
    public static ProductStorage sample() {
        ProductStorage storage = new MapProductStorage();
        storage.loadOnShelf(1, new Product("Woda", Price.fromCents(100)));
        storage.loadOnShelf(1, new Product("Woda", Price.fromCents(100)));
        storage.loadOnShelf(1, new Product("Cola", Price.fromCents(250)));
        storage.loadOnShelf(2, new Product("Cola", Price.fromCents(250)));
        storage.loadOnShelf(2, new Product("Cola", Price.fromCents(250)));
        storage.loadOnShelf(2, new Product("Baton", Price.fromCents(160)));
        storage.loadOnShelf(3, new Product("Baton", Price.fromCents(160)));
        storage.loadOnShelf(3, new Product("Baton", Price.fromCents(160)));
        return storage;
    }

    @Bean
    @Autowired
    public static VendingMachine vendingMachine(ProductStorage productStorage) {
        return new VendingMachine(productStorage, new CoinDispenser(), new ProductFeeder());
    }

    @Autowired
    public VendingMachineController(VendingMachine vendingMachine, ProductStorage productStorage) {
        this.vendingMachine = vendingMachine;
        this.productStorage = productStorage;
    }

    @RequestMapping(value = "/vending-machine/push",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public @ResponseBody
    Response selectProduct(@RequestBody Integer shelfNumber) {

        try {
            vendingMachine.select(shelfNumber);
            return Response.success();

        } catch (Exception e) {
            return Response.failure(e.getMessage());
        }
    }

    @RequestMapping(value = "/vending-machine/insert",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public @ResponseBody
    Response insertCoin(@RequestBody String coin) {

        try {
            Optional<Coin> coinOptional = Coin.fromString(coin.replace("\"", ""));
            if (coinOptional.isPresent()) {
                vendingMachine.insert(coinOptional.get());
                return Response.success();
            } else {
                return Response.failure(String.format("Invalid argument: '%s'", coin));
            }
        } catch (Exception e) {
            return Response.failure(e.getMessage());
        }
    }

    @RequestMapping(value = "/vending-machine/state",
            method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    VendingMachineDTO state() {
        return new VendingMachineDTO(vendingMachine.getDisplay(), buildDTO(productStorage));
    }

    private StorageDTO buildDTO(ProductStorage productStorage) {

        StorageDTO storage = new StorageDTO();

        for (int shelfNumber = 1; shelfNumber <= MAX_SHELF_NUMBER; shelfNumber++) {
            Optional<Product> product = productStorage.productOnShelf(shelfNumber);
            if (product.isPresent())
                storage.addShelf(shelfNumber, product.get(), productStorage.itemsOnShelf(shelfNumber));
        }

        return storage;
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class VendingMachineDTO {

        String display;
        StorageDTO storage;

        VendingMachineDTO(String display, StorageDTO storage) {
            this.storage = storage;
            this.display = display;
        }
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class StorageDTO {

        Map<Integer, ShelfDTO> shelfs = new HashMap<>();

        void addShelf(Integer shelfNumber, Product product, Integer items) {
            shelfs.put(shelfNumber, new ShelfDTO(product, items));
        }
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class ShelfDTO {

        Product product;
        Integer items;

        ShelfDTO(Product product, Integer items) {
            this.product = product;
            this.items = items;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(VendingMachineApplication.class, args);
    }
}
