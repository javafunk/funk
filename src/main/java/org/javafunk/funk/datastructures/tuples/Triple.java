/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Third;

import static org.javafunk.funk.Literals.iterableBuilderFrom;

public class Triple<R, S, T>
        extends Pair<R, S>
        implements Third<T> {
    private T third;

    public Triple(R first, S second, T third) {
        super(first, second);
        this.third = third;
    }

    @Override public T getThird() {
        return third;
    }

    @Override public Iterable<Object> values() {
        return iterableBuilderFrom(super.values()).with(getThird()).build();
    }
}
