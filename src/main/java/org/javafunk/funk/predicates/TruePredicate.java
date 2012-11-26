/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.predicates;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.javafunk.funk.functors.Predicate;

/**
 * A {@code Predicate} implementation that returns {@code true}
 * regardless of the object being evaluated.
 *
 * <p>{@code TruePredicate} equality is implemented according
 * to class alone, i.e., two distinct {@code TruePredicate} instances
 * will be considered equal. Unfortunately, due to type erasure,
 * {@code new TruePredicate<X>().equals(new TruePredicate<Y>())}
 * is {@code true} for all types {@code X} and {@code Y} which may
 * not be desired.</p>
 *
 * @param <T> The type of object this {@code TruePredicate}
 *            can evaluate.
 */
public class TruePredicate<T> implements Predicate<T> {
    /**
     * Evaluates the supplied instance of type {@code T}
     * and always returns {@code true} regardless of
     * the instance supplied.
     *
     * @param item An instance of type {@code T} to evaluate.
     * @return {@code true} always.
     */
    @Override public boolean evaluate(T item) {
        return true;
    }

    /**
     * Implements class equality for {@code TruePredicate} instances.
     * Two {@code TruePredicate}s are always considered equal.
     *
     * <p>Due to type erasure, {@code new TruePredicate<X>().equals(new TruePredicate<Y>()}
     * will be {@code true} for all types {@code X} and {@code Y}.</p>
     *
     * @param other The object to check for equality to this {@code TruePredicate}.
     * @return {@code true} if and only if the supplied object is also
     *         a {@code TruePredicate}, otherwise {@code false}.
     */
    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    /**
     * Two {@code TruePredicate} instances will have always have
     * equal hash codes.
     *
     * @return The hash code of this {@code TruePredicate}.
     */
    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
