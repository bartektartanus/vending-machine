package tdd.training;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Factors {

    @Test(expected = RuntimeException.class)
    public void shouldFactorNumberTest(){
        // given

        // when

        // then
        assertThat(factorsOf(1), is(equalTo(listOf())));
        assertThat(factorsOf(2), is(equalTo(listOf(2))));
        assertThat(factorsOf(3), is(equalTo(listOf(3))));
        assertThat(factorsOf(4), is(equalTo(listOf(2,2))));
        assertThat(factorsOf(5), is(equalTo(listOf(5))));
        assertThat(factorsOf(6), is(equalTo(listOf(2,3))));
        assertThat(factorsOf(7), is(equalTo(listOf(7))));
        assertThat(factorsOf(8), is(equalTo(listOf(2,2,2))));
        assertThat(factorsOf(9), is(equalTo(listOf(3,3))));
        assertThat(factorsOf(25), is(equalTo(listOf(5,5))));
        assertThat(factorsOf(2*2*2*3*11*17), is(equalTo(listOf(2,2,2,3,11,17))));
        throw new RuntimeException();
    }

    private List<Integer> factorsOf(int n) {
        ArrayList<Integer> factors = new ArrayList<>();
        int divisor = 2;
        while(n > 1){
            while(n % divisor == 0){
                factors.add(divisor);
                n /= divisor; // n = n / 2;
            }
            divisor++;
        }
        return factors;
    }

    private List<Integer> listOf(Integer... values) {
        return Arrays.asList(values);
    }

}
