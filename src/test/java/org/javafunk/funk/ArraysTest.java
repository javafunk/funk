/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.listWith;

public class ArraysTest {
    @Test
    public void shouldConvertTheSuppliedArrayToAList() throws Exception {
        // Given
        String[] array = {"a", "b", "c"};
        List<String> expectedList = listWith("a", "b", "c");

        // When
        List<String> actualList = Arrays.asList(array);

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldConvertTheSuppliedArrayToAnIterable() throws Exception {
        // Given
        String[] array = {"a", "b", "c"};

        // When
        Iterable<String> iterable = Arrays.asIterable(array);
        Iterator<String> iterator = iterable.iterator();

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("a"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("b"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("c"));
        assertThat(iterator.hasNext(), is(false));
    }
}
