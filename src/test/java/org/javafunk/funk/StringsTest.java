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

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Strings.join;

public class StringsTest {
    @Test
    public void shouldJoinAllStringsInTheCollectionUsingTheSeparator() {
        // Given
        Collection<String> strings = listWith("First", "Second", "Third");
        String separator = ", ";

        // When
        String joinedStrings = join(strings, separator);

        // Then
        assertThat(joinedStrings, is("First, Second, Third"));
    }

    @Test
    public void shouldJoinAllStringsInTheCollectionWithNoSeparator() {
        // Given
        Collection<String> strings = listWith("He", "ll", "o");

        // When
        String joinedStrings = join(strings);

        // Then
        assertThat(joinedStrings, is("Hello"));
    }

    @Test
    public void shouldJoinAllPassedStringWithNoSeparator() {
        // When
        String joinedStrings = join("Goo", "db", "ye");

        // Then
        assertThat(joinedStrings, is("Goodbye"));
    }
}
