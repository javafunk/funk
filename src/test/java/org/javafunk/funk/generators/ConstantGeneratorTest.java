package org.javafunk.funk.generators;

import org.javafunk.funk.matchers.SelfDescribingPredicate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Generators.toGeneratable;
import static org.javafunk.funk.Lazily.take;
import static org.javafunk.funk.matchers.Matchers.trueForAll;

public class ConstantGeneratorTest {
    @Test
    public void shouldAlwaysReturnTheSuppliedValueForNext() throws Exception {
        // Given
        final String expectedValue = "Hello";
        final ConstantGenerator<String> generator = new ConstantGenerator<String>(expectedValue);

        // When
        Iterable<String> values = take(toGeneratable(generator), 1000);

        // Then
        assertThat(values, trueForAll(new SelfDescribingPredicate<String>() {
            @Override public Boolean evaluate(String actualValue) {
                return actualValue.equals(expectedValue);
            }

            @Override public String describe() {
                return "value equal to: " + expectedValue;
            }
        }));
    }

    @Test
    public void shouldBeEqualIfHasTheSameValue() throws Exception {
        // Given
        ConstantGenerator<Integer> firstGenerator = new ConstantGenerator<Integer>(1439);
        ConstantGenerator<Integer> secondGenerator = new ConstantGenerator<Integer>(1439);

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentValue() throws Exception {
        // Given
        ConstantGenerator<Integer> firstGenerator = new ConstantGenerator<Integer>(1439);
        ConstantGenerator<Integer> secondGenerator = new ConstantGenerator<Integer>(22984);

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }
}
