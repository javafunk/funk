package org.javafunk.funk.functors.predicates;

import org.javafunk.funk.functors.functions.OctaryFunction;

public abstract class OctaryPredicate<A, B, C, D, E, F, G, H>
        implements OctaryFunction<A, B, C, D, E, F, G, H, Boolean> {
    public abstract Boolean evaluate(A firstInput,
                                     B secondInput,
                                     C thirdInput,
                                     D fourthInput,
                                     E fifthInput,
                                     F sixthInput,
                                     G seventhInput,
                                     H octaryInput);

    @Override public Boolean call(A firstArgument,
                                  B secondArgument,
                                  C thirdArgument,
                                  D fourthArgument,
                                  E fifthArgument,
                                  F sixthArgument,
                                  G seventhArgument,
                                  H octaryArgument) {
        return evaluate(
                firstArgument,
                secondArgument,
                thirdArgument,
                fourthArgument,
                fifthArgument,
                sixthArgument,
                seventhArgument,
                octaryArgument);
    }
}
