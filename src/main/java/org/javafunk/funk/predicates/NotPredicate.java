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
import org.javafunk.funk.functors.predicates.UnaryPredicate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@code NotPredicate} is a {@code Predicate} implementation that
 * takes a delegate {@code Predicate} and when asked to evaluate an
 * instance of type {@code T} returns {@code true} if the delegate
 * {@code Predicate} returns {@code false} and {@code false} if the
 * delegate returns {@code true}.
 *
 * @param <T> The type of object this {@code NotPredicate} can evaluate.
 */
public class NotPredicate<T> implements Predicate<T> {
    private UnaryPredicate<? super T> predicate;

    /**
     * Constructs a {@code NotPredicate} instance over the supplied
     * {@code UnaryPredicate}.
     *
     * <p>If the supplied {@code UnaryPredicate} is {@code null}, a
     * {@code NullPointerException} is thrown.</p>
     *
     * @param predicate The {@code UnaryPredicate} over type {@code T}
     *                  for which to form the logical complement.
     */
    public NotPredicate(UnaryPredicate<? super T> predicate) {
        this.predicate = checkNotNull(predicate);
    }

    /**
     * Evaluates the supplied instance of type {@code T} against
     * the {@code UnaryPredicate} instance associated with this
     * {@code NotPredicate}.
     *
     * <p>If the delegate {@code UnaryPredicate} returns {@code true}
     * for the supplied object, {@code false} is returned and if it
     * returns false, {@code true} is returned.</p>
     *
     * @param instance An instance of type {@code T} to evaluate.
     * @return {@code true} if the delegate {@code UnaryPredicate}
     *         returns {@code false}, {@code false} if it returns
     *         {@code true}.
     */
    @Override public boolean evaluate(T instance) {
        return !predicate.evaluate(instance);
    }

    /**
     * Implements value equality for {@code NotPredicate} instances. Two
     * {@code NotPredicate}s are considered equal if the {@code UnaryPredicate}
     * instances supplied to each at initialisation are equal.
     *
     * <p>Due to type erasure,
     * {@code new NotPredicate<X>(iterableWith(Predicates.<X>alwaysFalse())).equals(new NotPredicate<Y>(iterableWith(Predicates.<Y>alwaysFalse()))}
     * will be {@code true} for all types {@code X} and {@code Y}.</p>
     *
     * @param other The object to check for equality to this {@code NotPredicate}.
     * @return {@code true} if the supplied object is also a {@code NotPredicate}
     *         composed of the same delegate {@code UnaryPredicate} instance,
     *         otherwise {@code false}.
     */
    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    /**
     * Two {@code NotPredicate} instances will have equal hash codes
     * if they are composed of the same delegate {@code UnaryPredicate}
     * instance.
     *
     * @return The hash code of this {@code NotPredicate}.
     */
    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
