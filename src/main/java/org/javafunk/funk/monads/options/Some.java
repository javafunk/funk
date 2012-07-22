package org.javafunk.funk.monads.options;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter;
import org.javafunk.funk.functors.functions.NullaryFunction;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.monads.Option;

import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

public class Some<T> extends Option<T> {
    private final T value;

    public static <T> Some<T> some(T value) {
        return new Some<T>(value);
    }

    private Some(T value) {
        this.value = value;
    }

    @Override public Boolean hasValue() {
        return true;
    }

    @Override public Boolean hasNoValue() {
        return false;
    }

    @Override public T get() {
        return value;
    }

    @Override public T getOrElse(T value) {
        checkNotNull(value);
        return get();
    }

    @Override public T getOrNull() {
        return get();
    }

    @Override public T getOrCall(NullaryFunction<? extends T> function) {
        checkNotNull(function);
        return get();
    }

    @Override public T getOrCall(Callable<? extends T> callable) {
        checkNotNull(callable);
        return get();
    }

    @Override public <E extends Throwable> T getOrThrow(E throwable) throws E {
        checkNotNull(throwable);
        return get();
    }

    @Override public Option<T> or(Option<? extends T> other) {
        checkNotNull(other);
        return this;
    }

    @Override public Option<T> orSome(T other) {
        return this;
    }

    @Override public Option<T> orOption(T other) {
        return this;
    }

    @Override public <S> Option<S> map(UnaryFunction<? super T, ? extends S> function) {
        checkNotNull(function);
        return some(function.call(get()));
    }

    @SuppressWarnings("unchecked")
    @Override public <S> Option<S> flatMap(UnaryFunction<? super T, ? extends Option<? extends S>> function) {
        checkNotNull(function);
        return (Option<S>) function.call(get());
    }

    @Override
    public String toString() {
        return String.format("Option::Some[%s]", get());
    }
}
