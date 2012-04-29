package org.javafunk.funk.functors.predicates;

import org.javafunk.funk.functors.functions.NullaryFunction;

public abstract class NullaryPredicate
        implements NullaryFunction<Boolean> {
    public abstract boolean evaluate();

    @Override public Boolean call() {
        return evaluate();
    }
}
