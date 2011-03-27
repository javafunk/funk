package org.smallvaluesofcool.misc.functional;

import org.junit.Test;
import org.smallvaluesofcool.misc.functional.functors.MapFunction;
import org.smallvaluesofcool.misc.functional.functors.ReduceFunction;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.functional.Accumulators.longAdditionAccumulator;

public class EagerMapReduceTest {
    @Test
    public void shouldMapIterableUsingCustomMapFunction() {
        // Given
        Iterable<Integer> inputs = listWith(1, 2, 3);
        Collection<Integer> expectedOutputs = listWith(2, 4, 6);

        // When
        Collection<Integer> actualOutputs = Eager.map(inputs, new MapFunction<Integer, Integer>() {
            @Override
            public Integer map(Integer input) {
                return input * 2;
            }
        });

        // Then
        assertThat(actualOutputs, is(expectedOutputs));
    }

    @Test
    public void shouldReduceTheSuppliedInputToTheirSumUsingALongAccumulator() throws Exception {
        // Given
        Collection<Long> input = listWith(2L, 3L, 4L);

        // When
        Long actual = Eager.reduce(input, longAdditionAccumulator());

        // Then
        assertThat(actual, equalTo(9L));
    }

    @Test
    public void shouldReduceToTheSameTypeUsingACustomReduceFunction() throws Exception {
        // Given
        List<List<Integer>> inputLists = listWith(
                listWith(1, 2, 3).build(),
                listWith(4, 5, 6).build(),
                listWith(7, 8, 9).build());

        // When
        List<Integer> actual = Eager.reduce(inputLists, new ReduceFunction<List<Integer>, List<Integer>>() {
            // Example flattening accumulator.
            public List<Integer> accumulate(List<Integer> accumulator, List<Integer> element) {
                accumulator.addAll(element);
                return accumulator;
            }
        });

        // Then
        assertThat(actual, hasItems(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test
    public void shouldReduceToATypeOtherThanThatOfTheInputElementsWithASuppliedInitialValue() {
        // Given
        List<Integer> inputs = listWith(1, 2, 3);

        // When
        String output = Eager.reduce(inputs, "", new ReduceFunction<Integer, String>() {
            public String accumulate(String accumulator, Integer element) {
                accumulator += element.toString();
                return accumulator;
            }
        });

        // Then
        assertThat(output, is("123"));
    }
}
