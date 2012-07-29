/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.matchers.implementations;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class BetweenMatcher<T extends Comparable<T>> extends TypeSafeMatcher<T> {
    private final T low;
    private final T high;

    public BetweenMatcher(T low, T high) {
        this.low = low;
        this.high = high;
    }

    @Override
    protected boolean matchesSafely(T number) {
        return greaterThanOrEqualTo(low).matches(number) && lessThanOrEqualTo(high).matches(number);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Value between ").appendValue(low).appendText(" and ").appendValue(high).appendText(" inclusive.");
    }
}
