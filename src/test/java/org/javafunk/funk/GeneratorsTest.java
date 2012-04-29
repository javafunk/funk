package org.javafunk.funk;

import org.javafunk.funk.behaviours.Generator;
import org.javafunk.funk.generators.ConstantGenerator;
import org.javafunk.funk.generators.FiniteGenerator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Generators.constantGenerator;
import static org.javafunk.funk.Generators.finiteGenerator;
import static org.javafunk.funk.Literals.iterableWith;

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
        Generator<Integer> expectedGenerator = new FiniteGenerator<Integer>(iterableWith(1, 2, 3, 4));

        // When
        Generator<Integer> actualGenerator = finiteGenerator(iterableWith(1, 2, 3, 4));

        // Then
        assertThat(actualGenerator, is(expectedGenerator));
    }
}
