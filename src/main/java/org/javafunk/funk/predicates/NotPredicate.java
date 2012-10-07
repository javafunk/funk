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

public class NotPredicate<T> implements Predicate<T> {
    private UnaryPredicate<? super T> predicate;

    public NotPredicate(UnaryPredicate<? super T> predicate) {
        this.predicate = checkNotNull(predicate);
    }

    @Override
    public boolean evaluate(T item) {
        return !predicate.evaluate(item);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
