package org.smallvaluesofcool.misc.functional;

import org.junit.Test;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;
import org.smallvaluesofcool.misc.functional.functors.DoFunction;
import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.Literals.twoTuple;

public class EagerFilterRejectPartitionTest {
    @Test
    public void shouldOnlyReturnThoseElementsMatchingTheSuppliedPredicate() {
        // Given
        Iterable<Integer> inputs = listWith(1, 2, 3, 4, 5, 6);
        Collection<Integer> expectedOutput = listWith(2, 4, 6);

        // When
        Collection<Integer> actualOutput = Eager.filter(inputs, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldOnlyReturnThoseElementsThatDontMatchTheSuppliedPredicate() {
        // Given
        Iterable<Integer> inputs = listWith(1, 2, 3, 4, 5, 6);
        Collection<Integer> expectedOutput = listWith(1, 3, 5);

        // When
        Collection<Integer> actualOutput = Eager.reject(inputs, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnTheMatchingElementsFirstAndTheNonMatchingElementsSecond() {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h");
        Collection<String> expectedMatchingItems = listWith("a", "b", "c", "d");
        Collection<String> expectedNonMatchingItems = listWith("e", "f", "g", "h");

        // When
        TwoTuple<Collection<String>, Collection<String>> partitionResults = Eager.partition(input,
                new PredicateFunction<String>() {
                    public boolean matches(String item) {
                        return item.compareTo("e") < 0;
                    }
                });

        // Then
        Collection<String> actualMatchingItems = partitionResults.first();
        Collection<String> actualNonMatchingItems = partitionResults.second();

        assertThat(actualMatchingItems, is(expectedMatchingItems));
        assertThat(actualNonMatchingItems, is(expectedNonMatchingItems));
    }
}
