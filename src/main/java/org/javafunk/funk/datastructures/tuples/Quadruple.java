/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.First;
import org.javafunk.funk.behaviours.ordinals.Fourth;
import org.javafunk.funk.behaviours.ordinals.Second;
import org.javafunk.funk.behaviours.ordinals.Third;

import static org.javafunk.funk.Literals.iterableWith;

public class Quadruple<R, S, T, U>
        extends AbstractTuple
        implements First<R>, Second<S>, Third<T>, Fourth<U> {
    private R first;
    private S second;
    private T third;
    private U fourth;

    public Quadruple(R first, S second, T third, U fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
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

    @Override public Iterable<Object> values() {
        return iterableWith(first, second, third, fourth);
    }
}
