package org.javafunk.datastructures;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import static java.lang.String.format;

public class ThreeTuple<S, T, U> {
    private S first;
    private T second;
    private U third;

    public ThreeTuple(S first, T second, U third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public S first() {
        return first;
    }

    public T second() {
        return second;
    }

    public U third() {
        return third;
    }

    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String toString() {
        return format("(%s, %s, %s)", first, second, third);
    }

}
