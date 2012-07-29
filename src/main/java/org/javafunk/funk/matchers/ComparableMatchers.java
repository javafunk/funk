/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.matchers;

import org.hamcrest.Matcher;
import org.javafunk.funk.matchers.implementations.BetweenMatcher;

public class ComparableMatchers {
    public static <T extends Comparable<T>> Matcher<? super T> between(final T low, final T high) {
        return new BetweenMatcher<T>(low, high);
    }
}
