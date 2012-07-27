package org.javafunk.funk.monads.options;

import org.javafunk.funk.Iterators;
import org.javafunk.funk.functors.functions.NullaryFunction;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.monads.Option;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.javafunk.funk.Literals.collection;

/**
 * The {@code None<T>} class is an implementation of {@code Option}
 * representing the absence of a value of type {@code T}. See
 * {@link Option} for more details.
 *
 * @param <T> The type of value contained by this {@code None}.
 * @see Option
 * @see Some
 * @since 1.0
 */
public class None<T> extends Option<T> {
    /**
     * A generic factory method for building a {@code None} of type
     * {@code T}.
     *
     * @param <T> The type of the value to be represented by this {@code None}.
     * @return A {@code None} of type {@code T}.
     */
    public static <T> None<T> none() {
        return new None<T>();
    }

    /**
     * A generic factory method for building a {@code None} of type
     * {@code T}.
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
     * @param typeClass The class of the type {@code T} for which this {@code None}
     *                  represents the absence of a value.
     * @param <T> The type of the value to be represented by this {@code None}.
     * @return A {@code None} of type {@code T}.
     */
    public static <T> None<T> none(Class<T> typeClass) {
        return new None<T>();
    }

    /**
     * The no argument constructor is privatised since all construction
     * should go through the static factory methods {@link None#none()},
     * {@link None#none(Class)}, {@link Option#none()}
     * and {@link Option#none(Class)}.
     */
    private None() { super(); }

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

    /**
     * A query method for whether this {@code None} contains a value, which
     * by definition is always {@code false}.
     *
     * @return {@code false} since, by definition, {@code None} represents the
     *         absence of a value.
     */
    @Override public Boolean hasValue() {
        return false;
    }

    /**
     * A query method for whether this {@code None} contains no value, which
     * by definition is always {@code true}.
     *
     * @return {@code true} since, by definition, {@code None} represents the
     *         absence of a value.
     */
    @Override public Boolean hasNoValue() {
        return true;
    }

    /**
     * A value access method to obtain the value contained in this {@code None} which,
     * by definition, does not exist. Thus this method always throws a
     * {@code NoSuchElementException}.
     *
     * @return The value contained if this {@code Option} implementation
     *         represented the presence of a value.
     * @throws NoSuchElementException since {@code None} represents the absence
     *         of a value.
     */
    @Override public T get() {
        throw new NoSuchElementException();
    }

    /**
     * A value access method which can convert this {@code None} into an alternative
     * value.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * the supplied alternative value will always be used.</p>
     *
     * <p>This access method will throw a {@code NullPointerException} if the supplied
     * value is {@code null}.</p>
     *
     * @param value An alternative value to be returned since this {@code Option}
     *              implementation represents the absence of a value.
     * @return The supplied value.
     *
     */
    @Override public T getOrElse(T value) {
        checkNotNull(value);
        return value;
    }

    /**
     * A value access method which can convert this {@code None} into {@code null}.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * {@code null} will always be returned.</p>
     *
     * @return {@code null} always, since {@code None} has no value.
     */
    @Override public T getOrNull() {
        return null;
    }

    /**
     * A value access method which can convert this {@code None} into a value returned
     * by the supplied {@code NullaryFunction}.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * the supplied function will always be called and its return value will
     * be used as the return value of this method.</p>
     *
     * <p>This access method will throw a {@code NullPointerException} if the supplied
     * function is {@code null}.</p>
     *
     * @param function A function which will be called to provide an alternative value
     *                 to be returned since this {@code Option} implementation
     *                 represents the absence of a value.
     * @return The value resulting from calling the supplied {@code NullaryFunction}.
     */
    @Override public T getOrCall(NullaryFunction<? extends T> function) {
        checkNotNull(function);
        return function.call();
    }

    /**
     * A value access method which can convert this {@code None} into a value returned
     * by the supplied {@code Callable}.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * the supplied callable will always be called and its return value will
     * be used as the return value of this method.</p>
     *
     * <p>This access method will throw a {@code NullPointerException} if the supplied
     * callable is {@code null}.</p>
     *
     * @param callable A callable which will be called to provide an alternative value
     *                 to be returned since this {@code Option} implementation
     *                 represents the absence of a value.
     * @return The value resulting from calling the supplied {@code Callable}.
     */
    @Override public T getOrCall(Callable<? extends T> callable) throws Exception {
        checkNotNull(callable);
        return callable.call();
    }

    /**
     * A value access method which can convert this {@code None} into a throw of the
     * supplied {@code Throwable}.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * the supplied throwable will always be thrown.</p>
     *
     * @param throwable A throwable which will be thrown since this
     *                  {@code Option} implementation represents the absence
     *                  of a value.
     * @return Nothing, since this method will always throw.
     */
    @Override public <E extends Throwable> T getOrThrow(E throwable) throws E {
        throw throwable;
    }

    /**
     * A translation method to translate this {@code Option} into another {@code Option}
     * in the case that it does not contain a value.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * the supplied option will always be returned by this method.</p>
     *
     * <p>This translation method will throw a {@code NullPointerException} if the supplied
     * value is {@code null}.</p>
     *
     * @param other An option to return since this {@code Option}
     *              implementation represents the absence of a value.
     * @return The supplied {@code Option} instance.
     */
    @SuppressWarnings("unchecked")
    @Override public Option<T> or(Option<? extends T> other) {
        return (Option<T>) checkNotNull(other);
    }

    /**
     * A translation method to translate this {@code Option} into an {@code Option}
     * built by calling {@link #some(Object)} over the supplied value in the case
     * that it does not contain a value.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * the supplied value will always be used and this method will always return
     * an {@code Option} built from it.</p>
     *
     * @param other The value of an {@code Option} built with {@link #some(Object)}
     *              that will be returned since this {@code Option} implementation
     *              represents the absence of a value.
     * @return An {@code Option} instance built from the supplied value.
     */
    @Override public Option<T> orSome(T other) {
        return some(other);
    }

    /**
     * A translation method to translate this {@code Option} into an {@code Option}
     * built by calling {@link #option(Object)} over the supplied value in the case
     * that it does not contain a value.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * the supplied value will always be used and this method will always return
     * an {@code Option} built from it.</p>
     *
     * @param other The value of an {@code Option} built with {@link Option#option(Object)}
     *              that will be returned since this {@code Option} implementation
     *              represents the absence of a value.
     * @return An {@code Option} instance built from the supplied value.
     */
    @Override public Option<T> orOption(T other) {
        return option(other);
    }

    /**
     * A translation method to translate this {@code Option} into {@code null} in the
     * case that it does not contain a value.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * {@code null} will always be returned.</p>
     *
     * @return {@code null} always, since {@code None} represents the absence of a
     *         value.
     */
    @Override public Option<T> orNull() {
        return null;
    }

    /**
     * A mapping method to map the value of this {@code Option} into an {@code Option}
     * over a value of type {@code S} built by calling {@link #some(Object)} on the
     * value returned after calling the supplied {@link UnaryFunction} with the current
     * value.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * the function will never be called and this method will always return
     * a {@code None} of type {@code S}.</p>
     *
     * <p>This mapping method will throw a {@code NullPointerException} if the supplied
     * function is {@code null}, even though it is not used, to maintain the contract
     * defined by {@code Option}.</p>
     *
     * @param function A function that would be used to map from the value of
     *                 this {@code None} into a value of type {@code S} if
     *                 it did not represent the absence of a value.
     * @param <S> The type of the value of the resulting {@code Option}.
     * @return A {@code None} representing the absence of a value of type {@code S}.
     * @throws NullPointerException if the supplied function is {@code null}.
     */
    @Override public <S> Option<S> map(UnaryFunction<? super T, ? extends S> function) {
        checkNotNull(function);
        return none();
    }

    /**
     * A mapping method to map the value of this {@code Option} into the {@code Option}
     * returned after calling the supplied {@link UnaryFunction} with the current value.
     *
     * <p>Since a {@code None} instance represents the absence of a value,
     * the function will never be called and this method will always return
     * a {@code None} of type {@code S}.</p>
     *
     * <p>This mapping method will throw a {@code NullPointerException} if the supplied
     * function is {@code null}, even though it is not used, to maintain the contract
     * defined by {@code Option}.</p>
     *
     * @param function A function that would be used to map from the value of
     *                 this {@code None} into an {@code Option} of type {@code S}
     *                 if it did not represent the absence of a value.
     * @param <S> The type of the value of the resulting {@code Option}.
     * @return A {@code None} representing the absence of a value of type {@code S}.
     * @throws NullPointerException if the supplied function is {@code null}.
     */
    @Override public <S> Option<S> flatMap(UnaryFunction<? super T, ? extends Option<? extends S>> function) {
        checkNotNull(function);
        return none();
    }

    @Override
    public String toString() {
        return "Option::None[]";
    }
}
