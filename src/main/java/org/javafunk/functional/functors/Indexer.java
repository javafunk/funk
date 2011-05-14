package org.javafunk.functional.functors;

public interface Indexer<I, O> {
    public O index(I item);
}
