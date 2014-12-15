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
}