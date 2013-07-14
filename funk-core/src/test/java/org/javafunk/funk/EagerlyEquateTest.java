/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Equivalence;
import org.javafunk.funk.functors.predicates.BinaryPredicate;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class EagerlyEquateTest {
    @Test
    public void shouldReturnACollectionContainingTheResultOfEquatingEachElementUsingTheSuppliedEquator() throws Exception {
        // Given
        Iterable<String> first = iterableWith("Dog", "Cat", "Goldfish");
        Iterable<String> second = iterableWith("DOG", "BAT", "GOLDFISH");
        Collection<Boolean> expectedEqualityResult = collectionWith(true, false, true);

        // When
        Collection<Boolean> actualEqualityResult = Eagerly.equate(first, second, new Equivalence<String>() {
            public boolean equal(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });

        // Then
        assertThat(actualEqualityResult, hasOnlyItemsInOrder(expectedEqualityResult));
    }

    @Test
    public void shouldOnlyEquateAsManyElementsAsPossibleIfTheFirstIterableIsShorterThanTheSecond() throws Exception {
        // Given
        Iterable<String> first = iterableWith("Dog", "Cat", "Goldfish");
        Iterable<String> second = iterableWith("DOG", "BAT", "GOLDFISH", "HORSE", "PIG");
        Collection<Boolean> expectedEqualityResult = collectionWith(true, false, true);

        // When
        Collection<Boolean> actualEqualityResult = Eagerly.equate(first, second, new Equivalence<String>() {
            public boolean equal(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });

        // Then
        assertThat(actualEqualityResult, hasOnlyItemsInOrder(expectedEqualityResult));
    }

    @Test
    public void shouldOnlyEquateAsManyElementsAsPossibleIfTheSecondIterableIsShorterThanTheFirst() throws Exception {
        // Given
        Iterable<String> first = iterableWith("Dog", "Cat", "Goldfish", "Horse", "Pig");
        Iterable<String> second = iterableWith("DOG", "BAT", "GOLDFISH");
        Collection<Boolean> expectedEqualityResult = collectionWith(true, false, true);

        // When
        Collection<Boolean> actualEqualityResult = Eagerly.equate(first, second, new Equivalence<String>() {
            public boolean equal(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });

        // Then
        assertThat(actualEqualityResult, hasOnlyItemsInOrder(expectedEqualityResult));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSuppliedEquivalenceIsNull() throws Exception {
        // Given
        Iterable<String> first = iterableWith("Dog", "Cat", "Goldfish", "Horse", "Pig");
        Iterable<String> second = iterableWith("DOG", "BAT", "GOLDFISH");
        Equivalence<String> equivalence = null;

        // When
        Eagerly.equate(first, second, equivalence);

        // Then a NullPointerException should be thrown
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfTheFirstIterableSuppliedToEquivalenceEquateIsNull() throws Exception {
        // Given
        Iterable<String> first = null;
        Iterable<String> second = iterableWith("DOG", "BAT", "GOLDFISH");
        Equivalence<String> equivalence = new Equivalence<String>() {
            public boolean equal(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        };

        // When
        Eagerly.equate(first, second, equivalence);

        // Then a NullPointerException should be thrown
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfTheSecondIterableSuppliedToEquivalenceEquateIsNull() throws Exception {
        // Given
        Iterable<String> first = iterableWith("DOG", "BAT", "GOLDFISH");
        Iterable<String> second = null;
        Equivalence<String> equivalence = new Equivalence<String>() {
            public boolean equal(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        };

        // When
        Eagerly.equate(first, second, equivalence);

        // Then a NullPointerException should be thrown
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfTheFirstIterableSuppliedToBinaryPredicateEquateIsNull() throws Exception {
        // Given
        Iterable<String> first = null;
        Iterable<String> second = iterableWith("DOG", "BAT", "GOLDFISH");
        BinaryPredicate<String, String> equivalence = new BinaryPredicate<String, String>() {
            @Override public boolean evaluate(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        };

        // When
        Eagerly.equate(first, second, equivalence);

        // Then a NullPointerException should be thrown
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfTheSecondIterableSuppliedToBinaryPredicateEquateIsNull() throws Exception {
        // Given
        Iterable<String> first = iterableWith("DOG", "BAT", "GOLDFISH");
        Iterable<String> second = null;
        BinaryPredicate<String, String> equivalence = new BinaryPredicate<String, String>() {
            @Override public boolean evaluate(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        };

        // When
        Eagerly.equate(first, second, equivalence);

        // Then a NullPointerException should be thrown
    }
}
