package org.javafunk.funk.functors.predicates;

import org.javafunk.funk.functors.functions.SeptenaryFunction;

public abstract class SeptenaryPredicate<A, B, C, D, E, F, G>
        implements SeptenaryFunction<A, B, C, D, E, F, G, Boolean> {
    public abstract Boolean evaluate(A firstInput,
                                     B secondInput,
                                     C thirdInput,
                                     D fourthInput,
                                     E fifthInput,
                                     F sixthInput,
                                     G seventhInput);

    @Override public Boolean call(A firstArgument,
                                  B secondArgument,
                                  C thirdArgument,
                                  D fourthArgument,
                                  E fifthArgument,
                                  F sixthArgument,
                                  G seventhArgument) {
        return evaluate(
                firstArgument,
                secondArgument,
                thirdArgument,
                fourthArgument,
                fifthArgument,
                sixthArgument,
                seventhArgument);
    }
}
