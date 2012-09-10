/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import org.javafunk.funk.behaviours.Generator;
import org.javafunk.funk.generators.ConstantGenerator;
import org.javafunk.funk.generators.FiniteGenerator;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Generators.constantGenerator;
import static org.javafunk.funk.Generators.finiteGenerator;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.listBuilderWith;

public class GeneratorsTest {
    @Test
    public void shouldConstructAConstantGeneratorWithTheSpecifiedValue() throws Exception {
        // Given
        Generator<Integer> expectedGenerator = new ConstantGenerator<Integer>(67463);

        // When
        Generator<Integer> actualGenerator = constantGenerator(67463);

        // Then
        assertThat(actualGenerator, is(expectedGenerator));
    }

    @Test
    public void shouldConstructAFiniteGeneratorUsingTheSpecifiedIterable() throws Exception {
        // Given
        Generator<Integer> expectedGenerator = new FiniteGenerator<Integer>(listBuilderWith(1, 2, 3, 4).build(ArrayList.class));

        // When
        Generator<Integer> actualGenerator = finiteGenerator(listBuilderWith(1, 2, 3, 4).build(ArrayList.class));

        // Then
        assertThat(actualGenerator, is(expectedGenerator));
    }
}
