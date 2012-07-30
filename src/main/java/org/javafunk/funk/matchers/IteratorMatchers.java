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
import org.javafunk.funk.matchers.implementations.IsIteratorWithSameElementsAsMatcher;

import java.util.Iterator;

public class IteratorMatchers {
    public static <E> Matcher<? super Iterator<E>> isIteratorWithSameElementsAs(final Iterator<E> expected) {
        return new IsIteratorWithSameElementsAsMatcher<E>(expected);
    }
}
