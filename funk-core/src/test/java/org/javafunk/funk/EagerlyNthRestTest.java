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

import java.util.Collection;

import static org.javafunk.funk.Literals.collection;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class EagerlyNthRestTest {
    @Test
    public void returnsRestOfTheCollectionAfterGivenPosition(){
        Collection<String> collection = collectionWith("a", "b", "c", "d");
        Collection<String> expectedRest = collectionWith("c", "d");

        Collection<String> rest = Eagerly.nthRest(collection, 2);
        
        assertThat(rest, hasOnlyItemsInOrder(expectedRest));
    }

    @Test
    public void returnsCollectionForPositionZero(){
        Collection<String> collection = collectionWith("a", "b");
        Collection<String> expectedRest = collectionWith("a", "b");

        Collection<String> rest = Eagerly.nthRest(collection, 0);

        assertThat(rest, hasOnlyItemsInOrder(expectedRest));
    }

    @Test
    public void returnsEmptyIterableForAnIterableWithOneElementAndStartIsHigherThanZero(){
        Collection<String> collection = collectionWith("a");
        Collection<String> expectedRest = collection();

        Collection<String> rest = Eagerly.nthRest(collection, 2);

        assertThat(rest, hasOnlyItemsInOrder(expectedRest));
    }

    @Test
    public void returnsEmptyCollectionForAnEmptyCollection(){
        Collection<String> collection = collection();
        Collection<String> expectedRest = collection();

        Collection<String> rest = Eagerly.nthRest(collection, 3);

        assertThat(rest, hasOnlyItemsInOrder(expectedRest));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionIfNullIsPassedToRest() throws Exception {
        Eagerly.nthRest(null, 2);
    }
}
