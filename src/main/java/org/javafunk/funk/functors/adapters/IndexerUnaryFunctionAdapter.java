/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Indexer;
import org.javafunk.funk.functors.functions.UnaryFunction;

import static com.google.common.base.Preconditions.checkNotNull;

public class IndexerUnaryFunctionAdapter<S, T> implements UnaryFunction<S, T> {
    public static <S, T> IndexerUnaryFunctionAdapter<S, T> indexerUnaryFunction(Indexer<? super S, T> indexer) {
        return new IndexerUnaryFunctionAdapter<S, T>(indexer);
    }

    private final Indexer<? super S, T> indexer;

    public IndexerUnaryFunctionAdapter(Indexer<? super S, T> indexer) {
        this.indexer = checkNotNull(indexer);
    }

    @Override public T call(S index) {
        return indexer.index(index);
    }
}
