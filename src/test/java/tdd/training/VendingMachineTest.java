package tdd.training;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import tdd.training.domain.Price;
import tdd.training.domain.Product;
import tdd.training.domain.VendingMachine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class VendingMachineTest {

    VendingMachine vendingMachine = new VendingMachine();

    @Test
    public void shouldShowEmptyDisplayTest(){
        // given
        // when

        // then
        assertThat(vendingMachine.getDisplay(), is(equalTo("")));
    }

    @Test
    public void shouldShowProductTest(){
        // given
        Product product = new Product("Cola", Price.fromCents(100));
        vendingMachine.putOnShelf(1, product, 5);
        // when
        vendingMachine.pushButton(1);
        // then
        assertThat(vendingMachine.getDisplay(), is(equalTo(product.toString())));
    }

    @Test
    public void shouldReducePriceTest(){
        // given
        Product product = new Product("Cola", Price.fromCents(100));
        vendingMachine.putOnShelf(1, product, 5);

        // when
        vendingMachine.insertCoin("FIVE_DOLLAR");

        // then

    }

}
