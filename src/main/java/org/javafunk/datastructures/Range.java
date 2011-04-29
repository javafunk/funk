package org.javafunk.datastructures;

public interface Range<T> extends Iterable<T> {
    boolean contains(Object other);
    T getStart();
    T getEnd();
    T getStep();
}
