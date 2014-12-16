/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Factory;
import org.javafunk.funk.functors.functions.NullaryFunction;

import java.util.NoSuchElementException;

public class Exceptions {
    public static Factory<RuntimeException> runtimeFactory(final String message) {
        return new Factory<RuntimeException>() {
            @Override public RuntimeException create() {
                return new RuntimeException(message);
            }
        };
    }

    public static Factory<RuntimeException> runtimeFactory() {
        return new Factory<RuntimeException>() {
            @Override public RuntimeException create() {
                return new RuntimeException();
            }
        };
    }

    public static Factory<? extends RuntimeException> nullPointerFactory() {
        return new Factory<RuntimeException>() {
            @Override public RuntimeException create() {
                return new NullPointerException();
            }
        };
    }

    public static Factory<NullPointerException> nullPointerFactory(final String message) {
        return new Factory<NullPointerException>() {
            @Override public NullPointerException create() {
                return new NullPointerException(message);
            }
        };
    }

    public static Factory<IllegalArgumentException> illegalArgumentFactory(final String message) {
        return new Factory<IllegalArgumentException>() {
            @Override public IllegalArgumentException create() {
                return new IllegalArgumentException(message);
            }
        };
    }

    public static Factory<NoSuchElementException> noSuchElementFactory(final String message) {
        return new Factory<NoSuchElementException>() {
            @Override public NoSuchElementException create() {
                return new NoSuchElementException(message);
            }
        };
    }

    public static Factory<ArithmeticException> arithmeticFactory(final String message) {
        return new Factory<ArithmeticException>() {
            @Override public ArithmeticException create() {
                return new ArithmeticException(message);
            }
        };
    }
}
