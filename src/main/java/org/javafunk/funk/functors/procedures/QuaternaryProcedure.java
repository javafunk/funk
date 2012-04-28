package org.javafunk.funk.functors.procedures;

public interface QuaternaryProcedure<A, B, C, D> {
    void execute(A firstArgument,
                 B secondArgument,
                 C thirdArgument,
                 D fourthArgument);
}
