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
        return model;
    }


    @Bean
    public static ProductStorage sample() {
        ProductStorage storage = null;
        return storage;
    }

    @Bean
    @Autowired
    public static VendingMachine vendingMachine() {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.putOnShelf(1, new Product("Cola", Price.fromCents(250)), 5);
        return vendingMachine;
    }

    @Autowired
    public VendingMachineController(VendingMachine vendingMachine, ProductStorage productStorage) {
        this.vendingMachine = vendingMachine;
        this.productStorage = productStorage;
    }

    // wybranie przycisku z półką na automacie
    @RequestMapping(value = "/vending-machine/push",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public @ResponseBody
    Response selectProduct(@RequestBody Integer shelfNumber) {
        vendingMachine.pushButton(shelfNumber);
        return Response.success();
    }

    // wrzucenie monety o zadanym nominale
    @RequestMapping(value = "/vending-machine/insert",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public Response insertCoin(@RequestBody String coin) {
        return Response.failure("Not implemented");
    }

    @RequestMapping(value = "/vending-machine/state",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public VendingMachineDTO state() {
        return new VendingMachineDTO(vendingMachine.getDisplay(), buildDTO(productStorage));
    }

    private StorageDTO buildDTO(ProductStorage productStorage) {

        StorageDTO storage = new StorageDTO();

        return storage;
    }

    // pomocniczne klasy DTO dla GUI
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
