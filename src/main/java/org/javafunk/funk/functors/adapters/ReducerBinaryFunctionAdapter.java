package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Reducer;
import org.javafunk.funk.functors.functions.BinaryFunction;

public class ReducerBinaryFunctionAdapter<S, T> implements BinaryFunction<T, S, T> {
    public static <S, T> ReducerBinaryFunctionAdapter<S, T> reducerBinaryFunction(Reducer<? super S, T> reducer) {
        return new ReducerBinaryFunctionAdapter<S, T>(reducer);
    }

    private final Reducer<? super S, T> reducer;

    public ReducerBinaryFunctionAdapter(Reducer<? super S, T> reducer) {this.reducer = reducer;}

    @Override public T call(T accumulator, S element) {
        return reducer.accumulate(accumulator, element);
    }
}
