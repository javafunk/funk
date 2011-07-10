package org.javafunk.functors;

public interface Action<T> {
    void on(T input);
}
