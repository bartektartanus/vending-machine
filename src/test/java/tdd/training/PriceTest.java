package tdd.training;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import tdd.training.domain.Price;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class PriceTest {

    @Test
    public void shouldCreateValidTest(){
        // given
        Price price = Price.fromCents(100);
        // when

        // then
        assertThat(price.toString(), is(equalTo("1,00$")));

    }

}
