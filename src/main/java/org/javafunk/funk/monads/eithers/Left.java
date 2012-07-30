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
public class Left<L, R> extends Either<L, R> {
    private L value;

    /**
     * A generic factory method for building a {@code Left} over
     * the types {@code L} and {@code R} with the supplied value.
     *
     * @param value The value to be contained in the built {@code Left}.
     * @param <L> The type of the left slot of this {@code Left}.
     * @param <R> The type of the right slot of this {@code Left}.
     * @return A {@code Left<L, R>} containing the supplied value.
     */
    public static <L, R> Left<L, R> left(L value) {
        return new Left<L, R>(value);
    }

    /**
     * The no argument constructor is privatised since all construction
     * should go through the static factory methods {@link Left#left(Object)}
     * and {@link Either#left(Object)}.
     */
    private Left() {}

    /**
     * The no argument constructor is privatised since all construction
     * should go through the static factory methods {@link Left#left(Object)}
     * and {@link Either#left(Object)}.
     */
    private Left(L value){
        this.value = value;
    }

    /**
     * A query method to determine whether this {@code Left} represents
     * a left either which, by definition, is always {@code true}.
     *
     * @return {@code true} since, by definition, {@code Left} represents
     *         a left either.
     */
    @Override
    public boolean isLeft() {
        return true;
    }

    /**
     * A query method to determine whether this {@code Left} represents
     * a right either which, by definition, is always {@code false}.
     *
     * @return {@code false} since, by definition, {@code Left} represents
     *         a left either.
     */
    @Override
    public boolean isRight() {
        return false;
    }

    /**
     * A value access method to obtain the value in the left slot of this
     * {@code Left}. Since by definition, a {@code Left} always has a
     * value in the left slot, no {@code NoSuchElementException} is ever
     * thrown by this method for this {@code Either} implementation.
     *
     * @return The value contained in the left slot of this {@code Left}.
     */
    @Override
    public L getLeft(){
        return value;
    }
}
