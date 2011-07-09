package org.javafunk;

import org.javafunk.collections.Bag;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Iterables.*;
import static org.javafunk.Literals.*;

public class IterablesTest {
    @Test
    public void shouldConvertTheSuppliedIterableToAList() {
        // Given
        Iterable<Integer> iterable = basicIterableOver(1, 2, 2, 3, 3, 3);
        List<Integer> expectedList = listWith(1, 2, 2, 3, 3, 3);

        // When
        List<Integer> actualList = asList(iterable);

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldMaterialiseTheSuppliedIterableToACollection() throws Exception {
        // Given
        Iterable<Integer> iterable = basicIterableOver(1, 2, 2, 3, 3, 3);
        Collection<Integer> expectedCollection = listWith(1, 2, 2, 3, 3, 3);

        // When
        Collection<Integer> actualCollection = materialize(iterable);

        // Then
        assertThat(actualCollection, is(expectedCollection));
    }

    @Test
    public void shouldConvertTheSuppliedIterableToASet() throws Exception {
        // Given
        Iterable<Integer> iterable = basicIterableOver(1, 2, 2, 3, 3, 3);
        Set<Integer> expectedSet = setWith(1, 2, 3);

        // When
        Set<Integer> actualSet = asSet(iterable);

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldConvertTheSuppliedIterableToABag() throws Exception {
        // Given
        Iterable<Integer> iterable = basicIterableOver(1, 2, 2, 3, 3, 3);
        Bag<Integer> expectedBag = bagWith(1, 2, 2, 3, 3, 3);

        // When
        Bag<Integer> actualBag = asBag(iterable);

        // Then
        assertThat(actualBag, is(expectedBag));
    }

    private static <T> Iterable<T> basicIterableOver(final T... elements) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private Integer cursor = 0;

                    @Override
                    public boolean hasNext
                            () {
                        return cursor < (elements.length);
                    }

                    @Override
                    public T next
                            () {
                        T next = elements[cursor];
                        cursor++;
                        return next;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }


}
