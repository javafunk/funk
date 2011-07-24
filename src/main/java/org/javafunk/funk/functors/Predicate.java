package org.javafunk.funk.functors;

public interface Predicate<T> {
    boolean evaluate(T item);
}