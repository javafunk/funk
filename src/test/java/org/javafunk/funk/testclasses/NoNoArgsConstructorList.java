package org.javafunk.funk.testclasses;

import java.util.ArrayList;

public class NoNoArgsConstructorList<E> extends ArrayList<E> {
    public NoNoArgsConstructorList(Throwable argument) {
        throw new UnsupportedOperationException("should never throw", argument);
    }
}
