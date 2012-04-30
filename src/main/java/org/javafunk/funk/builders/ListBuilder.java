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
import java.util.List;

import static java.util.Arrays.asList;

public class ListBuilder<E> extends ArrayList<E> {
    public ListBuilder<E> with(E... elements) {
        return and(asList(elements));
    }

    public ListBuilder<E> with(Iterable<? extends E> elements) {
        return and(elements);
    }

    public ListBuilder<E> and(E... elements) {
        return and(asList(elements));
    }

    public ListBuilder<E> and(Iterable<? extends E> elements) {
        for (E element : elements) {
            add(element);
        }
        return this;
    }

    public List<E> build() {
        return this;
    }
}
