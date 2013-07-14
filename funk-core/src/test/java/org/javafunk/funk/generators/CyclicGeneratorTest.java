/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.generators;

import org.javafunk.funk.behaviours.Generator;
import org.javafunk.funk.functors.Action;
import org.javafunk.matchbox.SelfDescribingPredicate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Eagerly.times;
import static org.javafunk.funk.Generators.toGeneratable;
import static org.javafunk.funk.Lazily.batch;
import static org.javafunk.funk.Lazily.take;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasAllElementsSatisfying;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class CyclicGeneratorTest {
    @Test
    public void shouldInfinitelyCycleThroughTheSuppliedIterable() throws Exception {
        // Given
        final Iterable<Integer> expectedValues = iterableWith(1, 2, 3);
        final Generator<Integer> generator = new CyclicGenerator<Integer>(expectedValues);

        // When
        Iterable<Iterable<Integer>> actualValues = batch(take(toGeneratable(generator), 3000), 3);

        // Then
        assertThat(actualValues, hasAllElementsSatisfying(new SelfDescribingPredicate<Iterable<Integer>>() {
            @Override public boolean evaluate(Iterable<Integer> batch) {
                return hasOnlyItemsInOrder(expectedValues).matches(batch);
            }

            @Override public String describe() {
                return "value equal to: " + expectedValues;
            }
        }));
    }

    @Test
    public void shouldBeEqualIfSuppliedIterableIsTheSameAndCursorPositionThroughTheIterableIsTheSame() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new CyclicGenerator<Integer>(iterableWith(1, 2, 3));
        times(10, progress(firstGenerator));
        Generator<Integer> secondGenerator = new CyclicGenerator<Integer>(iterableWith(1, 2, 3));
        times(10, progress(secondGenerator));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldBeEqualIfSuppliedIterableIsTheSameAndNextElementWouldBeTheSameEvenIfCycleCountDifferent() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new CyclicGenerator<Integer>(iterableWith(1, 2, 3));
        times(13, progress(firstGenerator));
        Generator<Integer> secondGenerator = new CyclicGenerator<Integer>(iterableWith(1, 2, 3));
        times(16, progress(secondGenerator));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfSuppliedIterablesAreDifferent() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new CyclicGenerator<Integer>(iterableWith(1, 2, 3));
        Generator<Integer> secondGenerator = new CyclicGenerator<Integer>(iterableWith(4, 5, 6));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }

    @Test
    public void shouldNotBeEqualIfAtDifferentPositionsThroughTheIterable() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new CyclicGenerator<Integer>(iterableWith(1, 2, 3));
        times(13, progress(firstGenerator));
        Generator<Integer> secondGenerator = new CyclicGenerator<Integer>(iterableWith(1, 2, 3));
        times(17, progress(secondGenerator));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfIterableSuppliedAtConstructionTimeIsNull() throws Exception {
        // Given
        Iterable<Integer> input = null;

        // When
        new CyclicGenerator<Integer>(input);

        // Then a NullPointerException is thrown
    }

    private Action<Integer> progress(final Generator<Integer> generator) {
        return new Action<Integer>() {
            @Override public void on(Integer input) {
                generator.next();
            }
        };
    }
}
