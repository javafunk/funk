package org.javafunk.predicates;

import org.javafunk.functors.Predicate;

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
