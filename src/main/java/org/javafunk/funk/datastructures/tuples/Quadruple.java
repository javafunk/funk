/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.functors.ordinals.First;
import org.javafunk.funk.functors.ordinals.Fourth;
import org.javafunk.funk.functors.ordinals.Second;
import org.javafunk.funk.functors.ordinals.Third;

import static org.javafunk.funk.Literals.listWith;

public class Quadruple<S, T, U, V>
        extends AbstractTuple
        implements First<S>, Second<T>, Third<U>, Fourth<V> {
    private S first;
    private T second;
    private U third;
    private V fourth;

    public Quadruple(S first, T second, U third, V fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    @Override public S first() {
        return first;
    }

    @Override public T second() {
        return second;
    }

    @Override public U third() {
        return third;
    }

    @Override public V fourth() {
        return fourth;
    }

    @Override public Iterable<Object> values() {
        return listWith(first, second, third, fourth);
    }
}
