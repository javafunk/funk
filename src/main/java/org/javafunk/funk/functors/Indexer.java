package org.javafunk.funk.functors;

public interface Indexer<I, O> {
    O index(I item);
}
