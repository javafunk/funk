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
import org.javafunk.funk.functors.ordinals.Second;
import org.javafunk.funk.functors.ordinals.Third;

import static org.javafunk.funk.Literals.listWith;

public class Triple<S, T, U>
        extends AbstractTuple
        implements First<S>, Second<T>, Third<U> {
    private S first;
    private T second;
    private U third;

    public Triple(S first, T second, U third) {
        this.first = first;
        this.second = second;
        this.third = third;
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

    @Override public Iterable<Object> values() {
        return listWith(first, second, third);
    }
}
