package org.smallvaluesofcool.misc.functional.functors;

public interface ReduceFunction<T> {
    T accumulate(T accumulator, T element);
}
