package org.javafunk.funk;

import org.javafunk.funk.behaviours.ordinals.First;
import org.javafunk.funk.behaviours.ordinals.Fourth;
import org.javafunk.funk.behaviours.ordinals.Second;
import org.javafunk.funk.behaviours.ordinals.Third;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.Mapper;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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

    @Test
    public void shouldMapToSecondOfSuppliedSecondable() throws Exception {
        // Given
        Second<Integer> secondable = tuple("first", 2);
        Integer expected = 2;
        Mapper<? super Second<Integer>, Integer> toSecond = Tuples.toSecond();

        // When
        Integer actual = toSecond.map(secondable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldGetSecondsFromIterableOfSecondables() throws Exception {
        // Given
        Iterable<? extends Second<Integer>> secondables = iterableWith(tuple("1", 2), tuple(3, 4, 5.4), tuple(6.2, 7));
        Iterable<Integer> expected = iterableWith(2, 4, 7);

        // When
        Iterable<Integer> actual = Tuples.seconds(secondables);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldMapToThirdOfSuppliedThirdable() throws Exception {
        // Given
        Third<Boolean> thirdable = tuple("first", 2, true);
        Boolean expected = true;
        Mapper<? super Third<Boolean>, Boolean> toThird = Tuples.toThird();

        // When
        Boolean actual = toThird.map(thirdable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldGetThirdsFromIterableOfThirdables() throws Exception {
        // Given
        Iterable<? extends Third<Boolean>> thirdables = iterableWith(
                tuple("1", 2, false),
                tuple(3, 'a', true, 5.4),
                tuple(6.2, 7, false));
        Iterable<Boolean> expected = iterableWith(false, true, false);

        // When
        Iterable<Boolean> actual = Tuples.thirds(thirdables);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldMapToFourthOfSuppliedFourthable() throws Exception {
        // Given
        Fourth<Double> fourthable = tuple("first", 2, true, 1.25);
        Double expected = 1.25;
        Mapper<? super Fourth<Double>, Double> toFourth = Tuples.toFourth();

        // When
        Double actual = toFourth.map(fourthable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldGetFourthsFromIterableOfFourthables() throws Exception {
        // Given
        Iterable<? extends Fourth<Character>> fourthables = iterableWith(
                tuple("1", 2, false, 'a'),
                tuple(3, 'a', 5.4, 'b'),
                tuple(6.2, 7, false, 'c'));
        Iterable<Character> expected = iterableWith('a', 'b', 'c');

        // When
        Iterable<Character> actual = Tuples.fourths(fourthables);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }
}
