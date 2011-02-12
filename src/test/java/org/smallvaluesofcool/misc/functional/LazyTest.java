package org.smallvaluesofcool.misc.functional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.smallvaluesofcool.misc.collections.TwoTuple;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.collections.IterableUtils.toList;
import static org.smallvaluesofcool.misc.collections.TwoTuple.twoTuple;
import static org.smallvaluesofcool.misc.matchers.Matchers.hasOnlyItemsInOrder;

public class LazyTest {

    @Test
    public void shouldMapCollectionUsingCustomFunction() throws Exception {
        // Given
        Collection<Integer> input = listWith(1, 2, 3);

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
    public void shouldLazilyExecuteSuppliedFunctionOnEachElementInTheIterable() {
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
}
