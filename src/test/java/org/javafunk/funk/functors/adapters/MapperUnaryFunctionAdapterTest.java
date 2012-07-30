/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MapperUnaryFunctionAdapterTest {
    @Mock Mapper<String, Double> mapper;

    @Test
    public void shouldDelegateCallToSuppliedAction() throws Exception {
        // Given
        String target = "some string";
        Double expected = 67.3;
        UnaryFunction<String, Double> adapter = mapperUnaryFunction(mapper);
        given(mapper.map(target)).willReturn(expected);


        // When
        Double actual = adapter.call(target);

        // Then
        assertThat(actual, is(expected));
    }
}
