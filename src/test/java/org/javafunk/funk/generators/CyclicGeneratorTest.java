package org.javafunk.funk.generators;

import org.javafunk.funk.functors.Action;
import org.javafunk.funk.behaviours.Generator;
import org.javafunk.funk.matchers.SelfDescribingPredicate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Eagerly.times;
import static org.javafunk.funk.Generators.toGeneratable;
import static org.javafunk.funk.Lazily.batch;
import static org.javafunk.funk.Lazily.take;
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
        assertThat(actualValues, trueForAll(new SelfDescribingPredicate<Iterable<Integer>>() {
            @Override public boolean evaluate(Iterable<Integer> batch) {
                return batch.equals(expectedValues);
            }

            @Override public String describe() {
                return "value equal to: " + expectedValues;
            }
        }));
    }

    @Test
    public void shouldBeEqualIfSuppliedIterableIsTheSameAndCursorPositionThroughTheIterableIsTheSame() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new CyclicGenerator<Integer>(listWith(1, 2, 3));
        times(10, progress(firstGenerator));
        Generator<Integer> secondGenerator = new CyclicGenerator<Integer>(listWith(1, 2, 3));
        times(10, progress(secondGenerator));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldBeEqualIfSuppliedIterableIsTheSameAndNextElementWouldBeTheSameEvenIfCycleCountDifferent() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new CyclicGenerator<Integer>(listWith(1, 2, 3));
        times(13, progress(firstGenerator));
        Generator<Integer> secondGenerator = new CyclicGenerator<Integer>(listWith(1, 2, 3));
        times(16, progress(secondGenerator));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfSuppliedIterablesAreDifferent() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new CyclicGenerator<Integer>(listWith(1, 2, 3));
        Generator<Integer> secondGenerator = new CyclicGenerator<Integer>(listWith(4, 5, 6));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }

    @Test
    public void shouldNotBeEqualIfAtDifferentPositionsThroughTheIterable() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new CyclicGenerator<Integer>(listWith(1, 2, 3));
        times(13, progress(firstGenerator));
        Generator<Integer> secondGenerator = new CyclicGenerator<Integer>(listWith(1, 2, 3));
        times(17, progress(secondGenerator));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }

    private Action<Integer> progress(final Generator<Integer> generator) {
        return new Action<Integer>() {
            @Override public void on(Integer input) {
                generator.next();
            }
        };
    }
}
