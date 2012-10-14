package org.javafunk.funk;

import org.javafunk.funk.behaviours.ordinals.First;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.Mapper;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableBuilderOf;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class TuplesTest {
    @Test
    public void shouldConvertPairToMapEntryWithKeyAsFirstValueAsSecond() throws Exception {
        // Given
        Pair<String, Integer> pair = tuple("string", 100);
        Map.Entry<String, Integer> actual = new AbstractMap.SimpleImmutableEntry<String, Integer>("string", 100);
        Mapper<Pair<String, Integer>, Map.Entry<String, Integer>> toMapEntry = Tuples.toMapEntry();

        // When
        Map.Entry<String, Integer> expected = toMapEntry.map(pair);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldMapToFirstOfSuppliedFirstable() throws Exception {
        // Given
        First<String> firstable = tuple("first", "second");
        String expected = "first";
        Mapper<? super First<String>, String> toFirst = Tuples.toFirst();

        // When
        String actual = toFirst.map(firstable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldGetFirstsFromIterableOfFirstables() throws Exception {
        // Given
        Iterable<? extends First<Integer>> firstables = iterableWith(tuple(1, 2), tuple(3, 4, 5), tuple(6, 7));
        Iterable<Integer> expected = iterableWith(1, 3, 6);

        // When
        Iterable<Integer> actual = Tuples.firsts(firstables);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }
}
