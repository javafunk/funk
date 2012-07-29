/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Equivalence;
import org.javafunk.funk.functors.predicates.BinaryPredicate;

public class EquivalenceBinaryPredicateAdapter<T> implements BinaryPredicate<T, T> {
    public static <T> EquivalenceBinaryPredicateAdapter<T> equivalenceBinaryPredicate(Equivalence<? super T> equivalence) {
        return new EquivalenceBinaryPredicateAdapter<T>(equivalence);
    }

    private final Equivalence<? super T> equivalence;

    public EquivalenceBinaryPredicateAdapter(Equivalence<? super T> equivalence) {
        this.equivalence = equivalence;
    }

    @Override public boolean evaluate(T first, T second) {
        return equivalence.equal(first, second);
    }
}
