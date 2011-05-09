package org.javafunk.functional.functors;

public class NotPredicateFunction<T> implements PredicateFunction<T> {
    private PredicateFunction<? super T> predicate;

    public NotPredicateFunction(PredicateFunction<? super T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean matches(T item) {
        return !predicate.matches(item);
    }
}
