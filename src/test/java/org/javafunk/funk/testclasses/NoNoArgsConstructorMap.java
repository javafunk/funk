package org.javafunk.funk.testclasses;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.HashMap;

public class NoNoArgsConstructorMap<K, V> extends HashMap<K, V> {
    private final String name = "Needed for equality.";

    public NoNoArgsConstructorMap(Throwable argument) {
        throw new UnsupportedOperationException("should never throw", argument);
    }

    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
