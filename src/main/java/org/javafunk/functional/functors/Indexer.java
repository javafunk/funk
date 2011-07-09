package org.javafunk.functional.functors;

public interface Indexer<I, O> {
    O index(I item);
}
