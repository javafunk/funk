/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Ninth;
import org.javafunk.funk.behaviours.ordinals.mappables.MappableNinth;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static org.javafunk.funk.Literals.iterableBuilderFrom;

public class Nonuple<R, S, T, U, V, W, X, Y, Z>
        extends Octuple<R, S, T, U, V, W, X, Y>
        implements Ninth<Z>,
                   MappableNinth<Z, Nonuple<R, S, T, U, V, W, X, Y, ?>> {
    private final Z ninth;

    public static <R, S, T, U, V, W, X, Y, Z> Nonuple<R, S, T, U, V, W, X, Y, Z> nonuple(
            R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth, Z ninth) {
        return new Nonuple<R, S, T, U, V, W, X, Y, Z>(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);
    }
    
    public Nonuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth, Z ninth) {
        super(first, second, third, fourth, fifth, sixth, seventh, eighth);
        this.ninth = ninth;
    }

    @Override public Z getNinth() {
        return ninth;
    }

    @Override public <A> Nonuple<A, S, T, U, V, W, X, Y, Z> mapFirst(UnaryFunction<R, A> function) {
        return nonuple(function.call(getFirst()), getSecond(), getThird(), getFourth(), getFifth(), getSixth(), getSeventh(), getEighth(), getNinth());
    }

    @Override public <A> Nonuple<R, A, T, U, V, W, X, Y, Z> mapSecond(UnaryFunction<S, A> function) {
        return nonuple(getFirst(), function.call(getSecond()), getThird(), getFourth(), getFifth(), getSixth(), getSeventh(), getEighth(), getNinth());
    }

    @Override public <A> Nonuple<R, S, A, U, V, W, X, Y, Z> mapThird(UnaryFunction<T, A> function) {
        return nonuple(getFirst(), getSecond(), function.call(getThird()), getFourth(), getFifth(), getSixth(), getSeventh(), getEighth(), getNinth());
    }

    @Override public <A> Nonuple<R, S, T, A, V, W, X, Y, Z> mapFourth(UnaryFunction<U, A> function) {
        return nonuple(getFirst(), getSecond(), getThird(), function.call(getFourth()), getFifth(), getSixth(), getSeventh(), getEighth(), getNinth());
    }

    @Override public <A> Nonuple<R, S, T, U, A, W, X, Y, Z> mapFifth(UnaryFunction<V, A> function) {
        return nonuple(getFirst(), getSecond(), getThird(), getFourth(), function.call(getFifth()), getSixth(), getSeventh(), getEighth(), getNinth());
    }

    @Override public <A> Nonuple<R, S, T, U, V, A, X, Y, Z> mapSixth(UnaryFunction<W, A> function) {
        return nonuple(getFirst(), getSecond(), getThird(), getFourth(), getFifth(), function.call(getSixth()), getSeventh(), getEighth(), getNinth());
    }

    @Override public <A> Nonuple<R, S, T, U, V, W, A, Y, Z> mapSeventh(UnaryFunction<X, A> function) {
        return nonuple(getFirst(), getSecond(), getThird(), getFourth(), getFifth(), getSixth(), function.call(getSeventh()), getEighth(), getNinth());
    }

    @Override public <A> Nonuple<R, S, T, U, V, W, X, A, Z> mapEighth(UnaryFunction<Y, A> function) {
        return nonuple(getFirst(), getSecond(), getThird(), getFourth(), getFifth(), getSixth(), getSeventh(), function.call(getEighth()), getNinth());
    }

    @Override public <A> Nonuple<R, S, T, U, V, W, X, Y, A> mapNinth(UnaryFunction<Z, A> function) {
        return nonuple(getFirst(), getSecond(), getThird(), getFourth(), getFifth(), getSixth(), getSeventh(), getEighth(), function.call(getNinth()));
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getNinth()).build();
    }
}
