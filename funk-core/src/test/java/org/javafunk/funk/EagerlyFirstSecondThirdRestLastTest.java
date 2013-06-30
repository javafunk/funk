/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.monads.Option;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.monads.Option.some;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class EagerlyFirstSecondThirdRestLastTest {
    @Test
    public void shouldReturnAnOptionOfTheFirstElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(10, 9, 8, 7);

        // When
        Option<Integer> output = Eagerly.first(input);

        // Then
        assertThat(output, is(some(10)));
    }

    @Test
    public void shouldReturnNoneForFirstIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.first(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnAnOptionOfTheSecondElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(10, 9, 8, 7);

        // When
        Option<Integer> output = Eagerly.second(input);

        // Then
        assertThat(output, is(some(9)));
    }

    @Test
    public void shouldReturnNoneForSecondIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.second(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForSecondIfTheSuppliedIterableContainsOnlyOneElement() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1);

        // When
        Option<Integer> output = Eagerly.second(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnAnOptionOfTheThirdElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(10, 9, 8, 7);

        // When
        Option<Integer> output = Eagerly.third(input);

        // Then
        assertThat(output, is(some(8)));
    }

    @Test
    public void shouldReturnNoneForThirdIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.third(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForThirdIfTheSuppliedIterableContainsOnlyOneElement() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1);

        // When
        Option<Integer> output = Eagerly.third(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForThirdIfTheSuppliedIterableContainsOnlyTwoElements() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2);

        // When
        Option<Integer> output = Eagerly.third(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnAnOptionOfTheFirstElementInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.firstMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(some(8)));
    }

    @Test
    public void shouldReturnNoneForFirstMatchingIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.firstMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForFirstMatchingIfNoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);

        // When
        Option<Integer> output = Eagerly.firstMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToFirstMatchingIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;

        // When
        Eagerly.firstMatching(input, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnAnOptionOfTheSecondElementInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.secondMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(some(6)));
    }

    @Test
    public void shouldReturnNoneForSecondMatchingIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.secondMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForSecondMatchingIfNoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);

        // When
        Option<Integer> output = Eagerly.secondMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToSecondMatchingIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;

        // When
        Eagerly.secondMatching(input, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnAnOptionOfTheThirdElementInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.thirdMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(some(4)));
    }

    @Test
    public void shouldReturnNoneForThirdMatchingIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.thirdMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForThirdMatchingIfNoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);

        // When
        Option<Integer> output = Eagerly.thirdMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForThirdMatchingIfOnlyTwoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 7, 9);

        // When
        Option<Integer> output = Eagerly.thirdMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToThirdMatchingIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;

        // When
        Eagerly.thirdMatching(input, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnTheFirstNElementsFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(10, 9, 8, 7);

        // When
        Collection<Integer> actualOutput = Eagerly.firstN(input, 4);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForFirstNIfThereAreNotEnoughElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eagerly.firstN(input, 4);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForFirstNIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.firstN(input, 3);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForFirstNIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.firstN(input, 0);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionForFirstNIfTheNumberOfElementsRequiredIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);

        // When
        Eagerly.firstN(input, -3);

        // Then an IllegalArgumentException should be thrown.
    }

    @Test
    public void shouldReturnTheFirstNElementsInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(8, 6);

        // When
        Collection<Integer> actualOutput = Eagerly.firstNMatching(input, 2, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForFirstNMatchingIfThereAreNotEnoughMatchingElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(4, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.firstNMatching(input, 4, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForFirstNMatchingIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.firstNMatching(input, 3, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForFirstNMatchingIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.firstNMatching(input, 0, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForFirstNMatchingIfThereAreNoElementsMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.firstNMatching(input, 2, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToFirstNMatchingIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;
        Integer numberOfElementsRequired = 5;

        // When
        Eagerly.firstNMatching(input, numberOfElementsRequired, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnAnOptionOfTheLastElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.last(input);

        // Then
        assertThat(output, is(some(1)));
    }

    @Test
    public void shouldReturnNoneForLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.last(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnAnOptionOfTheSecondLastElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.secondLast(input);

        // Then
        assertThat(output, is(some(2)));
    }

    @Test
    public void shouldReturnNoneForSecondLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.secondLast(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForSecondLastIfTheSuppliedIterableContainsOnlyOneElement() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1);

        // When
        Option<Integer> output = Eagerly.secondLast(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnAnOptionOfTheThirdLastElementFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.thirdLast(input);

        // Then
        assertThat(output, is(some(3)));
    }

    @Test
    public void shouldReturnNoneForThirdLastIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.thirdLast(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForThirdLastIfTheSuppliedIterableContainsOnlyTwoElements() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1);

        // When
        Option<Integer> output = Eagerly.thirdLast(input);

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnAnOptionOfTheLastElementInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.lastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(some(2)));
    }

    @Test
    public void shouldReturnNoneForLastMatchingIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.lastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForLastMatchingIfNoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);

        // When
        Option<Integer> output = Eagerly.lastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToLastMatchingIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;

        // When
        Eagerly.lastMatching(input, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnAnOptionOfTheSecondLastElementInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.secondLastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(some(4)));
    }

    @Test
    public void shouldReturnNoneForSecondLastMatchingIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.secondLastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForSecondLastMatchingIfNoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);

        // When
        Option<Integer> output = Eagerly.secondLastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForSecondLastMatchingIfOnlyOneElementInTheSuppliedIterableMatches() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 6, 7);

        // When
        Option<Integer> output = Eagerly.secondLastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToSecondLastMatchingIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;

        // When
        Eagerly.secondLastMatching(input, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnAnOptionOfTheThirdLastElementInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Option<Integer> output = Eagerly.thirdLastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(some(6)));
    }

    @Test
    public void shouldReturnNoneForThirdLastMatchingIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = new ArrayList<Integer>();

        // When
        Option<Integer> output = Eagerly.thirdLastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForThirdLastMatchingIfNoElementsInTheSuppliedIterableMatch() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);

        // When
        Option<Integer> output = Eagerly.thirdLastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test
    public void shouldReturnNoneForThirdLastMatchingIfOnlyTwoElementsInTheSuppliedIterableMatches() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 4, 5, 6, 7);

        // When
        Option<Integer> output = Eagerly.thirdLastMatching(input, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(output, is(Option.<Integer>none()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToThirdLastMatchingIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;

        // When
        Eagerly.thirdLastMatching(input, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnTheLastNElementsFromTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eagerly.lastN(input, 3);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForLastNIfThereAreNotEnoughElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eagerly.lastN(input, 4);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForLastNIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.lastN(input, 3);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForLastNIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.lastN(input, 0);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionForLastNIfTheNumberOfElementsRequiredIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);

        // When
        Eagerly.lastN(input, -3);

        // Then an IllegalArgumentException should be thrown.
    }

    @Test
    public void shouldReturnTheLastNElementsInTheSuppliedIterableMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(6, 4, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.lastNMatching(input, 3, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAsManyElementsAsPossibleForLastNMatchingIfThereAreNotEnoughMatchingElementsInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(4, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.lastNMatching(input, 4, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForLastNMatchingIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> input = Collections.emptyList();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.lastNMatching(input, 3, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForLastNMatchingIfTheNumberOfElementsRequiredIsZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(3, 2, 1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.lastNMatching(input, 0, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForLastNMatchingIfThereAreNoElementsMatchingTheSuppliedPredicate() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 3, 5, 7);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.lastNMatching(input, 2, new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfThePredicateSuppliedToLastNMatchingIsNull() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = null;
        Integer numberOfElementsRequired = 5;

        // When
        Eagerly.lastNMatching(input, numberOfElementsRequired, predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnAllButFirstElementForRestIfIterableHasMoreThanOneElement() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4);
        Collection<Integer> expectedOutput = collectionWith(2, 3, 4);

        // When
        Collection<Integer> actualOutput = Eagerly.rest(input);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForRestIfIterableHasOnlyOneElement() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.rest(input);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyCollectionForRestIfIterableHasNoElements() throws Exception {
        // Given
        Iterable<Integer> input = Iterables.empty();
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eagerly.rest(input);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }
}
