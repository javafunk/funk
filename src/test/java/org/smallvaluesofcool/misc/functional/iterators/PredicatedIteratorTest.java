package org.smallvaluesofcool.misc.functional.iterators;

import org.junit.Test;
import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.smallvaluesofcool.misc.Literals.listWith;

public class PredicatedIteratorTest {
    @Test
    public void shouldOnlyReturnElementsFromTheUnderlyingIteratorWhileThePredicateIsTrue() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aaa", "aaaa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new PredicateFunction<String>(){
            @Override
            public boolean matches(String item) {
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
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new PredicateFunction<String>(){
            @Override
            public boolean matches(String item) {
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
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new PredicateFunction<String>(){
            @Override
            public boolean matches(String item) {
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
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new PredicateFunction<String>(){
            @Override
            public boolean matches(String item) {
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
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new PredicateFunction<String>(){
            @Override
            public boolean matches(String item) {
                return item.length() < 3;
            }
        });

        predicatedIterator.next();
        predicatedIterator.next();
        predicatedIterator.next();

        // Then a NoSuchElementException should be thrown
    }

    @Test
    public void shouldRemoveTheElementFromTheUnderlyingIterator() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aaa", "aaaa");
        Iterable<String> expectedOutput = listWith("aa", "aaa", "aaaa");

        // When
        Iterator<String> predicatedIterator = new PredicatedIterator<String>(input.iterator(), new PredicateFunction<String>(){
            @Override
            public boolean matches(String item) {
                return item.length() < 3;
            }
        });

        predicatedIterator.next();
        predicatedIterator.remove();

        // Then
        assertThat(input, is(expectedOutput));
    }
}
