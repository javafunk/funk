/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static java.lang.String.format;
import static org.javafunk.funk.Literals.iterableWith;

public abstract class AbstractBuilder<E, B extends AbstractBuilder, C> {
    public abstract C build();

    protected abstract void handle(E element);

    protected abstract B updatedBuilder();

    public B with(Iterable<? extends E> elements) {
        return and(elements);
    }

    public B with(E[] elements) {
        return and(elements);
    }

    public B and(E[] elements) {
        for (E element : elements) {
            handle(element);
        }
        return updatedBuilder();
    }

    public B and(Iterable<? extends E> elements) {
        for (E element : elements) {
            handle(element);
        }
        return updatedBuilder();
    }

    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public static interface WithCustomImplementationSupport<S, C extends S> {
        C build(Class<? extends S> implementationClass);
    }

    public B with(E e) { return and(iterableWith(e)); }

    public B with(E e1, E e2) { return and(iterableWith(e1, e2)); }

    public B with(E e1, E e2, E e3) { return and(iterableWith(e1, e2, e3)); }

    public B with(E e1, E e2, E e3, E e4) { return and(iterableWith(e1, e2, e3, e4)); }

    public B with(E e1, E e2, E e3, E e4, E e5) { return and(iterableWith(e1, e2, e3, e4, e5)); }

    public B with(E e1, E e2, E e3, E e4, E e5, E e6) { return and(iterableWith(e1, e2, e3, e4, e5, e6)); }

    public B with(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return and(iterableWith(e1, e2, e3, e4, e5, e6, e7)); }

    public B with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return and(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8)); }

    public B with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return and(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }

    public B with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return and(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }

    public B with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        and(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
        and(e11on);
        return updatedBuilder();
    }

    public B and(E e) { return and(iterableWith(e)); }

    public B and(E e1, E e2) { return and(iterableWith(e1, e2)); }

    public B and(E e1, E e2, E e3) { return and(iterableWith(e1, e2, e3)); }

    public B and(E e1, E e2, E e3, E e4) { return and(iterableWith(e1, e2, e3, e4)); }

    public B and(E e1, E e2, E e3, E e4, E e5) { return and(iterableWith(e1, e2, e3, e4, e5)); }

    public B and(E e1, E e2, E e3, E e4, E e5, E e6) { return and(iterableWith(e1, e2, e3, e4, e5, e6)); }

    public B and(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return and(iterableWith(e1, e2, e3, e4, e5, e6, e7)); }

    public B and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return and(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8)); }

    public B and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return and(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }

    public B and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return and(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }

    public B and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        and(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
        and(e11on);
        return updatedBuilder();
    }
}
