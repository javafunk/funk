package org.javafunk.functors;

public interface Indexer<I, O> {
    O index(I item);
}
