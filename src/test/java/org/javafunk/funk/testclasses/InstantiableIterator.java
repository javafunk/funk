package org.javafunk.funk.testclasses;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Iterator;

public class InstantiableIterator<T> implements Iterator<T> {
    private final String name = "Needed for equality.";

    @Override public boolean hasNext() {
        throw new UnsupportedOperationException();
    }

    @Override public T next() {
        throw new UnsupportedOperationException();
    }

    @Override public void remove() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
