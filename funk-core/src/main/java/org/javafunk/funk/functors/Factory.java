/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors;

import org.javafunk.funk.functors.functions.NullaryFunction;

public abstract class Factory<T> implements NullaryFunction<T> {
    public abstract T create();

    @Override public T call() {
        return create();
    }
}
