package org.javafunk.funk.generators;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;
import java.util.Random;

import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.javafunk.funk.Iterables.asList;

public class RandomGenerator<T> extends AbstractGenerator<T> {
    private static final String[] excludedFields = new String[]{"random"};

    private final List<? extends T> elements;
    private final Random random;

    public RandomGenerator(Iterable<? extends T> iterable) {
        this.random = new Random();
        this.elements = asList(iterable);
    }

    @Override public T next() {
        return elements.get(random.nextInt(elements.size()));
    }

    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other, excludedFields);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override public String toString() {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
