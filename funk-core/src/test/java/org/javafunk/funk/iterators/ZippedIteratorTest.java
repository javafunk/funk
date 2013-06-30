package org.javafunk.funk.iterators;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZippedIteratorTest {
    @Test
    @SuppressWarnings("unchecked")
    public void shouldIncludeIteratorsInToStringRepresentation() throws Exception {
        // Given
        Iterable<Iterator<?>> iterators = (Iterable<Iterator<?>>) mock(Iterable.class);
        ZippedIterator iterator = new ZippedIterator(iterators);

        when(iterators.toString()).thenReturn("the-iterators");

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("the-iterators"));
    }
}
