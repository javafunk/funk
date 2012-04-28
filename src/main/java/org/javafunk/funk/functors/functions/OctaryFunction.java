package org.javafunk.funk.functors.functions;

public interface OctaryFunction<A, B, C, D, E, F, G, H, R> {
    R call(A firstArgument,
           B secondArgument,
           C thirdArgument,
           D fourthArgument,
           E fifthArgument,
           F sixthArgument,
           G seventhArgument,
           H eighthArgument);
}
