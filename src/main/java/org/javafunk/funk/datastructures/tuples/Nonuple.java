/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.*;

import static org.javafunk.funk.Literals.iterableWith;

public class Nonuple<R, S, T, U, V, W, X, Y, Z>
        extends AbstractTuple
        implements First<R>, Second<S>, Third<T>, Fourth<U>, Fifth<V>, Sixth<W>, Seventh<X>, Eighth<Y>, Ninth<Z> {
    private final R first;
    private final S second;
    private final T third;
    private final U fourth;
    private final V fifth;
    private final W sixth;
    private final X seventh;
    private final Y eighth;
    private final Z ninth;

    public Nonuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth, Z ninth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
        this.seventh = seventh;
        this.eighth = eighth;
        this.ninth = ninth;
    }

    @Override public Iterable<Object> values() {
        return iterableWith(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);
    }

    @Override public R first() {
        return first;
    }

    @Override public S second() {
        return second;
    }

    @Override public T third() {
        return third;
    }

    @Override public U fourth() {
        return fourth;
    }

    @Override public V fifth() {
        return fifth;
    }

    @Override public W sixth() {
        return sixth;
    }

    @Override public X seventh() {
        return seventh;
    }

    @Override public Y eighth() {
        return eighth;
    }

    @Override public Z ninth() {
        return ninth;
    }
}
