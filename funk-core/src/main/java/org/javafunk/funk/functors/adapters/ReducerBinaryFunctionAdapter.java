/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Reducer;
import org.javafunk.funk.functors.functions.BinaryFunction;

import static com.google.common.base.Preconditions.checkNotNull;

public class ReducerBinaryFunctionAdapter<S, T> implements BinaryFunction<T, S, T> {
    public static <S, T> ReducerBinaryFunctionAdapter<S, T> reducerBinaryFunction(Reducer<? super S, T> reducer) {
        return new ReducerBinaryFunctionAdapter<S, T>(reducer);
    }

    private final Reducer<? super S, T> reducer;

    public ReducerBinaryFunctionAdapter(Reducer<? super S, T> reducer) {
        this.reducer = checkNotNull(reducer);
    }

    @Override public T call(T accumulator, S element) {
        return reducer.accumulate(accumulator, element);
    }
}
