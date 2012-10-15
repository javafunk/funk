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
import com.google.common.collect.Multisets;
import org.javafunk.funk.Classes;
import org.javafunk.funk.functors.functions.UnaryFunction;

import java.util.Collections;

public class MultisetBuilder<E>
        extends AbstractBuilder<E, MultisetBuilder<E>, Multiset<E>>
        implements AbstractBuilder.WithCustomImplementationSupport<E, Multiset, Multiset<E>> {
    private HashMultiset<E> elements = HashMultiset.create();

    public static <E> MultisetBuilder<E> multisetBuilder() {
        return new MultisetBuilder<E>();
    }

    public static <E> MultisetBuilder<E> multisetBuilder(Class<E> elementClass) {
        return new MultisetBuilder<E>();
    }

    @Override public Multiset<E> build() {
        return Multisets.unmodifiableMultiset(HashMultiset.create(elements));
    }

    @Override public Multiset<E> build(Class<? extends Multiset> implementationClass) {
        @SuppressWarnings("unchecked")
        Multiset<E> multiset = (Multiset<E>) Classes.uncheckedInstantiate(implementationClass);
        multiset.addAll(elements);
        return multiset;
    }

    @Override public Multiset<E> build(UnaryFunction<? super Iterable<E>, ? extends Multiset<E>> builderFunction) {
        return builderFunction.call(Collections.unmodifiableCollection(elements));
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected MultisetBuilder<E> updatedBuilder() {
        return this;
    }
}
