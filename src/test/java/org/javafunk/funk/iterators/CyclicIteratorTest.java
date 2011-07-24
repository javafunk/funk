package org.javafunk.funk.iterators;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.javafunk.funk.Literals.listOf;
import static org.javafunk.funk.Literals.listWith;

public class CyclicIteratorTest {
    @Test
    public void shouldCycleThroughTheSuppliedIteratorInfinitely() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
    }

    @Test
    public void shouldCycleThroughTheSuppliedIteratorTheSpecifiedNumberOfTimes() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator(), 2);

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedNumberOfTimesToRepeatIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2);

        // When
        new CyclicIterator<Integer>(input.iterator(), -5);

        // Then an IllegalArgumentException is be thrown
    }

    @Test
    public void shouldReturnFalseForHasNextIfANumberOfRepeatsOfZeroIsSpecified() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator(), 0);

        // Then
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfNextIsCalledAndANumberOfRepeatsOfZeroIsSpecified() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2);
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator(), 0);

        // When
        iterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldReturnFalseForHasNextIfAnEmptyIteratorIsSupplied() throws Exception {
        // Given
        Iterable<Integer> input = listOf(Integer.class);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());

        // Then
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfNextIsCalledAndAnEmptyIteratorIsSupplied() throws Exception {
        // Given
        Iterable<Integer> input = listOf(Integer.class);
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());

        // When
        iterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutCallingHasNextFirst() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());

        // Then
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(3));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(3));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowAnUnsupportedOperationExceptionWhenTryingToRemoveElements() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());
        iterator.next();
        iterator.remove();

        // Then an UnsupportedOperationException should be thrown
    }

    @Test
    public void shouldAllowNullValuesInTheInputIterator() throws Exception {
        // Given
        Iterator<String> input = listWith("a", "b", null).iterator();

        // When
        Iterator<String> iterator = new CyclicIterator<String>(input);

        // Then
        assertThat(iterator.next(), is("a"));
        assertThat(iterator.next(), is("b"));
        assertThat(iterator.next(), is(nullValue()));
    }
}
