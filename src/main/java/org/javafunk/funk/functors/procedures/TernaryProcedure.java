package org.javafunk.funk.functors.procedures;

public interface TernaryProcedure<A, B, C> {
    void execute(A firstArgument,
                 B secondArgument,
                 C thirdArgument);
}
