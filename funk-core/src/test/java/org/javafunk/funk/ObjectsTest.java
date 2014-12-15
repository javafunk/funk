/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Objects.*;
import static org.junit.Assert.*;

public class ObjectsTest {
    @Test
    public void returnsTrueIfSuppliedObjectIsNull() {
        assertThat(isNull(null), is(true));
    }

    @Test
    public void returnsFalseIfSuppliedObjectIsNotNull() {
        assertThat(isNull(new Object()), is(false));
    }

    @Test
    public void returnsTrueIfSuppliedObjectIsNotNull() {
        assertThat(isNotNull(new Object()), is(true));
    }

    @Test
    public void returnsFalseIfSuppliedObjectIsNull() {
        assertThat(isNotNull(null), is(false));
    }

    @Test
    public void returnsPredicateWhichEvaluatesTrueWhenPassedNull() {
        assertThat(whereNull().evaluate(null), is(true));
    }

    @Test
    public void returnsPredicateWhichEvaluatesFalseWhenPassedNonNullObject() {
        assertThat(whereNull().evaluate(new Object()), is(false));
    }

    @Test
    public void returnsPredicateWhichEvaluatesTrueWhenPassedNonNullObject() {
        assertThat(whereNotNull().evaluate(new Object()), is(true));
    }

    @Test
    public void returnsPredicateWhichEvaluatesFalseWhenPassedNull() {
        assertThat(whereNotNull().evaluate(null), is(false));
    }

    @Test
    public void returnsMapperToStringValue() {
        // Given
        Object value = new Object() {
            @Override public String toString() {
                return "some custom toString";
            }
        };

        // When
        String result = Objects.toStringValue().map(value);

        // Then
        assertThat(result, is("some custom toString"));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenNullPassedToToStringValue() {
        // Given
        Object value = null;

        // When
        Objects.toStringValue().map(value);

        // Then a NullPointerException is thrown
    }

    @Test
    public void returnsConcreteTypeToStringValueMapper() {
        // Given
        Integer value = 123;
        Mapper<Integer, String> mapper = Objects.toStringValueFor(Integer.class);

        // When
        String result = mapper.map(value);

        // Then
        assertThat(result, is("123"));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenNullPassedToConcreteToStringValue() {
        // Given
        BigDecimal value = null;

        // When
        Objects.toStringValueFor(BigDecimal.class).map(value);

        // Then a NullPointerException is thrown
    }
}