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
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableWith;

public class LazilyEquateTest {
    @Test
    public void shouldReturnAnIterableContainingTheResultOfEquatingEachElementInTheSuppliedIterables() throws Exception {
        // Given
        Iterable<String> first = iterableWith("Dog", "Cat", "Goldfish");
        Iterable<String> second = iterableWith("DOG", "BAT", "GOLDFISH");
        
        // When
        Iterable<Boolean> equateResultIterable = Lazily.equate(first, second, new Equivalence<String>() {
            public boolean equal(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });
        Iterator<Boolean> equateResultIterator = equateResultIterable.iterator();

        // Then
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(false));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(false));
    }

    @Test
    public void shouldOnlyEquateAsManyElementsAsPossibleIfTheFirstIterableIsShorterThanTheSecond() throws Exception {
        // Given
        Iterable<String> first = iterableWith("Dog", "Cat", "Goldfish");
        Iterable<String> second = iterableWith("DOG", "BAT", "GOLDFISH", "HORSE", "PIG");

        // When
        Iterable<Boolean> equateResultIterable = Lazily.equate(first, second, new Equivalence<String>() {
            public boolean equal(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });
        Iterator<Boolean> equateResultIterator = equateResultIterable.iterator();

        // Then
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(false));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(false));
    }

    @Test
    public void shouldOnlyEquateAsManyElementsAsPossibleIfTheSecondIterableIsShorterThanTheFirst() throws Exception {
        // Given
        Iterable<String> first = iterableWith("Dog", "Cat", "Goldfish", "Horse", "Pig");
        Iterable<String> second = iterableWith("DOG", "BAT", "GOLDFISH");

        // When
        Iterable<Boolean> equateResultIterable = Lazily.equate(first, second, new Equivalence<String>() {
            public boolean equal(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });
        Iterator<Boolean> equateResultIterator = equateResultIterable.iterator();

        // Then
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(false));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<String> first = iterableWith("Dog", "Cat", "Goldfish");
        Iterable<String> second = iterableWith("DOG", "BAT", "GOLDFISH");

        // When
        Iterable<Boolean> iterable = Lazily.equate(first, second, new Equivalence<String>() {
            public boolean equal(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });
        Iterator<Boolean> iterator1 = iterable.iterator();
        Iterator<Boolean> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(true));
        assertThat(iterator1.next(), is(false));
        assertThat(iterator2.next(), is(true));
        assertThat(iterator1.next(), is(true));
        assertThat(iterator2.next(), is(false));
        assertThat(iterator2.next(), is(true));
    }
}
