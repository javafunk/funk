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
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.IterableUtils.toList;
import static org.smallvaluesofcool.misc.Literals.twoTuple;
import static org.smallvaluesofcool.misc.matchers.Matchers.hasOnlyItemsInOrder;

public class LazyTest {
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
    public void shouldZipIterables() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C");
        Iterable<Integer> iterable2 = listWith(1, 2, 3);

        Collection<TwoTuple<String, Integer>> expected = listWith(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Collection<TwoTuple<String, Integer>> actual = toList(Lazy.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterablesWithLongerFirstIterable() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C", "D");
        Iterable<Integer> iterable2 = listWith(1, 2, 3);
        Collection<TwoTuple<String, Integer>> expected = listWith(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Collection<TwoTuple<String, Integer>> actual = toList(Lazy.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterablesWithShorterFirstIterable() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C");
        Iterable<Integer> iterable2 = listWith(1, 2, 3, 4);
        Collection<TwoTuple<String, Integer>> expected = listWith(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Iterable<TwoTuple<String, Integer>> actual = Lazy.zip(iterable1, iterable2);

        // Then
        assertThat(toList(actual), hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldEnumerateSequence() {
        // Given
        Iterable<String> iterable = listWith("A", "B", "C");
        Collection<TwoTuple<Integer, String>> expected = listWith(twoTuple(0, "A"), twoTuple(1, "B"), twoTuple(2, "C"));

        // When
        Iterable<TwoTuple<Integer, String>> actual = Lazy.enumerate(iterable);

        // Then
        assertThat(toList(actual), hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldLazilyExecuteSuppliedFunctionOnEachElement() {
        // Given
        Iterable<Target> targets = listWith(mock(Target.class), mock(Target.class), mock(Target.class));

        // When
        Iterable<Target> preparedTargets = Lazy.each(targets, new DoFunction<Target>() {
            @Override
            public void actOn(Target input) {
                input.doSomething();
            }
        });

        // Then
        Iterator<Target> preparedTargetsIterator = preparedTargets.iterator();
        for (Target target : targets) {
            verify(target, never()).doSomething();
            preparedTargetsIterator.next();
            verify(target, times(1)).doSomething();
        }
    }

    private interface Target {
        void doSomething();
    }

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
    public void shouldOnlyReturnThoseElementsThatDontMatchTheSuppliedPredicate() {
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
