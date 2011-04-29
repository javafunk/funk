package org.javafunk.functional.iterators;

import org.junit.Test;
import org.javafunk.datastructures.TwoTuple;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.javafunk.Literals.listWith;
import static org.javafunk.Literals.twoTuple;

public class ZippedIteratorTest {
    @Test
    public void shouldZipTheSuppliedIterators() throws Exception {
        // Given
        Iterator<String> firstIterator = listWith("A", "B").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        // Then
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(twoTuple("A", 1)));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(twoTuple("B", 2)));
        assertThat(zippedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldOnlyHaveAsManyElementsAsTheSecondIteratorIfTheFirstIsLonger() {
        // Given
        Iterator<String> firstIterator = listWith("A", "B", "C", "D").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        // Then
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(twoTuple("A", 1)));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(twoTuple("B", 2)));
        assertThat(zippedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldOnlyHaveAsManyElementsAsTheFirstIteratorIfTheSecondIsLonger() {
        // Given
        Iterator<String> firstIterator = listWith("A", "B", "C").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2, 3, 4, 5).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        // Then
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(twoTuple("A", 1)));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(twoTuple("B", 2)));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(twoTuple("C", 3)));
        assertThat(zippedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutCallingHasNext() throws Exception {
        // Given
        Iterator<String> firstIterator = listWith("A", "B").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        // Then
        assertThat(zippedIterator.next(), is(twoTuple("A", 1)));
        assertThat(zippedIterator.next(), is(twoTuple("B", 2)));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() throws Exception {
        // Given
        Iterator<String> firstIterator = listWith("A", "B").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        // Then
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(twoTuple("A", 1)));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(twoTuple("B", 2)));
        assertThat(zippedIterator.hasNext(), is(false));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowAnUnsupportedOperationExceptionWhenTryingToRemoveElements() throws Exception {
        // Given
        Iterator<String> firstIterator = listWith("A", "B").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        zippedIterator.next();
        zippedIterator.remove();

        // Then an UnsupportedOperationException is thrown
    }
}
