package org.javafunk.funk.functors.procedures;

public interface QuinaryProcedure<A, B, C, D, E> {
    void execute(A firstArgument,
                 B secondArgument,
                 C thirdArgument,
                 D fourthArgument,
                 E fifthArgument);
}
