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

import java.util.concurrent.Callable;

/**
 * An adapter to represent a {@code Callable<T>} as a {@code NullaryFunction<T>}.
 *
 * <p>Note that since the {@link java.util.concurrent.Callable#call()} signature
 * includes a {@code throws} of {@code Exception}, it is necessary to
 * translate any checked exception thrown by the adapted {@code Callable}
 * to an instance of {@code RuntimeException}. In the case that the thrown
 * exception is already a {@code RuntimeException}, no further translation
 * takes place.</p>
 *
 * @param <T> The type returned by the {@code Callable} to be adapted.
 */
public class CallableNullaryFunctionAdapter<T> implements NullaryFunction<T> {
    private Callable<? extends T> callable;

    /**
     * A generic factory method for building a {@code CallableNullaryFunctionAdapter}
     * of type {@code T} over the supplied {@code Callable}.
     *
     * @param callable The {@code Callable} instance to adapt.
     * @param <T> The return type of the supplied {@code Callable}.
     * @return A {@code CallableNullaryFunctionAdapter} for the supplied {@code Callable}
     */
    public static <T> NullaryFunction<T> callableNullaryFunction(Callable<? extends T> callable) {
        return new CallableNullaryFunctionAdapter<T>(callable);
    }

    /**
     * @param callable The {@code Callable} to adapt to the interface
     *                 of {@code NullaryFunction}.
     */
    public CallableNullaryFunctionAdapter(Callable<? extends T> callable) {
        this.callable = callable;
    }

    /**
     * An implementation of {@link org.javafunk.funk.functors.functions.NullaryFunction#call()}
     * which delegates to {@link java.util.concurrent.Callable#call()} translating
     * any checked exception into a {@code RuntimeException} whilst leaving
     * {@code RuntimeException} instances unchanged.
     *
     * @return The value obtained by calling the adapted {@code Callable}.
     */
    @Override public T call() {
        try {
            return callable.call();
        } catch (Exception exception) {
            if (exception instanceof RuntimeException) {
                throw (RuntimeException) exception;
            }
            throw new RuntimeException(exception);
        }
    }
}
