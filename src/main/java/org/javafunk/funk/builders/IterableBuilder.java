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
import java.util.List;

public class IterableBuilder<E> extends AbstractBuilder<E, IterableBuilder<E>, Iterable<E>> {
    private List<E> elements = new ArrayList<E>();

    public static <E> IterableBuilder<E> iterableBuilder() {
        return new IterableBuilder<E>();
    }

    public static <E> IterableBuilder<E> iterableBuilder(Class<E> elementClass) {
        return new IterableBuilder<E>();
    }

    @Override public Iterable<E> build() {
        return Collections.unmodifiableList(new ArrayList<E>(elements));
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected IterableBuilder<E> updatedBuilder() {
        return this;
    }

}

