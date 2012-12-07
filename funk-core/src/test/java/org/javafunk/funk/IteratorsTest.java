/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterators.asIterable;
import static org.javafunk.funk.Iterators.asList;
import static org.javafunk.funk.Iterators.asMultiset;
import static org.javafunk.funk.Iterators.asSet;
import static org.javafunk.funk.Iterators.emptyIterator;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Literals.multisetWith;
import static org.javafunk.funk.Literals.setWith;

public class IteratorsTest {
    @Test
    public void shouldWrapTheSuppliedIteratorInAnIterable() {
        // Given
        Iterator<String> iterator = iterableWith("a", "b", "c").iterator();

        // When
        Iterable<String> iterable = asIterable(iterator);

        // Then
        assertThat(iterable.iterator(), is(iterator));
    }

    @Test
    public void shouldReturnAnIteratorContainingNoElements() {
        // When
        Iterator<Integer> iterator = emptyIterator();

        // Then
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldConvertTheSuppliedIteratorToAList() {
        // Given
        List<String> expectedList = listWith("a", "b", "c");
        Iterator<String> iterator = expectedList.iterator();

        // When
        List<String> actualList = asList(iterator);

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldConvertTheSuppliedIteratorToASet() {
        // Given
        Iterator<String> iterator = iterableWith("a", "a", "c", "d").iterator();
        Set<String> expectedSet = setWith("a", "c", "d");

        // When
        Set<String> actualSet = asSet(iterator);

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldConvertTheSuppliedIteratorToAMultiset() {
        // Given
        List<String> elements = listWith("a", "a", "c", "d");
        Multiset<String> expectedMultiset = multisetWith("a", "a", "c", "d");

        // When
        Multiset<String> actualMultiset = asMultiset(elements.iterator());

        // Then
        assertThat(actualMultiset, is(expectedMultiset));
    }
}
