package org.javafunk.funk;

import org.javafunk.funk.functors.Generator;
import org.javafunk.funk.generators.ConstantGenerator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Generators.constantGenerator;

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
}
