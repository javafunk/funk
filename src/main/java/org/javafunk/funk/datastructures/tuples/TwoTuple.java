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

public class TwoTuple<S, T> extends AbstractTuple {
    private S first;
    private T second;

    public TwoTuple(S first, T second) {
        this.first = first;
        this.second = second;
    }

    public S first() {
        return first;
    }

    public T second() {
        return second;
    }

    @Override public Iterable<Object> values() {
        return listWith(first, second);
    }
}
