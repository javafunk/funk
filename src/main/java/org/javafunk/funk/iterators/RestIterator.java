package org.javafunk.funk.iterators;

import java.util.Iterator;

public class RestIterator<T> implements Iterator<T> {
    private Iterator<T> iterator;

    public RestIterator(Iterable<T> iterable){
        this.iterator = iterable.iterator();
        if(iterator.hasNext()){
            iterator.next();
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}
