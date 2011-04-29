package org.javafunk.datastructures;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import static java.lang.String.format;

public class FourTuple<S, T, U, V> {
    private S first;
    private T second;
    private U third;
    private V fourth;

    public FourTuple(S first, T second, U third, V fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
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

    public V fourth() {
        return fourth;
    }

    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String toString() {
        return format("(%s, %s, %s, %s)", first, second, third, fourth);
    }
}
