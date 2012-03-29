package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.functors.ordinals.*;

import static org.javafunk.funk.Literals.listWith;

public class Quintuple<S, T, U, V, W>
        extends AbstractTuple
        implements First<S>, Second<T>, Third<U>, Fourth<V>, Fifth<W> {
    private final S first;
    private final T second;
    private final U third;
    private final V fourth;
    private final W fifth;

    public Quintuple(S first, T second, U third, V fourth, W fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
    }

    @Override public S first() {
        return first;
    }

    @Override public T second() {
        return second;
    }

    @Override public U third() {
        return third;
    }

    @Override public V fourth() {
        return fourth;
    }

    @Override public W fifth() {
        return fifth;
    }

    @Override public Iterable<Object> values() {
        return listWith(first, second, third, fourth, fifth);
    }
}
