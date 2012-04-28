package org.javafunk.funk.functors.functions;

public interface SenaryFunction<A, B, C, D, E, F, R> {
    R call(A firstArgument,
           B secondArgument,
           C thirdArgument,
           D fourthArgument,
           E fifthArgument,
           F sixthArgument);
}
