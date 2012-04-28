package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.Literals;
import org.javafunk.funk.behaviours.ordinals.*;
import org.javafunk.funk.testclasses.Age;
import org.javafunk.funk.testclasses.Colour;
import org.javafunk.funk.testclasses.Location;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.testclasses.Age.age;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Location.location;
import static org.javafunk.funk.testclasses.Name.name;

public class NonupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Integer first = nonuple.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        String second = nonuple.second();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean third = nonuple.third();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        Fourth<Double> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Double fourth = nonuple.fourth();

        // Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldReturnTheFifthObject() {
        // Given
        Fifth<Long> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Long fifth = nonuple.fifth();

        // Then
        assertThat(fifth, is(23L));
    }

    @Test
    public void shouldReturnTheSixthObject() {
        // Given
        Sixth<Name> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Name sixth = nonuple.sixth();

        // Then
        assertThat(sixth, is(name("fred")));
    }

    @Test
    public void shouldReturnTheSeventhObject() {
        // Given
        Seventh<Colour> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Colour seventh = nonuple.seventh();

        // Then
        assertThat(seventh, is(colour("blue")));
    }

    @Test
    public void shouldReturnTheEighthObject() {
        // Given
        Eighth<Age> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Age eighth = nonuple.eighth();

        // Then
        assertThat(eighth, is(age(25)));
    }

    @Test
    public void shouldReturnTheNinthObject() {
        // Given
        Ninth<Location> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Location ninth = nonuple.ninth();

        // Then
        assertThat(ninth, is(location("USA")));
    }

    @Test
    public void shouldUseTheStringValueOfAllElementsInToString() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        String stringRepresentation = nonuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true, 3.6, 23, Name[value=fred], Colour[value=blue], Age[value=25], Location[value=USA])"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondThirdFourthFifthSixthSeventhEighthAndNinth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(10, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Ten", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", false, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFourth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 5.4, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFifth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 46L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSixth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("george"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSeventh() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("red"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentEighth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(45), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentNinth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("UK"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        List<Object> expected = Literals.listWith(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = materialize(nonuple1).equals(expected);

        // Then
        assertThat(isEqual, is(true));
    }
}
