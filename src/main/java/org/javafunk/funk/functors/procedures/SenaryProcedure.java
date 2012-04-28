package org.javafunk.funk.functors.procedures;

public interface SenaryProcedure<A, B, C, D, E, F> {
    void execute(A firstArgument,
                 B secondArgument,
                 C thirdArgument,
                 D fourthArgument,
                 E fifthArgument,
                 F sixthArgument);
}
