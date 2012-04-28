package org.javafunk.funk.functors.predicates;

import org.javafunk.funk.functors.functions.NonaryFunction;

public abstract class NonaryPredicate<A, B, C, D, E, F, G, H, I>
        implements NonaryFunction<A, B, C, D, E, F, G, H, I, Boolean> {
    public abstract Boolean evaluate(A firstInput,
                                     B secondInput,
                                     C thirdInput,
                                     D fourthInput,
                                     E fifthInput,
                                     F sixthInput,
                                     G seventhInput,
                                     H octaryInput,
                                     I ninthInput);

    @Override public Boolean call(A firstArgument,
                                  B secondArgument,
                                  C thirdArgument,
                                  D fourthArgument,
                                  E fifthArgument,
                                  F sixthArgument,
                                  G seventhArgument,
                                  H octaryArgument,
                                  I ninthArgument) {
        return evaluate(
                firstArgument,
                secondArgument,
                thirdArgument,
                fourthArgument,
                fifthArgument,
                sixthArgument,
                seventhArgument,
                octaryArgument,
                ninthArgument);
    }
}
