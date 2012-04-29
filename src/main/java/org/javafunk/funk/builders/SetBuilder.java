package org.javafunk.funk.builders;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class SetBuilder<E> extends HashSet<E> {
    public SetBuilder<E> with(E... elements) {
        return and(asList(elements));
    }

    public SetBuilder<E> with(Iterable<? extends E> elements) {
        return and(elements);
    }

    public SetBuilder<E> and(E... elements) {
        return and(asList(elements));
    }

    public SetBuilder<E> and(Iterable<? extends E> elements) {
        for (E element : elements) {
            add(element);
        }
        return this;
    }

    public Set<E> build() {
        return this;
    }
}
