/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.predicates;

public interface SenaryPredicate<A, B, C, D, E, F> {
    boolean evaluate(A firstInput, B secondInput, C thirdInput,
                     D fourthInput, E fifthInput, F sixthInput);
}
