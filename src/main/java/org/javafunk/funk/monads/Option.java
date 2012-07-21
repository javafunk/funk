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
import org.javafunk.funk.behaviours.Mappable;
import org.javafunk.funk.behaviours.Value;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.NullaryFunction;
import org.javafunk.funk.monads.options.None;
import org.javafunk.funk.monads.options.Some;

import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code Option<T>} class is a base class for implementations of the option monad.
 * The option monad represents the presence or absence of a value of some type. An option
 * with a value is represented by the {@code Some<T>} subclass and an option with no value
 * is represented by the {@code None<T>} subclass. For a more mathematical description of the
 * option monad, see the
 * <a href="http://en.wikipedia.org/wiki/Option_type">option type article on Wikipedia</a>.
 *
 * <p>The {@code Option} class provides factory methods for constructing {@code Option} instances.
 * {@code Option} instances provide query methods, value access methods, translation methods
 * and mapping methods as described below.</p>
 *
 * <p>{@code Option} equality is based on the equivalence of the contained value, i.e.,
 * {@code Option} is a value object. Unfortunately, due to type erasure,
 * {@code new None<X>().equals(new None<Y>())} is {@code true} which may not be desired.</p>
 *
 * <h4>Example Usage</h4>
 *
 * A {@code null} safe option can be created as follows:
 * <blockquote>
 * <pre>
 *   Option<String> option = Option.option("Hello");
 * </pre>
 * </blockquote>
 * We can query the option to find out if it has a value:
 * <blockquote>
 * <pre>
 *   option.hasValue()   // => true
 *   option.hasNoValue() // => false
 * </pre>
 * </blockquote>
 * The value of the option can be retrieved:
 * <blockquote>
 * <pre>
 *   option.get()                              // => "Hello"
 *   option.getOrNull()                        // => "Hello"
 *   option.getOrElse("Goodbye")               // => "Hello"
 *   option.getOrThrow(new MannersException()) // => "Hello"
 * </pre>
 * </blockquote>
 * or mapped into something else:
 *  <blockquote>
 * <pre>
 *   option.map(new Mapper&lt;String, Integer&gt;(){
 *       &#64;Override public Integer map(String input) {
 *           return input.length();
 *       }
 *   });
 *   option.get() // => 5
 *
 *   option.flatMap(new Mapper&lt;String, Option&lt;Integer&gt;&gt;() {
 *       &#64;Override public Integer map(String input) {
 *           Integer length = input.length();
 *           if (length &gt; 10) {
 *               return Option.option(length - 10);
 *           }
 *           return Option.none();
 *       }
 *   });
 *   option.get()        // => throws NoSuchElementException
 *   option.getOrElse(0) // => 0
 * </pre>
 * </blockquote>
 * See the
 * <a href="https://github.com/javafunk/funk/blob/master/src/test/java/org/javafunk/funk/monads/OptionTest.java">tests </a>
 * and {@link Some} and {@link None} for more examples.
 *
 * @param <T> The type of the value of this {@code Option}.
 * @see org.javafunk.funk.monads.options.Some
 * @see org.javafunk.funk.monads.options.None
 * @since 1.0
 */
public abstract class Option<T>
        implements Mappable<T, Option<?>>, Value<T> {
    public static <T> Option<T> none() {
        return None.none();
    }

    public static <T> Option<T> none(Class<T> typeClass) {
        return None.none(typeClass);
    }

    public static <T> Option<T> some(T value) {
        return Some.some(value);
    }

    public static <T> Option<T> option(T value) {
        if (value == null) {
            return None.none();
        }
        return Some.some(value);
    }

    protected Option() {}

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
}
