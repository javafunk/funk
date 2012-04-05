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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.EagerlyMinMaxTest.NonComparableObject.nonComparableObject;

public class EagerlyMinMaxTest {
    @Test
    public void shouldReturnTheMinimumValue() throws Exception {
        // Given
        List<String> list = listWith("b", "a", "c");

        // When
        String actual = Eagerly.min(list);

        // Then
        assertThat(actual, is("a"));
    }

    @Test
    public void shouldReturnTheMinimumValueAccordingToTheSuppliedComparator() throws Exception {
        // Given

        List<NonComparableObject> list = Literals.listWith(
                nonComparableObject("aaaa"),
                nonComparableObject("aa"),
                nonComparableObject("aaa"),
                nonComparableObject("aaaaa"));

        // When
        NonComparableObject actual = Eagerly.min(list, new Comparator<NonComparableObject>() {
            public int compare(NonComparableObject first, NonComparableObject second) {
                return first.length() - second.length();
            }
        });

        // Then
        MatcherAssert.assertThat(actual, Matchers.is(nonComparableObject("aa")));
    }

    @Test
    public void shouldReturnTheMaximumValue() throws Exception {
        // Given
        List<Integer> list = listWith(3, 2, 6);

        // When
        Integer actual = Eagerly.max(list);

        // Then
        assertThat(actual, is(6));
    }

    @Test
    public void shouldReturnTheMaximumValueAccordingToTheSuppliedComparator() throws Exception {
        // Given
        List<NonComparableObject> list = Literals.listWith(
                nonComparableObject("aaaa"),
                nonComparableObject("aa"),
                nonComparableObject("aaa"),
                nonComparableObject("aaaaa"));

        // When
        NonComparableObject actual = Eagerly.max(list, new Comparator<NonComparableObject>() {
            public int compare(NonComparableObject first, NonComparableObject second) {
                return first.length() - second.length();
            }
        });

        // Then
        MatcherAssert.assertThat(actual, Matchers.is(nonComparableObject("aaaaa")));
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
