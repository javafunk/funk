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
