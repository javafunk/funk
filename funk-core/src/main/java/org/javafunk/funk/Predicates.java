/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.functors.predicates.UnaryPredicate;
import org.javafunk.funk.predicates.EqualsPredicate;
import org.javafunk.funk.predicates.FalsePredicate;
import org.javafunk.funk.predicates.InstanceOfPredicate;
import org.javafunk.funk.predicates.NotPredicate;
import org.javafunk.funk.predicates.TruePredicate;

import static com.google.common.base.Preconditions.checkNotNull;

public class Predicates {
    private Predicates() {}

    public static <T> Predicate<T> alwaysTrue() {
        return new TruePredicate<T>();
    }

    public static <T> Predicate<T> alwaysTrue(
            @SuppressWarnings("unused") Class<T> klass) {
        return alwaysTrue();
    }

    public static <T> Predicate<T> alwaysFalse() {
        return new FalsePredicate<T>();
    }

    public static <T> Predicate<T> alwaysFalse(
            @SuppressWarnings("unused") Class<T> klass) {
        return alwaysFalse();
    }

    public static <T> Predicate<T> not(UnaryPredicate<? super T> predicate) {
        return new NotPredicate<T>(checkNotNull(predicate));
    }

    public static <T> Predicate<T> instanceOf(Class<?> testClass) {
        return new InstanceOfPredicate<T>(testClass);
    }

    public static <T> Predicate<T> equalTo(T testObject) {
        return new EqualsPredicate<T>(testObject);
    }
}
