package org.javafunk.testclasses;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Dog extends Animal {
    public static Dog dog(Colour colour, Name name) {
        return new Dog(colour, name);
    }

    public Dog(Colour colour, Name name) {
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
