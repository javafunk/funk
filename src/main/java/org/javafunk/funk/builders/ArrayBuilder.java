package org.javafunk.funk.builders;

import org.javafunk.funk.Eagerly;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.javafunk.funk.Eagerly.first;
import static org.javafunk.funk.Predicates.instanceOf;
import static org.javafunk.funk.Predicates.not;

public class ArrayBuilder<E> extends AbstractBuilder<E, ArrayBuilder<E>, E[]> {
    private List<E> elements = new ArrayList<E>();

    public static <E> ArrayBuilder<E> arrayBuilder() {
        return new ArrayBuilder<E>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] build() {
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("Cannot construct array from empty Iterable.");
        }
        Class<?> targetClass = first(elements).get().getClass();
        if (Eagerly.any(elements, not(instanceOf(targetClass)))) {
            throw new IllegalArgumentException("Cannot construct array from Iterable containing instances of different classes");
        }
        return elements.toArray((E[]) Array.newInstance(targetClass, 0));
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected ArrayBuilder<E> updatedBuilder() {
        return this;
    }
}
