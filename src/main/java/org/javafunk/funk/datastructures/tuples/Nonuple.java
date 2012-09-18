/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Ninth;

import static org.javafunk.funk.Literals.iterableBuilderFrom;

public class Nonuple<R, S, T, U, V, W, X, Y, Z>
        extends Octuple<R, S, T, U, V, W, X, Y>
        implements Ninth<Z> {
    private final Z ninth;

    public Nonuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth, Z ninth) {
        super(first, second, third, fourth, fifth, sixth, seventh, eighth);
        this.ninth = ninth;
    }

    @Override public Z getNinth() {
        return ninth;
    }

    @Override public Iterable<Object> values() {
        return iterableBuilderFrom(super.values()).with(getNinth()).build();
    }
}
