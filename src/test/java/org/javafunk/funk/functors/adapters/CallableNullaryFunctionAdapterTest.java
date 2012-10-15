/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.functions.NullaryFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.functors.adapters.CallableNullaryFunctionAdapter.callableNullaryFunction;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CallableNullaryFunctionAdapterTest {
    @Mock Callable<Integer> callable;

    @Test
    public void shouldDelegateCallToSuppliedAction() throws Exception {
        // Given
        Integer expected = 3;
        NullaryFunction<Integer> adapter = callableNullaryFunction(callable);
        given(callable.call()).willReturn(expected);

        // When
        Integer actual = adapter.call();

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = RuntimeException.class)
    public void shouldConvertToRuntimeExceptionIfCallableThrowsCheckedException() throws Exception {
        // Given
        NullaryFunction<Integer> adapter = callableNullaryFunction(callable);
        given(callable.call()).willThrow(new IOException());

        // When
        adapter.call();

        // Then a RuntimeException will be thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotConvertExceptionIfCallableThrowsRuntimeException() throws Exception {
        // Given
        NullaryFunction<Integer> adapter = callableNullaryFunction(callable);
        given(callable.call()).willThrow(new NullPointerException());

        // When
        adapter.call();

        // Then a RuntimeException will be thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfCallableSuppliedToCallableNullaryFunctionStaticConstructorIsNull() throws Exception {
        // Given
        Callable<String> callable = null;

        // When
        callableNullaryFunction(callable);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfCallableSuppliedToCallableNullaryFunctionConstructorIsNull() throws Exception {
        // Given
        Callable<String> callable = null;

        // When
        new CallableNullaryFunctionAdapter<String>(callable);

        // Then a NullPointerException is thrown.
    }
}
