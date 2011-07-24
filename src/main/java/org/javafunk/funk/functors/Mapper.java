package org.javafunk.funk.functors;

public interface Mapper<S, T> {
    T map(S input);
}
