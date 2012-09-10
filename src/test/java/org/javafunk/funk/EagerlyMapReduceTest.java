/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.Reducer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.javafunk.funk.Accumulators.longAdditionAccumulator;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class EagerlyMapReduceTest {
    @Test
    public void shouldMapIterableUsingCustomMapFunction() {
        // Given
        Iterable<Integer> inputs = iterableWith(1, 2, 3);
        Collection<Integer> expectedOutputs = collectionWith(2, 4, 6);

        // When
        Collection<Integer> actualOutputs = Eagerly.map(inputs, new Mapper<Integer, Integer>() {
            @Override
            public Integer map(Integer input) {
                return input * 2;
            }
        });

        // Then
        assertThat(actualOutputs, hasOnlyItemsInOrder(expectedOutputs));
    }

    @Test
    public void shouldReduceTheSuppliedInputToTheirSumUsingALongAccumulator() throws Exception {
        // Given
        Collection<Long> input = collectionWith(2L, 3L, 4L);

        // When
        Long actual = Eagerly.reduce(input, longAdditionAccumulator());

        // Then
        assertThat(actual, equalTo(9L));
    }

    @Test
    public void shouldReduceToTheSameTypeUsingACustomReduceFunction() throws Exception {
        // Given
        List<List<Integer>> inputLists = listBuilderWith(
                listBuilderWith(1, 2, 3).build(ArrayList.class),
                listBuilderWith(4, 5, 6).build(ArrayList.class),
                listBuilderWith(7, 8, 9).build(ArrayList.class)
        ).build(ArrayList.class);

        // When
        List<Integer> actual = Eagerly.reduce(inputLists, new Reducer<List<Integer>, List<Integer>>() {
            // Example flattening accumulator.
            public List<Integer> accumulate(List<Integer> accumulator, List<Integer> element) {
                accumulator.addAll(element);
                return accumulator;
            }
        });

        // Then
        assertThat(actual, hasItems(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test
    public void shouldReduceToATypeOtherThanThatOfTheInputElementsWithASuppliedInitialValue() {
        // Given
        List<Integer> inputs = listWith(1, 2, 3);

        // When
        String output = Eagerly.reduce(inputs, "", new Reducer<Integer, String>() {
            public String accumulate(String accumulator, Integer element) {
                accumulator += element.toString();
                return accumulator;
            }
        });

        // Then
        assertThat(output, is("123"));
    }
}
