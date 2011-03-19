package org.smallvaluesofcool.misc.functional;

import org.junit.Test;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;
import org.smallvaluesofcool.misc.functional.functors.DoFunction;
import org.smallvaluesofcool.misc.functional.functors.MapFunction;
import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.smallvaluesofcool.misc.IterableUtils.materialize;
import static org.smallvaluesofcool.misc.IterableUtils.toList;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.Literals.twoTuple;
import static org.smallvaluesofcool.misc.matchers.Matchers.hasOnlyItemsInOrder;

public class LazyTakeDropTest {
    @Test
    public void shouldReturnAnIterableContainingTheSpecifiedNumberOfElements() {
        // Given
        List<Integer> tenFibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Collection<Integer> expectedOutputs = listWith(1, 1, 2, 3, 5);

        // When
        Collection<Integer> actualOutputs = materialize(Lazy.take(tenFibonaccis, 5));

        // Then
        assertThat(actualOutputs, is(expectedOutputs));
    }

    @Test
    public void shouldReturnAnIterableWithTheFirstNElementsDropped() {
        // Given
        List<Integer> tenFibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Collection<Integer> expectedOutputs = listWith(8, 13, 21, 34, 55);

        // When
        Collection<Integer> actualOutputs = materialize(Lazy.drop(tenFibonaccis, 5));

        // Then
        assertThat(actualOutputs, is(expectedOutputs));
    }

    @Test
    public void shouldTakeElementsFromTheIterableWhileTheSuppliedPredicateIsTrue() {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8);
        Collection<Integer> expectedOutput = listWith(1, 2, 3, 4);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.takeWhile(input, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer input) {
                return input < 5;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldTakeElementsFromTheIterableUntilTheSuppliedPredicateIsTrue() {
        // Given
        Iterable<Integer> input = listWith(8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(8, 7, 6, 5);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.takeUntil(input, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer input) {
                return input < 5;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDropElementsFromTheIterableWhileTheSuppliedPredicateIsTrue() throws Exception {
        // Given
        Iterable<Integer> input = listWith(8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(4, 3, 2, 1);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.dropWhile(input, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer input) {
                return input > 4;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDropElementsFromTheIterableUntilTheSuppliedPredicateIsTrue() throws Exception {
        // Given
        Iterable<Integer> input = listWith(8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(4, 3, 2, 1);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.dropUntil(input, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer input) {
                return input < 5;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
