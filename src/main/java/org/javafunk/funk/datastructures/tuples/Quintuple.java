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

public class Quintuple<R, S, T, U, V>
        extends AbstractTuple
        implements First<R>, Second<S>, Third<T>, Fourth<U>, Fifth<V> {
    private final R first;
    private final S second;
    private final T third;
    private final U fourth;
    private final V fifth;

    public Quintuple(R first, S second, T third, U fourth, V fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
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

    @Override public Iterable<Object> values() {
        return iterableWith(first, second, third, fourth, fifth);
    }
}
