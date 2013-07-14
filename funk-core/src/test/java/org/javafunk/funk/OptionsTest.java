package org.javafunk.funk;

import org.javafunk.funk.monads.Option;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.some;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class OptionsTest {
    @Test
    public void shouldReturnAllSomesFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Option<Integer>> options = iterableWith(some(10), none(Integer.class), some(20), some(30), none(Integer.class));
        Iterable<Option<Integer>> expected = iterableWith(some(10), some(20), some(30));

        // When
        Iterable<Option<Integer>> actual = Options.somes(options);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfIterablePassedToSomesIsNull() throws Exception {
        // Given
        Iterable<Option<Integer>> input = null;

        // When
        Options.somes(input);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnAllNonesFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Option<Integer>> options = iterableWith(some(10), none(Integer.class), some(20), some(30), none(Integer.class));
        Iterable<Option<Integer>> expected = iterableWith(none(Integer.class), none(Integer.class));

        // When
        Iterable<Option<Integer>> actual = Options.nones(options);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfIterablePassedToNoneIsNull() throws Exception {
        // Given
        Iterable<Option<Integer>> input = null;

        // When
        Options.nones(input);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnTrueIfTheSuppliedOptionHasAValue() throws Exception {
        // Given
        Option<Integer> option = some(10);

        // When
        boolean result = Options.<Integer>isSome().evaluate(option);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfTheSuppliedOptionHasNoValue() throws Exception {
        // Given
        Option<Integer> option = none();

        // When
        boolean result = Options.<Integer>isSome().evaluate(option);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrueIfTheSuppliedOptionHasNoValue() throws Exception {
        // Given
        Option<Integer> option = none();

        // When
        boolean result = Options.<Integer>isNone().evaluate(option);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfTheSuppliedOptionHasAValue() throws Exception {
        // Given
        Option<Integer> option = some(10);

        // When
        boolean result = Options.<Integer>isNone().evaluate(option);

        // Then
        assertThat(result, is(false));
    }
}
