/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Seventh;
import org.javafunk.funk.behaviours.ordinals.mappables.MappableSeventh;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static org.javafunk.funk.Literals.iterableBuilderFrom;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

public class Septuple<R, S, T, U, V, W, X>
        extends Sextuple<R, S, T, U, V, W>
        implements Seventh<X>,
                   MappableSeventh<X, Septuple<R, S, T, U, V, W, ?>> {
    private final X seventh;

    public static <R, S, T, U, V, W, X> Septuple<R, S, T, U, V, W, X> septuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh) {
        return new Septuple<R, S, T, U, V, W, X>(first, second, third, fourth, fifth, sixth, seventh);
    }

    public Septuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh) {
        super(first, second, third, fourth, fifth, sixth);
        this.seventh = seventh;
    }

    @Override public X getSeventh() {
        return seventh;
    }

    @Override public <A> Septuple<A, S, T, U, V, W, X> mapFirst(UnaryFunction<R, A> function) {
        return septuple(function.call(getFirst()), getSecond(), getThird(), getFourth(), getFifth(), getSixth(), getSeventh());
    }

    @Override public <A> Septuple<A, S, T, U, V, W, X> mapFirst(Mapper<R, A> mapper) {
        return mapFirst(mapperUnaryFunction(mapper));
    }

    @Override public <A> Septuple<R, A, T, U, V, W, X> mapSecond(UnaryFunction<S, A> function) {
        return septuple(getFirst(), function.call(getSecond()), getThird(), getFourth(), getFifth(), getSixth(), getSeventh());
    }

    @Override public <A> Septuple<R, A, T, U, V, W, X> mapSecond(Mapper<S, A> mapper) {
        return mapSecond(mapperUnaryFunction(mapper));
    }

    @Override public <A> Septuple<R, S, A, U, V, W, X> mapThird(UnaryFunction<T, A> function) {
        return septuple(getFirst(), getSecond(), function.call(getThird()), getFourth(), getFifth(), getSixth(), getSeventh());
    }

    @Override public <A> Septuple<R, S, A, U, V, W, X> mapThird(Mapper<T, A> mapper) {
        return mapThird(mapperUnaryFunction(mapper));
    }

    @Override public <A> Septuple<R, S, T, A, V, W, X> mapFourth(UnaryFunction<U, A> function) {
        return septuple(getFirst(), getSecond(), getThird(), function.call(getFourth()), getFifth(), getSixth(), getSeventh());
    }

    @Override public <A> Septuple<R, S, T, A, V, W, X> mapFourth(Mapper<U, A> mapper) {
        return mapFourth(mapperUnaryFunction(mapper));
    }

    @Override public <A> Septuple<R, S, T, U, A, W, X> mapFifth(UnaryFunction<V, A> function) {
        return septuple(getFirst(), getSecond(), getThird(), getFourth(), function.call(getFifth()), getSixth(), getSeventh());
    }

    @Override public <A> Septuple<R, S, T, U, A, W, X> mapFifth(Mapper<V, A> mapper) {
        return mapFifth(mapperUnaryFunction(mapper));
    }

    @Override public <A> Septuple<R, S, T, U, V, A, X> mapSixth(UnaryFunction<W, A> function) {
        return septuple(getFirst(), getSecond(), getThird(), getFourth(), getFifth(), function.call(getSixth()), getSeventh());
    }

    @Override public <A> Septuple<R, S, T, U, V, A, X> mapSixth(Mapper<W, A> mapper) {
        return mapSixth(mapperUnaryFunction(mapper));
    }

    @Override public <A> Septuple<R, S, T, U, V, W, A> mapSeventh(UnaryFunction<X, A> function) {
        return septuple(getFirst(), getSecond(), getThird(), getFourth(), getFifth(), getSixth(), function.call(getSeventh()));
    }

    public <A> Septuple<R, S, T, U, V, W, A> mapSeventh(Mapper<X, A> function) {
        return mapSeventh(mapperUnaryFunction(function));
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getSeventh()).build();
    }
}
