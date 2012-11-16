/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Eighth;
import org.javafunk.funk.behaviours.ordinals.mappables.MappableEighth;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static org.javafunk.funk.Literals.iterableBuilderFrom;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

public class Octuple<R, S, T, U, V, W, X, Y>
        extends Septuple<R, S, T, U, V, W, X>
        implements Eighth<Y>,
                   MappableEighth<Y, Octuple<R, S, T, U, V, W, X, ?>> {
    private final Y eighth;

    public static <R, S, T, U, V, W, X, Y> Octuple<R, S, T, U, V, W, X, Y> octuple(
            R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth) {
        return new Octuple<R, S, T, U, V, W, X, Y>(first, second, third, fourth, fifth, sixth, seventh, eighth);
    }

    public Octuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth) {
        super(first, second, third, fourth, fifth, sixth, seventh);
        this.eighth = eighth;
    }

    @Override public Y getEighth() {
        return eighth;
    }

    @Override public <A> Octuple<A, S, T, U, V, W, X, Y> mapFirst(UnaryFunction<R, A> function) {
        return octuple(function.call(getFirst()), getSecond(), getThird(), getFourth(), getFifth(), getSixth(), getSeventh(), getEighth());
    }

    @Override public <A> Octuple<A, S, T, U, V, W, X, Y> mapFirst(Mapper<R, A> mapper) {
        return mapFirst(mapperUnaryFunction(mapper));
    }

    @Override public <A> Octuple<R, A, T, U, V, W, X, Y> mapSecond(UnaryFunction<S, A> function) {
        return octuple(getFirst(), function.call(getSecond()), getThird(), getFourth(), getFifth(), getSixth(), getSeventh(), getEighth());
    }

    @Override public <A> Octuple<R, A, T, U, V, W, X, Y> mapSecond(Mapper<S, A> mapper) {
        return mapSecond(mapperUnaryFunction(mapper));
    }

    @Override public <A> Octuple<R, S, A, U, V, W, X, Y> mapThird(UnaryFunction<T, A> function) {
        return octuple(getFirst(), getSecond(), function.call(getThird()), getFourth(), getFifth(), getSixth(), getSeventh(), getEighth());
    }

    @Override public <A> Octuple<R, S, A, U, V, W, X, Y> mapThird(Mapper<T, A> mapper) {
        return mapThird(mapperUnaryFunction(mapper));
    }

    @Override public <A> Octuple<R, S, T, A, V, W, X, Y> mapFourth(UnaryFunction<U, A> function) {
        return octuple(getFirst(), getSecond(), getThird(), function.call(getFourth()), getFifth(), getSixth(), getSeventh(), getEighth());
    }

    @Override public <A> Octuple<R, S, T, A, V, W, X, Y> mapFourth(Mapper<U, A> mapper) {
        return mapFourth(mapperUnaryFunction(mapper));
    }

    @Override public <A> Octuple<R, S, T, U, A, W, X, Y> mapFifth(UnaryFunction<V, A> function) {
        return octuple(getFirst(), getSecond(), getThird(), getFourth(), function.call(getFifth()), getSixth(), getSeventh(), getEighth());
    }

    @Override public <A> Octuple<R, S, T, U, A, W, X, Y> mapFifth(Mapper<V, A> mapper) {
        return mapFifth(mapperUnaryFunction(mapper));
    }

    @Override public <A> Octuple<R, S, T, U, V, A, X, Y> mapSixth(UnaryFunction<W, A> function) {
        return octuple(getFirst(), getSecond(), getThird(), getFourth(), getFifth(), function.call(getSixth()), getSeventh(), getEighth());
    }

    @Override public <A> Octuple<R, S, T, U, V, A, X, Y> mapSixth(Mapper<W, A> mapper) {
        return mapSixth(mapperUnaryFunction(mapper));
    }

    @Override public <A> Octuple<R, S, T, U, V, W, A, Y> mapSeventh(UnaryFunction<X, A> function) {
        return octuple(getFirst(), getSecond(), getThird(), getFourth(), getFifth(), getSixth(), function.call(getSeventh()), getEighth());
    }

    @Override public <A> Octuple<R, S, T, U, V, W, A, Y> mapSeventh(Mapper<X, A> mapper) {
        return mapSeventh(mapperUnaryFunction(mapper));
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getEighth()).build();
    }

    @Override public <A> Octuple<R, S, T, U, V, W, X, A> mapEighth(UnaryFunction<Y, A> function) {
        return octuple(getFirst(), getSecond(), getThird(), getFourth(), getFifth(), getSixth(), getSeventh(), function.call(getEighth()));
    }

    public <A> Octuple<R, S, T, U, V, W, X, A> mapEighth(Mapper<Y, A> mapper) {
        return mapEighth(mapperUnaryFunction(mapper));
    }
}
