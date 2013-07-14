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
import static org.javafunk.funk.Eagerly.first;
import static org.javafunk.funk.Eagerly.rest;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class EagerlyBatchTest {
    @Test
    public void shouldReturnTheElementsOfTheSuppliedIterableInBatchesOfTheSpecifiedSize() {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);
        Collection<Integer> expectedFirstBatch = collectionWith(1, 2, 3);
        Collection<Integer> expectedSecondBatch = collectionWith(4, 5);

        // When
        Collection<Collection<Integer>> actualBatches = Eagerly.batch(input, 3);

        // Then
        assertThat(first(actualBatches).get(), hasOnlyItemsInOrder(expectedFirstBatch));
        assertThat(first(rest(actualBatches)).get(), hasOnlyItemsInOrder(expectedSecondBatch));
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

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfTheIterablePassedToBatchIsNull() throws Exception {
        // Given
        Iterable<Integer> input = null;
        Integer batchSize = 2;

        // When
        Eagerly.batch(input, batchSize);

        // Then a NullPointerException is thrown.
    }
}
