package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.functors.ordinals.First;

import java.util.ArrayList;

import static org.javafunk.funk.Literals.listWith;

public class Single<T>
        extends AbstractTuple
        implements First<T> {
    private T first;

    public Single(T first) {
        this.first = first;
    }

    @Override public T first() {
        return first;
    }

    @Override public Iterable<Object> values() {
        return new ArrayList<Object>(listWith(first));
    }
}
