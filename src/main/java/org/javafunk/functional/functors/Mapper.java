package org.javafunk.functional.functors;

public interface Mapper<S, T> {
    T map(S input);
}
