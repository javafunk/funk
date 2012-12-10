/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Fifth;
import org.javafunk.funk.behaviours.ordinals.mappables.MappableFifth;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static org.javafunk.funk.Literals.iterableBuilderFrom;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

public class Quintuple<R, S, T, U, V>
        extends Quadruple<R, S, T, U>
        implements Fifth<V>,
                   MappableFifth<V, Quintuple<R, S, T, U, ?>> {
    private final V fifth;

    public static <R, S, T, U, V> Quintuple<R, S, T, U, V> quintuple(R first, S second, T third, U fourth, V fifth) {
        return new Quintuple<R, S, T, U, V>(first, second, third, fourth, fifth);
    }

    public Quintuple(R first, S second, T third, U fourth, V fifth) {
        super(first, second, third, fourth);
        this.fifth = fifth;
    }

    @Override public V getFifth() {
        return fifth;
    }

    @Override public <A> Quintuple<A, S, T, U, V> mapFirst(UnaryFunction<R, A> function) {
        return quintuple(function.call(getFirst()), getSecond(), getThird(), getFourth(), getFifth());
    }

    @Override public <A> Quintuple<A, S, T, U, V> mapFirst(Mapper<R, A> mapper) {
        return mapFirst(mapperUnaryFunction(mapper));
    }

    @Override public <A> Quintuple<R, A, T, U, V> mapSecond(UnaryFunction<S, A> function) {
        return quintuple(getFirst(), function.call(getSecond()), getThird(), getFourth(), getFifth());
    }

    @Override public <A> Quintuple<R, A, T, U, V> mapSecond(Mapper<S, A> mapper) {
        return mapSecond(mapperUnaryFunction(mapper));
    }

    @Override public <A> Quintuple<R, S, A, U, V> mapThird(UnaryFunction<T, A> function) {
        return quintuple(getFirst(), getSecond(), function.call(getThird()), getFourth(), getFifth());
    }

    @Override public <A> Quintuple<R, S, A, U, V> mapThird(Mapper<T, A> mapper) {
        return mapThird(mapperUnaryFunction(mapper));
    }

    @Override public <A> Quintuple<R, S, T, A, V> mapFourth(UnaryFunction<U, A> function) {
        return quintuple(getFirst(), getSecond(), getThird(), function.call(getFourth()), getFifth());
    }

    @Override public <A> Quintuple<R, S, T, A, V> mapFourth(Mapper<U, A> mapper) {
        return mapFourth(mapperUnaryFunction(mapper));
    }

    @Override public <A> Quintuple<R, S, T, U, A> mapFifth(UnaryFunction<V, A> function) {
        return quintuple(getFirst(), getSecond(), getThird(), getFourth(), function.call(getFifth()));
    }

    public <A> Quintuple<R, S, T, U, A> mapFifth(Mapper<V, A> mapper) {
        return mapFifth(mapperUnaryFunction(mapper));
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getFifth()).build();
    }
}
