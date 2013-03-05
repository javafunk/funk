package org.javafunk.funk;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.Literals.iterableOf;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertTrue;

public class EagerlyReverseTest {

    @Test
    public void shouldReverseTheIterable() throws Exception {
        // Given
        Iterable<String> iterable = iterableWith("a", "b", "c");

        // When
        Collection<String> actual = Eagerly.reverse(iterable);

        // Then
        assertThat(actual, hasOnlyItemsInOrder("c","b","a"));
    }

    @Test
    public void shouldKeepTheIterableReversed() throws Exception {
        // Given
        ArrayList<String> listToModify = new ArrayList<String>();
        listToModify.add("a");
        listToModify.add("b");
        listToModify.add("c");

        // When
        Collection<String> reversedListBeforeRemoval = Eagerly.reverse(listToModify);

        Iterator<String> iterator = listToModify.iterator();
        iterator.next();
        iterator.remove();

        // Then
        assertThat(reversedListBeforeRemoval, hasOnlyItemsInOrder("c", "b", "a"));
        assertThat(listToModify, hasOnlyItemsInOrder("b", "c"));
    }

    @Test
    public void shouldReverseNulls() throws Exception {
        // Given
        Iterable<String> iterable = iterableWith("a", "b", null, "d");

        // When
        Collection<String> actual = Eagerly.reverse(iterable);

        // Then
        assertThat(actual, hasOnlyItemsInOrder("d", null, "b", "a"));
    }

    @Test
    public void shouldReturnEmptyCollectionWhenReversingAnEmptyIterable() throws Exception {
        // Given
        Iterable<String> iterable = iterableOf(String.class);

        // When
        Collection<String> actual = Eagerly.reverse(iterable);

        // Then
        assertTrue(actual.isEmpty());
    }
}
