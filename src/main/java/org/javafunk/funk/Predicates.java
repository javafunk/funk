package org.javafunk.funk;

import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.predicates.FalsePredicate;
import org.javafunk.funk.predicates.NotPredicate;
import org.javafunk.funk.predicates.TruePredicate;

public class Predicates {
    public static <T> Predicate<T> alwaysTrue() {
        return new TruePredicate<T>();
    }

    public static <T> Predicate<T> alwaysFalse() {
        return new FalsePredicate<T>();
    }

    public static <T> Predicate<T> not(Predicate<? super T> predicate) {
        return new NotPredicate<T>(predicate);
    }
}
