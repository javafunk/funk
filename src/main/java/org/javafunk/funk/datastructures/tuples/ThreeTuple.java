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

public class ThreeTuple<S, T, U> extends AbstractTuple {
    private S first;
    private T second;
    private U third;

    public ThreeTuple(S first, T second, U third) {
        this.first = first;
        this.second = second;
        this.third = third;
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

    @Override public Iterable<Object> values() {
        return listWith(first, second, third);
    }
}
