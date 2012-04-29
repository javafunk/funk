package org.javafunk.funk.functors.predicates;

import org.javafunk.funk.functors.functions.TernaryFunction;

public abstract class TernaryPredicate<A, B, C>
        implements TernaryFunction<A, B, C, Boolean> {
    public abstract boolean evaluate(A firstInput,
                                     B secondInput,
                                     C thirdInput);

    @Override public Boolean call(A firstArgument,
                                  B secondArgument,
                                  C thirdArgument) {
        return evaluate(
                firstArgument,
                secondArgument,
                thirdArgument);
    }
}
