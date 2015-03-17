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

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.javafunk.funk.Iterables.asList;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.junit.Assert.assertThat;

public class EagerlyMapCatTest {
    @Test
    public void shouldMapCat() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3);

        // When
        Mapper<Integer, Iterable<String>> mapfn = new Mapper<Integer, Iterable<String>>() {
            public Iterable<String> map(Integer input) {
                return collectionWith(String.valueOf(input), "another");
            }
        };

        Iterable<String> actual = Eagerly.mapCat(input, mapfn);

        // Then
        assertThat(asList(actual), hasItems("1", "another", "2", "another", "3", "another"));
    }
}
