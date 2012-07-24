/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.monads;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.javafunk.funk.Iterators;
import org.javafunk.funk.behaviours.Mappable;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.NullaryFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.unmodifiableCollection;
import static org.javafunk.funk.Literals.collectionWith;

public abstract class Option<T>
        implements Mappable<T, Option<?>>,
                   Iterable<T> {
    public static <T> Option<T> none() {
        return new None<T>();
    }

    public static <T> Option<T> none(Class<T> typeClass) {
        return new None<T>();
    }

    public static <T> Option<T> some(T value) {
        return new Some<T>(value);
    }

    public static <T> Option<T> option(T value) {
        if (value == null) {
            return none();
        }
        return some(value);
    }

    public abstract Boolean hasValue();

    public abstract Boolean hasNoValue();

    public abstract T get();

    public abstract T getOrElse(T value);

    public abstract T getOrNull();

    public abstract T getOrCall(NullaryFunction<T> function);

    public abstract T getOrCall(Callable<T> callable) throws Exception;

    public abstract <E extends Throwable> T getOrThrow(E throwable) throws E;

    public abstract Option<T> or(Option<T> other);

    public abstract Option<T> orSome(T other);

    public abstract Option<T> orOption(T other);

    public abstract <S> Option<S> map(Mapper<? super T, ? extends S> mapper);

    public abstract <S> Option<S> flatMap(Mapper<? super T, ? extends Option<S>> mapper);

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    private static class None<T> extends Option<T> {
        /**
         * Provides an iterator so that {@code Option} instances conform to the
         * {@code Iterable} interface.
         *
         * <p>Since a {@code None} instance represents the absence of a value,
         * the iterator returned will be empty.</p>
         *
         * <p>The returned iterator does not support removal.</p>
         *
         * @return An empty iterator.
         */
        @Override public Iterator<T> iterator() {
            return Iterators.emptyIterator();
        }

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

        @Override public <S> Option<S> map(Mapper<? super T, ? extends S> mapper) {
            checkNotNull(mapper);
            return none();
        }

        @Override public <S> Option<S> flatMap(Mapper<? super T, ? extends Option<S>> mapper) {
            checkNotNull(mapper);
            return none();
        }

        @Override
        public String toString() {
            return "Option::None[]";
        }
    }

    private static class Some<T> extends Option<T> {
        private final T value;

        public Some(T value) {
            this.value = value;
        }

        /**
         * Provides an iterator so that {@code Option} instances conform to the
         * {@code Iterable} interface.
         *
         * <p>Since a {@code Some} instance represents the presence of a value,
         * the iterator returned will be over the value of this {@code Some}.</p>
         *
         * <p>The returned iterator does not support removal.</p>
         *
         * @return An iterator over the value of this {@code Some}.
         */
        @Override public Iterator<T> iterator() {
            return unmodifiableCollection(collectionWith(value)).iterator();
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
}
