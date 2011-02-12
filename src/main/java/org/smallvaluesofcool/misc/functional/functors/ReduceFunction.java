package org.smallvaluesofcool.misc.functional.functors;

public interface ReduceFunction<S, T> {
    T accumulate(T accumulator, S element);
}
