/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Sixth;

import static org.javafunk.funk.Literals.iterableBuilderFrom;

public class Sextuple<R, S, T, U, V, W>
        extends Quintuple<R, S, T, U, V>
        implements Sixth<W> {
    private final W sixth;

    public Sextuple(R first, S second, T third, U fourth, V fifth, W sixth) {
        super(first, second, third, fourth, fifth);
        this.sixth = sixth;
    }

    @Override public W getSixth() {
        return sixth;
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getSixth()).build();
    }
}
