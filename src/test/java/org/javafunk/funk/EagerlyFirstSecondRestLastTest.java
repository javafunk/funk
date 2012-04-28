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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.listWith;

public class EagerlyFirstSecondRestLastTest {
    @Test
    public void shouldReturnTheFirstElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7);

        // When
        Integer output = Eagerly.first(input);

        // Then
        assertThat(output, is(10));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionForFirstIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Eagerly.first(input);

        // Then a NoSuchElementException should be thrown.
    }

    @Test
    public void shouldReturnTheSecondElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7);

        // When
        Integer output = Eagerly.second(input);

        // Then
        assertThat(output, is(9));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionForSecondIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Eagerly.second(input);

        // Then a NoSuchElementException should be thrown.
    }

    @Test
    public void shouldReturnTheFirstElementInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = listWith(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Integer output = Eagerly.first(input, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(8));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionForPredicatedFirstIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Eagerly.first(input, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then a NoSuchElementException is thrown
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionForPredicatedFirstIfNoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 3, 5, 7);

        // When
        Eagerly.first(input, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldReturnTheFirstNElementsFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(10, 9, 8, 7);

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 4);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForFirstIfThereAreNotEnoughElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(3, 2, 1);
        Collection<Integer> expectedOutput = listWith(3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 4);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForFirstIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 3);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForFirstIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = listWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 0);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionForFirstIfTheNumberOfElementsRequiredIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = listWith(3, 2, 1);

        // When
        Eagerly.first(input, -3);

        // Then an IllegalArgumentException should be thrown.
    }

    @Test
    public void shouldReturnTheFirstNElementsInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = listWith(9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(8, 6);

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 2, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForPredicatedNFirstIfThereAreNotEnoughMatchingElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(4, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 4, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNFirstIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 3, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNFirstIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = listWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 0, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNFirstIfThereAreNoElementsMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 3, 5, 7);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.first(input, 2, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnTheLastElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Integer output = Eagerly.last(input);

        // Then
        assertThat(output, is(1));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionForLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Eagerly.last(input);

        // Then a NoSuchElementException should be thrown.
    }

    @Test
    public void shouldReturnTheLastElementInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = listWith(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Integer output = Eagerly.last(input, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionForPredicatedLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Eagerly.last(input, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then a NoSuchElementException is thrown
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionForPredicatedLastIfNoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 3, 5, 7);

        // When
        Eagerly.last(input, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldReturnTheLastNElementsFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 3);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForLastIfThereAreNotEnoughElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(3, 2, 1);
        Collection<Integer> expectedOutput = listWith(3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 4);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 3);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForLastIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = listWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 0);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionForLastIfTheNumberOfElementsRequiredIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = listWith(3, 2, 1);

        // When
        Eagerly.last(input, -3);

        // Then an IllegalArgumentException should be thrown.
    }

    @Test
    public void shouldReturnTheLastNElementsInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = listWith(9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(6, 4, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 3, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForPredicatedNLastIfThereAreNotEnoughMatchingElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(4, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 4, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 3, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNLastIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = listWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 0, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForPredicatedNLastIfThereAreNoElementsMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 3, 5, 7);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.last(input, 2, new Predicate<Integer>() {
            public Boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAllButFirstElementForRestIfIterableHasMoreThanOneElement() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4);
        Collection<Integer> expectedOutput = listWith(2, 3, 4);

        // When
        Collection<Integer> actualOutput = Eagerly.rest(input);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForRestIfIterableHasOnlyOneElement() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.rest(input);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForRestIfIterableHasNoElements() throws Exception {
        // Given
        Iterable<Integer> input = Iterables.empty();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.rest(input);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
