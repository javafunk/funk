package org.javafunk.funk.predicates;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.javafunk.funk.Literals;
import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.functors.predicates.UnaryPredicate;

import static org.javafunk.funk.Eagerly.all;

/**
 * {@code AndPredicate} is a {@code Predicate} implementation that
 * returns {@code true} if all {@code UnaryPredicate} instances that the
 * {@code AndPredicate} was initialised with also return {@code true}
 * when asked to evaluate the object supplied to the {@code evaluate}
 * method. Otherwise, {@code false} is returned.
 *
 * <p>Each of the delegate {@code UnaryPredicate} instances is asked to
 * evaluate the object supplied to this {@code Predicate}'s
 * {@code evaluate} method in the order in which they were supplied
 * at the time of construction. If any {@code UnaryPredicate} instance
 * returns {@code false} then evaluation is short circuited and no
 * further {@code UnaryPredicate} instances are asked to evaluate.</p>
 *
 * @param <T> The type of object this {@code AndPredicate} can evaluate.
 */
public class AndPredicate<T> implements Predicate<T> {
    private Iterable<UnaryPredicate<? super T>> predicates;

    /**
     * Constructs an {@code AndPredicate} instance over all
     * {@code UnaryPredicate} instances in the supplied {@code Iterable}
     * in the order in which they are yielded.
     *
     * <p>If the supplied {@code Iterable} of {@code UnaryPredicates}
     * contains only one element, the resulting {@code AndPredicate}
     * will always return the same result as that delegate. If the
     * supplied {@code Iterable} is empty, the {@code AndPredicate}
     * will always return {@code true}.</p>
     *
     * @param predicates The {@code UnaryPredicate} instances to be delegated to
     *                   in order to determine the outcome of this
     *                   {@code AndPredicate} when asked to evaluate
     *                   instances of type {@code T}.
     */
    public AndPredicate(Iterable<UnaryPredicate<? super T>> predicates) {
        this.predicates = predicates;
    }

    /**
     * Evaluates the supplied instance of type {@code T} against
     * the {@code UnaryPredicate} instances associated with this
     * {@code AndPredicate} in order to determine whether all
     * are satisfied.
     *
     * <p>The evaluation is short circuited, returning {@code false}
     * if any of the {@code UnaryPredicate} instances returns {@code false}.
     * Evaluation is performed against the delegate {@code UnaryPredicate}
     * instances in the order in which they were supplied to the
     * {@code AndPredicate}.</p>
     *
     * @param instance An instance of type {@code T} to evaluate.
     * @return {@code true} if all delegate {@code UnaryPredicate} instances
     *         return {@code true}, {@code false} otherwise.
     */
    @Override public boolean evaluate(final T instance) {
        return all(predicates, new UnaryPredicate<UnaryPredicate<? super T>>() {
            @Override public boolean evaluate(UnaryPredicate<? super T> predicate) {
                return predicate.evaluate(instance);
            }
        });
    }

    /**
     * Implements value equality for {@code AndPredicate} instances. Two
     * {@code AndPredicate}s are considered equal if all {@code UnaryPredicate}
     * instances supplied at initialisation are equal.
     *
     * <p>Due to type erasure,
     * {@code new AndPredicate<X>(iterableWith(Predicates.<X>alwaysFalse(), Predicates.<X>alwaysFalse())).equals(new AndPredicate<Y>(iterableWith(Predicates.<Y>alwaysFalse(), Predicates.<Y>alwaysFalse()))}
     * will be {@code true} for all types {@code X} and {@code Y}.</p>
     *
     * @param other The object to check for equality to this {@code AndPredicate}.
     * @return {@code true} if the supplied object is also an {@code AndPredicate}
     *         composed of the same delegate {@code UnaryPredicate} instances
     *         supplied in the same order, otherwise {@code false}.
     */
    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    /**
     * Two {@code AndPredicate} instances will have equal hash codes
     * if they are composed of the same delegate {@code UnaryPredicate}
     * instances supplied in the same order.
     *
     * @return The hash code of this {@code AndPredicate}.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Constructs an {@code AndPredicate} over type {@code T} composing
     * the two supplied {@code UnaryPredicate} instances.
     *
     * @param p1  The first delegate {@code UnaryPredicate} instance.
     * @param p2  The second delegate {@code UnaryPredicate} instance.
     * @param <T> The type of object this {@code AndPredicate} can evaluate.
     * @return An {@code AndPredicate} composing the supplied {@code UnaryPredicate}
     *         instances.
     */
    public static <T> Predicate<T> and(
            UnaryPredicate<? super T> p1,
            UnaryPredicate<? super T> p2) {
        return new AndPredicate<T>(Literals.<UnaryPredicate<? super T>>iterableWith(p1, p2));
    }

    /**
     * Constructs an {@code AndPredicate} over type {@code T} composing
     * the three supplied {@code UnaryPredicate} instances.
     *
     * @param p1  The first delegate {@code UnaryPredicate} instance.
     * @param p2  The second delegate {@code UnaryPredicate} instance.
     * @param p3  The third delegate {@code UnaryPredicate} instance.
     * @param <T> The type of object this {@code AndPredicate} can evaluate.
     * @return An {@code AndPredicate} composing the supplied {@code UnaryPredicate}
     *         instances.
     */
    public static <T> Predicate<T> and(
            UnaryPredicate<? super T> p1,
            UnaryPredicate<? super T> p2,
            UnaryPredicate<? super T> p3) {
        return new AndPredicate<T>(Literals.<UnaryPredicate<? super T>>iterableWith(p1, p2, p3));
    }

    /**
     * Constructs an {@code AndPredicate} over type {@code T} composing
     * the four supplied {@code UnaryPredicate} instances.
     *
     * @param p1  The first delegate {@code UnaryPredicate} instance.
     * @param p2  The second delegate {@code UnaryPredicate} instance.
     * @param p3  The third delegate {@code UnaryPredicate} instance.
     * @param p4  The fourth delegate {@code UnaryPredicate} instance.
     * @param <T> The type of object this {@code AndPredicate} can evaluate.
     * @return An {@code AndPredicate} composing the supplied {@code UnaryPredicate}
     *         instances.
     */
    public static <T> Predicate<T> and(
            UnaryPredicate<? super T> p1,
            UnaryPredicate<? super T> p2,
            UnaryPredicate<? super T> p3,
            UnaryPredicate<? super T> p4) {
        return new AndPredicate<T>(Literals.<UnaryPredicate<? super T>>iterableWith(p1, p2, p3, p4));
    }

    /**
     * Constructs an {@code AndPredicate} over type {@code T} composing
     * the five supplied {@code UnaryPredicate} instances.
     *
     * @param p1  The first delegate {@code UnaryPredicate} instance.
     * @param p2  The second delegate {@code UnaryPredicate} instance.
     * @param p3  The third delegate {@code UnaryPredicate} instance.
     * @param p4  The fourth delegate {@code UnaryPredicate} instance.
     * @param p5  The fifth delegate {@code UnaryPredicate} instance.
     * @param <T> The type of object this {@code AndPredicate} can evaluate.
     * @return An {@code AndPredicate} composing the supplied {@code UnaryPredicate}
     *         instances.
     */
    public static <T> Predicate<T> and(
            UnaryPredicate<? super T> p1,
            UnaryPredicate<? super T> p2,
            UnaryPredicate<? super T> p3,
            UnaryPredicate<? super T> p4,
            UnaryPredicate<? super T> p5) {
        return new AndPredicate<T>(Literals.<UnaryPredicate<? super T>>iterableWith(p1, p2, p3, p4, p5));
    }

    /**
     * Constructs an {@code AndPredicate} over type {@code T} composing
     * the six supplied {@code UnaryPredicate} instances.
     *
     * @param p1  The first delegate {@code UnaryPredicate} instance.
     * @param p2  The second delegate {@code UnaryPredicate} instance.
     * @param p3  The third delegate {@code UnaryPredicate} instance.
     * @param p4  The fourth delegate {@code UnaryPredicate} instance.
     * @param p5  The fifth delegate {@code UnaryPredicate} instance.
     * @param p6  The sixth delegate {@code UnaryPredicate} instance.
     * @param <T> The type of object this {@code AndUnaryPredicate} can evaluate.
     * @return An {@code AndPredicate} composing the supplied {@code UnaryPredicate}
     *         instances.
     */
    public static <T> Predicate<T> and(
            UnaryPredicate<? super T> p1,
            UnaryPredicate<? super T> p2,
            UnaryPredicate<? super T> p3,
            UnaryPredicate<? super T> p4,
            UnaryPredicate<? super T> p5,
            UnaryPredicate<? super T> p6) {
        return new AndPredicate<T>(Literals.<UnaryPredicate<? super T>>iterableWith(p1, p2, p3, p4, p5, p6));
    }

    /**
     * Constructs an {@code AndPredicate} over type {@code T} composing
     * the seven supplied {@code UnaryPredicate} instances.
     *
     * @param p1  The first delegate {@code UnaryPredicate} instance.
     * @param p2  The second delegate {@code UnaryPredicate} instance.
     * @param p3  The third delegate {@code UnaryPredicate} instance.
     * @param p4  The fourth delegate {@code UnaryPredicate} instance.
     * @param p5  The fifth delegate {@code UnaryPredicate} instance.
     * @param p6  The sixth delegate {@code UnaryPredicate} instance.
     * @param p7  The seventh delegate {@code UnaryPredicate} instance.
     * @param <T> The type of object this {@code AndPredicate} can evaluate.
     * @return An {@code AndPredicate} composing the supplied {@code UnaryPredicate}
     *         instances.
     */
    public static <T> Predicate<T> and(
            UnaryPredicate<? super T> p1,
            UnaryPredicate<? super T> p2,
            UnaryPredicate<? super T> p3,
            UnaryPredicate<? super T> p4,
            UnaryPredicate<? super T> p5,
            UnaryPredicate<? super T> p6,
            UnaryPredicate<? super T> p7) {
        return new AndPredicate<T>(Literals.<UnaryPredicate<? super T>>iterableWith(p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Constructs an {@code AndPredicate} over type {@code T} composing
     * the eighth supplied {@code UnaryPredicate} instances.
     *
     * @param p1  The first delegate {@code UnaryPredicate} instance.
     * @param p2  The second delegate {@code UnaryPredicate} instance.
     * @param p3  The third delegate {@code UnaryPredicate} instance.
     * @param p4  The fourth delegate {@code UnaryPredicate} instance.
     * @param p5  The fifth delegate {@code UnaryPredicate} instance.
     * @param p6  The sixth delegate {@code UnaryPredicate} instance.
     * @param p7  The seventh delegate {@code UnaryPredicate} instance.
     * @param p8  The eighth delegate {@code UnaryPredicate} instance.
     * @param <T> The type of object this {@code AndPredicate} can evaluate.
     * @return An {@code AndPredicate} composing the supplied {@code UnaryPredicate}
     *         instances.
     */
    public static <T> Predicate<T> and(
            UnaryPredicate<? super T> p1,
            UnaryPredicate<? super T> p2,
            UnaryPredicate<? super T> p3,
            UnaryPredicate<? super T> p4,
            UnaryPredicate<? super T> p5,
            UnaryPredicate<? super T> p6,
            UnaryPredicate<? super T> p7,
            UnaryPredicate<? super T> p8) {
        return new AndPredicate<T>(Literals.<UnaryPredicate<? super T>>iterableWith(p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Constructs an {@code AndPredicate} over type {@code T} composing
     * the ninth supplied {@code UnaryPredicate} instances.
     *
     * @param p1  The first delegate {@code UnaryPredicate} instance.
     * @param p2  The second delegate {@code UnaryPredicate} instance.
     * @param p3  The third delegate {@code UnaryPredicate} instance.
     * @param p4  The fourth delegate {@code UnaryPredicate} instance.
     * @param p5  The fifth delegate {@code UnaryPredicate} instance.
     * @param p6  The sixth delegate {@code UnaryPredicate} instance.
     * @param p7  The seventh delegate {@code UnaryPredicate} instance.
     * @param p8  The eighth delegate {@code UnaryPredicate} instance.
     * @param p9  The ninth delegate {@code UnaryPredicate} instance.
     * @param <T> The type of object this {@code AndPredicate} can evaluate.
     * @return An {@code AndPredicate} composing the supplied {@code UnaryPredicate}
     *         instances.
     */
    public static <T> Predicate<T> and(
            UnaryPredicate<? super T> p1,
            UnaryPredicate<? super T> p2,
            UnaryPredicate<? super T> p3,
            UnaryPredicate<? super T> p4,
            UnaryPredicate<? super T> p5,
            UnaryPredicate<? super T> p6,
            UnaryPredicate<? super T> p7,
            UnaryPredicate<? super T> p8,
            UnaryPredicate<? super T> p9) {
        return new AndPredicate<T>(Literals.<UnaryPredicate<? super T>>iterableWith(p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Constructs an {@code AndPredicate} over type {@code T} composing
     * all supplied {@code UnaryPredicate} instances.
     *
     * @param p1    The first delegate {@code UnaryPredicate} instance.
     * @param p2    The second delegate {@code UnaryPredicate} instance.
     * @param p3    The third delegate {@code UnaryPredicate} instance.
     * @param p4    The fourth delegate {@code UnaryPredicate} instance.
     * @param p5    The fifth delegate {@code UnaryPredicate} instance.
     * @param p6    The sixth delegate {@code UnaryPredicate} instance.
     * @param p7    The seventh delegate {@code UnaryPredicate} instance.
     * @param p8    The eighth delegate {@code UnaryPredicate} instance.
     * @param p9    The ninth delegate {@code UnaryPredicate} instance.
     * @param p10on The remaining delegate {@code UnaryPredicate} instances.
     * @param <T>   The type of object this {@code AndPredicate} can evaluate.
     * @return An {@code AndPredicate} composing the supplied {@code UnaryPredicate}
     *         instances.
     */
    public static <T> Predicate<T> and(
            UnaryPredicate<? super T> p1,
            UnaryPredicate<? super T> p2,
            UnaryPredicate<? super T> p3,
            UnaryPredicate<? super T> p4,
            UnaryPredicate<? super T> p5,
            UnaryPredicate<? super T> p6,
            UnaryPredicate<? super T> p7,
            UnaryPredicate<? super T> p8,
            UnaryPredicate<? super T> p9,
            UnaryPredicate<? super T>... p10on) {
        return new AndPredicate<T>(Literals.<UnaryPredicate<? super T>>iterableBuilderWith(p1, p2, p3, p4, p5, p6, p7, p8, p9).and(p10on).build());
    }
}
