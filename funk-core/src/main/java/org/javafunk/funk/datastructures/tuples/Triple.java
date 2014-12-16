/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Third;
import org.javafunk.funk.behaviours.ordinals.mappables.MappableThird;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static org.javafunk.funk.Literals.iterableBuilderFrom;

public class Triple<R, S, T>
        extends Pair<R, S>
        implements Third<T>,
                   MappableThird<T, Triple<R, S, ?>> {
    private T third;

    public static <R, S, T> Triple<R, S, T> triple(R first, S second, T third) {
        return new Triple<R, S, T>(first, second, third);
    }

    public Triple(R first, S second, T third) {
        super(first, second);
        this.third = third;
    }

    @Override public T getThird() {
        return third;
    }

    @Override public <A> Triple<A, S, T> mapFirst(UnaryFunction<R, A> function) {
        return triple(function.call(getFirst()), getSecond(), getThird());
    }

    @Override public <A> Triple<R, A, T> mapSecond(UnaryFunction<S, A> function) {
        return triple(getFirst(), function.call(getSecond()), getThird());
    }


    @Override public <A> Triple<R, S, A> mapThird(UnaryFunction<T, A> function) {
        return triple(getFirst(), getSecond(), function.call(getThird()));
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getThird()).build();
    }
}
