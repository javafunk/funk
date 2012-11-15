/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.mappables.MappableFirst;
import org.javafunk.funk.behaviours.ordinals.First;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static org.javafunk.funk.Literals.iterableBuilderOf;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

public class Single<R>
        extends AbstractTuple
        implements First<R>,
                   MappableFirst<R, Single<?>> {
    private R first;

    private static <R> Single<R> single(R first) {
        return new Single<R>(first);
    }

    public Single(R first) {
        this.first = first;
    }

    @Override public R getFirst() {
        return first;
    }

    @Override public <A> Single<A> mapFirst(UnaryFunction<R, A> mapper) {
        return single(mapper.call(first));
    }

    public <A> Single<A> mapFirst(Mapper<R, A> mapper) {
        return mapFirst(mapperUnaryFunction(mapper));
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderOf(Object.class).with(first).build();
    }
}
