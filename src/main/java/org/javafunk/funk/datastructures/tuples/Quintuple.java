/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Fifth;

import static org.javafunk.funk.Literals.iterableBuilderFrom;

public class Quintuple<R, S, T, U, V>
        extends Quadruple<R, S, T, U>
        implements Fifth<V> {
    private final V fifth;

    public Quintuple(R first, S second, T third, U fourth, V fifth) {
        super(first, second, third, fourth);
        this.fifth = fifth;
    }

    @Override public V getFifth() {
        return fifth;
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getFifth()).build();
    }
}
