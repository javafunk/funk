package org.javafunk;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.listWith;
import static org.javafunk.StringUtils.join;

public class StringUtilsTest {
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
