/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Sixth;
import org.javafunk.funk.behaviours.ordinals.mappables.MappableSixth;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static org.javafunk.funk.Literals.iterableBuilderFrom;

public class Sextuple<R, S, T, U, V, W>
        extends Quintuple<R, S, T, U, V>
        implements Sixth<W>,
                   MappableSixth<W, Sextuple<R, S, T, U, V, ?>> {
    private final W sixth;

    public static <R, S, T, U, V, W> Sextuple<R, S, T, U, V, W> sextuple(R first, S second, T third, U fourth, V fifth, W sixth) {
        return new Sextuple<R, S, T, U, V, W>(first, second, third, fourth, fifth, sixth);
    }

    public Sextuple(R first, S second, T third, U fourth, V fifth, W sixth) {
        super(first, second, third, fourth, fifth);
        this.sixth = sixth;
    }

    @Override public W getSixth() {
        return sixth;
    }

    @Override public <A> Sextuple<A, S, T, U, V, W> mapFirst(UnaryFunction<R, A> function) {
        return sextuple(function.call(getFirst()), getSecond(), getThird(), getFourth(), getFifth(), getSixth());
    }

    @Override public <A> Sextuple<R, A, T, U, V, W> mapSecond(UnaryFunction<S, A> function) {
        return sextuple(getFirst(), function.call(getSecond()), getThird(), getFourth(), getFifth(), getSixth());
    }

    @Override public <A> Sextuple<R, S, A, U, V, W> mapThird(UnaryFunction<T, A> function) {
        return sextuple(getFirst(), getSecond(), function.call(getThird()), getFourth(), getFifth(), getSixth());
    }

    @Override public <A> Sextuple<R, S, T, A, V, W> mapFourth(UnaryFunction<U, A> function) {
        return sextuple(getFirst(), getSecond(), getThird(), function.call(getFourth()), getFifth(), getSixth());
    }

    @Override public <A> Sextuple<R, S, T, U, A, W> mapFifth(UnaryFunction<V, A> function) {
        return sextuple(getFirst(), getSecond(), getThird(), getFourth(), function.call(getFifth()), getSixth());
    }

    @Override public <A> Sextuple<R, S, T, U, V, A> mapSixth(UnaryFunction<W, A> function) {
        return sextuple(getFirst(), getSecond(), getThird(), getFourth(), getFifth(), function.call(getSixth()));
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getSixth()).build();
    }
}
