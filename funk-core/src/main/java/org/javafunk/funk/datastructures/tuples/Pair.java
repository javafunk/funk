/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Second;
import org.javafunk.funk.behaviours.ordinals.mappables.MappableSecond;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static org.javafunk.funk.Literals.iterableBuilderFrom;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

public class Pair<R, S>
        extends Single<R>
        implements Second<S>,
                   MappableSecond<S, Pair<R, ?>> {
    private S second;

    public static <R, S> Pair<R, S> pair(R first, S second) {
        return new Pair<R, S>(first, second);
    }

    public Pair(R first, S second) {
        super(first);
        this.second = second;
    }

    @Override public S getSecond() {
        return second;
    }

    @Override public <A> Pair<A, S> mapFirst(UnaryFunction<R, A> function) {
        return pair(function.call(getFirst()), getSecond());
    }

    public <A> Pair<A, S> mapFirst(Mapper<R, A> mapper) {
        return mapFirst(mapperUnaryFunction(mapper));
    }

    @Override public <A> Pair<R, A> mapSecond(UnaryFunction<S, A> function) {
        return pair(getFirst(), function.call(getSecond()));
    }

    public <A> Pair<R, A> mapSecond(Mapper<S, A> mapper) {
        return mapSecond(mapperUnaryFunction(mapper));
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getSecond()).build();
    }
}
