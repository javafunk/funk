package org.javafunk.funk;

import org.javafunk.funk.functors.functions.NullaryFunction;

import java.util.NoSuchElementException;

public class Exceptions {
    public static NullaryFunction<RuntimeException> runtimeFactory(final String message) {
        return new NullaryFunction<RuntimeException>() {
            @Override public RuntimeException call() {
                return new RuntimeException(message);
            }
        };
    }

    public static NullaryFunction<RuntimeException> runtimeFactory() {
        return new NullaryFunction<RuntimeException>() {
            @Override public RuntimeException call() {
                return new RuntimeException();
            }
        };
    }

    public static NullaryFunction<? extends RuntimeException> nullPointerFactory() {
        return new NullaryFunction<RuntimeException>() {
            @Override public RuntimeException call() {
                return new NullPointerException();
            }
        };
    }

    public static NullaryFunction<NullPointerException> nullPointerFactory(final String message) {
        return new NullaryFunction<NullPointerException>() {
            @Override public NullPointerException call() {
                return new NullPointerException(message);
            }
        };
    }

    public static NullaryFunction<IllegalArgumentException> illegalArgumentFactory(final String message) {
        return new NullaryFunction<IllegalArgumentException>() {
            @Override public IllegalArgumentException call() {
                return new IllegalArgumentException(message);
            }
        };
    }

    public static NullaryFunction<NoSuchElementException> noSuchElementFactory(final String message) {
        return new NullaryFunction<NoSuchElementException>() {
            @Override public NoSuchElementException call() {
                return new NoSuchElementException(message);
            }
        };
    }

    public static NullaryFunction<ArithmeticException> arithmeticFactory(final String message) {
        return new NullaryFunction<ArithmeticException>() {
            @Override public ArithmeticException call() {
                return new ArithmeticException(message);
            }
        };
    }
}
