/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Equivalence;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;

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
        assertThat(actualEqualityResult, is(expectedEqualityResult));
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
        assertThat(actualEqualityResult, is(expectedEqualityResult));
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
        assertThat(actualEqualityResult, is(expectedEqualityResult));
    }
}
