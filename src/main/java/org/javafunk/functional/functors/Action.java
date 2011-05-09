package org.javafunk.functional.functors;

public interface Action<T> {
    void on(T input);
}
