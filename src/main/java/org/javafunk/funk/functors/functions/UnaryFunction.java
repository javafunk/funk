package org.javafunk.funk.functors.functions;

public interface UnaryFunction<A, R> {
    R call(A firstArgument);
}
