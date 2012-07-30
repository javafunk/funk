/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.monads.eithers;

import org.javafunk.funk.monads.Either;

/**
 * The {@code Right<L, R>} class is an implementation of {@code Either}
 * representing the presence of a value in the right slot. See
 * {@link Either} for more details.
 *
 * @param <L> The type of the left slot of this {@code Either}.
 * @param <R> The type of the right slot of this {@code Either}.
 */
public class Right<L, R> extends Either<L, R> {
    private R value;

    /**
     * A generic factory method for building a {@code Right} over
     * the types {@code L} and {@code R} with the supplied value.
     *
     * @param value The value to be contained in the built {@code Right}.
     * @param <L> The type of the left slot of this {@code Right}.
     * @param <R> The type of the right slot of this {@code Right}.
     * @return A {@code Right<L, R>} containing the supplied value.
     */
    public static <L, R> Right<L, R> right(R value) {
        return new Right<L, R>(value);
    }

    /**
     * The no argument constructor is privatised since all construction
     * should go through the static factory methods {@link Right#right(Object)}
     * and {@link Either#right(Object)}.
     */
    private Right() {}

    /**
     * The single argument constructor is privatised since all construction
     * should go through the static factory methods {@link Right#right(Object)}
     * and {@link Either#right(Object)}.
     */
    private Right(R value){
        this.value = value;
    }

    /**
     * A query method to determine whether this {@code Right} represents
     * a left either which, by definition, is always {@code false}.
     *
     * @return {@code false} since, by definition, {@code Right} represents
     *         a right either.
     */
    @Override
    public boolean isLeft() {
        return false;
    }

    /**
     * A query method to determine whether this {@code Right} represents
     * a right either which, by definition, is always {@code true}.
     *
     * @return {@code true} since, by definition, {@code Right} represents
     *         a right either.
     */
    @Override
    public boolean isRight() {
        return true;
    }

    /**
     * A value access method to obtain the value in the right slot of this
     * {@code Right}. Since by definition, a {@code Right} always has a
     * value in the right slot, no {@code NoSuchElementException} is ever
     * thrown by this method for this {@code Either} implementation.
     *
     * @return The value contained in the right slot of this {@code Right}.
     */
    @Override
    public R getRight(){
        return value;
    }
}
