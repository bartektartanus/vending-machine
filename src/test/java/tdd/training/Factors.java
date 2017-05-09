package tdd.training;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Factors {

    public static List<Integer> of(int n){
        List<Integer> result = new ArrayList<>();
        int divisor = 2;
        while(n > 1){
            while(n % divisor == 0){
                n /= divisor;
                result.add(divisor);
            }
            divisor++;
        }
        return result;
    }

    @Test
    public void test() {
        assertThat(of(1),is(equalTo(listOf())));
        assertThat(of(2),is(equalTo(listOf(2))));
        assertThat(of(3),is(equalTo(listOf(3))));
        assertThat(of(4),is(equalTo(listOf(2,2))));
        assertThat(of(5),is(equalTo(listOf(5))));
        assertThat(of(6),is(equalTo(listOf(2,3))));
        assertThat(of(7),is(equalTo(listOf(7))));
        assertThat(of(8),is(equalTo(listOf(2,2,2))));
        assertThat(of(9),is(equalTo(listOf(3,3))));
        assertThat(of(10),is(equalTo(listOf(2,5))));
        assertThat(of(16),is(equalTo(listOf(2,2,2,2))));
        assertThat(of(25),is(equalTo(listOf(5,5))));
        assertThat(of(2 * 2 * 3 * 3 * 7 * 11 * 11),is(equalTo(listOf(2, 2, 3, 3, 7, 11, 11))));
    }

    public static List<Integer> listOf(Integer... values){
        return Arrays.asList(values);
    }

}
