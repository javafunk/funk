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

import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class LazilyNthRestTest {
    @Test
    public void returnsRestOfTheIterableAfterGivenPosition(){
        Iterable<String> iterable = iterableWith("a", "b", "c", "d");
        Iterable<String> expectedRest = iterableWith("c", "d");

        Iterable<String> rest = Lazily.nthRest(iterable, 2);
        
        assertThat(materialize(rest), hasOnlyItemsInOrder(expectedRest));
    }

    @Test
    public void returnsIterableForPositionZero(){
        Iterable<String> iterable = iterableWith("a", "b");
        Iterable<String> expectedRest = iterableWith("a", "b");

        Iterable<String> rest = Lazily.nthRest(iterable, 0);

        assertThat(materialize(rest), hasOnlyItemsInOrder(expectedRest));
    }

    @Test
    public void returnsEmptyIterableForAnIterableWithOneElementAndStartIsHigherThanZero(){
        Iterable<String> iterable = iterableWith("a");
        Iterable<String> expectedRest = Iterables.empty();

        Iterable<String> rest = Lazily.nthRest(iterable, 2);

        assertThat(materialize(rest), hasOnlyItemsInOrder(expectedRest));
    }

    @Test
    public void returnsEmptyIterableForAnEmptyIterable(){
        Iterable<String> iterable = Iterables.empty();
        Iterable<String> expectedRest = Iterables.empty();

        Iterable<String> rest = Lazily.nthRest(iterable, 3);

        assertThat(materialize(rest), hasOnlyItemsInOrder(expectedRest));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionIfNullIsPassedToRest() throws Exception {
        Lazily.nthRest(null, 2);
    }
}
