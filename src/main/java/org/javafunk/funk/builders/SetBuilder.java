/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class SetBuilder<E> {
    private Set<E> elements = new HashSet<E>();

    public static <E> SetBuilder<E> setBuilder() {
        return new SetBuilder<E>();
    }

    public static <E> SetBuilder<E> setBuilder(Class<E> elementClass) {
        return new SetBuilder<E>();
    }

    public Set<E> build() {
        return new HashSet<E>(elements);
    }

    @SuppressWarnings("unchecked")
    public Set<E> build(Class<? extends Set> implementationClass)
            throws IllegalAccessException, InstantiationException {
        Set<E> set = (Set<E>) implementationClass.newInstance();
        for (E element : elements) {
            set.add(element);
        }
        return set;
    }

    public SetBuilder<E> with(Iterable<? extends E> elements) {
        return and(elements);
    }

    public SetBuilder<E> with(E[] elements) {
        return and(elements);
    }

    public SetBuilder<E> and(E[] elements) {
        for (int i = 0, elementsLength = elements.length; i < elementsLength; i++) {
            this.elements.add(elements[i]);
        }
        return this;
    }

    public SetBuilder<E> and(Iterable<? extends E> elements) {
        for (E element : elements) {
            this.elements.add(element);
        }
        return this;
    }

    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e) { return and(asList(e)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e1, E e2) { return and(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e1, E e2, E e3) { return and(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e1, E e2, E e3, E e4) { return and(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e1, E e2, E e3, E e4, E e5) { return and(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e1, E e2, E e3, E e4, E e5, E e6) { return and(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return and(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
        and(e11on);
        return this;
    }

    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e) { return and(asList(e)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e1, E e2) { return and(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e1, E e2, E e3) { return and(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e1, E e2, E e3, E e4) { return and(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e1, E e2, E e3, E e4, E e5) { return and(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e1, E e2, E e3, E e4, E e5, E e6) { return and(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return and(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public SetBuilder<E> and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
        and(e11on);
        return this;
    }
}
