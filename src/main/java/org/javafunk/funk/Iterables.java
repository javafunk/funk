/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.google.common.collect.Multiset;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.javafunk.funk.Literals.*;

public class Iterables {
    private Iterables() {}

    public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> iterables) {
        return com.google.common.collect.Iterables.concat(iterables);
    }

    public static <T> List<T> asList(Iterable<? extends T> iterable) {
        return listFrom(iterable);
    }

    public static <T> Set<T> asSet(Iterable<? extends T> iterable) {
        return setFrom(iterable);
    }

    public static <T> Multiset<T> asMultiset(Iterable<? extends T> iterable) {
        return multisetFrom(iterable);
    }

    public static <T> Collection<T> materialize(Iterable<? extends T> iterable) {
        return collectionFrom(iterable);
    }

    public static <T> Iterable<T> empty() {
        return iterable();
    }
}
