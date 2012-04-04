package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.Literals;
import org.javafunk.funk.functors.ordinals.First;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.tuple;

public class SingleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> single = tuple(5);

        // When
        Integer first = single.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstInToString() {
        // Given
        First<Integer> single = tuple(5);

        // When
        String stringRepresentation = single.toString();

        // Then
        assertThat(stringRepresentation, is("(5)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirst() {
        // Given
        Single<Integer> single1 = tuple(5);
        Single<Integer> single2 = tuple(5);

        // When
        Boolean isEqual = single1.equals(single2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Single<Integer> single1 = tuple(5);
        Single<Integer> single2 = tuple(10);

        // When
        Boolean isEqual = single1.equals(single2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Single<Integer> single1 = tuple(5);
        List<Object> expected = Literals.<Object>listWith(5);

        // When
        Boolean isEqual = materialize(single1).equals(expected);

        // Then
        assertThat(isEqual, is(true));
    }
}
