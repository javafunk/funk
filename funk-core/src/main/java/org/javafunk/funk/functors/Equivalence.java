/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors;

import org.javafunk.funk.functors.predicates.BinaryPredicate;

public abstract class Equivalence<T> implements BinaryPredicate<T, T> {
    public abstract boolean equal(T first, T second);

    @Override public boolean evaluate(T first, T second) {
        return equal(first, second);
    }
}
