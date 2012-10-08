/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Factory;
import org.javafunk.funk.functors.functions.NullaryFunction;

import static com.google.common.base.Preconditions.checkNotNull;

public class FactoryNullaryFunctionAdapter<T> implements NullaryFunction<T> {
    private final Factory<? extends T> factory;

    public static <T> FactoryNullaryFunctionAdapter<T> factoryNullaryFunction(Factory<? extends T> factory) {
        return new FactoryNullaryFunctionAdapter<T>(factory);
    }

    public FactoryNullaryFunctionAdapter(Factory<? extends T> factory) {
        this.factory = checkNotNull(factory);
    }

    @Override public T call() {
        return factory.create();
    }
}
