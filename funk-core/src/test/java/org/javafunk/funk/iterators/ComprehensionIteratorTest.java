/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.apache.commons.lang.NotImplementedException;
import org.javafunk.funk.Mappers;
import org.javafunk.funk.Predicates;
import org.javafunk.funk.UnaryFunctions;
import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComprehensionIteratorTest {
    @Test
    public void shouldOnlyYieldValuesThatPassAllPredicates() {
        UnaryFunction<? super String, String> mapper = mapperUnaryFunction(Mappers.<String>identity());
        Iterable<String> iterable = listWith("no", "nope", "no way", "nem", "you betcha");

        Predicate<String> containsO = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        };
        Predicate<String> containsE = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("e");
            }
        };
        Predicate<String> containsY = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("y");
            }
        };

        ComprehensionIterator<String, String> iterator =
                new ComprehensionIterator<String, String>(
                        mapper,
                        iterable.iterator(),
                        listWith(containsO, containsE, containsY));

        assertThat(iterator.findNext(), is("you betcha"));
    }

    @Test
    public void shouldOnlyYieldMappedValuesThatPassAllPredicates() {
        UnaryFunction<Integer, String> subtractThreeAddYes = new UnaryFunction<Integer, String>() {
            @Override
            public String call(Integer input) {
                Integer subtractThree = input - 3;
                return subtractThree.toString() + ": yes";
            }
        };

        Iterable<Integer> iterable = listWith(-1, -2, 0, 3, 4, 6, 7);

        Predicate<Integer> evenPredicate = new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer firstInput) {
                return firstInput % 2 == 0;
            }
        };
        Predicate<Integer> positivePredicate = new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer firstInput) {
                return firstInput > 0;
            }
        };

        ComprehensionIterator<Integer, String> iterator =
                new ComprehensionIterator<Integer, String>(
                        subtractThreeAddYes,
                        iterable.iterator(),
                        listWith(evenPredicate, positivePredicate));

        assertThat(iterator.findNext(), is("1: yes"));
        assertThat(iterator.findNext(), is("3: yes"));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() {
        // Given
        UnaryFunction<String, String> identity = mapperUnaryFunction(Mappers.<String>identity());
        Iterable<String> iterable = listWith("passOne", "passTwo", "failOne", "passThree", "failTwo");

        Predicate<String> containsP = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("p");
            }
        };
        Predicate<String> containsS = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("s");
            }
        };

        // When
        ComprehensionIterator<String, String> iterator =
                new ComprehensionIterator<String, String>(
                        identity,
                        iterable.iterator(),
                        listWith(containsP, containsS));

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("passOne"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("passTwo"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("passThree"));
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutHavingCalledHasNext() {
        // Given
        UnaryFunction<String, String> mapper = mapperUnaryFunction(Mappers.<String>identity());
        Iterable<String> iterable = listWith("passOne", "passTwo", "failOne", "passThree", "failTwo");

        Predicate<String> containsP = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("p");
            }
        };
        Predicate<String> containsS = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("s");
            }
        };

        // When
        ComprehensionIterator<String, String> iterator =
                new ComprehensionIterator<String, String>(
                        mapper,
                        iterable.iterator(),
                        listWith(containsP, containsS));

        // Then
        assertThat(iterator.next(), is("passOne"));
        assertThat(iterator.next(), is("passTwo"));
        assertThat(iterator.next(), is("passThree"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfDoesntHaveNext() {
        // Given
        UnaryFunction<String, String> mapper = mapperUnaryFunction(Mappers.<String>identity());
        Iterable<String> iterable = listWith("passOne", "passTwo", "failOne", "passThree", "failTwo");

        Predicate<String> containsP = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("p");
            }
        };
        Predicate<String> containsS = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("s");
            }
        };

        // When
        ComprehensionIterator<String, String> iterator =
                new ComprehensionIterator<String, String>(
                        mapper,
                        iterable.iterator(),
                        listWith(containsP, containsS));

        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldRemoveTheElementFromTheUnderlyingIterator() {
        // Given
        UnaryFunction<String, String> mapper = mapperUnaryFunction(Mappers.<String>identity());
        Collection<String> actualList = collectionBuilderWith("one", "two", "three", "four", "five").build(ArrayList.class);
        Collection<String> expectedList = collectionBuilderWith("three", "four", "five").build(ArrayList.class);
        Iterator<String> iterator = actualList.iterator();

        // When
        Predicate<String> containsO = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        };
        Predicate<String> doesNotContainR = new Predicate<String>() {
            public boolean evaluate(String item) {
                return !item.contains("r");
            }
        };
        Predicate<String> doesNotContainF = new Predicate<String>() {
            public boolean evaluate(String item) {
                return !item.contains("f");
            }
        };

        ComprehensionIterator<String, String> ComprehensionIterator = new ComprehensionIterator<String, String>(mapper, iterator,
                listWith(containsO, doesNotContainR, doesNotContainF));


        ComprehensionIterator.next();
        ComprehensionIterator.remove();
        ComprehensionIterator.next();
        ComprehensionIterator.remove();

        // Then
        assertThat(expectedList, is(actualList));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfNextHasNotBeenCalledBeforeRemove() throws Exception {
        // Given
        UnaryFunction<String, String> mapper = mapperUnaryFunction(Mappers.<String>identity());
        Iterator<String> iterator = listWith("one", "two", "three").iterator();

        // When
        Predicate<String> containsO = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        };
        Predicate<String> doesNotContainR = new Predicate<String>() {
            public boolean evaluate(String item) {
                return !item.contains("r");
            }
        };

        ComprehensionIterator<String, String> ComprehensionIterator = new ComprehensionIterator<String, String>(
                mapper,
                iterator,
                listWith(containsO, doesNotContainR));


        ComprehensionIterator.hasNext();
        ComprehensionIterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledMoreThanOnceInARow() throws Exception {
        // Given
        UnaryFunction<String, String> mapper = mapperUnaryFunction(Mappers.<String>identity());
        Iterator<String> iterator = collectionBuilderWith("one", "two", "three").build(ArrayList.class).iterator();

        // When
        Predicate<String> containsO = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        };
        Predicate<String> doesNotContainR = new Predicate<String>() {
            public boolean evaluate(String item) {
                return !item.contains("r");
            }
        };
        ComprehensionIterator<String, String> ComprehensionIterator = new ComprehensionIterator<String, String>(
                mapper,
                iterator,
                listWith(containsO, doesNotContainR));

        ComprehensionIterator.next();
        ComprehensionIterator.remove();
        ComprehensionIterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test
    public void shouldNotRemoveAnElementThatDoesNotMatchTheSuppliedPredicate() throws Exception {
        // Given
        UnaryFunction<String, String> mapper = mapperUnaryFunction(Mappers.<String>identity());
        Collection<String> initialElements = collectionBuilderWith("one", "three").build(ArrayList.class);
        Collection<String> expectedElements = collectionBuilderWith("three").build(ArrayList.class);

        // When
        Predicate<String> containsO = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        };
        Predicate<String> containsN = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("n");
            }
        };
        ComprehensionIterator<String, String> ComprehensionIterator = new ComprehensionIterator<String, String>(
                mapper,
                initialElements.iterator(),
                listWith(containsO, containsN));


        // Then
        assertThat(ComprehensionIterator.next(), is("one"));
        ComprehensionIterator.remove();
        assertThat(ComprehensionIterator.hasNext(), is(false));

        try {
            ComprehensionIterator.remove();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException exception) {
            // continue
        }

        assertThat(initialElements, is(expectedElements));
    }

    @Test
    public void shouldNotRemoveAnElementThatDoesNotMatchTheSuppliedPredicateEvenIfNextCalled() throws Exception {
        // Given
        UnaryFunction<String, String> mapper = mapperUnaryFunction(Mappers.<String>identity());
        Collection<String> initialElements = collectionBuilderWith("one", "three").build(ArrayList.class);
        Collection<String> expectedElements = collectionBuilderWith("three").build(ArrayList.class);

        // When
        Predicate<String> containsO = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        };
        Predicate<String> containsN = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("n");
            }
        };

        ComprehensionIterator<String, String> ComprehensionIterator = new ComprehensionIterator<String, String>(
                mapper,
                initialElements.iterator(),
                listWith(containsO, containsN));

        // Then
        assertThat(ComprehensionIterator.next(), is("one"));
        ComprehensionIterator.remove();

        try {
            ComprehensionIterator.next();
            fail("Expected a NoSuchElementException");
        } catch (NoSuchElementException exception) {
            // continue
        }

        try {
            ComprehensionIterator.remove();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException exception) {
            // continue
        }

        assertThat(initialElements, is(expectedElements));
    }

    @Test
    public void shouldAllowNullValuesInTheIterator() throws Exception {
        // Given
        UnaryFunction<Integer, Integer> mapper = mapperUnaryFunction(Mappers.<Integer>identity());
        Iterator<Integer> delegateIterator = listWith(1, null, 10, 5).iterator();

        // When
        Predicate<Integer> isNullOrDivisibleByTen = new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return item == null || item % 10 != 0;
            }
        };
        ComprehensionIterator<Integer, Integer> iterator = new ComprehensionIterator<Integer, Integer>(
                mapper,
                delegateIterator,
                listWith(isNullOrDivisibleByTen));

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(nullValue()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(5));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfAnyPredicatesSuppliedToConstructorAreNull() throws Exception {
        // Given
        UnaryFunction<Integer, Integer> mapper = mapperUnaryFunction(Mappers.<Integer>identity());
        Iterator<Integer> input = listWith(1, 2, 3).iterator();
        Predicate<Integer> nullPredicate = null;
        Predicate<Integer> nonNullPredicate = Predicates.alwaysTrue();

        // When
        new ComprehensionIterator<Integer, Integer>(mapper, input, listWith(nonNullPredicate, nullPredicate));

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfMapperSuppliedToConstructorIsNull() throws Exception {
        // Given
        UnaryFunction<Integer, Integer> mapper = null;
        Iterator<Integer> input = listWith(1, 2, 3).iterator();
        Predicate<Integer> nonNullPredicate = Predicates.alwaysTrue();

        // When
        new ComprehensionIterator<Integer, Integer>(mapper, input, listWith(nonNullPredicate));

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldIncludeTheMappingFunctionInTheToStringRepresentation() throws Exception {
        // Given
        UnaryFunction<Integer, Integer> mapper = new UnaryFunction<Integer, Integer>() {
            @Override public Integer call(Integer integer) {
                throw new NotImplementedException();
            }

            @Override public String toString() {
                return "some-mapping-function";
            }
        };
        Iterator<Integer> input = listWith(1, 2, 3).iterator();
        Predicate<Integer> predicate = Predicates.alwaysTrue();

        ComprehensionIterator<Integer, Integer> iterator =
                new ComprehensionIterator<Integer, Integer>(mapper, input, listWith(predicate));

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("some-mapping-function"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldIncludeTheIteratorFunctionInTheToStringRepresentation() throws Exception {
        // Given
        UnaryFunction<Integer, Integer> mapper = UnaryFunctions.identity();
        Iterator<Integer> input = (Iterator<Integer>) mock(Iterator.class);
        Predicate<Integer> predicate = Predicates.alwaysTrue();

        when(input.toString()).thenReturn("the-iterator");

        ComprehensionIterator<Integer, Integer> iterator =
                new ComprehensionIterator<Integer, Integer>(mapper, input, listWith(predicate));

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("the-iterator"));
    }

    @Test
    public void shouldIncludeThePredicatesInTheToStringRepresentation() throws Exception {
        // Given
        UnaryFunction<Integer, Integer> mapper = UnaryFunctions.identity();
        Iterator<Integer> input = iteratorWith(1, 2, 3);
        Predicate<Integer> predicate = Predicates.alwaysTrue();
        List<Predicate<Integer>> predicates = listWith(predicate);

        ComprehensionIterator<Integer, Integer> iterator =
                new ComprehensionIterator<Integer, Integer>(mapper, input, predicates);

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString(predicates.toString()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfSourceIteratorPassedAtConstructionTimeIsNull() throws Exception {
        // Given
        UnaryFunction<Integer, Integer> function = UnaryFunctions.identity();
        Iterator<Integer> iterator = null;
        Predicate<Object> predicate = Predicates.alwaysTrue();

        // When
        new ComprehensionIterator<Integer, Integer>(function, iterator, iterableWith(predicate));

        // Then a NullPointerException is thrown
    }
}
