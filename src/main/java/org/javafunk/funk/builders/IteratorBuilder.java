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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class IteratorBuilder<E> extends AbstractBuilder<E, IteratorBuilder<E>, Iterator<E>> {
    private List<E> elements = new ArrayList<E>();

    public static <E> IteratorBuilder<E> iteratorBuilder() {
        return new IteratorBuilder<E>();
    }

    public static <E> IteratorBuilder<E> iteratorBuilder(Class<E> elementClass) {
        return new IteratorBuilder<E>();
    }

    @Override public Iterator<E> build() {
        return Collections.unmodifiableCollection(new ArrayList<E>(elements)).iterator();
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected IteratorBuilder<E> updatedBuilder() {
        return this;
    }
}
