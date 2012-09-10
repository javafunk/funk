/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class MultisetBuilder<E>
        extends AbstractBuilder.WithCustomImplementationSupport<E, MultisetBuilder<E>, Multiset, Multiset<E>> {
    private HashMultiset<E> elements = HashMultiset.create();

    public static <E> MultisetBuilder<E> multisetBuilder() {
        return new MultisetBuilder<E>();
    }

    public static <E> MultisetBuilder<E> multisetBuilder(Class<E> elementClass) {
        return new MultisetBuilder<E>();
    }

    @Override public Multiset<E> build() {
        return HashMultiset.create(elements);
    }

    @Override protected Multiset<E> buildForClass(Class<? extends Multiset> implementationClass)
            throws IllegalAccessException, InstantiationException {
        @SuppressWarnings("unchecked")
        Multiset<E> multiset = (Multiset<E>) implementationClass.newInstance();
        multiset.addAll(elements);
        return multiset;
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected MultisetBuilder<E> updatedBuilder() {
        return this;
    }
}
