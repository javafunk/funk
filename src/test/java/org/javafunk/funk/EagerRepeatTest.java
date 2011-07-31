/*
 * Copyright (C) 2011 Funk committers.
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
import static org.javafunk.funk.Literals.listWith;

public class EagerRepeatTest {
    @Test
    public void shouldRepeatTheElementsOfTheSpecifiedIterableInOrderTheSpecifiedNumberOfTimes() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2);
        Collection<Integer> expectedOutput = listWith(1, 2, 1, 2, 1, 2);

        // When
        Collection<Integer> actualOutput = Eager.repeat(input, 3);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
