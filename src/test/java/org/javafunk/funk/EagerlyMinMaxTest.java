/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.EagerlyMinMaxTest.NonComparableObject.nonComparableObject;
import static org.javafunk.funk.Literals.iterable;
import static org.javafunk.funk.Literals.iterableWith;

public class EagerlyMinMaxTest {
    @Test
    public void shouldReturnTheMinimumValue() throws Exception {
        // Given
        Iterable<String> iterable = iterableWith("b", "a", "c");

        // When
        String actual = Eagerly.min(iterable);

        // Then
        assertThat(actual, is("a"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfAnEmptyIterableIsSuppliedToMin() throws Exception {
        // Given
        Iterable<Integer> iterable = iterable();

        // When
        Eagerly.min(iterable);

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldReturnTheMinimumValueAccordingToTheSuppliedComparator() throws Exception {
        // Given

        Iterable<NonComparableObject> iterable = iterableWith(
                nonComparableObject("aaaa"),
                nonComparableObject("aa"),
                nonComparableObject("aaa"),
                nonComparableObject("aaaaa"));

        // When
        NonComparableObject actual = Eagerly.min(iterable, new Comparator<NonComparableObject>() {
            public int compare(NonComparableObject first, NonComparableObject second) {
                return first.length() - second.length();
            }
        });

        // Then
        MatcherAssert.assertThat(actual, Matchers.is(nonComparableObject("aa")));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfAnEmptyIterableAndAComparatorIsSuppliedToMin() throws Exception {
        // Given
        Iterable<NonComparableObject> iterable = iterable();

        // When

        Eagerly.min(iterable, new Comparator<NonComparableObject>() {
            @Override public int compare(NonComparableObject first, NonComparableObject second) {
                return first.length() - second.length();
            }
        });

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldReturnTheMaximumValue() throws Exception {
        // Given
        Iterable<Integer> iterable = iterableWith(3, 2, 6);

        // When
        Integer actual = Eagerly.max(iterable);

        // Then
        assertThat(actual, is(6));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfAnEmptyIterableIsSuppliedToMax() throws Exception {
        // Given
        Iterable<Integer> iterable = iterable();

        // When
        Eagerly.max(iterable);

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldReturnTheMaximumValueAccordingToTheSuppliedComparator() throws Exception {
        // Given
        Iterable<NonComparableObject> iterable = iterableWith(
                nonComparableObject("aaaa"),
                nonComparableObject("aa"),
                nonComparableObject("aaa"),
                nonComparableObject("aaaaa"));

        // When
        NonComparableObject actual = Eagerly.max(iterable, new Comparator<NonComparableObject>() {
            public int compare(NonComparableObject first, NonComparableObject second) {
                return first.length() - second.length();
            }
        });

        // Then
        MatcherAssert.assertThat(actual, Matchers.is(nonComparableObject("aaaaa")));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfAnEmptyIterableAndAComparatorIsSuppliedToMax() throws Exception {
        // Given
        Iterable<NonComparableObject> iterable = iterable();

        // When

        Eagerly.max(iterable, new Comparator<NonComparableObject>() {
            @Override public int compare(NonComparableObject first, NonComparableObject second) {
                return first.length() - second.length();
            }
        });

        // Then a NoSuchElementException is thrown
    }

    static class NonComparableObject {
        private String field;

        public static NonComparableObject nonComparableObject(String field) {
            return new NonComparableObject(field);
        }

        private NonComparableObject(String field) {
            this.field = field;
        }

        public int length() {
            return field.length();
        }

        @Override
        public boolean equals(Object other) {
            return EqualsBuilder.reflectionEquals(this, other);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
