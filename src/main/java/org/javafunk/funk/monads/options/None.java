package org.javafunk.funk.monads.options;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.javafunk.funk.behaviours.Value;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter;
import org.javafunk.funk.functors.functions.NullaryFunction;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.monads.Option;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

public class None<T> extends Option<T> {

    public static <T> None<T> none() {
        return new None<T>();
    }

    public static <T> None<T> none(Class<T> typeClass) {
        return new None<T>();
    }

    private None() { super(); }

    @Override public Boolean hasValue() {
        return false;
    }

    @Override public Boolean hasNoValue() {
        return true;
    }

    @Override public T get() {
        throw new NoSuchElementException();
    }

    @Override public T getOrElse(T value) {
        checkNotNull(value);
        return value;
    }

    @Override public T getOrNull() {
        return null;
    }

    @Override public T getOrCall(NullaryFunction<T> function) {
        checkNotNull(function);
        return function.call();
    }

    @Override public T getOrCall(Callable<T> callable) throws Exception {
        checkNotNull(callable);
        return callable.call();
    }

    @Override public <E extends Throwable> T getOrThrow(E throwable) throws E {
        throw throwable;
    }

    @Override public Option<T> or(Option<T> other) {
        return checkNotNull(other);
    }

    @Override public Option<T> orSome(T other) {
        return some(other);
    }

    @Override public Option<T> orOption(T other) {
        return option(other);
    }

    @Override public <S> Option<S> map(UnaryFunction<? super T, ? extends S> unaryFunction) {
        checkNotNull(unaryFunction);
        return none();
    }

    @Override public <S> Option<S> flatMap(UnaryFunction<? super T, ? extends Option<S>> function) {
        checkNotNull(function);
        return none();
    }

    @Override
    public String toString() {
        return "Option::None[]";
    }
}
