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
import org.javafunk.funk.matchers.SelfDescribingPredicate;

import static org.javafunk.funk.Eagerly.all;

public class HasAllElementsSatisfyingMatcher<T> extends TypeSafeMatcher<Iterable<T>> {
    private final SelfDescribingPredicate<T> predicate;

    public HasAllElementsSatisfyingMatcher(SelfDescribingPredicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    protected boolean matchesSafely(Iterable<T> items) {
        return all(items, predicate);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Collection with all items matching predicate ").appendValue(predicate.describe());
    }
}
