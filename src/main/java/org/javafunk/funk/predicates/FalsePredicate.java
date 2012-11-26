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
 * A {@code Predicate} implementation that returns {@code false}
 * regardless of the object being evaluated.
 *
 * <p>{@code FalsePredicate} equality is implemented according
 * to class alone, i.e., two distinct {@code FalsePredicate} instances
 * will be considered equal. Unfortunately, due to type erasure,
 * {@code new FalsePredicate<X>().equals(new FalsePredicate<Y>())}
 * is {@code true} for all types {@code X} and {@code Y} which may
 * not be desired.</p>
 *
 * @param <T> The type of object this {@code FalsePredicate}
 *            can evaluate.
 */
public class FalsePredicate<T> implements Predicate<T> {
    /**
     * Evaluates the supplied instance of type {@code T}
     * and always returns {@code false} regardless of
     * the instance supplied.
     *
     * @param item An instance of type {@code T} to evaluate.
     * @return {@code false} always.
     */
    @Override public boolean evaluate(T item) {
        return false;
    }

    /**
     * Implements class equality for {@code FalsePredicate} instances.
     * Two {@code FalsePredicate}s are always considered equal.
     *
     * <p>Due to type erasure, {@code new FalsePredicate<X>().equals(new FalsePredicate<Y>()}
     * will be {@code true} for all types {@code X} and {@code Y}.</p>
     *
     * @param other The object to check for equality to this {@code FalsePredicate}.
     * @return {@code true} if and only if the supplied object is also
     *         a {@code FalsePredicate}, otherwise {@code false}.
     */
    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    /**
     * Two {@code FalsePredicate} instances will have always have
     * equal hash codes.
     *
     * @return The hash code of this {@code FalsePredicate}.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
