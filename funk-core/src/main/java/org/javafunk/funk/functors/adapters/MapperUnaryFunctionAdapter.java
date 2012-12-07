/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static com.google.common.base.Preconditions.checkNotNull;

public class MapperUnaryFunctionAdapter<S, T> implements UnaryFunction<S, T> {
    public static <S, T> MapperUnaryFunctionAdapter<S, T> mapperUnaryFunction(Mapper<S, T> mapper) {
        return new MapperUnaryFunctionAdapter<S, T>(mapper);
    }

    private final Mapper<? super S, T> mapper;

    public MapperUnaryFunctionAdapter(Mapper<? super S, T> mapper) {
        this.mapper = checkNotNull(mapper);
    }

    @Override public T call(S input) {
        return mapper.map(input);
    }
}
