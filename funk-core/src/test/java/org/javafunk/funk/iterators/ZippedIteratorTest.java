package org.javafunk.funk.iterators;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.iteratorWith;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZippedIteratorTest {
    @Test
    @SuppressWarnings("unchecked")
    public void shouldIncludeIteratorsInToStringRepresentation() throws Exception {
        // Given
        Iterable<Iterator<?>> iterators = (Iterable<Iterator<?>>) mock(Iterable.class, RETURNS_MOCKS);
        ZippedIterator iterator = new ZippedIterator(iterators);

        when(iterators.toString()).thenReturn("the-iterators");

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("the-iterators"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfIterablePassedAtConstructionTimeIsNull() throws Exception {
        // Given
        Iterable<Iterator<?>> iterators = null;

        // When
        new ZippedIterator(iterators);

        // Then a NullPointerException is thrown
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfAnyOfTheIteratorsInTheSuppliedIterableAreNull() throws Exception {
        // Given
        Iterator<Integer> first = iteratorWith(1, 2, 3);
        Iterator<String> second = null;
        Iterable<? extends Iterator<?>> iterators = iterableWith(first, second);

        // When
        new ZippedIterator(iterators);

        // Then a NullPointerException is thrown
    }
}
