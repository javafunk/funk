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

import static org.javafunk.funk.Literals.listWith;

public class Pair<S, T>
        extends AbstractTuple
        implements First<S>, Second<T> {
    private S first;
    private T second;

    public Pair(S first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override public S first() {
        return first;
    }

    @Override public T second() {
        return second;
    }

    @Override public Iterable<Object> values() {
        return listWith(first, second);
    }
}
