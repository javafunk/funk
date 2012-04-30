/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.generators;

import org.javafunk.funk.behaviours.Generator;

public abstract class AbstractGenerator<T> implements Generator<T> {
    @Override public boolean hasNext() {
        return true;
    }

    @Override public void remove() {
        throw new UnsupportedOperationException();
    }
}
