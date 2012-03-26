package org.javafunk.funk.generators;

import org.javafunk.funk.functors.Generator;
import org.junit.Test;

import java.util.Collection;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Eager.take;
import static org.javafunk.funk.Generators.toGeneratable;
import static org.javafunk.funk.Literals.listWith;

public class FiniteGeneratorTest {
    @Test
    public void shouldGenerateValuesInTheSameOrderAsTheUnderlyingIterable() throws Exception {
        // Given
        Iterable<Integer> backingValues = listWith(1, 4, 9, 16, 25);
        Generator<Integer> generator = new FiniteGenerator<Integer>(backingValues);
        Collection<Integer> expectedValues = listWith(1, 4, 9, 16);

        // When
        Collection<Integer> actualValues = take(toGeneratable(generator), 4);

        // Then
        assertThat(actualValues, is(expectedValues));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionOnceGeneratorExhausted() throws Exception {
        // Given
        Iterable<Integer> backingValues = listWith(1);
        Generator<Integer> generator = new FiniteGenerator<Integer>(backingValues);
        generator.next();

        // When
        generator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldNotHaveNextOnceGeneratorExhausted() throws Exception {
        // Given
        Iterable<Integer> backingValues = listWith(1);
        Generator<Integer> generator = new FiniteGenerator<Integer>(backingValues);

        // When
        generator.next();

        // Then
        assertThat(generator.hasNext(), is(false));
    }

    @Test
    public void shouldBeEqualIfSuppliedIterableIsEqualAndSameNumberOfItemsGeneratedFromEach() throws Exception {
        // Given
        Iterable<Integer> backingValues = listWith(1, 2, 3, 4);
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
        Generator<Integer> firstGenerator = new FiniteGenerator<Integer>(listWith(1, 2, 3));
        Generator<Integer> secondGenerator = new FiniteGenerator<Integer>(listWith(4, 5, 6));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }

    @Test
    public void shouldNotBeEqualIfDifferentNumbersOfItemsGeneratedFromEach() throws Exception {
        // Given
        Generator<Integer> firstGenerator = new FiniteGenerator<Integer>(listWith(1, 2, 3, 4));
        firstGenerator.next();
        Generator<Integer> secondGenerator = new FiniteGenerator<Integer>(listWith(1, 2, 3, 4));
        secondGenerator.next();
        secondGenerator.next();

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }
}
