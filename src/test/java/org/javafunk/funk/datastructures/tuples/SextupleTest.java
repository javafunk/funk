package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.Literals;
import org.javafunk.funk.functors.ordinals.*;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.testclasses.Name.name;

public class SextupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Integer first = sextuple.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        String second = sextuple.second();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Boolean third = sextuple.third();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        Fourth<Double> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Double fourth = sextuple.fourth();

        // Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldReturnTheFifthObject() {
        // Given
        Fifth<Long> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Long fifth = sextuple.fifth();

        // Then
        assertThat(fifth, is(23L));
    }

    @Test
    public void shouldReturnTheSixthObject() {
        // Given
        Sixth<Name> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Name sixth = sextuple.sixth();

        // Then
        assertThat(sixth, is(name("fred")));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstSecondThirdAndFourthInToString() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        String stringRepresentation = sextuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true, 3.6, 23, Name[value=fred])"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondThirdFourthFifthAndSixth() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(10, "Five", true, 3.6, 23L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Ten", true, 3.6, 23L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Five", false, 3.6, 23L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFourth() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Five", true, 4.2, 23L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFifth() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Five", true, 3.6, 36L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSixth() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Five", true, 3.6, 23L, name("james"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        List<Object> expected = Literals.listWith(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Boolean isEqual = materialize(sextuple1).equals(expected);

        // Then
        assertThat(isEqual, is(true));
    }
}
