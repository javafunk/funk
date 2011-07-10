package org.javafunk;

import org.javafunk.Lazy;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.javafunk.Literals.listWith;

public class LazyCycleRepeatTest {
    @Test
    public void shouldCycleTheSuppliedIterableInfinitely() {
        // Given
        Iterable<String> input = listWith("Red", "Green", "Blue");

        // When
        Iterable<String> output = Lazy.cycle(input);
        Iterator<String> cyclicIterator = output.iterator();

        // Then
        for(int i = 0; i < 20; i++) {
            assertThat(cyclicIterator.next(), is("Red"));
            assertThat(cyclicIterator.next(), is("Green"));
            assertThat(cyclicIterator.next(), is("Blue"));
        }
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnCycleIterableReturningDifferentIterators() throws Exception {
        // Given
        Iterable<String> input = listWith("Red", "Green", "Blue");

        // When
        Iterable<String> iterable = Lazy.cycle(input);
        Iterator<String> iterator1 = iterable.iterator();
        Iterator<String> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is("Red"));
        assertThat(iterator1.next(), is("Green"));
        assertThat(iterator2.next(), is("Red"));
        assertThat(iterator1.next(), is("Blue"));
        assertThat(iterator2.next(), is("Green"));
        assertThat(iterator1.next(), is("Red"));
    }

    @Test
    public void shouldRepeatTheSuppliedIterableTheSpecifiedNumberOfTimes() throws Exception {
        // Given
        Iterable<String> input = listWith("Black", "White");

        // When
        Iterable<String> output = Lazy.repeat(input, 2);
        Iterator<String> repeatedIterator = output.iterator();

        // Then
        assertThat(repeatedIterator.hasNext(), is(true));
        assertThat(repeatedIterator.next(), is("Black"));
        assertThat(repeatedIterator.hasNext(), is(true));
        assertThat(repeatedIterator.next(), is("White"));
        assertThat(repeatedIterator.hasNext(), is(true));
        assertThat(repeatedIterator.next(), is("Black"));
        assertThat(repeatedIterator.hasNext(), is(true));
        assertThat(repeatedIterator.next(), is("White"));
        assertThat(repeatedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnRepeatIterableReturningDifferentIterators() throws Exception {
        // Given
        Iterable<String> input = listWith("Black", "White");

        // When
        Iterable<String> iterable = Lazy.repeat(input, 2);
        Iterator<String> iterator1 = iterable.iterator();
        Iterator<String> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is("Black"));
        assertThat(iterator1.next(), is("White"));
        assertThat(iterator2.next(), is("Black"));
        assertThat(iterator1.next(), is("Black"));
        assertThat(iterator2.next(), is("White"));
        assertThat(iterator2.next(), is("Black"));
    }
}
