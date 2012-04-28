package org.javafunk.funk.functors.predicates;

import org.javafunk.funk.functors.functions.QuaternaryFunction;

public abstract class QuaternaryPredicate<A, B, C, D>
        implements QuaternaryFunction<A, B, C, D, Boolean> {
    public abstract Boolean evaluate(A firstInput,
                                     B secondInput,
                                     C thirdInput,
                                     D fourthInput);

    @Override public Boolean call(A firstArgument,
                                  B secondArgument,
                                  C thirdArgument,
                                  D fourthArgument) {
        return evaluate(
                firstArgument,
                secondArgument,
                thirdArgument,
                fourthArgument);
    }
}
