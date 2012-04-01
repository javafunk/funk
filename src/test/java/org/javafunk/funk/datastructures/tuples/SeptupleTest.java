package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.functors.ordinals.*;
import org.javafunk.funk.testclasses.Colour;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Name.name;

public class SeptupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Integer first = septuple.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        String second = septuple.second();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Boolean third = septuple.third();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        Fourth<Double> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Double fourth = septuple.fourth();

        // Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldReturnTheFifthObject() {
        // Given
        Fifth<Long> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Long fifth = septuple.fifth();

        // Then
        assertThat(fifth, is(23L));
    }

    @Test
    public void shouldReturnTheSixthObject() {
        // Given
        Sixth<Name> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Name sixth = septuple.sixth();

        // Then
        assertThat(sixth, is(name("fred")));
    }

    @Test
    public void shouldReturnTheSeventhObject() {
        // Given
        Seventh<Colour> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Colour seventh = septuple.seventh();

        // Then
        assertThat(seventh, is(colour("blue")));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstSecondThirdAndFourthInToString() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        String stringRepresentation = septuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true, 3.6, 23, Name[value=fred], Colour[value=blue])"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondThirdFourthFifthSixthAndSeventh() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(10, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Ten", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", false, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);


        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFourth() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", true, 4.2, 23L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFifth() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", true, 3.6, 36L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSixth() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", true, 3.6, 23L, name("james"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSeventh() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("red"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }
}
