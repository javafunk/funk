package org.javafunk.functional.predicates;

import org.javafunk.functional.functors.Predicate;

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
