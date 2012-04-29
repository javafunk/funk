package org.javafunk.funk.functors.predicates;

import org.javafunk.funk.functors.functions.QuinaryFunction;

public abstract class QuinaryPredicate<A, B, C, D, E>
        implements QuinaryFunction<A, B, C, D, E, Boolean> {
    public abstract boolean evaluate(A firstInput,
                                     B secondInput,
                                     C thirdInput,
                                     D fourthInput,
                                     E fifthInput);

    @Override public Boolean call(A firstArgument,
                                  B secondArgument,
                                  C thirdArgument,
                                  D fourthArgument,
                                  E fifthArgument) {
        return evaluate(
                firstArgument,
                secondArgument,
                thirdArgument,
                fourthArgument,
                fifthArgument);
    }
}
