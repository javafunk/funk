package org.javafunk.funk.iterators;

import org.javafunk.funk.Eagerly;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.Predicate;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ZippedIterator implements Iterator<Iterable<?>> {
    private final Iterable<? extends Iterator<?>> iterators;

    public ZippedIterator(Iterable<? extends Iterator<?>> iterators) {
        this.iterators = iterators;
    }

    public boolean hasNext() {
        return Eagerly.all(iterators, new Predicate<Iterator<?>>() {
            public boolean evaluate(Iterator<?> iterator) {
                return iterator.hasNext();
            }
        });
    }

    public Iterable<?> next() {
        if (hasNext()) {
            return Eagerly.map(iterators, new Mapper<Iterator<?>, Object>() {
                public Object map(Iterator<?> iterator) {
                    return iterator.next();
                }
            });
        } else {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
