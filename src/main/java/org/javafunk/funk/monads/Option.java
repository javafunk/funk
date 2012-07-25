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
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.monads.options.None;
import org.javafunk.funk.monads.options.Some;

import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

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
 * <p>An {@code Option} is immutable, however the provided translation and mapping methods
 * allow the contained value to be transformed as necessary.</p>
 *
 * <h4>Example Usage</h4>
 *
 * A {@code null} safe option can be created as follows:
 * <blockquote>
 * <pre>
 *   Option&lt;String&gt; option = Option.option("Hello");
 * </pre>
 * </blockquote>
 * We can query the option to find out if it has a value:
 * <blockquote>
 * <pre>
 *   option.hasValue();   // => true
 *   option.hasNoValue(); // => false
 * </pre>
 * </blockquote>
 * The value of the option can be retrieved:
 * <blockquote>
 * <pre>
 *   option.get();                              // => "Hello"
 *   option.getOrNull();                        // => "Hello"
 *   option.getOrElse("Goodbye");               // => "Hello"
 *   option.getOrThrow(new MannersException()); // => "Hello"
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
 *   option.get(); // => 5
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
 *   option.get();        // => throws NoSuchElementException
 *   option.getOrElse(0); // => 0
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
    /**
     * A generic factory method for building an {@code Option} of type
     * {@code T} representing the absence of a value.
     *
     * @param <T> The type of the value of the built {@code Option}.
     * @return An {@code Option<T>} representing the absence of a value.
     */
    public static <T> Option<T> none() {
        return None.none();
    }

    /**
     * A generic factory method for building an {@code Option} of type
     * {@code T} representing the absence of a value.
     *
     * <p>This overloaded version of {@link #none()} is provided so that
     * the generic type resolution works correctly when the factory is
     * used inline. For example, compare the following:
     * <blockquote>
     * <pre>
     *   someObject.methodTakingOption(Option.none(Integer.class));
     *   someObject.methodTakingOption(Option.&lt;Integer&gt;none();
     * </pre>
     * </blockquote>
     * the benefit being that the first call to none can be statically imported
     * whilst the second cannot.</p>
     *
     * @param typeClass The class of the type {@code T} that this option contains.
     * @param <T> The type of the value of the built {@code Option}.
     * @return An {@code Option<T>} representing the absence of a value.
     */
    public static <T> Option<T> none(Class<T> typeClass) {
        return None.none(typeClass);
    }

    /**
     * A generic factory method for building an {@code Option} of type
     * {@code T} representing the presence of a value.
     *
     * <p>Note that {@code null} is not explicitly forbidden in an {@code Option}
     * to be consistent with the rest of Funk which allows {@code null}s as
     * valid values. In order to have {@code null} coerced into {@code None<T>},
     * use {@link #option(Object)}. For example, consider the following:
     * <blockquote>
     * <pre>
     *   Option&lt;String&gt; nullNotAllowed = Option.option(null);
     *   nullNotAllowed.get(); // => NoSuchElementException
     *
     *   Option&lt;String&gt; nullAllowed = Option.some(null);
     *   nullAllowed.get(); // => null
     * </pre>
     * </blockquote>
     * Thus, it is important to ensure the correct factory method is used for the
     * desired behaviour.</p>
     *
     * @param value The value of the resulting {@code Option}.
     * @param <T> The type of the value of the built {@code Option}.
     * @return An {@code Option<T>} representing the presence of the supplied value.
     */
    public static <T> Option<T> some(T value) {
        return Some.some(value);
    }

    /**
     * A generic factory method for building an {@code Option} of type
     * {@code T} representing the presence of a value.
     *
     * <p>Note that this method will coerce {@code null} into {@code None<T>}.
     * Compare this with {@link #some(Object)} which allows {@code null} as a value.
     * As an example, consider the following:
     * <blockquote>
     * <pre>
     *   Option&lt;String&gt; nullAllowed = Option.some(null);
     *   nullAllowed.get(); // => null
     *
     *   Option&lt;String&gt; nullNotAllowed = Option.option(null);
     *   nullNotAllowed.get(); // => NoSuchElementException
     * </pre>
     * </blockquote>
     * Thus, it is important to ensure the correct factory method is used for the
     * desired behaviour.</p>
     *
     * @param value The value of the resulting {@code Option}.
     * @param <T> The type of the value of the built {@code Option}.
     * @return An {@code Option<T>} representing the presence of the supplied value.
     */
    public static <T> Option<T> option(T value) {
        if (value == null) {
            return None.none();
        }
        return Some.some(value);
    }

    /**
     * The no arguments constructor is protected since all sub classes should decide
     * whether or not it should be exposed. The preferred construction mechanism
     * is via the generic factory methods on {@link Option}, {@link Some} and {@link None}.
     */
    protected Option() {}

    /**
     * A query method to determine whether this {@code Option} has a value.
     * This method will return {@code true} in the presence of a value
     * and {@code false} in the absence.
     *
     * @return A {@code Boolean} representing whether or not a value
     * is present in this {@code Option}.
     */
    public abstract Boolean hasValue();

    /**
     * A query method to determine whether this {@code Option} has no value.
     * This method will return {@code true} in the absence of a value and
     * {@code false} in the presence.
     *
     * @return A {@code Boolean} representing whether or not a value
     * is absent in this {@code Option}.
     */
    public abstract Boolean hasNoValue();

    /**
     * A value access method to obtain the value contained in this {@code Option}.
     * If a value is present, it is returned. If no value is present, a
     * {@code NoSuchElementException} is thrown.
     *
     * <p>This method is present to satisfy the {@link Value} interface. It is
     * contract equivalent with {@link #get()} and that method should be
     * preferred when interacting with {@code Option}s.</p>
     *
     * @return The value contained in this {@code Option} if one is present.
     * @throws java.util.NoSuchElementException if no value is present;
     */
    @Override public T getValue() { return get(); }

    /**
     * A value access method to obtain the value contained in this {@code Option}.
     * If a value is present, it is returned. If no value is present, a
     * {@code NoSuchElementException} is thrown.
     *
     * @return The value contained in this {@code Option} if one is present.
     * @throws java.util.NoSuchElementException if no value is present;
     */
    public abstract T get();

    /**
     * A value access method to obtain the value contained in this {@code Option}
     * or an alternative. If a value is present, it is returned. If no value is
     * present, the supplied alternative value is returned.
     *
     * <p>This access method will throw a {@code NullPointerException} if the supplied
     * value is {@code null}. In the case that {@code null} is required, use
     * {@link #getOrNull()}.</p>
     *
     * @param value The value to return in the case that this {@code Option} does not
     *              contain a value
     * @return The value contained in this {@code Option}, otherwise the supplied value.
     */
    public abstract T getOrElse(T value);

    /**
     * A value access method to obtain the value contained in this {@code Option}
     * or null. If a value is present, it is returned. If no value is present,
     * {@code null} is returned.
     *
     * @return The value contained in this {@code Option}, otherwise {@code null}.
     */
    public abstract T getOrNull();

    /**
     * A value access method to obtain the value contained in this {@code Option}
     * or the result of calling a {@code NullaryFunction<? extends T>}. If a value
     * is present, it is returned. If no value is present, the result of calling
     * the supplied function is returned.
     *
     * @param function A function to call in the absence of a value in this
     *                 {@code Option}.
     * @return The value contained in this {@code Option}, otherwise the result of
     *         calling the supplied function.
     * @throws NullPointerException if the supplied function is {@code null}.
     */
    public abstract T getOrCall(NullaryFunction<? extends T> function);

    /**
     * A value access method to obtain the value contained in this {@code Option}
     * or the result of calling a {@code Callable<? extends T>}. If a value is
     * present, it is returned. If no value is present, the result of calling
     * the supplied callable is returned.
     *
     * @param callable A callable to call in the absence of a value in this
     *                 {@code Option}.
     * @return The value contained in this {@code Option}, otherwise the result of
     *         calling the supplied callable.
     * @throws Exception if the supplied callable throws Exception.
     * @throws NullPointerException if the supplied callable is {@code null}.
     */
    public abstract T getOrCall(Callable<? extends T> callable) throws Exception;

    /**
     * A value access method to obtain the value contained in this {@code Option}
     * or throw the provided exception. If a value is present, it is returned.
     * If no value is present, the supplied exception is thrown.
     *
     * @param throwable An exception to throw in the absence of a value in this
     *                  {@code Option}.
     * @param <E> The type of the exception to throw.
     * @return The value contained in this {@code Option}.
     * @throws E if this {@code Option} contains no value.
     */
    public abstract <E extends Throwable> T getOrThrow(E throwable) throws E;

    /**
     * A translation method to translate this {@code Option} into another {@code Option}
     * in the case that it does not contain a value. If a value is present, the
     * {@code Option} itself is returned. If no value is present the supplied
     * {@code Option} is returned.
     *
     * <p>This is a shortcut method in the constant case for calling
     * {@link #flatMap(org.javafunk.funk.functors.functions.UnaryFunction)} or
     * {@link #flatMap(org.javafunk.funk.functors.Mapper)}.</p>
     *
     * <p>If the supplied {@code Option} is {@code null}, a {@code NullPointerException}
     * is thrown.</p>
     *
     * @param other The {@code Option} to return in the case that this {@code Option}
     *              contains no value.
     * @return This {@code Option} if a value is present, otherwise the supplied
     *         {@code Option}.
     * @throws NullPointerException if the supplied {@code Option} is {@code null}.
     */
    public abstract Option<T> or(Option<? extends T> other);

    /**
     * A translation method to translate this {@code Option} into an {@code Option}
     * built by calling {@link #some(Object)} over the supplied value in the case
     * that it does not contain a value. If a value is present, the {@code Option}
     * itself is returned. If no value is present the result of calling
     * {@link #some(Object)} on the supplied value is returned.
     *
     * <p>This is a shortcut method in the constant case for calling
     * {@link #map(org.javafunk.funk.functors.functions.UnaryFunction)} or
     * {@link #map(org.javafunk.funk.functors.Mapper)}.</p>
     *
     * @param other The value of an {@code Option} built with {@link #some(Object)}
     *              to return in the case that this {@code Option} contains no value.
     * @return This {@code Option} if a value is present, otherwise an {@code Option}
     *         over the supplied value.
     */
    public abstract Option<T> orSome(T other);

    /**
     * A translation method to translate this {@code Option} into an {@code Option}
     * built by calling {@link #option(Object)} over the supplied value in the case
     * that it does not contain a value. If a value is present, the {@code Option}
     * itself is returned. If no value is present the result of calling
     * {@link #option(Object)} on the supplied value is returned.
     *
     * @param other The value of an {@code Option} built with {@link #option(Object)}
     *              to return in the case that this {@code Option} contains no value.
     * @return This {@code Option} if a value is present, otherwise an {@code Option}
     *         over the supplied value.
     */
    public abstract Option<T> orOption(T other);

    /**
     * A mapping method to map the value of this {@code Option} into an {@code Option}
     * over a value of type {@code S} built by calling {@link #some(Object)} on the
     * value returned after calling the supplied {@link UnaryFunction} with the current
     * value.
     *
     * <p>In the case that this {@code Option} contains no value, an {@code Option} with
     * no value of type {@code S} is returned and the supplied function will not be
     * called.</p>
     *
     * <p>If the supplied function is {@code null}, a {@code NullPointerException}
     * will be thrown.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * <blockquote>
     * <pre>
     *   Option&lt;String&gt; some = Option.some("Flip");
     *   Option&lt;String&gt; firstResult = some.map(new UnaryFunction&lt;String, String&gt;() {
     *      &#64;Override public String call(String value) {
     *          return "Flop";
     *      }
     *   });
     *   firstResult.hasValue(); // => true
     *   firstResult.get();      // => "Flop"
     *
     *   Option&lt;String&gt; none = Option.none();
     *   Option&lt;Integer&gt; secondResult = none.map(new UnaryFunction&lt;String, Integer&gt;() {
     *      &#64;Override public Integer call(String value) {
     *          throw new ShouldNotGetCalledException();
     *      }
     *   });
     *   secondResult.hasValue();   // => false
     *   secondResult.getOrElse(0); // => 0
     * </pre>
     * </blockquote>
     *
     * @param function A function to map from the value of this {@code Option} into
     *                 a value of type {@code S}.
     * @param <S> The type of the value of the resulting {@code Option}.
     * @return An {@code Option} built using {@link #some(Object)} containing the
     *         value returned after calling the supplied {@link UnaryFunction} with
     *         the current value of this {@code Option} if one is present, otherwise
     *         an {@code Option} of type {@code S} containing no value.
     * @throws NullPointerException if the supplied function is {@code null}.
     */
    public abstract <S> Option<S> map(UnaryFunction<? super T, ? extends S> function);

    /**
     * A mapping method to map the value of this {@code Option} into an {@code Option}
     * over a value of type {@code S} built by calling {@link #some(Object)} on the
     * value returned after calling the supplied {@link Mapper} with the current
     * value.
     *
     * <p>In the case that this {@code Option} contains no value, an {@code Option} with
     * no value of type {@code S} is returned and the supplied mapper will not be
     * called.</p>
     *
     * <p>If the supplied mapper is {@code null}, a {@code NullPointerException}
     * will be thrown.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * <blockquote>
     * <pre>
     *   Option&lt;String&gt; some = Option.some("Flip");
     *   Option&lt;String&gt; firstResult = some.map(new Mapper&lt;String, String&gt;() {
     *      &#64;Override public String map(String value) {
     *          return "Flop";
     *      }
     *   });
     *   firstResult.hasValue(); // => true
     *   firstResult.get();      // => "Flop"
     *
     *   Option&lt;String&gt; none = Option.none();
     *   Option&lt;Integer&gt; secondResult = none.map(new Mapper&lt;String, Integer&gt;() {
     *      &#64;Override public Integer map(String value) {
     *          throw new ShouldNotGetCalledException();
     *      }
     *   });
     *   secondResult.hasValue();   // => false
     *   secondResult.getOrElse(0); // => 0
     * </pre>
     * </blockquote>
     *
     * @param mapper A mapper to map from the value of this {@code Option} into
     *               a value of type {@code S}.
     * @param <S> The type of the value of the resulting {@code Option}.
     * @return An {@code Option} built using {@link #some(Object)} containing the
     *         value returned after calling the supplied {@link Mapper} with
     *         the current value of this {@code Option} if one is present, otherwise
     *         an {@code Option} of type {@code S} containing no value.
     * @throws NullPointerException if the supplied mapper is {@code null}.
     */
    public <S> Option<S> map(Mapper<? super T, ? extends S> mapper) {
        return map(mapperUnaryFunction(checkNotNull(mapper)));
    }

    /**
     * A mapping method to map the value of this {@code Option} into the {@code Option}
     * returned after calling the supplied {@link UnaryFunction} with the current value.
     *
     * <p>In the case that this {@code Option} contains no value, an {@code Option} with
     * no value of type {@code S} is returned and the supplied function will not be
     * called.</p>
     *
     * <p>If the supplied function is {@code null}, a {@code NullPointerException}
     * will be thrown.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * <blockquote>
     * <pre>
     *   Option&lt;String&gt; some = Option.some("Flip");
     *   Option&lt;Person&gt; firstResult = some.map(new UnaryFunction&lt;String, Option&lt;Person&gt;&gt;() {
     *      &#64;Override public Option&lt;Person&gt; map(String name) {
     *          return Option.option(repository.find(name));
     *      }
     *   });
     *   firstResult.hasValue(); // => dependent on result of repository.find(name)
     *
     *   Option&lt;String&gt; none = Option.none();
     *   Option&lt;Person&gt; secondResult = none.map(new UnaryFunction&lt;String, Option&lt;Person&gt;&gt;() {
     *      &#64;Override public Option&lt;Person&gt; map(String value) {
     *          throw new ShouldNotGetCalledException();
     *      }
     *   });
     *   secondResult.hasValue();   // => false
     *   secondResult.getOrNull();  // => null
     * </pre>
     * </blockquote>
     *
     * @param function A function to map from the value of this {@code Option} into
     *                 an {@code Option} of type {@code S}.
     * @param <S> The type of the value of the resulting {@code Option}.
     * @return The {@code Option} resulting from  calling the supplied {@link UnaryFunction} with
     *         the current value of this {@code Option} if one is present, otherwise
     *         an {@code Option} of type {@code S} containing no value.
     * @throws NullPointerException if the supplied function is {@code null}.
     */
    public abstract <S> Option<S> flatMap(UnaryFunction<? super T, ? extends Option<? extends S>> function);

    /**
     * A mapping method to map the value of this {@code Option} into the {@code Option}
     * returned after calling the supplied {@link Mapper} with the current value.
     *
     * <p>In the case that this {@code Option} contains no value, an {@code Option} with
     * no value of type {@code S} is returned and the supplied mapper will not be
     * called.</p>
     *
     * <p>If the supplied mapper is {@code null}, a {@code NullPointerException}
     * will be thrown.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * <blockquote>
     * <pre>
     *   Option&lt;String&gt; some = Option.some("Flip");
     *   Option&lt;Person&gt; firstResult = some.map(new Mapper&lt;String, Option&lt;Person&gt;&gt;() {
     *      &#64;Override public Option&lt;Person&gt; map(String name) {
     *          return Option.option(repository.find(name));
     *      }
     *   });
     *   firstResult.hasValue(); // => dependent on result of repository.find(name)
     *
     *   Option&lt;String&gt; none = Option.none();
     *   Option&lt;Person&gt; secondResult = none.map(new Mapper&lt;String, Option&lt;Person&gt;&gt;() {
     *      &#64;Override public Option&lt;Person&gt; map(String value) {
     *          throw new ShouldNotGetCalledException();
     *      }
     *   });
     *   secondResult.hasValue();   // => false
     *   secondResult.getOrNull();  // => null
     * </pre>
     * </blockquote>
     *
     * @param mapper A mapper to map from the value of this {@code Option} into
     *               an {@code Option} of type {@code S}.
     * @param <S> The type of the value of the resulting {@code Option}.
     * @return The {@code Option} resulting from  calling the supplied {@link Mapper} with
     *         the current value of this {@code Option} if one is present, otherwise
     *         an {@code Option} of type {@code S} containing no value.
     * @throws NullPointerException if the supplied mapper is {@code null}.
     */
    public <S> Option<S> flatMap(Mapper<? super T, ? extends Option<? extends S>> mapper) {
        return flatMap(mapperUnaryFunction(checkNotNull(mapper)));
    }

    /**
     * Implements value equality for {@code Option} instances. Two {@code Option}s are
     * equal if they both contain the same value or both contain no value, otherwise
     * they are not equal.
     *
     * <p>Due to type erasure, {@code Option.<X>none().equals(Option.<Y>none()} will
     * be {@code true} for all types {@code X} and {@code Y}.</p>
     *
     * @param other The object to check for equality to this {@code Option}.
     * @return {@code true} if the supplied {@code Option} is equal to this {@code Option},
     *         otherwise, {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    /**
     * Two {@code Option} objects will have equal hash codes either if they both represent
     * the absence of a value (of any type) or if they contain the same value (of the same
     * type).
     *
     * @return The hash code of this {@code Option}.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
