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
import org.javafunk.funk.matchers.implementations.IsBeanWithSameAttributesAsMatcher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class ObjectMatchers {
    public static <T> Matcher<T> isBeanWithSameAttributesAs(final T expectedObject) {
        return isBeanWithSameAttributesAs(expectedObject, Collections.<String>emptySet());
    }

    public static <T> Matcher<T> isBeanWithSameAttributesAs(final T expectedObject, final Set<String> ignoreProperties) {
        return new IsBeanWithSameAttributesAsMatcher<T>(expectedObject, ignoreProperties);
    }

    public static Set<String> ignoring(String... ignoreProperties) {
        return new HashSet<String>(asList(ignoreProperties));
    }
}
