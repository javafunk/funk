package org.javafunk.funk.monads.options;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.NullaryFunction;
import org.javafunk.funk.monads.Option;

import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;

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

    @Override public T getOrCall(NullaryFunction<T> function) {
        checkNotNull(function);
        return get();
    }

    @Override public T getOrCall(Callable<T> callable) {
        checkNotNull(callable);
        return get();
    }

    @Override public <E extends Throwable> T getOrThrow(E throwable) throws E {
        checkNotNull(throwable);
        return get();
    }

    @Override public Option<T> or(Option<T> other) {
        checkNotNull(other);
        return this;
    }

    @Override public Option<T> orSome(T other) {
        return this;
    }

    @Override public Option<T> orOption(T other) {
        return this;
    }

    @Override public <S> Option<S> map(Mapper<? super T, ? extends S> mapper) {
        checkNotNull(mapper);
        return some(mapper.map(get()));
    }

    @Override public <S> Option<S> flatMap(Mapper<? super T, ? extends Option<S>> mapper) {
        checkNotNull(mapper);
        return mapper.map(get());
    }

    @Override
    public String toString() {
        return String.format("Option::Some[%s]", get());
    }
}
