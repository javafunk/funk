/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class IterableBuilder<E> extends ArrayList<E> {
    public IterableBuilder<E> with(E... elements) {
        return and(asList(elements));
    }

    public IterableBuilder<E> with(Iterable<? extends E> elements) {
        return and(elements);
    }

    public IterableBuilder<E> and(E... elements) {
        return and(asList(elements));
    }

    public IterableBuilder<E> and(Iterable<? extends E> elements) {
        for (E element : elements) {
            add(element);
        }
        return this;
    }

    public Iterable<E> build() {
        return this;
    }
}
