package org.javafunk.functional.functors;

public interface Reducer<S, T> {
    T accumulate(T accumulator, S element);
}
