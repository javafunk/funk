package org.javafunk.funk.functors;

public interface Action<T> {
    void on(T input);
}
