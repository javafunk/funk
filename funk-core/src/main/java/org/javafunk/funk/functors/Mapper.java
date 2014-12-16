/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors;

import org.javafunk.funk.functors.functions.UnaryFunction;

public abstract class Mapper<S, T> implements UnaryFunction<S, T> {
    public abstract T map(S input);

    @Override public T call(S input) {
        return map(input);
    }
}
