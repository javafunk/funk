package org.smallvaluesofcool.misc.functional;

import org.junit.Test;
import org.smallvaluesofcool.misc.collections.IterableUtils;
import org.smallvaluesofcool.misc.collections.TwoTuple;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.smallvaluesofcool.misc.collections.IterableUtils.toList;
import static org.smallvaluesofcool.misc.collections.TwoTuple.twoTuple;
import static org.smallvaluesofcool.misc.matchers.Matchers.hasOnlyItemsInOrder;

public class LazyTest {

    @Test
    public void shouldMapCollectionUsingCustomFunction() throws Exception {
        // Given
        Collection<Integer> input = asList(1, 2, 3);

        // When
        Collection<String> actual = toList(Lazy.map(input, new MapFunction<Integer, String>() {
            public String map(Integer input) {
                return String.valueOf(input);
            }
        }));

        // Then
        assertThat(actual, hasItems("1", "2", "3"));
    }

    @Test
    public void shouldZipIterables() {
        // Given
        Iterable<String> iterable1 = asList("A", "B", "C");
        Iterable<Integer> iterable2 = asList(1, 2, 3);

        Collection<TwoTuple<String, Integer>> expected = asList(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Collection<TwoTuple<String, Integer>> actual = toList(Lazy.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterablesWithLongerFirstIterable() {
        // Given
        Iterable<String> iterable1 = asList("A", "B", "C", "D");
        Iterable<Integer> iterable2 = asList(1, 2, 3);
        Collection<TwoTuple<String, Integer>> expected = asList(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Collection<TwoTuple<String, Integer>> actual = toList(Lazy.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterablesWithShorterFirstIterable() {
        // Given
        Iterable<String> iterable1 = asList("A", "B", "C");
        Iterable<Integer> iterable2 = asList(1, 2, 3, 4);
        Collection<TwoTuple<String, Integer>> expected = asList(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Iterable<TwoTuple<String, Integer>> actual = Lazy.zip(iterable1, iterable2);

        // Then
        assertThat(toList(actual), hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldEnumerateSequence() {
        // Given
        Iterable<String> iterable = asList("A", "B", "C");
        Collection<TwoTuple<Integer, String>> expected = asList(twoTuple(0, "A"), twoTuple(1, "B"), twoTuple(2, "C"));

        // When
        Iterable<TwoTuple<Integer, String>> actual = Lazy.enumerate(iterable);

        // Then
        assertThat(toList(actual), hasOnlyItemsInOrder(expected));
    }
}
