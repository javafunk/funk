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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListBuilder<E>
        extends AbstractBuilder<E, ListBuilder<E>, List<E>>
        implements AbstractBuilder.WithCustomImplementationSupport<E, List, List<E>> {
    private List<E> elements = new ArrayList<E>();

    public static <E> ListBuilder<E> listBuilder() {
        return new ListBuilder<E>();
    }

    public static <E> ListBuilder<E> listBuilder(Class<E> elementClass) {
        return new ListBuilder<E>();
    }

    @Override public List<E> build() {
        return Collections.unmodifiableList(new ArrayList<E>(elements));
    }

    @Override public List<E> build(Class<? extends List> implementationClass) {
        @SuppressWarnings("unchecked")
        List<E> list = (List<E>) Classes.uncheckedInstantiate(implementationClass);
        list.addAll(elements);
        return list;
    }

    @Override public <T extends List<E>> T build(UnaryFunction<? super Iterable<E>, ? extends T> builderFunction) {
        return builderFunction.call(Collections.unmodifiableList(elements));
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected ListBuilder<E> updatedBuilder() {
        return this;
    }
}
