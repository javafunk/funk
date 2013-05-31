/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.javafunk.funk.Mappers;
import org.javafunk.funk.Predicates;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.Predicate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.javafunk.funk.Literals.collectionBuilderWith;
import static org.javafunk.funk.Literals.listWith;
import static org.junit.Assert.fail;

public class ComprehensionIteratorTest {
    @Test
    public void shouldOnlyYieldValuesThatPassAllPredicates(){
        Mapper mapper = Mappers.identity();
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
        ComprehensionIterator<String, String> iterator = new ComprehensionIterator<String, String>(mapper, iterable.iterator(), listWith(containsO, containsE, containsY));

        assertThat(iterator.findNext(), is("you betcha"));
    }

    @Test
    public void shouldOnlyYieldMappedValuesThatPassAllPredicates(){
        Mapper<Integer, String> subtractThreeAddYes = new Mapper<Integer, String>() {
            @Override
            public String map(Integer input) {
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
        }                                  ;

        Predicate<Integer> positivePredicate = new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer firstInput) {
                return firstInput > 0;
            }
        };




        ComprehensionIterator<Integer, String> iterator = new ComprehensionIterator<Integer, String>(subtractThreeAddYes, iterable.iterator(), listWith(evenPredicate, positivePredicate));

        assertThat(iterator.findNext(), is("1: yes"));
        assertThat(iterator.findNext(), is("3: yes"));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() {
        // Given
        Mapper<String, String> identity = Mappers.identity();
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
        ComprehensionIterator<String, String> iterator = new ComprehensionIterator<String, String>(identity, iterable.iterator(), listWith(containsP, containsS));

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
        Mapper mapper = Mappers.identity();
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
        ComprehensionIterator<String, String> iterator = new ComprehensionIterator<String, String>(mapper, iterable.iterator(), listWith(containsP, containsS));

        // Then
        assertThat(iterator.next(), is("passOne"));
        assertThat(iterator.next(), is("passTwo"));
        assertThat(iterator.next(), is("passThree"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfDoesntHaveNext() {
        // Given
        Mapper mapper = Mappers.identity();
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
        ComprehensionIterator<String, String> iterator = new ComprehensionIterator<String, String>(mapper, iterable.iterator(), listWith(containsP, containsS));
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldRemoveTheElementFromTheUnderlyingIterator() {
        // Given
        Mapper mapper = Mappers.identity();
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
        Mapper mapper = Mappers.identity();
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
        ComprehensionIterator<String, String> ComprehensionIterator = new ComprehensionIterator<String, String>(mapper, iterator,
                listWith(containsO, doesNotContainR));


        ComprehensionIterator.hasNext();
        ComprehensionIterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledMoreThanOnceInARow() throws Exception {
        // Given
        Mapper mapper = Mappers.identity();
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
        ComprehensionIterator<String, String> ComprehensionIterator = new ComprehensionIterator<String, String>(mapper, iterator,
                listWith(containsO, doesNotContainR));

        ComprehensionIterator.next();
        ComprehensionIterator.remove();
        ComprehensionIterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test
    public void shouldNotRemoveAnElementThatDoesNotMatchTheSuppliedPredicate() throws Exception {
        // Given
        Mapper mapper = Mappers.identity();
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
        ComprehensionIterator<String, String> ComprehensionIterator = new ComprehensionIterator<String, String>(mapper, initialElements.iterator(),
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
        Mapper mapper = Mappers.identity();
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
        ComprehensionIterator<String, String> ComprehensionIterator = new ComprehensionIterator<String, String>(mapper, initialElements.iterator(),
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
        Mapper mapper = Mappers.identity();
        Iterator<Integer> delegateIterator = listWith(1, null, 10, 5).iterator();

        // When
        Predicate<Integer> isNullOrDivisibleByTen = new Predicate<Integer>() {
            public boolean evaluate(Integer item) {
                return item == null || item % 10 != 0;
            }
        };
        ComprehensionIterator<Integer, Integer> iterator = new ComprehensionIterator<Integer, Integer>(mapper, delegateIterator, listWith(isNullOrDivisibleByTen));

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
        Mapper mapper = Mappers.identity();
        Iterator<Integer> input = listWith(1, 2, 3).iterator();
        Predicate<Integer> nullPredicate = null;
        Predicate<Integer> nonNullPredicate = Predicates.alwaysTrue();

        // When
        new ComprehensionIterator<Integer, Integer>(mapper, input, listWith(nonNullPredicate,nullPredicate));

        // Then a NullPointerException is thrown.
    }
    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfMapperSuppliedToConstructorIsNull() throws Exception {
        // Given
        Mapper mapper = null;
        Iterator<Integer> input = listWith(1, 2, 3).iterator();
        Predicate<Integer> nonNullPredicate = Predicates.alwaysTrue();

        // When
        new ComprehensionIterator<Integer, Integer>(mapper, input, listWith(nonNullPredicate));

        // Then a NullPointerException is thrown.
    }
}
