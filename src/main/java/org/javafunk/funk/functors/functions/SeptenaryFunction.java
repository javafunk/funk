package org.javafunk.funk.functors.functions;

public interface SeptenaryFunction<A, B, C, D, E, F, G, R> {
    R call(A firstArgument,
           B secondArgument,
           C thirdArgument,
           D fourthArgument,
           E fifthArgument,
           F sixthArgument,
           G seventhArgument);
}
