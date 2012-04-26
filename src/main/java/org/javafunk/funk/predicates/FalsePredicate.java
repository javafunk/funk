package org.javafunk.funk.predicates;

import org.javafunk.funk.functors.Predicate;

public class FalsePredicate<T> implements Predicate<T> {
    @Override
    public boolean evaluate(T item) {
        return false;
    }
}
