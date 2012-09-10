/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import org.javafunk.funk.Classes;
import org.javafunk.funk.functors.functions.UnaryFunction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

public class SetBuilder<E>
        extends AbstractBuilder<E, SetBuilder<E>, Set<E>>
        implements AbstractBuilder.WithCustomImplementationSupport<E, Set, Set<E>> {
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

    @Override public Set<E> build(Class<? extends Set> implementationClass) {
        @SuppressWarnings("unchecked")
        Set<E> set = (Set<E>) Classes.uncheckedInstantiate(implementationClass);
        set.addAll(elements);
        return set;
    }

    @Override public Set<E> build(UnaryFunction<? super Iterable<E>, ? extends Set<E>> builderFunction) {
        return builderFunction.call(Collections.unmodifiableSet(elements));
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected SetBuilder<E> updatedBuilder() {
        return this;
    }
}
