package org.javafunk.datastructures;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.twoTuple;

public class TwoTupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        TwoTuple<Integer, String> tuple = twoTuple(5, "Five");

        // When
        Integer first = tuple.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        TwoTuple<Integer, String> tuple = twoTuple(5, "Five");

        // When
        String second = tuple.second();

        // Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstAndSecondInToString() {
        // Given
        TwoTuple<Integer, String> tuple = twoTuple(5, "Five");

        // When
        String stringRepresentation = tuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstAndSecond() {
        // Given
        TwoTuple<Integer, String> tuple1 = twoTuple(5, "Five");
        TwoTuple<Integer, String> tuple2 = twoTuple(5, "Five");

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        TwoTuple<Integer, String> tuple1 = twoTuple(5, "Five");
        TwoTuple<Integer, String> tuple2 = twoTuple(10, "Five");

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        TwoTuple<Integer, String> tuple1 = twoTuple(5, "Five");
        TwoTuple<Integer, String> tuple2 = twoTuple(5, "Ten");

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirstAndSecond() {
        // Given
        TwoTuple<Integer, String> tuple1 = twoTuple(5, "Five");
        TwoTuple<Integer, String> tuple2 = twoTuple(10, "Ten");

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(false));
    }
}
