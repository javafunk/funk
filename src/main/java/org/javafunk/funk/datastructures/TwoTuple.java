/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import static java.lang.String.format;

public class TwoTuple<S, T> {
    private S first;
    private T second;

    public TwoTuple(S first, T second) {
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

}
