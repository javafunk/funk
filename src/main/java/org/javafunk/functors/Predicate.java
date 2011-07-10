package org.javafunk.functors;

public interface Predicate<T> {
    boolean evaluate(T item);
}