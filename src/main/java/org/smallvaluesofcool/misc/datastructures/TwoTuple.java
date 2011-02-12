package org.smallvaluesofcool.misc.datastructures;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import static java.lang.String.format;

public class TwoTuple<S, T> {
    private S first;
    private T second;

    private TwoTuple(S first, T second) {
        this.first = first;
        this.second = second;
    }

    public S first() {
        return first;
    }

    public T second() {
        return second;
    }

    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String toString() {
        return format("(%s, %s)", first, second);
    }

    public static <S, T> TwoTuple<S, T> twoTuple(S first, T second) {
        return new TwoTuple<S, T>(first, second);
    }
}
