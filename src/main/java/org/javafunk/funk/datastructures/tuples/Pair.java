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
import org.javafunk.funk.behaviours.ordinals.Second;

import static org.javafunk.funk.Literals.listWith;

public class Pair<R, S>
        extends AbstractTuple
        implements First<R>, Second<S> {
    private R first;
    private S second;

    public Pair(R first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override public R first() {
        return first;
    }

    @Override public S second() {
        return second;
    }

    @Override public Iterable<Object> values() {
        return listWith(first, second);
    }
}
