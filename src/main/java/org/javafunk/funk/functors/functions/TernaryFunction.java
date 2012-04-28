package org.javafunk.funk.functors.functions;

public interface TernaryFunction<A, B, C, R> {
    R call(A firstArgument,
           B secondArgument,
           C thirdArgument);
}
