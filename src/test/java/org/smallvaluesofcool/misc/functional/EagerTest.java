package org.smallvaluesofcool.misc.functional;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.smallvaluesofcool.misc.Literals.listWith;

public class EagerTest {

    @Test
    public void shouldSumSuppliedInputUsingLongAccumulator() throws Exception {
        // Given
        Collection<Long> input = listWith(2L, 3L, 4L);

        // When
        Long actual = Eager.reduce(input, Eager.longAccumulator());

        // Then
        Assert.assertThat(actual, equalTo(9L));
    }

    @Test
    public void shouldReduceOtherTypesUsingCustomFunction() throws Exception {
        // Given
        List<List<Integer>> inputLists = listWith(listWith(1, 2, 3), listWith(4, 5, 6), listWith(7, 8, 9));

        // When
        List<Integer> actual = Eager.reduce(inputLists, new ReduceFunction<List<Integer>>() {
            // Example flattening accumulator.
            public List<Integer> accumulate(List<Integer> accumulator, List<Integer> element) {
                accumulator.addAll(element);
                return accumulator;
            }
        });

        // Then
        Assert.assertThat(actual, Matchers.hasItems(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test
    public void shouldReturnMinValue() throws Exception {
        // Given
        List<String> list = listWith("a", "b", "c");

        // When
        String actual = Eager.min(list);

        // Then
        assertThat(actual, is("a"));
    }

    @Test
    public void shouldReturnMaxValue() throws Exception {
        // Given
        List<Integer> list = listWith(2, 6, 3);

        // When
        Integer actual = Eager.max(list);

        // Then
        assertThat(actual, is(6));
    }
}
