package org.javafunk.funk.testclasses;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Cat extends Animal {
    public static Cat cat(Colour colour, Name name) {
        return new Cat(colour, name);
    }

    public Cat(Colour colour, Name name) {
        super(colour, name);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
