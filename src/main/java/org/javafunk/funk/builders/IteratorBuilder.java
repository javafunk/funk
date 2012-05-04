package org.javafunk.funk.builders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorBuilder<E> extends AbstractBuilder<E, IteratorBuilder<E>, Iterator<E>> {
    private List<E> elements = new ArrayList<E>();

    public static <E> IteratorBuilder<E> iteratorBuilder() {
        return new IteratorBuilder<E>();
    }

    public static <E> IteratorBuilder<E> iteratorBuilder(Class<E> elementClass) {
        return new IteratorBuilder<E>();
    }

    @Override public Iterator<E> build() {
        return new ArrayList<E>(elements).iterator();
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected IteratorBuilder<E> updatedBuilder() {
        return this;
    }
}
