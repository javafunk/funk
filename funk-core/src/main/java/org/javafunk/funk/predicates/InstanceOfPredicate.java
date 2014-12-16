package org.javafunk.funk.predicates;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.javafunk.funk.functors.Predicate;

/**
 * {@code InstanceOfPredicate} is a {@code Predicate} implementation that
 * is constructed with a {@code Class} instance and when asked to evaluate
 * an object will return {@code true} if that object is an instance of the
 * supplied {@code Class} and {@code false} otherwise.
 *
 * @param <T> The type of object this {@code InstanceOfPredicate} can
 *            evaluate.
 */
public class InstanceOfPredicate<T> implements Predicate<T> {
    private final Class<?> testClass;

    /**
     * Constructs an {@code InstanceOfPredicate} instance over the
     * supplied {@code Class} instance.
     *
     * @param testClass The {@code Class} instance against which
     *                  instances will be checked.
     */
    public InstanceOfPredicate(Class<?> testClass) {
        this.testClass = testClass;
    }

    /**
     * Evaluates the supplied instance of type {@code T} against
     * the {@code Class} instance associated with this
     * {@code InstanceOfPredicate}.
     *
     * <p>If the supplied object is an instance of the
     * {@code Class} instance associated with this
     * {@code InstanceOfPredicate}, {@code true} is returned,
     * otherwise {@code false} is returned.</p>
     *
     * @param instance An instance of type {@code T} to evaluate.
     * @return {@code true} if the supplied object is an instance
     *         of the associated {@code Class}, {@code false}
     *         otherwise.
     */
    @Override public boolean evaluate(T instance) {
        return testClass.isInstance(instance);
    }

    /**
     * Implements value equality for {@code InstanceOfPredicate} instances. Two
     * {@code InstanceOfPredicate}s are considered equal if the {@code Class}
     * instance supplied at construction is equal.
     *
     * <p>Due to type erasure,
     * {@code new InstanceOfPredicate<X>(Some.class).equals(new InstanceOfPredicate<Y>(Some.class)}
     * will be {@code true} for all types {@code X} and {@code Y}.</p>
     *
     * @param other The object to check for equality to this {@code InstanceOfPredicate}.
     * @return {@code true} if the supplied object is also an {@code InstanceOfPredicate}
     *         over the same {@code Class} instance, otherwise {@code false}.
     */
    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    /**
     * Two {@code InstanceOfPredicate} instances will have equal hash codes
     * if they are constructed over the same {@code Class} instance.
     *
     * @return The hash code of this {@code InstanceOfPredicate}.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
