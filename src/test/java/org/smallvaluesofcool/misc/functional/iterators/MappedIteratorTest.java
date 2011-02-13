package org.smallvaluesofcool.misc.functional.iterators;

import org.junit.Test;
import org.smallvaluesofcool.misc.functional.functors.MapFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.smallvaluesofcool.misc.Literals.listWith;

public class MappedIteratorTest {
    @Test
    public void shouldHaveNextAsLongAsDelegateHasNext() {
        // Given
        Iterator<Integer> delegateIterator = listWith(1, 2, 3).iterator();

        // When
        MappedIterator<Integer, String> iterator = new MappedIterator<Integer, String>(
                delegateIterator, stringValueMapFunction());

        // Then
        assertThat(iterator.hasNext(), is(true));
        iterator.next();
        assertThat(iterator.hasNext(), is(true));
        iterator.next();
        assertThat(iterator.hasNext(), is(true));
        iterator.next();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldMapTheValuesOfTheDelegateIteratorUsingTheSuppliedMapFunction() {
        // Given
        Iterator<Integer> delegateIterator = listWith(1, 2, 3).iterator();

        // When
        MappedIterator<Integer, String> iterator = new MappedIterator<Integer, String>(
                delegateIterator, stringValueMapFunction());

        // Then
        assertThat(iterator.next(), is("1"));
        assertThat(iterator.next(), is("2"));
        assertThat(iterator.next(), is("3"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionIfNoMoreElements() {
        // Given
        Iterator<Integer> delegateIterator = listWith(1, 2, 3).iterator();

        // When
        MappedIterator<Integer, String> iterator = new MappedIterator<Integer, String>(
                delegateIterator, stringValueMapFunction());
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotSupportRemovingElementsFromTheIterator() {
        // Given
        Iterator<Integer> delegateIterator = listWith(1, 2, 3).iterator();

        // When
        MappedIterator<Integer, String> iterator = new MappedIterator<Integer, String>(
                delegateIterator, stringValueMapFunction());
        iterator.remove();

        // Then an UnsupportedOperationException is thrown
    }

    private MapFunction<Integer, String> stringValueMapFunction() {
        return new MapFunction<Integer, String>() {
            public String map(Integer input) {
                return String.valueOf(input);
            }
        };
    }
}
