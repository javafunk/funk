package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.functors.ordinals.*;

import static org.javafunk.funk.Literals.listWith;

public class Quintuple<R, S, T, U, V>
        extends AbstractTuple
        implements First<R>, Second<S>, Third<T>, Fourth<U>, Fifth<V> {
    private final R first;
    private final S second;
    private final T third;
    private final U fourth;
    private final V fifth;

    public Quintuple(R first, S second, T third, U fourth, V fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
    }

    @Override public R first() {
        return first;
    }

    @Override public S second() {
        return second;
    }

    @Override public T third() {
        return third;
    }

    @Override public U fourth() {
        return fourth;
    }

    @Override public V fifth() {
        return fifth;
    }

    @Override public Iterable<Object> values() {
        return listWith(first, second, third, fourth, fifth);
    }
}
