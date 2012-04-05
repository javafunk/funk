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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.listWith;

public class EagerlySumTest {
    @Test
    public void shouldSumTheSuppliedIntegers() {
        // Given
        List<Integer> inputs = listWith(1, 2, 3);

        // When
        Integer sum = Eagerly.sum(inputs);

        // Then
        assertThat(sum, is(6));
    }

    @Test
    public void shouldSumTheSuppliedLongs() {
        // Given
        List<Long> inputs = listWith(1L, 2L, 3L);

        // When
        Long sum = Eagerly.sum(inputs);

        // Then
        assertThat(sum, is(6L));
    }

    @Test
    public void shouldSumTheSuppliedDoubles() {
        // Given
        List<Double> inputs = listWith(1.6D, 2.2D, 3.5D);

        // When
        Double sum = Eagerly.sum(inputs);

        // Then
        assertThat(sum, is(closeTo(7.3D, 0.01D)));
    }

    @Test
    public void shouldSumTheSuppliedFloats() {
        // Given
        List<Float> inputs = listWith(1.6F, 2.2F, 3.5F);

        // When
        Float sum = Eagerly.sum(inputs);

        // Then
        assertThat(sum.doubleValue(), is(closeTo(7.3D, 0.01)));
    }
}
