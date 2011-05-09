package org.javafunk.functional.functors;

public interface Predicate<T> {
    boolean evaluate(T item);
}