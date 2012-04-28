/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Predicate;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.listWith;

public class EagerlyTakeDropTest {
    @Test
    public void shouldTakeTheSpecifiedNumberOfElements() {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        Collection<String> expectedOutput = listWith("a", "b", "c", "d", "e");

        // When
        Collection<String> actualOutput = Eagerly.take(input, 5);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionIfTheNumberToTakeIsZero() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        Collection<String> expectedOutput = Collections.emptyList();
        Integer numberToTake = 0;

        // When
        Collection<String> actualOutput = Eagerly.take(input, numberToTake);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheNumberToTakeIsLessThanZero() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        Integer numberToTake = -5;

        // When
        Eagerly.take(input, numberToTake);

        // Then an IllegalArgumentException is thrown
    }

    @Test
    public void shouldDropTheSpecifiedNumberOfElements() {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        Collection<String> expectedOutput = listWith("f", "g", "h", "i", "j");

        // When
        Collection<String> actualOutput = Eagerly.drop(input, 5);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnTheSuppliedIterableIfTheNumberToDropIsZero() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        Collection<String> expectedOutput = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        Integer numberToDrop = 0;

        // When
        Collection<String> actualOutput = Eagerly.drop(input, numberToDrop);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheNumberToDropIsLessThanZero() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        Integer numberToDrop = -4;

        // When
        Eagerly.drop(input, numberToDrop);

        // Then an IllegalArgumentException is thrown
    }

    @Test
    public void shouldTakeElementsWhileTheSuppliedPredicateIsTrue() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 1, 2, 3);
        Collection<Integer> expectedOutput = listWith(1, 2, 1, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.takeWhile(input, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return item < 3;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldTakeElementsUntilTheSuppliedPredicateBecomesTrue() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5);
        Collection<Integer> expectedOutput = listWith(1, 2, 3);

        // When
        Collection<Integer> actualOutput = Eagerly.takeUntil(input, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return item > 3;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDropElementsWhileTheSuppliedPredicateIsTrue() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aaa", "aaaa");
        Collection<String> expectedOutput = listWith("aaa", "aaaa");

        // When
        Collection<String> actualOutput = Eagerly.dropWhile(input, new Predicate<String>() {
            @Override
            public Boolean evaluate(String item) {
                return item.length() < 3;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDropElementsUntilTheSuppliedPredicateBecomesTrue() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aab", "aba", "aba");
        Collection<String> expectedOutput = listWith("aab", "aba", "aba");

        // When
        Collection<String> actualOutput = Eagerly.dropUntil(input, new Predicate<String>() {
            @Override
            public Boolean evaluate(String item) {
                return item.contains("b");
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
