/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.predicates;

import org.javafunk.funk.functors.Predicate;

public class NotPredicate<T> implements Predicate<T> {
    private Predicate<? super T> predicate;

    public NotPredicate(Predicate<? super T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean evaluate(T item) {
        return !predicate.evaluate(item);
    }
}
