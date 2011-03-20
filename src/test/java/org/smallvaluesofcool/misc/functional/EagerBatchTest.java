package org.smallvaluesofcool.misc.functional;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.smallvaluesofcool.misc.Literals.listWith;

public class EagerBatchTest {
    @Test
    public void shouldReturnTheElementsOfTheSuppliedIterableInBatchesOfTheSpecifiedSize() {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5);
        Collection<Integer> expectedFirstBatch = listWith(1, 2, 3);
        Collection<Integer> expectedSecondBatch = listWith(4, 5);
        Collection<Collection<Integer>> expectedBatches = listWith(expectedFirstBatch, expectedSecondBatch);

        // When
        Collection<Collection<Integer>> actualBatches = Eager.batch(input, 3);

        // Then
        assertThat(actualBatches, is(expectedBatches));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheBatchSizeIsZero() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5);
        Integer batchSize = 0;

        // When
        Eager.batch(input, batchSize);

        // Then an IllegalArgumentException is thrown.
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheBatchSizeIsLessThanZero() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5);
        Integer batchSize = -5;

        // When
        Eager.batch(input, batchSize);

        // Then an IllegalArgumentException is thrown.
    }
}
