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

import java.util.ArrayList;

import static org.javafunk.funk.Literals.collectionWith;

public class Single<R>
        extends AbstractTuple
        implements First<R> {
    private R first;

    public Single(R first) {
        this.first = first;
    }

    @Override public R first() {
        return first;
    }

    @Override public Iterable<Object> values() {
        return new ArrayList<Object>(collectionWith(first));
    }
}
