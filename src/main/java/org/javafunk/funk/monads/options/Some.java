package org.javafunk.funk.monads.options;

import org.javafunk.funk.functors.functions.NullaryFunction;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.monads.Option;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.unmodifiableCollection;
import static org.javafunk.funk.Literals.collectionWith;

/**
 * The {@code Some<T>} class is an implementation of {@code Option}
 * representing the presence of a value of type {@code T}. See
 * {@link Option} for more details.
 *
 * @param <T> The type of value contained by this {@code Some}.
 * @see Option
 * @see None
 * @since 1.0
 */
public class Some<T> extends Option<T> {
    private final T value;

    /**
     * A generic factory method for building a {@code Some} of type
     * {@code T}.
     *
     * <p>If a {@code null} value is supplied, it is treated no
     * differently to any other value. If {@code null} coercion is
     * required see {@link Option#option(Object)}.</p>
     *
     * @param value The value to be contained in the built {@code Some}.
     * @param <T> The type of the value to be contained in the built {@code Some}.
     * @return A {@code Some<T>} containing the supplied value.
     */
    public static <T> Some<T> some(T value) {
        return new Some<T>(value);
    }

    /**
     * The single argument constructor is privatised since all construction
     * should go through the static factory methods {@link Some#some(Object)},
     * {@link Option#some(Object)} or {@link Option#option(Object)}.
     *
     * @param value The value to be contained in this {@code Some}.
     */
    private Some(T value) {
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

    /**
     * A query method for whether this {@code Some} contains a value, which
     * by definition is always {@code true}.
     *
     * @return {@code true} since, by definition, {@code Some} represents the
     *         presence of a value.
     */
    @Override public Boolean hasValue() {
        return true;
    }

    /**
     * A query method for whether this {@code Some} contains no value, which
     * by definition is always {@code false}.
     *
     * @return {@code false} since, by definition, {@code Some} represents the
     *         presence of a value.
     */
    @Override public Boolean hasNoValue() {
        return false;
    }

    /**
     * A value access method to obtain the value contained in this {@code Some}.
     *
     * @return The value contained in this {@code Some}.
     */
    @Override public T get() {
        return value;
    }

    /**
     * A value access method to obtain the value contained in this {@code Some}.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * the supplied alternative value will never be used and the value of this
     * {@code Some} will always be returned.</p>
     *
     * <p>This access method will throw a {@code NullPointerException} if the supplied
     * value is {@code null}, even though it is not used, to maintain the contract
     * defined by {@code Option}.</p>
     *
     * @param value A value that would be returned if this {@code Option} implementation
     *              represented the absence of a value.
     * @return The value of this {@code Some} instance.
     * @throws NullPointerException if the supplied value is {@code null}.
     */
    @Override public T getOrElse(T value) {
        checkNotNull(value);
        return get();
    }

    /**
     * A value access method to obtain the value contained in this {@code Some}.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * this method will only return {@code null} if the contained value
     * is {@code null}.</p>
     *
     * @return The value of this {@code Some} instance.
     */
    @Override public T getOrNull() {
        return get();
    }

    /**
     * A value access method to obtain the value contained in this {@code Some}.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * the supplied function will never be called and the value of this
     * {@code Some} will always be returned.</p>
     *
     * <p>This access method will throw a {@code NullPointerException} if the supplied
     * function is {@code null}, even though it is not used, to maintain the contract
     * defined by {@code Option}.</p>
     *
     * @param function A function that would be called if this {@code Option}
     *                 implementation represented the absence of a value.
     * @return The value of this {@code Some} instance.
     */
    @Override public T getOrCall(NullaryFunction<? extends T> function) {
        checkNotNull(function);
        return get();
    }

    /**
     * A value access method to obtain the value contained in this {@code Some}.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * the supplied callable will never be called and the value of this
     * {@code Some} will always be returned.</p>
     *
     * <p>This access method will throw a {@code NullPointerException} if the supplied
     * callable is {@code null}, even though it is not used, to maintain the contract
     * defined by {@code Option}.</p>
     *
     * @param callable A callable that would be called if this {@code Option}
     *                 implementation represented the absence of a value.
     * @return The value of this {@code Some} instance.
     */
    @Override public T getOrCall(Callable<? extends T> callable) {
        checkNotNull(callable);
        return get();
    }

    /**
     * A value access method to obtain the value contained in this {@code Some}.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * the supplied throwable will never be used and the value of this
     * {@code Some} will always be returned.</p>
     *
     * <p>This access method will throw a {@code NullPointerException} if the supplied
     * throwable is {@code null}, even though it is not used, to maintain the contract
     * defined by {@code Option}.</p>
     *
     * @param throwable A throwable that would be thrown if this {@code Option}
     *                  implementation represented the absence of a value.
     * @return The value of this {@code Some} instance.
     */
    @Override public <E extends Throwable> T getOrThrow(E throwable) throws E {
        checkNotNull(throwable);
        return get();
    }

    /**
     * A translation method to translate this {@code Option} into another {@code Option}
     * in the case that it does not contain a value.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * the supplied option will never be used and this method will always return
     * this {@code Some} instance.</p>
     *
     * <p>This translation method will throw a {@code NullPointerException} if the supplied
     * value is {@code null}, even though it is not used, to maintain the contract
     * defined by {@code Option}.</p>
     *
     * @param other An option that would be returned if this {@code Option}
     *              implementation represented the absence of a value.
     * @return This {@code Option} instance.
     */
    @Override public Option<T> or(Option<? extends T> other) {
        checkNotNull(other);
        return this;
    }

    /**
     * A translation method to translate this {@code Option} into an {@code Option}
     * built by calling {@link #some(Object)} over the supplied value in the case
     * that it does not contain a value.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * the supplied value will never be used and this method will always return
     * this {@code Some} instance.</p>
     *
     * <p>This translation method will throw a {@code NullPointerException} if the supplied
     * value is {@code null}, even though it is not used, to maintain the contract
     * defined by {@code Option}.</p>
     *
     * @param other The value of an {@code Option} built with {@link #some(Object)}
     *              that would be returned if this {@code Option} implementation
     *              represented the absence of a value.
     * @return This {@code Option} instance.
     */
    @Override public Option<T> orSome(T other) {
        return this;
    }

    /**
     * A translation method to translate this {@code Option} into an {@code Option}
     * built by calling {@link #option(Object)} over the supplied value in the case
     * that it does not contain a value.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * the supplied value will never be used and this method will always return
     * this {@code Some} instance.</p>
     *
     * <p>This translation method will throw a {@code NullPointerException} if the supplied
     * value is {@code null}, even though it is not used, to maintain the contract
     * defined by {@code Option}.</p>
     *
     * @param other The value of an {@code Option} built with {@link #option(Object)}
     *              that would be returned if this {@code Option} implementation
     *              represented the absence of a value.
     * @return This {@code Option} instance.
     */
    @Override public Option<T> orOption(T other) {
        return this;
    }

    /**
     * A translation method to translate this {@code Option} into {@code null} in
     * the case that it does not contain a value.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * this method with always return this {@code Some} instance.</p>
     *
     * @return This {@code Option} instance.
     */
    @Override public Option<T> orNull() {
        return this;
    }

    /**
     * A mapping method to map the value of this {@code Option} into an {@code Option}
     * over a value of type {@code S} built by calling {@link #some(Object)} on the
     * value returned after calling the supplied {@link UnaryFunction} with the current
     * value.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * the function will always be called and this {@code Some}'s value will
     * always be mapped.</p>
     *
     * <p>If the supplied function is {@code null}, a {@code NullPointerException}
     * will be thrown.</p>
     *
     * @param function A function to map from the value of this {@code Option} into
     *                 a value of type {@code S}.
     * @param <S> The type of the value of the resulting {@code Option}.
     * @return An {@code Option} built using {@link #some(Object)} containing the
     *         value returned after calling the supplied {@link UnaryFunction} with
     *         the current value of this {@code Option}.
     * @throws NullPointerException if the supplied function is {@code null}.
     */
    @Override public <S> Option<S> map(UnaryFunction<? super T, ? extends S> function) {
        checkNotNull(function);
        return some(function.call(get()));
    }

    /**
     * A mapping method to map the value of this {@code Option} into the {@code Option}
     * returned after calling the supplied {@link UnaryFunction} with the current value.
     *
     * <p>Since a {@code Some} instance represents the presence of a value,
     * the function will always be called and this {@code Some}'s value will
     * always be mapped.</p>
     *
     * <p>If the supplied function is {@code null}, a {@code NullPointerException}
     * will be thrown.</p>
     *
     * @param function A function to map from the value of this {@code Option} into
     *                 an {@code Option} of type {@code S}.
     * @param <S> The type of the value of the resulting {@code Option}.
     * @return An {@code Option} resulting from calling the supplied {@link UnaryFunction} with
     *         the current value of this {@code Option}.
     * @throws NullPointerException if the supplied function is {@code null}.
     */
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
