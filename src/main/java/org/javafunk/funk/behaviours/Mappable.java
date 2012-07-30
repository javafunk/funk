/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.behaviours;

import org.javafunk.funk.functors.functions.UnaryFunction;

public interface Mappable<I, O extends Mappable<?, O>> {
    <T> O map(UnaryFunction<? super I, ? extends T> mapper);
}
