package org.smallvaluesofcool.misc.functional;

import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.smallvaluesofcool.misc.IterableUtils.materialize;
import static org.smallvaluesofcool.misc.Literals.listWith;

public class LazyBatchTest {
    @Test
    public void shouldReturnElementsOfTheIterableInBatchesOfTheSpecifiedSize() {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> firstBatch = listWith(1, 2, 3);
        Collection<Integer> secondBatch = listWith(4, 5, 6);
        Collection<Integer> thirdBatch = listWith(7, 8, 9);

        // When
        Iterator<Iterable<Integer>> returnedIterator = Lazy.batch(input, 3).iterator();

        // Then
        assertThat(materialize(returnedIterator.next()), is(firstBatch));
        assertThat(materialize(returnedIterator.next()), is(secondBatch));
        assertThat(materialize(returnedIterator.next()), is(thirdBatch));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheSuppliedBatchSizeIsZero() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Integer batchSize = 0;

        // When
        Lazy.batch(input, batchSize);

        // Then an IllegalArgumentException is thrown.
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> firstBatch = listWith(1, 2, 3);
        Collection<Integer> secondBatch = listWith(4, 5, 6);
        Collection<Integer> thirdBatch = listWith(7, 8, 9);

        // When
        Iterable<Iterable<Integer>> iterable = Lazy.batch(input, 3);
        Iterator<Iterable<Integer>> iterator1 = iterable.iterator();
        Iterator<Iterable<Integer>> iterator2 = iterable.iterator();

        // Then
        assertThat(materialize(iterator1.next()), is(firstBatch));
        assertThat(materialize(iterator1.next()), is(secondBatch));
        assertThat(materialize(iterator2.next()), is(firstBatch));
        assertThat(materialize(iterator1.next()), is(thirdBatch));
        assertThat(materialize(iterator2.next()), is(secondBatch));
        assertThat(materialize(iterator2.next()), is(thirdBatch));
    }
}
