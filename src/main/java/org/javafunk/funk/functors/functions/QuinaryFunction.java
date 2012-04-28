package org.javafunk.funk.functors.functions;

public interface QuinaryFunction<A, B, C, D, E, R> {
    R call(A firstArgument,
           B secondArgument,
           C thirdArgument,
           D fourthArgument,
           E fifthArgument);
}
