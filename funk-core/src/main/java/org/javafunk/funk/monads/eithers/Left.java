/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.monads.eithers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
 * @see Either
 * @see Right
 * @since 1.0
 */
public class Left<L, R> extends Either<L, R> {
    private L value;

    /**
     * A generic factory method for building a {@code Left} over
     * the types {@code L} and {@code R} with the supplied value.
     *
     * @param value The value to be contained in the built {@code Left}.
     * @param <L>   The type of the left slot of this {@code Left}.
     * @param <R>   The type of the right slot of this {@code Left}.
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
    private Left(L value) {
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
    public L getLeft() {
        return value;
    }

    /**
     * A mapping method to map this {@code Either} into an {@code Either} over
     * a right value of type {@code S} obtained by calling the supplied
     * {@code UnaryFunction} with the current right value of this
     * {@code Either}.
     *
     * <p>Since, by definition, a {@code Left} represents the absence of a right
     * value, the supplied mapper will not be called and a {@code Left} over the
     * current value of type {@code S} in the right slot will be returned.</p>
     *
     * <p>If the supplied {@code UnaryFunction} is {@code null}, a
     * {@code NullPointerException} will be thrown.</p>
     *
     * @param function A {@code UnaryFunction} that would be used to map the
     *                 right value of this {@code Either} into a value of type
     *                 {@code S} if it did not represent a left value.
     * @param <S>      The type of the right slot in the resulting {@code Either}.
     * @return A {@code Left} over the current left value of type {@code S} in
     *         the right slot.
     * @throws NullPointerException if the supplied function is {@code null}.
     */
    @Override
    public <S> Either<L, S> map(UnaryFunction<? super R, ? extends S> function) {
        return mapRight(function);
    }

    /**
     * A mapping method to map this {@code Either} into an {@code Either}
     * over a left value of type {@code S} obtained by calling the
     * supplied {@code UnaryFunction} with the current left value of this
     * {@code Either}.
     *
     * <p>Since, by definition, a {@code Left} represents the presence of a
     * left value, the supplied mapper will always be called and a {@code Left}
     * over the value of type {@code S} returned by the mapper will be returned
     * with the same type {@code R} in the right slot as this {@code Either}.</p>
     *
     * <p>If the supplied {@code UnaryFunction} is {@code null}, a
     * {@code NullPointerException} will be thrown.</p>
     *
     * @param function A {@code UnaryFunction} to map the left value of
     *                 this {@code Either} into a value of type {@code S}.
     * @param <S>      The type of the left slot of the resulting {@code Either}.
     * @return A {@code Left} representing a left value of type {@code S}
     *         obtained by calling the supplied {@code UnaryFunction} with the
     *         current left value.
     * @throws NullPointerException if the supplied mapper is {@code null}.
     */
    @Override
    public <S> Either<S, R> mapLeft(UnaryFunction<? super L, ? extends S> function) {
        return left(checkNotNull(function).call(value));
    }

    /**
     * A mapping method to map this {@code Either} into an {@code Either}
     * over a right value of type {@code S} obtained by calling the
     * supplied {@code UnaryFunction} with the current right value of this
     * {@code Either}.
     *
     * <p>Since, by definition, a {@code Left} represents the absence of a right
     * value, the supplied mapper will not be called and a {@code Left} over the
     * current value of type {@code S} in the right slot will be returned.</p>
     *
     * <p>If the supplied {@code UnaryFunction} is {@code null}, a
     * {@code NullPointerException} will be thrown.</p>
     *
     * @param function A {@code UnaryFunction} that would be used to map the
     *                 right value of this {@code Either} into a value of type
     *                 {@code S} if it did not represent a left value.
     * @param <S>      The type of the right slot of the resulting {@code Either}.
     * @return A {@code Left} over the current left value of type {@code S} in
     *         the right slot.
     * @throws NullPointerException if the supplied mapper is {@code null}.
     */
    @Override
    public <S> Either<L, S> mapRight(UnaryFunction<? super R, ? extends S> function) {
        checkNotNull(function);
        return left(value);
    }

    /**
     * A mapping method to map this {@code Either}, regardless of whether it
     * represents a right value or a left value, using the supplied
     * {@code UnaryFunction} instances.
     *
     * <p>Since, by definition, a {@code Left} represents the presence of a right
     * value, the supplied left mapper will be called and a {@code Left} over the
     * returned value of type {@code M} with a type {@code S} in the right slot
     * will be returned.</p>
     *
     * <p>If either of the supplied {@code UnaryFunction}s are {@code null}, a
     * {@code NullPointerException} will be thrown.</p>
     *
     * @param leftMapper  A {@code UnaryFunction} to map the left value of
     *                    this {@code Either} into a value of type {@code M}.
     * @param rightMapper A {@code UnaryFunction} that would be used to map
     *                    the right value of this {@code Either} into a value
     *                    of type {@code S} if it represented a right value.
     * @param <M>         The type of the left value of the resulting {@code Either}.
     * @param <S>         The type of the right value of the resulting {@code Either}.
     * @return An {@code Either} of left type {@code M} and right type {@code S}
     *         obtained by applying the supplied left mapper to the contained
     *         value.
     * @throws NullPointerException if either of the supplied functions is
     *                              {@code null}.
     */
    @Override public <M, S> Either<M, S> mapAll(
            UnaryFunction<? super L, ? extends M> leftMapper,
            UnaryFunction<? super R, ? extends S> rightMapper) {
        checkNotNull(leftMapper);
        checkNotNull(rightMapper);
        return left(leftMapper.call(value));
    }

    /**
     * Implements value equality for {@code Left} instances. Two {@code Either}s are
     * equal if they both contain the same value in the same slot thus in the case of
     * {@code Left}, if both {@code Either} instances are {@code Left} instances and
     * they hold the same value, they are equal, otherwise they are not.
     *
     * <p>Due to type erasure, {@code Either.<X, Y>left(x).equals(Either.<X, Z>left(x)}
     * will be {@code true} for all types {@code X} and {@code Y} and {@code Z}.</p>
     *
     * @param other The object to check for equality to this {@code Left}.
     * @return {@code true} if the supplied {@code Left} is equal to this {@code Left},
     *         otherwise, {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    /**
     * Two {@code Either} objects will have equal hash codes either if they both represent
     * the same slot and they both contain the same value (of the same type) in that slot.
     *
     * @return The hash code of this {@code Either}.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format("Either::Left[%s]", value);
    }
}
