/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors;

import org.javafunk.funk.functors.functions.UnaryFunction;

public abstract class Indexer<I, O> implements UnaryFunction<I, O> {
    public abstract O index(I item);

    @Override public O call(I item) {
        return index(item);
    }
}
