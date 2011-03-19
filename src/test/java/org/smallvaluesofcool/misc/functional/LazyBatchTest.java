package org.smallvaluesofcool.misc.functional;

import org.junit.Test;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;
import org.smallvaluesofcool.misc.functional.functors.MapFunction;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.smallvaluesofcool.misc.IterableUtils.materialize;
import static org.smallvaluesofcool.misc.IterableUtils.toList;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.Literals.twoTuple;
import static org.smallvaluesofcool.misc.matchers.Matchers.hasOnlyItemsInOrder;

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
}
