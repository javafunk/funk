package org.javafunk.funk.predicates;

import org.javafunk.funk.functors.Predicate;

public class TruePredicate<T> implements Predicate<T> {
    @Override
    public boolean evaluate(T item) {
        return true;
    }
}
