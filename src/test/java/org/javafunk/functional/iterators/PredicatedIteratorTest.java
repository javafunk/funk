package org.javafunk.functional.iterators;

import org.javafunk.functional.functors.Predicate;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;
import static org.javafunk.Literals.listWith;

public class PredicatedIteratorTest {
    @Test
    public void shouldOnlyReturnElementsFromTheUnderlyingIteratorWhileThePredicateIsTrue() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aaa", "aaaa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new Predicate<String>(){
            @Override
            public boolean evaluate(String item) {
                return item.length() < 3;
            }
        });

        // Then
        assertThat(predicatedIterator.hasNext(), is(true));
        assertThat(predicatedIterator.next(), is("a"));
        assertThat(predicatedIterator.hasNext(), is(true));
        assertThat(predicatedIterator.next(), is("aa"));
        assertThat(predicatedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new Predicate<String>(){
            @Override
            public boolean evaluate(String item) {
                return item.length() < 2;
            }
        });

        // Then
        assertThat(predicatedIterator.hasNext(), is(true));
        assertThat(predicatedIterator.hasNext(), is(true));
        assertThat(predicatedIterator.next(), is("a"));
        assertThat(predicatedIterator.hasNext(), is(false));
        assertThat(predicatedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutCallingHasNextFirst() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aaa", "aaaa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new Predicate<String>(){
            @Override
            public boolean evaluate(String item) {
                return item.length() < 3;
            }
        });

        // Then
        assertThat(predicatedIterator.next(), is("a"));
        assertThat(predicatedIterator.next(), is("aa"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfTheUnderlyingIteratorIsExhausted() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new Predicate<String>(){
            @Override
            public boolean evaluate(String item) {
                return item.length() < 10;
            }
        });

        predicatedIterator.next();
        predicatedIterator.next();
        predicatedIterator.next();

        // Then a NoSuchElementException should be thrown
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfNoMoreElementsMatchThePredicate() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aaa", "aaaa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new Predicate<String>(){
            @Override
            public boolean evaluate(String item) {
                return item.length() < 3;
            }
        });

        predicatedIterator.next();
        predicatedIterator.next();
        predicatedIterator.next();

        // Then a NoSuchElementException should be thrown
    }

    @Test
    public void shouldAllowNullValuesInTheIterator() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, null, 1, 2);

        // When
        Iterator<Integer> predicatedIterator = new PredicatedIterator<Integer>(input.iterator(), new Predicate<Integer>(){
            @Override
            public boolean evaluate(Integer item) {
                return item == null || item != 2;
            }
        });

        // Then
        assertThat(predicatedIterator.hasNext(), is(true));
        assertThat(predicatedIterator.next(), is(1));
        assertThat(predicatedIterator.hasNext(), is(true));
        assertThat(predicatedIterator.next(), is(nullValue()));
        assertThat(predicatedIterator.hasNext(), is(true));
        assertThat(predicatedIterator.next(), is(1));
        assertThat(predicatedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldRemoveTheElementFromTheUnderlyingIterator() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aaa", "aaaa");
        Iterable<String> expectedOutput = listWith("aa", "aaa", "aaaa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new Predicate<String>(){
            @Override
            public boolean evaluate(String item) {
                return item.length() < 3;
            }
        });

        predicatedIterator.next();
        predicatedIterator.remove();

        // Then
        assertThat(input, is(expectedOutput));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledBeforeNext() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aaa", "aaaa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new Predicate<String>(){
            @Override
            public boolean evaluate(String item) {
                return item.length() < 3;
            }
        });

        predicatedIterator.hasNext();
        predicatedIterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledMoreThanOnceInARow() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aaa", "aaaa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new Predicate<String>(){
            @Override
            public boolean evaluate(String item) {
                return item.length() < 4;
            }
        });

        predicatedIterator.next();
        predicatedIterator.remove();
        predicatedIterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test
    public void shouldNotRemoveAnElementThatDoesNotMatchTheSuppliedPredicate() throws Exception {
        // Given
        Collection<String> input = listWith("a", "aa", "aaa", "aaaa");
        Collection<String> expectedResult = listWith("aa", "aaa", "aaaa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new Predicate<String>(){
            @Override
            public boolean evaluate(String item) {
                return item.length() < 2;
            }
        });

        // Then
        assertThat(predicatedIterator.next(), is("a"));
        predicatedIterator.remove();
        assertThat(predicatedIterator.hasNext(), is(false));

        try {
            predicatedIterator.remove();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException exception) {
            // continue
        }

        assertThat(input, is(expectedResult));
    }

    @Test
    public void shouldNotRemoveAnElementThatDoesNotMatchTheSuppliedPredicateEvenIfNextCalled() throws Exception {
        // Given
        Collection<String> input = listWith("a", "aa", "aaa", "aaaa");
        Collection<String> expectedResult = listWith("aa", "aaa", "aaaa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new Predicate<String>(){
            @Override
            public boolean evaluate(String item) {
                return item.length() < 2;
            }
        });

        // Then
        assertThat(predicatedIterator.next(), is("a"));
        predicatedIterator.remove();

        try {
            predicatedIterator.next();
            fail("Expected a NoSuchElementException");
        } catch (NoSuchElementException exception) {
            // continue
        }

        try {
            predicatedIterator.remove();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException exception) {
            // continue
        }

        assertThat(input, is(expectedResult));
    }
}
