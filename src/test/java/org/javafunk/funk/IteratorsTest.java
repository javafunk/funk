package org.javafunk.funk;

import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterators.*;
import static org.javafunk.funk.Literals.*;

public class IteratorsTest {
    @Test
    public void shouldWrapTheSuppliedIteratorInAnIterable() {
        // Given
        Iterator<String> iterator = listWith("a", "b", "c").iterator();

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
        Iterator<String> iterator = listWith("a", "a", "c", "d").iterator();
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
