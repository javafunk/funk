/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Second;

import static org.javafunk.funk.Literals.iterableBuilderFrom;

public class Pair<R, S>
        extends Single<R>
        implements Second<S> {
    private S second;

    public Pair(R first, S second) {
        super(first);
        this.second = second;
    }

    @Override public S getSecond() {
        return second;
    }

    @Override public Iterable<Object> getValues() {
        return iterableBuilderFrom(super.getValues()).with(getSecond()).build();
    }
}
