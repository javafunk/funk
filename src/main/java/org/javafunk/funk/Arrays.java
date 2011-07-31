/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import java.util.List;

import static org.javafunk.funk.Literals.listFrom;

public class Arrays {
    private Arrays() {}

    public static <T> List<T> asList(T[] array) {
        return listFrom(array);
    }

    public static <T> Iterable<T> asIterable(T[] array) {
        return listFrom(array);
    }
}
