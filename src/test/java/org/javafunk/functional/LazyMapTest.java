package org.javafunk.functional;

import org.junit.Test;
import org.javafunk.functional.functors.MapFunction;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.javafunk.IterableUtils.toList;
import static org.javafunk.Literals.listWith;

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
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3);

        // When
        Iterable<String> iterable = Lazy.map(input, new MapFunction<Integer, String>() {
            public String map(Integer input) {
                return String.valueOf(input);
            }
        });

        Iterator<String> iterator1 = iterable.iterator();
        Iterator<String> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is("1"));
        assertThat(iterator1.next(), is("2"));
        assertThat(iterator2.next(), is("1"));
        assertThat(iterator1.next(), is("3"));
        assertThat(iterator2.next(), is("2"));
        assertThat(iterator2.next(), is("3"));
    }
}
