package org.smallvaluesofcool.misc.functional.iterators;

import org.junit.Test;
import org.smallvaluesofcool.misc.Literals;
import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;
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

    @Test
    public void shouldRemoveTheElementFromTheUnderlyingIterator() {
        // Given
        Collection<String> actualList = listWith("one", "two", "three", "four", "five");
        Collection<String> expectedList = listWith("three", "four", "five");
        Iterator<String> iterator = actualList.iterator();

        // When
        FilteredIterator<String> filteredIterator = new FilteredIterator<String>(iterator, new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("o");
            }
        });

        filteredIterator.next();
        filteredIterator.remove();
        filteredIterator.next();
        filteredIterator.remove();

        // Then
        assertThat(expectedList, is(actualList));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfNextHasNotBeenCalledBeforeRemove() throws Exception {
        // Given
        Iterator<String> iterator = listWith("one", "two", "three").iterator();

        // When
        FilteredIterator<String> filteredIterator = new FilteredIterator<String>(iterator, new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("o");
            }
        });

        filteredIterator.hasNext();
        filteredIterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledMoreThanOnceInARow() throws Exception {
        // Given
        Iterator<String> iterator = listWith("one", "two", "three").iterator();

        // When
        FilteredIterator<String> filteredIterator = new FilteredIterator<String>(iterator, new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("o");
            }
        });

        filteredIterator.next();
        filteredIterator.remove();
        filteredIterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test
    public void shouldNotRemoveAnElementThatDoesNotMatchTheSuppliedPredicate() throws Exception {
        // Given
        Collection<String> initialElements = listWith("one", "three");
        Collection<String> expectedElements = listWith("three");

        // When
        FilteredIterator<String> filteredIterator = new FilteredIterator<String>(initialElements.iterator(),
                new PredicateFunction<String>() {
                    public boolean matches(String item) {
                        return item.contains("o");
                    }
                });

        // Then
        assertThat(filteredIterator.next(), is("one"));
        filteredIterator.remove();
        assertThat(filteredIterator.hasNext(), is(false));

        try {
            filteredIterator.remove();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException exception) {
            // continue
        }

        assertThat(initialElements, is(expectedElements));
    }

    @Test
    public void shouldNotRemoveAnElementThatDoesNotMatchTheSuppliedPredicateEvenIfNextCalled() throws Exception {
        // Given
        Collection<String> initialElements = listWith("one", "three");
        Collection<String> expectedElements = listWith("three");

        // When
        FilteredIterator<String> filteredIterator = new FilteredIterator<String>(initialElements.iterator(),
                new PredicateFunction<String>() {
                    public boolean matches(String item) {
                        return item.contains("o");
                    }
                });

        // Then
        assertThat(filteredIterator.next(), is("one"));
        filteredIterator.remove();

        try {
            filteredIterator.next();
            fail("Expected a NoSuchElementException");
        } catch (NoSuchElementException exception) {
            // continue
        }

        try {
            filteredIterator.remove();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException exception) {
            // continue
        }

        assertThat(initialElements, is(expectedElements));
    }

    @Test
    public void shouldAllowNullValuesInTheIterator() throws Exception {
        // Given
        Iterator<Integer> delegateIterator = listWith(1, null, 10, 5).iterator();

        // When
        FilteredIterator<Integer> iterator = new FilteredIterator<Integer>(delegateIterator, new PredicateFunction<Integer>(){
            public boolean matches(Integer item) {
                return item == null || item % 10 != 0;
            }
        });

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(nullValue()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(5));
        assertThat(iterator.hasNext(), is(false));
    }
}
