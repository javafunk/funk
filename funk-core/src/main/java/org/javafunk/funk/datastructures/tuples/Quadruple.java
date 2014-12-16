/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Fourth;
import org.javafunk.funk.behaviours.ordinals.mappables.MappableFourth;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static org.javafunk.funk.Literals.iterableBuilderFrom;

public class Quadruple<R, S, T, U>
        extends Triple<R, S, T>
        implements Fourth<U>,
                   MappableFourth<U, Quadruple<R, S, T, ?>> {
    private U fourth;

    public static <R, S, T, U> Quadruple<R, S, T, U> quadruple(R first, S second, T third, U fourth) {
        return new Quadruple<R, S, T, U>(first, second, third, fourth);
    }

    public Quadruple(R first, S second, T third, U fourth) {
        super(first, second, third);
        this.fourth = fourth;
    }

    @Override public U getFourth() {
        return fourth;
    }

    @Override public <A> Quadruple<A, S, T, U> mapFirst(UnaryFunction<R, A> function) {
        return quadruple(function.call(getFirst()), getSecond(), getThird(), getFourth());
    }

    @Override public <A> Quadruple<R, A, T, U> mapSecond(UnaryFunction<S, A> function) {
        return quadruple(getFirst(), function.call(getSecond()), getThird(), getFourth());
    }

    @Override public <A> Quadruple<R, S, A, U> mapThird(UnaryFunction<T, A> function) {
        return quadruple(getFirst(), getSecond(), function.call(getThird()), getFourth());
    }

    @Override public <A> Quadruple<R, S, T, A> mapFourth(UnaryFunction<U, A> function) {
        return quadruple(getFirst(), getSecond(), getThird(), function.call(getFourth()));
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getFourth()).build();
    }
}
