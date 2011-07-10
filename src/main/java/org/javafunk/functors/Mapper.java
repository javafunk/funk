package org.javafunk.functors;

public interface Mapper<S, T> {
    T map(S input);
}
