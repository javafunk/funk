package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.First;

import java.util.ArrayList;

import static org.javafunk.funk.Literals.collectionWith;

public class Single<R>
        extends AbstractTuple
        implements First<R> {
    private R first;

    public Single(R first) {
        this.first = first;
    }

    @Override public R first() {
        return first;
    }

    @Override public Iterable<Object> values() {
        return new ArrayList<Object>(collectionWith(first));
    }
}
