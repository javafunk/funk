package org.smallvaluesofcool.misc.functional.iterators;

import org.junit.Test;
import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.smallvaluesofcool.misc.Literals.listWith;

public class FilteredIteratorTest {
    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() {
        // Given
        Iterable<String> iterable = listWith("one", "two", "three", "four", "five");
        PredicateFunction<String> predicate = new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("o");
            }
        };

        // When
        FilteredIterator<String> iterator = new FilteredIterator<String>(iterable.iterator(), predicate);

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("one"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("two"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("four"));
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutHavingCalledHasNext() {
        // Given
        Iterable<String> iterable = listWith("one", "two", "three", "four", "five");
        PredicateFunction<String> predicate = new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("o");
            }
        };

        // When
        FilteredIterator<String> iterator = new FilteredIterator<String>(iterable.iterator(), predicate);

        // Then
        assertThat(iterator.next(), is("one"));
        assertThat(iterator.next(), is("two"));
        assertThat(iterator.next(), is("four"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfDoesntHaveNext() {
        // Given
        Iterable<String> iterable = listWith("one", "two", "three", "four", "five");
        PredicateFunction<String> predicate = new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("o");
            }
        };

        // When
        FilteredIterator<String> iterator = new FilteredIterator<String>(iterable.iterator(), predicate);
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotSupportRemovingElements() {
        // Given
        Iterable<String> iterable = listWith("one", "two", "three", "four", "five");
        PredicateFunction<String> predicate = new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("o");
            }
        };

        // When
        FilteredIterator<String> iterator = new FilteredIterator<String>(iterable.iterator(), predicate);
        iterator.remove();

        // Then an UnsupportedOperationException is thrown
    }
}
