package org.javafunk.funk.generators;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Iterator;

import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class FiniteGenerator<T> extends AbstractGenerator<T> {
    private static final String[] excludedFields = new String[]{"iterator"};

    private final Iterator<? extends T> iterator;

    // These fields are required for equality
    private final Iterable<? extends T> iterable;
    private T mostRecentElement;

    public FiniteGenerator(Iterable<? extends T> iterable) {
        this.iterable = iterable;
        this.iterator = iterable.iterator();
    }

    @Override public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override public T next() {
        mostRecentElement = iterator.next();
        return mostRecentElement;
    }
    
    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other, excludedFields);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, excludedFields);
    }

    @Override public String toString() {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
