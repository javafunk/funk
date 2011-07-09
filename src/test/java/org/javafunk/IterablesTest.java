package org.javafunk;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Iterables.toList;
import static org.javafunk.Iterators.toIterable;
import static org.javafunk.Literals.listWith;

public class IterablesTest {
    @Test
    public void shouldFetchAllValuesFromTheAssociatedIteratorAndReturnThemInAList() {
        // Given
        final List<Integer> expectedList = listWith(1, 2, 3);
        Iterable<Integer> input = toIterable(new Iterator<Integer>() {
            private Integer cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < (expectedList.size());
            }

            @Override
            public Integer next() {
                Integer next = expectedList.get(cursor);
                cursor++;
                return next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        });

        // When
        List<Integer> actualList = toList(input);

        // Then
        assertThat(actualList, is(expectedList));
    }
}
