package org.smallvaluesofcool.misc.functional;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.smallvaluesofcool.misc.Literals.listWith;

public class LazyCycleTest {
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
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
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
    }
}
