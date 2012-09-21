package org.javafunk.funk.iterators;

import org.javafunk.funk.functors.procedures.UnaryProcedure;

import java.util.Iterator;

public class EachIterator<T> implements Iterator<T> {
    private Iterator<T> iterator;
    private UnaryProcedure<? super T> procedure;

    public EachIterator(Iterator<T> iterator, UnaryProcedure<? super T> procedure) {
        this.iterator = iterator;
        this.procedure = procedure;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        T next = iterator.next();
        procedure.execute(next);
        return next;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
