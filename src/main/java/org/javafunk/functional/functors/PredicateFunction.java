package org.javafunk.functional.functors;

public interface PredicateFunction<T> {
    boolean matches(T item);
}