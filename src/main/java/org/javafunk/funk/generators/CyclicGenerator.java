package org.javafunk.funk.generators;

import java.util.Iterator;

import static org.javafunk.funk.Lazy.cycle;

public class CyclicGenerator<T> extends AbstractGenerator<T> {
    private final Iterable<T> iterable;
    private final Iterator<T> iterator;

    public CyclicGenerator(Iterable<T> iterable) {
        this.iterable = iterable;
        this.iterator = cycle(iterable).iterator();
    }

    @Override public T next() {
        return iterator.next();
    }

    // TODO: Toby (2012-03-25) What should equality be based on?
    // Just iterable or iterable, current position inside iterator
    // and/or number of cycles?
}
