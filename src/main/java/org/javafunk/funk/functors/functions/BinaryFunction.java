package org.javafunk.funk.functors.functions;

public interface BinaryFunction<A, B, R> {
    R call(A firstArgument,
           B secondArgument);
}
