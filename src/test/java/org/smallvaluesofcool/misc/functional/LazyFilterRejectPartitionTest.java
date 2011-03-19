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

public class LazyFilterRejectPartitionTest {
    @Test
    public void shouldOnlyReturnThoseElementsMatchingTheSuppliedPredicate() {
        // Given
        List<String> inputs = listWith("ac", "ab", "bc", "abc", "bcd", "bad");
        Collection<String> expectedOutputs = listWith("ac", "bc", "abc", "bcd");

        // When
        Collection<String> actualOutputs = materialize(Lazy.filter(inputs, new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("c");
            }
        }));

        // Then
        assertThat(actualOutputs, is(expectedOutputs));
    }

    @Test
    public void shouldOnlyReturnThoseElementsThatDoNotMatchTheSuppliedPredicate() {
        // Given
        List<String> inputs = listWith("ac", "ab", "bc", "abc", "bcd", "bad");
        Collection<String> expectedOutputs = listWith("ab", "bad");

        // When
        Collection<String> actualOutputs = materialize(Lazy.reject(inputs, new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("c");
            }
        }));

        // Then
        assertThat(actualOutputs, is(expectedOutputs));
    }

    @Test
    public void shouldReturnMatchingElementsFirstAndNonMatchingElementsSecond() {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8);
        Collection<Integer> expectedMatchingItems = listWith(2, 4, 6, 8);
        Collection<Integer> expectedNonMatchingItems = listWith(1, 3, 5, 7);

        // When
        TwoTuple<Iterable<Integer>, Iterable<Integer>> partitionResults = Lazy.partition(input,
                new PredicateFunction<Integer>(){
                    public boolean matches(Integer item) {
                        return isEven(item);
                    }

                    private boolean isEven(Integer item) {
                        return item % 2 == 0;
                    }
                });

        // Then
        Collection<Integer> actualMatchingItems = materialize(partitionResults.first());
        Collection<Integer> actualNonMatchingItems = materialize(partitionResults.second());
        
        assertThat(actualMatchingItems, is(expectedMatchingItems));
        assertThat(actualNonMatchingItems, is(expectedNonMatchingItems));
    }
}
