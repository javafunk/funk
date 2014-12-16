/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors;

import org.javafunk.funk.functors.procedures.UnaryProcedure;

public abstract class Action<T> implements UnaryProcedure<T> {
    public abstract void on(T input);

    @Override public void execute(T input) {
        on(input);
    }
}
