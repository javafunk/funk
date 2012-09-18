/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.First;

import static org.javafunk.funk.Literals.iterableBuilderOf;

public class Single<R>
        extends AbstractTuple
        implements First<R> {
    private R first;

    public Single(R first) {
        this.first = first;
    }

    @Override public R getFirst() {
        return first;
    }

    @Override public Iterable<Object> values() {
        return iterableBuilderOf(Object.class).with(first).build();
    }
}
