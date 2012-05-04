package org.javafunk.funk.builders;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import static java.util.Arrays.asList;

public abstract class AbstractBuilder<E, B extends AbstractBuilder, C> {
    public abstract C build();
    
    protected abstract void handle(E element);

    protected abstract B updatedBuilder();

    public B with(Iterable<? extends E> elements) {
        return and(elements);
    }

    public B with(E[] elements) {
        return and(elements);
    }

    public B and(E[] elements) {
        for (int i = 0, elementsLength = elements.length; i < elementsLength; i++) {
            handle(elements[i]);
        }
        return updatedBuilder();
    }

    public B and(Iterable<? extends E> elements) {
        for (E element : elements) {
            handle(element);
        }
        return updatedBuilder();
    }

    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public static interface WithCustomImplementationSupport<T, S extends T> {
        S build(Class<? extends T> implementationClass) throws IllegalAccessException, InstantiationException;
    }

    @SuppressWarnings("unchecked") public B with(E e) { return and(asList(e)); }
    @SuppressWarnings("unchecked") public B with(E e1, E e2) { return and(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public B with(E e1, E e2, E e3) { return and(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public B with(E e1, E e2, E e3, E e4) { return and(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public B with(E e1, E e2, E e3, E e4, E e5) { return and(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public B with(E e1, E e2, E e3, E e4, E e5, E e6) { return and(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public B with(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return and(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public B with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public B with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public B with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public B with(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
        and(e11on);
        return updatedBuilder();
    }

    @SuppressWarnings("unchecked") public B and(E e) { return and(asList(e)); }
    @SuppressWarnings("unchecked") public B and(E e1, E e2) { return and(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public B and(E e1, E e2, E e3) { return and(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public B and(E e1, E e2, E e3, E e4) { return and(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public B and(E e1, E e2, E e3, E e4, E e5) { return and(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public B and(E e1, E e2, E e3, E e4, E e5, E e6) { return and(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public B and(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return and(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public B and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public B and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public B and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public B and(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        and(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
        and(e11on);
        return updatedBuilder();
    }
}
