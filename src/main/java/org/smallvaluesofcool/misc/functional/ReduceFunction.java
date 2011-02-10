package org.smallvaluesofcool.misc.functional;

public interface ReduceFunction<T> {
    T accumulate(T accumulator, T element);
}
