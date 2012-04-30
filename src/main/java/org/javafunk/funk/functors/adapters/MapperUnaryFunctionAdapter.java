package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;

public class MapperUnaryFunctionAdapter<S, T> implements UnaryFunction<S, T> {
    public static <S, T> MapperUnaryFunctionAdapter<S, T> mapperUnaryFunction(Mapper<S, T> mapper) {
        return new MapperUnaryFunctionAdapter<S, T>(mapper);
    }

    private final Mapper<? super S, T> mapper;

    public MapperUnaryFunctionAdapter(Mapper<? super S, T> mapper) {
        this.mapper = mapper;
    }

    @Override public T call(S input) {
        return mapper.map(input);
    }
}
