/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Fourth;

import static org.javafunk.funk.Literals.iterableBuilderFrom;

public class Quadruple<R, S, T, U>
        extends Triple<R, S, T>
        implements Fourth<U> {
    private U fourth;

    public Quadruple(R first, S second, T third, U fourth) {
        super(first, second, third);
        this.fourth = fourth;
    }

    @Override public U fourth() {
        return fourth;
    }

    @Override public Iterable<Object> values() {
        return iterableBuilderFrom(super.values()).with(fourth()).build();
    }
}
