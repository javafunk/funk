package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Factory;
import org.javafunk.funk.functors.functions.NullaryFunction;

public class FactoryNullaryFunctionAdapter<T> implements NullaryFunction<T> {
    private final Factory<? extends T> factory;

    public static <T> FactoryNullaryFunctionAdapter<T> factoryNullaryFunction(Factory<? extends T> factory) {
        return new FactoryNullaryFunctionAdapter<T>(factory);
    }

    public FactoryNullaryFunctionAdapter(Factory<? extends T> factory) {
        this.factory = factory;
    }

    @Override public T call() {
        return factory.create();
    }
}
