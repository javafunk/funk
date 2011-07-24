package org.javafunk.funk.testclasses;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Animal {
    private Colour colour;
    private Name name;

    public static Animal animal(Colour colour, Name name) {
        return new Animal(colour, name);
    }

    public Animal(Colour colour, Name name) {
        this.colour = colour;
        this.name = name;
    }

    public Colour getColour() {
        return colour;
    }

    public Name getName() {
        return name;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
