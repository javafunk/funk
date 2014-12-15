package org.javafunk.funk;

import org.javafunk.funk.monads.Option;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.option;
import static org.javafunk.funk.monads.Option.some;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class OptionsTest {
    @Test
    public void returnsAllSomesFromTheSuppliedIterable() {
        // Given
        Iterable<Option<Integer>> options = iterableWith(some(10), none(Integer.class), some(20), some(30), none(Integer.class));
        Iterable<Option<Integer>> expected = iterableWith(some(10), some(20), some(30));

        // When
        Iterable<Option<Integer>> actual = Options.somes(options);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test(expected = NullPointerException.class)
    public void throwsANullPointerExceptionIfIterablePassedToSomesIsNull() {
        // Given
        Iterable<Option<Integer>> input = null;

        // When
        Options.somes(input);

        // Then a NullPointerException is thrown
    }

    @Test
    public void returnsAllNonesFromTheSuppliedIterable() {
        // Given
        Iterable<Option<Integer>> options = iterableWith(some(10), none(Integer.class), some(20), some(30), none(Integer.class));
        Iterable<Option<Integer>> expected = iterableWith(none(Integer.class), none(Integer.class));

        // When
        Iterable<Option<Integer>> actual = Options.nones(options);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test(expected = NullPointerException.class)
    public void throwsANullPointerExceptionIfIterablePassedToNoneIsNull() {
        // Given
        Iterable<Option<Integer>> input = null;

        // When
        Options.nones(input);

        // Then a NullPointerException is thrown
    }

    @Test
    public void returnsTrueForIsSomeIfTheSuppliedOptionHasAValue() {
        // Given
        Option<Integer> option = some(10);

        // When
        boolean result = Options.<Integer>isSome().evaluate(option);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void returnsFalseForIsSomeIfTheSuppliedOptionHasNoValue() {
        // Given
        Option<Integer> option = none();

        // When
        boolean result = Options.<Integer>isSome().evaluate(option);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void returnsTrueForIsNoneIfTheSuppliedOptionHasNoValue() {
        // Given
        Option<Integer> option = none();

        // When
        boolean result = Options.<Integer>isNone().evaluate(option);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void returnsFalseForIsNoneIfTheSuppliedOptionHasAValue() {
        // Given
        Option<Integer> option = some(10);

        // When
        boolean result = Options.<Integer>isNone().evaluate(option);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void returnsTrueForHasValueIfTheSuppliedOptionHasAValue() {
        // Given
        Option<Integer> option = some(10);

        // When
        boolean result = Options.<Integer>hasValue().evaluate(option);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void returnsFalseForHasValueIfTheSuppliedOptionHasNoValue() {
        // Given
        Option<Integer> option = none();

        // When
        boolean result = Options.<Integer>hasValue().evaluate(option);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void returnsTrueForHasNoValueIfTheSuppliedOptionHasNoValue() {
        // Given
        Option<Integer> option = none();

        // When
        boolean result = Options.<Integer>hasNoValue().evaluate(option);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void returnsFalseForHasNoValueIfTheSuppliedOptionHasAValue() {
        // Given
        Option<Integer> option = some(10);

        // When
        boolean result = Options.<Integer>hasNoValue().evaluate(option);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void returnsMapperThatConvertsOptionToValue() {
        // Given
        Option<String> option = some("string");

        // When
        String value = Options.<String>toValue().map(option);

        // Then
        assertThat(value, is("string"));
    }

    @Test(expected = NoSuchElementException.class)
    public void throwsExceptionWhenOptionPassedToToValueMapperIsNone() {
        // Given
        Option<String> option = none();

        // When
        String value = Options.<String>toValue().map(option);

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void returnsMapperThatConvertsOptionToValueOfSpecifiedType() {
        // Given
        Option<String> option = some("string");

        // When
        String value = Options.toValue(String.class).map(option);

        // Then
        assertThat(value, is("string"));
    }

    @Test(expected = NoSuchElementException.class)
    public void throwsExceptionWhenOptionPassedToToValueMapperOfTypeIsNone() {
        // Given
        Option<String> option = none();

        // When
        String value = Options.toValue(String.class).map(option);

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void flattensIterableOfOptionsToValues() {
        // Given
        Iterable<Option<Integer>> options = iterableWith(
                option(1), option(2), option(3));
        Iterable<Integer> expectedValues = iterableWith(1, 2, 3);

        // When
        Iterable<Integer> actualValues = Options.flatten(options);

        // Then
        assertThat(actualValues, hasOnlyItemsInOrder(expectedValues));
    }

    @Test
    public void filtersNonesWhenFlatteningIterableOfOptions() {
        // Given
        Iterable<Option<Integer>> options = iterableWith(
                option(1),
                none(Integer.class), option(2),
                none(Integer.class), option(3));
        Iterable<Integer> expectedValues = iterableWith(1, 2, 3);

        // When
        Iterable<Integer> actualValues = Options.flatten(options);

        // Then
        assertThat(actualValues, hasOnlyItemsInOrder(expectedValues));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenFlattenPassedNullIterable() {
        // Given
        Iterable<Option<String>> options = null;

        // When
        Options.flatten(options);

        // Then a NullPointerException is thrown.
    }
}
