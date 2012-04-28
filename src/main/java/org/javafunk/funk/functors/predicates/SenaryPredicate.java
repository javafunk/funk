package org.javafunk.funk.functors.predicates;

import org.javafunk.funk.functors.functions.SenaryFunction;

public abstract class SenaryPredicate<A, B, C, D, E, F>
        implements SenaryFunction<A, B, C, D, E, F, Boolean> {
    public abstract Boolean evaluate(A firstInput,
                                     B secondInput,
                                     C thirdInput,
                                     D fourthInput,
                                     E fifthInput,
                                     F sixthInput);

    @Override public Boolean call(A firstArgument,
                                  B secondArgument,
                                  C thirdArgument,
                                  D fourthArgument,
                                  E fifthArgument,
                                  F sixthArgument) {
        return evaluate(
                firstArgument,
                secondArgument,
                thirdArgument,
                fourthArgument,
                fifthArgument,
                sixthArgument);
    }
}
