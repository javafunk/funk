package org.javafunk;

import org.javafunk.Lazy;
import org.javafunk.functors.Equator;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.listWith;

public class LazyEquateTest {
    @Test
    public void shouldReturnAnIterableContainingTheResultOfEquatingEachElementInTheSuppliedIterables() throws Exception {
        // Given
        Iterable<String> first = listWith("Dog", "Cat", "Goldfish");
        Iterable<String> second = listWith("DOG", "BAT", "GOLDFISH");
        
        // When
        Iterable<Boolean> equateResultIterable = Lazy.equate(first, second, new Equator<String>() {
            public boolean equate(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });
        Iterator<Boolean> equateResultIterator = equateResultIterable.iterator();

        // Then
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(false));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(false));
    }

    @Test
    public void shouldOnlyEquateAsManyElementsAsPossibleIfTheFirstIterableIsShorterThanTheSecond() throws Exception {
        // Given
        Iterable<String> first = listWith("Dog", "Cat", "Goldfish");
        Iterable<String> second = listWith("DOG", "BAT", "GOLDFISH", "HORSE", "PIG");

        // When
        Iterable<Boolean> equateResultIterable = Lazy.equate(first, second, new Equator<String>() {
            public boolean equate(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });
        Iterator<Boolean> equateResultIterator = equateResultIterable.iterator();

        // Then
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(false));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(false));
    }

    @Test
    public void shouldOnlyEquateAsManyElementsAsPossibleIfTheSecondIterableIsShorterThanTheFirst() throws Exception {
        // Given
        Iterable<String> first = listWith("Dog", "Cat", "Goldfish", "Horse", "Pig");
        Iterable<String> second = listWith("DOG", "BAT", "GOLDFISH");

        // When
        Iterable<Boolean> equateResultIterable = Lazy.equate(first, second, new Equator<String>() {
            public boolean equate(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });
        Iterator<Boolean> equateResultIterator = equateResultIterable.iterator();

        // Then
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(false));
        assertThat(equateResultIterator.hasNext(), is(true));
        assertThat(equateResultIterator.next(), is(true));
        assertThat(equateResultIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<String> first = listWith("Dog", "Cat", "Goldfish");
        Iterable<String> second = listWith("DOG", "BAT", "GOLDFISH");

        // When
        Iterable<Boolean> iterable = Lazy.equate(first, second, new Equator<String>() {
            public boolean equate(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });
        Iterator<Boolean> iterator1 = iterable.iterator();
        Iterator<Boolean> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(true));
        assertThat(iterator1.next(), is(false));
        assertThat(iterator2.next(), is(true));
        assertThat(iterator1.next(), is(true));
        assertThat(iterator2.next(), is(false));
        assertThat(iterator2.next(), is(true));
    }
}
