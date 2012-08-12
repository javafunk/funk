/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.procedures;

public interface QuinaryProcedure<A, B, C, D, E> {
    void execute(A firstArgument,
                 B secondArgument,
                 C thirdArgument,
                 D fourthArgument,
                 E fifthArgument);
}
