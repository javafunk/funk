package org.javafunk.funk.functors.procedures;

public interface BinaryProcedure<A, B> {
    void execute(A firstArgument,
                 B secondArgument);
}
