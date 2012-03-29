/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import static org.javafunk.funk.Literals.listWith;

public class FourTuple<S, T, U, V> extends AbstractTuple {
    private S first;
    private T second;
    private U third;
    private V fourth;

    public FourTuple(S first, T second, U third, V fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public S first() {
        return first;
    }

    public T second() {
        return second;
    }

    public U third() {
        return third;
    }

    public V fourth() {
        return fourth;
    }

    @Override public Iterable<Object> values() {
        return listWith(first, second, third, fourth);
    }
}
