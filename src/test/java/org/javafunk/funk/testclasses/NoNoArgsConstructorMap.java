package org.javafunk.funk.testclasses;

import java.util.HashMap;

public class NoNoArgsConstructorMap<K, V> extends HashMap<K, V> {
    public NoNoArgsConstructorMap(Throwable argument) {
        throw new UnsupportedOperationException("should never throw", argument);
    }
}
