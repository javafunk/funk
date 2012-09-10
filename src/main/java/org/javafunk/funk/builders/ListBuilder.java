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

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class ListBuilder<E>
        extends AbstractBuilder<E, ListBuilder<E>, List<E>>
        implements AbstractBuilder.WithCustomImplementationSupport<List, List<E>> {
    private List<E> elements = new ArrayList<E>();

    public static <E> ListBuilder<E> listBuilder() {
        return new ListBuilder<E>();
    }

    public static <E> ListBuilder<E> listBuilder(Class<E> elementClass) {
        return new ListBuilder<E>();
    }

    @Override public List<E> build() {
        return new ArrayList<E>(elements);
    }

    @Override public List<E> build(Class<? extends List> implementationClass) {
        @SuppressWarnings("unchecked")
        List<E> list = (List<E>) Classes.uncheckedInstantiate(
                implementationClass,
                new IllegalArgumentException(
                        format("Could not instantiate instance of type %s. " +
                                "Does it have a public no argument constructor?", implementationClass.getSimpleName())));
        list.addAll(elements);
        return list;
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected ListBuilder<E> updatedBuilder() {
        return this;
    }
}
