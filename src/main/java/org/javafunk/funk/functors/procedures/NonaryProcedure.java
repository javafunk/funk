package org.javafunk.funk.functors.procedures;

public interface NonaryProcedure<A, B, C, D, E, F, G, H, I> {
    void execute(A firstArgument,
                 B secondArgument,
                 C thirdArgument,
                 D fourthArgument,
                 E fifthArgument,
                 F sixthArgument,
                 G seventhArgument,
                 H eighthArgument,
                 I ninthArgument);
}
