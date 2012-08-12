/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.monads.eithers;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.monads.Either;

import static com.google.common.base.Preconditions.checkNotNull;

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
     * @param <L>   The type of the left slot of this {@code Right}.
     * @param <R>   The type of the right slot of this {@code Right}.
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
    private Right(R value) {
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
    public R getRight() {
        return value;
    }

    /**
     * A mapping method to map this {@code Either} into an {@code Either} over
     * a right value of type {@code S} obtained by calling the supplied
     * {@code UnaryFunction} with the current right value of this
     * {@code Either}.
     *
     * <p>Since, by definition, a {@code Right} represents the presence of a
     * right value, the supplied mapper will always be called and a {@code Right}
     * over the value of type {@code S} returned by the mapper will be returned
     * with the same type {@code L} in the left slot as this {@code Either}.</p>
     *
     * <p>If the supplied {@code UnaryFunction} is {@code null}, a
     * {@code NullPointerException} will be thrown.</p>
     *
     * @param function A {@code UnaryFunction} to map the right value of this
     *                 {@code Either} into a value of type {@code S}.
     * @param <S>      The type of the right slot in the resulting {@code Either}.
     * @return A {@code Right} over the value returned after calling the supplied
     *         {@code UnaryFunction} with the current value of this {@code Either}
     *         with the same type {@code L} as this {@code Either} in the left
     *         slot.
     * @throws NullPointerException if the supplied function is {@code null}.
     */
    @Override
    public <S> Either<L, S> map(UnaryFunction<? super R, ? extends S> function) {
        return right(checkNotNull(function).call(value));
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format("Either::Right[%s]", value);
    }
}
