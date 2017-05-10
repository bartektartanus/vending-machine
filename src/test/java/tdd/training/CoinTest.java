package tdd.training;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import tdd.training.domain.Coin;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class CoinTest {


    @Test
    public void shouldCreateValidCoinTest(){
        // given
        Coin coin = Coin.fromString("FIVE_DOLLAR").orElseThrow(AssertionError::new);
        // when

        // then
        MatcherAssert.assertThat(coin, is(equalTo(Coin.FIVE_DOLLAR)));

    }
}