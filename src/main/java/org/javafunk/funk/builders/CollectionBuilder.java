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
import java.util.Collection;

public class CollectionBuilder<E>
        extends AbstractBuilder<E, CollectionBuilder<E>, Collection<E>>
        implements AbstractBuilder.WithCustomImplementationSupport<Collection, Collection<E>> {
    private Collection<E> elements = new ArrayList<E>();

    public static <E> CollectionBuilder<E> collectionBuilder() {
        return new CollectionBuilder<E>();
    }

    public static <E> CollectionBuilder<E> collectionBuilder(Class<E> elementClass) {
        return new CollectionBuilder<E>();
    }

    @Override public Collection<E> build() {
        return new ArrayList<E>(elements);
    }

    @Override
    public Collection<E> build(Class<? extends Collection> implementationClass)
            throws IllegalAccessException, InstantiationException {
        @SuppressWarnings("unchecked")
        Collection<E> collection = (Collection<E>) implementationClass.newInstance();
        collection.addAll(elements);
        return collection;
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected CollectionBuilder<E> updatedBuilder() {
        return this;
    }
}
