/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;

public class EagerlyBatchTest {
    @Test
    public void shouldReturnTheElementsOfTheSuppliedIterableInBatchesOfTheSpecifiedSize() {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Collection<Integer> expectedFirstBatch = collectionWith(1, 2, 3);
        Collection<Integer> expectedSecondBatch = collectionWith(4, 5);
        Collection<Collection<Integer>> expectedBatches = collectionWith(expectedFirstBatch, expectedSecondBatch);

        // When
        Collection<Collection<Integer>> actualBatches = Eagerly.batch(input, 3);

        // Then
        assertThat(actualBatches, is(expectedBatches));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheBatchSizeIsZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Integer batchSize = 0;

        // When
        Eagerly.batch(input, batchSize);

        // Then an IllegalArgumentException is thrown.
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheBatchSizeIsLessThanZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Integer batchSize = -5;

        // When
        Eagerly.batch(input, batchSize);

        // Then an IllegalArgumentException is thrown.
    }
}
