package org.javafunk.funk.predicates;

import com.google.common.base.Objects;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.javafunk.funk.functors.Predicate;

/**
 * A {@code Predicate} implementation that returns {@code true}
 * if the evaluated object is equal to the object supplied
 * at construction according to that object's {@code equals}
 * method.
 *
 * @param <T> The type of the element this {@code Predicate}
 *            can evaluate.
 */
public class EqualsPredicate<T> implements Predicate<T> {
    private T controlObject;

    /**
     * Constructs an {@code EqualsPredicate} over the
     * supplied instance of type {@code T}.
     *
     * @param controlObject The object to which this {@code EqualsPredicate}
     *                      should compare all evaluated objects.
     */
    public EqualsPredicate(T controlObject) {
        this.controlObject = controlObject;
    }

    /**
     * Evaluates the supplied instance of type {@code T}
     * against the control object with which this {@code Predicate}
     * was initialised.
     *
     * @param other The object to be equated.
     * @return A {@code boolean} representing whether or
     *         not the supplied object is equal to the
     *         control object specified at construction
     *         of this {@code Predicate} according to
     *         that object's {@code equals} method.
     */
    @Override public boolean evaluate(T other) {
        return Objects.equal(controlObject, other);
    }

    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
