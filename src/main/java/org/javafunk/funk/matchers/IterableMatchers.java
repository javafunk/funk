/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.matchers;

import org.hamcrest.Matcher;
import org.javafunk.funk.matchers.implementations.*;

import static java.util.Arrays.asList;

public class IterableMatchers {
    private IterableMatchers() {}

    public static <T> Matcher<Iterable<T>> hasOnlyItemsInAnyOrder(T... items) {
        return hasOnlyItemsInAnyOrder(asList(items));
    }

    public static <T> Matcher<Iterable<T>> hasOnlyItemsInAnyOrder(final Iterable<T> expectedItems) {
        return new HasOnlyItemsInAnyOrderMatcher<T>(expectedItems);
    }

    public static <T> Matcher<Iterable<T>> hasOnlyItemsInOrder(T... items) {
        return hasOnlyItemsInOrder(asList(items));
    }

    public static <T> Matcher<Iterable<T>> hasOnlyItemsInOrder(final Iterable<T> expectedItems) {
        return new HasOnlyItemsInOrderMatcher<T>(expectedItems);
    }

    public static <T> Matcher<Iterable<T>> hasAllElementsSatisfying(final SelfDescribingPredicate<T> predicate) {
        return new HasAllElementsSatisfyingMatcher<T>(predicate);
    }

    public static <T> Matcher<Iterable<T>> hasSomeElementsSatisfying(final SelfDescribingPredicate<T> predicate) {
        return new HasSomeElementsSatisfyingMatcher<T>(predicate);
    }

}
