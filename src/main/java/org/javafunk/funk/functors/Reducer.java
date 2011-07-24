package org.javafunk.funk.functors;

public interface Reducer<S, T> {
    T accumulate(T accumulator, S element);
}
