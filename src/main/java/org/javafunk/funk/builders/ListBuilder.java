/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import java.util.ArrayList;
import java.util.List;

public class ListBuilder<E>
        extends AbstractBuilder.WithCustomImplementationSupport<E, ListBuilder<E>, List, List<E>> {
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

    @Override protected List<E> buildForClass(Class<? extends List> implementationClass)
            throws InstantiationException, IllegalAccessException {
        @SuppressWarnings("unchecked")
        List<E> list = (List<E>) implementationClass.newInstance();
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
