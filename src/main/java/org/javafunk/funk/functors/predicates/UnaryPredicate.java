package org.javafunk.funk.functors.predicates;

import org.javafunk.funk.functors.functions.UnaryFunction;

public abstract class UnaryPredicate<A>
        implements UnaryFunction<A, Boolean> {
    public abstract boolean evaluate(A firstInput);

    @Override public Boolean call(A firstArgument) {
        return evaluate(firstArgument);
    }
}
