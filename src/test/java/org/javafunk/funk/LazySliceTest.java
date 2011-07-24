package org.javafunk.funk;

import org.javafunk.funk.Lazy;
import org.javafunk.funk.Lazy;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.listWith;
import static org.junit.Assert.assertThat;

public class LazySliceTest {
    @Test
    public void shouldTakeElementsFromTheIterableBetweenTheSpecifiedStartAndStopInStepsOfTheSpecifiedSize() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> expectedOutput = listWith(3, 5, 7);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.slice(input, 2, 7, 2));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // When
        Iterable<Integer> iterable = Lazy.slice(input, 2, 7, 2);
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(3));
        assertThat(iterator1.next(), is(5));
        assertThat(iterator2.next(), is(3));
        assertThat(iterator1.next(), is(7));
        assertThat(iterator2.next(), is(5));
        assertThat(iterator2.next(), is(7));
    }
}
