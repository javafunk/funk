/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Strings.join;

public class StringsTest {
    @Test
    public void joinsAllStringsInTheCollectionUsingTheSeparator() {
        // Given
        Collection<String> strings = collectionWith("First", "Second", "Third");
        String separator = ", ";

        // When
        String joinedStrings = join(strings, separator);

        // Then
        assertThat(joinedStrings, is("First, Second, Third"));
    }

    @Test(expected = NullPointerException.class)
    public void throwsANullPointerExceptionIfIterablePassedToJoinWithSeparatorIsNull() throws Exception {
        // Given
        Iterable<String> input = null;
        String separator = ".";

        // When
        join(input, separator);

        // Then a NullPointerException is thrown
    }

    @Test
    public void joinsAllStringsInTheCollectionWithNoSeparator() {
        // Given
        Collection<String> strings = collectionWith("He", "ll", "o");

        // When
        String joinedStrings = join(strings);

        // Then
        assertThat(joinedStrings, is("Hello"));
    }

    @Test(expected = NullPointerException.class)
    public void throwsANullPointerExceptionIfIterablePassedToJoinIsNull() throws Exception {
        // Given
        Iterable<String> input = null;

        // When
        join(input);

        // Then a NullPointerException is thrown
    }

    @Test
    public void joinsAllPassedStringWithNoSeparator() {
        // When
        String joinedStrings = join("Goo", "db", "ye");

        // Then
        assertThat(joinedStrings, is("Goodbye"));
    }

    @Test
    public void rendersNullsAsEmptyStrings() throws Exception {
        // Given
        Collection<String> strings = collectionWith("He", null, "o");

        // When
        String joinedStrings = join(strings);

        // Then
        assertThat(joinedStrings, is("Heo"));
    }

    @Test
    public void convertsObjectToStringIfNotNullForToStringOrNull() {
        // Given
        Object value = new Object() {
            @Override public String toString() {
                return "string value";
            }
        };

        // When
        String result = Strings.toStringOrNull(value);

        // Then
        assertThat(result, is("string value"));
    }

    @Test
    public void convertsObjectToNullIfNullForToStringOrNull() {
        // Given
        Object value = null;

        // When
        String result = Strings.toStringOrNull(value);

        // Then
        assertThat(result, is(nullValue()));
    }

    @Test
    public void convertsObjectToStringIfNotNullForToStringOr() {
        // Given
        Object value = new Object() {
            @Override public String toString() {
                return "string value";
            }
        };
        String alternative = "alternative";

        // When
        String result = Strings.toStringOr(alternative, value);

        // Then
        assertThat(result, is("string value"));
    }

    @Test
    public void convertsObjectToAlternativeIfNullForToStringOr() {
        // Given
        Object value = null;
        String alternative = "alternative";

        // When
        String result = Strings.toStringOr(alternative, value);

        // Then
        assertThat(result, is(alternative));
    }

    @Test
    public void convertsObjectToStringIfNotNullForToStringOrEmpty() {
        // Given
        Object value = new Object() {
            @Override public String toString() {
                return "string value";
            }
        };

        // When
        String result = Strings.toStringOrEmpty(value);

        // Then
        assertThat(result, is("string value"));
    }

    @Test
    public void convertsObjectToAlternativeIfNullForToStringOrEmpty() {
        // Given
        Object value = null;

        // When
        String result = Strings.toStringOrEmpty(value);

        // Then
        assertThat(result, is(""));
    }

    @Test
    public void convertsBigDecimalToStringIfNotNullForToStringOrNull() {
        // Given
        BigDecimal value = new BigDecimal("1234.5")
                .movePointRight(5)
                .setScale(-5, BigDecimal.ROUND_HALF_EVEN);

        // When
        String result = Strings.toStringOrNull(value);

        // Then
        assertThat(result, is("123400000"));
    }

    @Test
    public void convertsBigDecimalToNullIfNullForToStringOrNull() {
        // Given
        BigDecimal value = null;

        // When
        String result = Strings.toStringOrNull(value);

        // Then
        assertThat(result, is(nullValue()));
    }

    @Test
    public void convertsBigDecimalToStringIfNotNullForToStringOr() {
        // Given
        BigDecimal value = new BigDecimal("1234.5")
                .movePointRight(5)
                .setScale(-5, BigDecimal.ROUND_HALF_EVEN);
        String alternative = "alternative";

        // When
        String result = Strings.toStringOr(alternative, value);

        // Then
        assertThat(result, is("123400000"));
    }

    @Test
    public void convertsBigDecimalToAlternativeIfNullForToStringOr() {
        // Given
        BigDecimal value = null;
        String alternative = "alternative";

        // When
        String result = Strings.toStringOr(alternative, value);

        // Then
        assertThat(result, is(alternative));
    }

    @Test
    public void convertsBigDecimalToStringIfNotNullForToStringOrEmpty() {
        // Given
        BigDecimal value = new BigDecimal("1234.5")
                .movePointRight(5)
                .setScale(-5, BigDecimal.ROUND_HALF_EVEN);

        // When
        String result = Strings.toStringOrEmpty(value);

        // Then
        assertThat(result, is("123400000"));
    }

    @Test
    public void convertsBigDecimalToAlternativeIfNullForToStringOrEmpty() {
        // Given
        BigDecimal value = null;

        // When
        String result = Strings.toStringOrEmpty(value);

        // Then
        assertThat(result, is(""));
    }

    @Test
    public void returnsTrueForIsNullOrBlankIfNull() {
        // Given
        String value = null;

        // When
        boolean result = Strings.isNullOrBlank(value);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void returnsTrueForIsNullOrBlankIfBlank() {
        // Given
        String value = "";

        // When
        boolean result = Strings.isNullOrBlank(value);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void returnsTrueForIsNullOrBlankIfWhitespace() {
        // Given
        String value = "     ";

        // When
        boolean result = Strings.isNullOrBlank(value);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void returnsFalseForIsNullOrBlankIfPresent() {
        // Given
        String value = "some string";

        // When
        boolean result = Strings.isNullOrBlank(value);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void returnsAPredicateWhichReturnsTrueIfStringIsBlankForWhereBlank() {
        // Given
        String value = "   ";

        // When
        boolean result = Strings.whereBlank().evaluate(value);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void returnsAPredicateWhichReturnsFalseIfStringIsPresentForWhereBlank() {
        // Given
        String value = "some string";

        // When
        boolean result = Strings.whereBlank().evaluate(value);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void returnsAPredicateWhichReturnsTrueIfStringIsBlankForWhereNullOrBlank() {
        // Given
        String value = "   ";

        // When
        boolean result = Strings.whereNullOrBlank().evaluate(value);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void returnsAPredicateWhichReturnsTrueIfStringIsNullForWhereNullOrBlank() {
        // Given
        String value = null;

        // When
        boolean result = Strings.whereNullOrBlank().evaluate(value);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void returnsAPredicateWhichReturnsFalseIfStringIsPresentForWhereNullOrBlank() {
        // Given
        String value = "some string";

        // When
        boolean result = Strings.whereNullOrBlank().evaluate(value);

        // Then
        assertThat(result, is(false));
    }
}
