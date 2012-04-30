/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.generators;

import org.javafunk.funk.behaviours.Generator;
import org.junit.Test;

import java.util.Collection;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Eagerly.take;
import static org.javafunk.funk.Generators.toGeneratable;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;

public class FiniteGeneratorTest {
    @Test
    public void shouldGenerateValuesInTheSameOrderAsTheUnderlyingIterable() throws Exception {
        // Given
        Iterable<Integer> backingValues = iterableWith(1, 4, 9, 16, 25);
        Generator<Integer> generator = new FiniteGenerator<Integer>(backingValues);
        Collection<Integer> expectedValues = collectionWith(1, 4, 9, 16);

        // When
        Collection<Integer> actualValues = take(toGeneratable(generator), 4);

        // Then
        assertThat(actualValues, is(expectedValues));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionOnceGeneratorExhausted() throws Exception {
        // Given
        Iterable<Integer> backingValues = iterableWith(1);
        Generator<Integer> generator = new FiniteGenerator<Integer>(backingValues);
        generator.next();

        // When
        generator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldNotHaveNextOnceGeneratorExhausted() throws Exception {
        // Given
        Iterable<Integer> backingValues = iterableWith(1);
        Generator<Integer> generator = new FiniteGenerator<Integer>(backingValues);

        // When
        generator.next();

        // Then
        assertThat(generator.hasNext(), is(false));
    }

    @Test
    public void shouldBeEqualIfSuppliedIterableIsEqualAndSameNumberOfItemsGeneratedFromEach() throws Exception {
        // Given
        Iterable<Integer> backingValues = iterableWith(1, 2, 3, 4);
        Generator<Integer> firstGenerator = new FiniteGenerator<Integer>(backingValues);
        firstGenerator.next();
        firstGenerator.next();
        Generator<Integer> secondGenerator = new FiniteGenerator<Integer>(backingValues);
        secondGenerator.next();
        secondGenerator.next();

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfSuppliedIterablesAreDifferent() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new FiniteGenerator<Integer>(iterableWith(1, 2, 3));
        Generator<Integer> secondGenerator = new FiniteGenerator<Integer>(iterableWith(4, 5, 6));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }

    @Test
    public void shouldNotBeEqualIfDifferentNumbersOfItemsGeneratedFromEach() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new FiniteGenerator<Integer>(iterableWith(1, 2, 3, 4));
        firstGenerator.next();
        Generator<Integer> secondGenerator = new FiniteGenerator<Integer>(iterableWith(1, 2, 3, 4));
        secondGenerator.next();
        secondGenerator.next();

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }
}
