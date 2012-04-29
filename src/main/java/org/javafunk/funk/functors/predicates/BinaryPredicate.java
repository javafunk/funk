package org.javafunk.funk.functors.predicates;

import org.javafunk.funk.functors.functions.BinaryFunction;

public abstract class BinaryPredicate<A, B>
        implements BinaryFunction<A, B, Boolean> {
    public abstract boolean evaluate(A firstInput,
                                     B secondInput);

    @Override public Boolean call(A firstArgument,
                                  B secondArgument) {
        return evaluate(
                firstArgument,
                secondArgument);
    }
}
