package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.functors.ordinals.*;

import static org.javafunk.funk.Literals.listWith;

public class Septuple<S, T, U, V, W, X, Y>
        extends AbstractTuple
        implements First<S>, Second<T>, Third<U>, Fourth<V>, Fifth<W>, Sixth<X>, Seventh<Y> {
    private final S first;
    private final T second;
    private final U third;
    private final V fourth;
    private final W fifth;
    private final X sixth;
    private final Y seventh;

    public Septuple(S first, T second, U third, V fourth, W fifth, X sixth, Y seventh) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
        this.seventh = seventh;
    }

    @Override public Iterable<Object> values() {
        return listWith(first, second, third, fourth, fifth, sixth, seventh);
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

    @Override public X sixth() {
        return sixth;
    }

    @Override public Y seventh() {
        return seventh;
    }
}
