/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.predicates;

public interface SeptenaryPredicate<A, B, C, D, E, F, G> {
    boolean evaluate(A firstInput, B secondInput, C thirdInput,
                     D fourthInput, E fifthInput, F sixthInput,
                     G seventhInput);
}
