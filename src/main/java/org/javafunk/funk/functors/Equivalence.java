package org.javafunk.funk.functors;

public interface Equivalence<T> {
    boolean equal(T first, T second);
}
