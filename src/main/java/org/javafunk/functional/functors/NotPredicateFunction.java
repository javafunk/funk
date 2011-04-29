package org.javafunk.functional.functors;

public class NotPredicateFunction<T> implements PredicateFunction<T> {
    private PredicateFunction<T> predicate;

    public NotPredicateFunction(PredicateFunction<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean matches(T item) {
        return !predicate.matches(item);
    }
}
