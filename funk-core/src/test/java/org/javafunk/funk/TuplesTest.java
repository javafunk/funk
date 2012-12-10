package org.javafunk.funk;

import org.javafunk.funk.behaviours.ordinals.Eighth;
import org.javafunk.funk.behaviours.ordinals.Fifth;
import org.javafunk.funk.behaviours.ordinals.First;
import org.javafunk.funk.behaviours.ordinals.Fourth;
import org.javafunk.funk.behaviours.ordinals.Ninth;
import org.javafunk.funk.behaviours.ordinals.Second;
import org.javafunk.funk.behaviours.ordinals.Seventh;
import org.javafunk.funk.behaviours.ordinals.Sixth;
import org.javafunk.funk.behaviours.ordinals.Third;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.testclasses.Age;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.testclasses.Age.age;
import static org.javafunk.funk.testclasses.Name.name;
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

    @Test
    public void shouldMapToFifthOfSuppliedFifthable() throws Exception {
        // Given
        Fifth<Character> fifthable = tuple("first", 2, true, 1.25, '5');
        Character expected = '5';
        Mapper<? super Fifth<Character>, Character> toFifth = Tuples.toFifth();

        // When
        Character actual = toFifth.map(fifthable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldGetFifthsFromIterableOfFifthables() throws Exception {
        // Given
        Iterable<? extends Fifth<Double>> fifthables = iterableWith(
                tuple("1", 2, 'a', false, 1.25),
                tuple(3, 'a', 5.4, 'b', 2.5),
                tuple(6.2, 7, false, 'c', 4.75));
        Iterable<Double> expected = iterableWith(1.25, 2.5, 4.75);

        // When
        Iterable<Double> actual = Tuples.fifths(fifthables);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldMapToSixthOfSuppliedSixthable() throws Exception {
        // Given
        Sixth<Long> sixthable = tuple("first", 2, true, 1.25, '5', 12L);
        Long expected = 12L;
        Mapper<? super Sixth<Long>, Long> toSixth = Tuples.toSixth();

        // When
        Long actual = toSixth.map(sixthable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldGetSixthsFromIterableOfSixthables() throws Exception {
        // Given
        Iterable<? extends Sixth<Long>> sixthables = iterableWith(
                tuple("1", 2, 'a', false, 1.25, 0L),
                tuple(3, 'a', 5.4, 'b', 2.5, 27L),
                tuple(6.2, 7, false, 4.75, 'c', 54L));
        Iterable<Long> expected = iterableWith(0L, 27L, 54L);

        // When
        Iterable<Long> actual = Tuples.sixths(sixthables);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldMapToSeventhOfSuppliedSeventhable() throws Exception {
        // Given
        Seventh<Name> seventhable = tuple("first", 2, true, 1.25, '5', 12L, name("Amy"));
        Name expected = name("Amy");
        Mapper<? super Seventh<Name>, Name> toSeventh = Tuples.toSeventh();

        // When
        Name actual = toSeventh.map(seventhable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldGetSeventhsFromIterableOfSeventhables() throws Exception {
        // Given
        Iterable<? extends Seventh<Name>> seventhables = iterableWith(
                tuple(false, 1.25, "1", 2, 'a', 0L, name("Bill")),
                tuple(3, 'a', 2.5, 27L, 5.4, 'b', name("Betty")),
                tuple(6.2, 7, false, 4.75, 'c', 54L, name("Beatrice")));
        Iterable<Name> expected = iterableWith(name("Bill"), name("Betty"), name("Beatrice"));

        // When
        Iterable<Name> actual = Tuples.sevenths(seventhables);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldMapToEighthOfSuppliedEighthable() throws Exception {
        // Given
        Eighth<Age> eighthable = tuple("first", 2, true, 1.25, '5', 12L, name("Amy"), age(24));
        Age expected = age(24);
        Mapper<? super Eighth<Age>, Age> toEighth = Tuples.toEighth();

        // When
        Age actual = toEighth.map(eighthable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldGetEighthsFromIterableOfEighthables() throws Exception {
        // Given
        Iterable<? extends Eighth<Age>> eighthables = iterableWith(
                tuple(false, 1.25, 'a', 0L, name("Bill"), "1", 2, age(50)),
                tuple(3, 'a', 2.5, 27L, 5.4, 'b', name("Betty"), age(60)),
                tuple(6.2, 7, false, 4.75, 54L, name("Beatrice"), 'c', age(70)));
        Iterable<Age> expected = iterableWith(age(50), age(60), age(70));

        // When
        Iterable<Age> actual = Tuples.eighths(eighthables);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldMapToNinthOfSuppliedNinthable() throws Exception {
        // Given
        Ninth<Short> ninthable = tuple("first", 2, true, 1.25, '5', 12L, name("Amy"), age(24), Short.valueOf("5"));
        Short expected = Short.valueOf("5");
        Mapper<? super Ninth<Short>, Short> toNinth = Tuples.toNinth();

        // When
        Short actual = toNinth.map(ninthable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldGetNinthsFromIterableOfNinthables() throws Exception {
        // Given
        Iterable<? extends Ninth<Short>> ninthables = iterableWith(
                tuple(false, 1.25, 'a', 0L, name("Bill"), "1", age(50), 2, Short.valueOf("5")),
                tuple(3, 'a', 2.5, 27L, 5.4, 'b', name("Betty"), age(60), Short.valueOf("10")),
                tuple(6.2, 7, false, 4.75, 54L, 'c', age(70), name("Beatrice"), Short.valueOf("15")));
        Iterable<Short> expected = iterableWith(Short.valueOf("5"), Short.valueOf("10"), Short.valueOf("15"));

        // When
        Iterable<Short> actual = Tuples.ninths(ninthables);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }
}
