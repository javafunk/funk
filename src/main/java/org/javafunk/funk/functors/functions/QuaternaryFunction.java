package org.javafunk.funk.functors.functions;

public interface QuaternaryFunction<A, B, C, D, R> {
    R call(A firstArgument,
           B secondArgument,
           C thirdArgument,
           D fourthArgument);
}
