package org.smallvaluesofcool.misc.functional;

import org.junit.Test;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;
import org.smallvaluesofcool.misc.functional.functors.MapFunction;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.smallvaluesofcool.misc.IterableUtils.materialize;
import static org.smallvaluesofcool.misc.IterableUtils.toList;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.Literals.twoTuple;
import static org.smallvaluesofcool.misc.matchers.Matchers.hasOnlyItemsInOrder;

public class LazyMapTest {
    @Test
    public void shouldMapIterableUsingCustomMapFunction() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3);

        // When
        Iterable<String> actual = Lazy.map(input, new MapFunction<Integer, String>() {
            public String map(Integer input) {
                return String.valueOf(input);
            }
        });

        // Then
        assertThat(toList(actual), hasItems("1", "2", "3"));
    }

    @Test
    public void shouldReturnElementsOfTheIterableInBatchesOfTheSpecifiedSize() {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> firstBatch = listWith(1, 2, 3);
        Collection<Integer> secondBatch = listWith(4, 5, 6);
        Collection<Integer> thirdBatch = listWith(7, 8, 9);

        // When
        Iterator<Iterable<Integer>> returnedIterator = Lazy.batch(input, 3).iterator();

        // Then
        assertThat(materialize(returnedIterator.next()), is(firstBatch));
        assertThat(materialize(returnedIterator.next()), is(secondBatch));
        assertThat(materialize(returnedIterator.next()), is(thirdBatch));
    }

    @Test
    public void shouldCycleTheSuppliedIterableInfinitely() {
        // Given
        Iterable<String> input = listWith("Red", "Green", "Blue");

        // When
        Iterable<String> output = Lazy.cycle(input);
        Iterator<String> cyclicIterator = output.iterator();

        // Then
        for(int i = 0; i < 20; i++) {
            assertThat(cyclicIterator.next(), is("Red"));
            assertThat(cyclicIterator.next(), is("Green"));
            assertThat(cyclicIterator.next(), is("Blue"));
        }
    }

    @Test
    public void shouldTakeElementsFromTheIterableBetweenTheSpecifiedStartAndStopInStepsOfTheSpecifiedSize() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> expectedOutput = listWith(3, 5, 7);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.slice(input, 2, 7, 2));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
