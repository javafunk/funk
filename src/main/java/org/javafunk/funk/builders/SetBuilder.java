/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import java.util.HashSet;
import java.util.Set;

public class SetBuilder<E>
        extends AbstractBuilder<E, SetBuilder<E>, Set<E>>
        implements AbstractBuilder.WithCustomImplementationSupport<Set, Set<E>> {
    private Set<E> elements = new HashSet<E>();

    public static <E> SetBuilder<E> setBuilder() {
        return new SetBuilder<E>();
    }

    public static <E> SetBuilder<E> setBuilder(Class<E> elementClass) {
        return new SetBuilder<E>();
    }

    @Override public Set<E> build() {
        return new HashSet<E>(elements);
    }

    @Override
    public Set<E> build(Class<? extends Set> implementationClass)
            throws IllegalAccessException, InstantiationException {
        @SuppressWarnings("unchecked")
        Set<E> set = (Set<E>) implementationClass.newInstance();
        set.addAll(elements);
        return set;
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected SetBuilder<E> updatedBuilder() {
        return this;
    }
}
