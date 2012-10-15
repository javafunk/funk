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
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class EagerlyRepeatTest {
    @Test
    public void shouldRepeatTheElementsOfTheSpecifiedIterableInOrderTheSpecifiedNumberOfTimes() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2);
        Collection<Integer> expectedOutput = collectionWith(1, 2, 1, 2, 1, 2);

        // When
        Collection<Integer> actualOutput = Eagerly.repeat(input, 3);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }
}
