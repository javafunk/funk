package org.javafunk.funk.generators;

import org.javafunk.funk.functors.Generator;
import org.javafunk.funk.matchers.SelfDescribingPredicate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.Generators.toGeneratable;
import static org.javafunk.funk.Lazy.batch;
import static org.javafunk.funk.Lazy.take;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.matchers.Matchers.trueForAll;

public class CyclicGeneratorTest {
    @Test
    public void shouldInfinitelyCycleThroughTheSuppliedIterable() throws Exception {
        // Given
        final Iterable<Integer> expectedValues = listWith(1, 2, 3);
        final Generator<Integer> generator = new CyclicGenerator<Integer>(expectedValues);

        // When
        Iterable<Iterable<Integer>> actualValues = batch(take(toGeneratable(generator), 3000), 3);

        // Then
        assertThat(actualValues, trueForAll(new SelfDescribingPredicate<Iterable<Integer>>(){
            @Override public boolean evaluate(Iterable<Integer> batch) {
                return batch.equals(expectedValues);
            }

            @Override public String describe() {
                return "value equal to: " + expectedValues;
            }
        }));
    }
}
