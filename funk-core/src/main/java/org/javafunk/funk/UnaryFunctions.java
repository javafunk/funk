/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.functions.UnaryFunction;

public class UnaryFunctions {
    private UnaryFunctions() {}

    public static <T> UnaryFunction<T, T> identity() {
        return new UnaryFunction<T, T>() {
            @Override public T call(T input) {
                return input;
            }
        };
    }
}
