package org.javafunk.functional;

import org.hamcrest.Matchers;
import org.javafunk.functional.functors.Predicate;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.javafunk.Iterables.materialize;
import static org.javafunk.Literals.listWith;
import static org.junit.Assert.assertThat;

public class LazyTakeDropTest {
    @Test
    public void shouldReturnAnIterableContainingTheSpecifiedNumberOfElements() {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Collection<Integer> expectedOutput = listWith(1, 1, 2, 3, 5);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.take(fibonaccis, 5));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfTheNumberOfElementsToTakeIsZero() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Integer numberToTake = 0;

        // When
        Collection<Integer> actualOutput = materialize(Lazy.take(fibonaccis, numberToTake));

        // Then
        assertThat(actualOutput, is(Matchers.<Integer>empty()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheNumberOfElementsToTakeIsLessThanZero() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Integer numberToTake = -5;

        // When
        Lazy.take(fibonaccis, numberToTake);

        // Then an IllegalArgumentException is thrown.
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedTakeIterable() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);

        // When
        Iterable<Integer> iterable = Lazy.take(fibonaccis, 5);
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(1));
        assertThat(iterator1.next(), is(1));
        assertThat(iterator1.next(), is(2));
        assertThat(iterator2.next(), is(1));
        assertThat(iterator2.next(), is(1));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(2));
    }

    @Test
    public void shouldReturnAnIterableWithTheFirstNElementsDropped() {
        // Given
        List<Integer> tenFibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Collection<Integer> expectedOutput = listWith(8, 13, 21, 34, 55);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.drop(tenFibonaccis, 5));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnTheSuppliedIterableIfTheNumberOfElementsToDropIsZero() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Collection<Integer> expectedOutput = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Integer numberToTake = 0;

        // When
        Collection<Integer> actualOutput = materialize(Lazy.drop(fibonaccis, numberToTake));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheNumberOfElementsToDropIsLessThanZero() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Integer numberToDrop = -5;

        // When
        Lazy.drop(fibonaccis, numberToDrop);

        // Then an IllegalArgumentException is thrown.
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedDropIterable() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);

        // When
        Iterable<Integer> iterable = Lazy.drop(fibonaccis, 5);
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(8));
        assertThat(iterator1.next(), is(13));
        assertThat(iterator1.next(), is(21));
        assertThat(iterator2.next(), is(8));
        assertThat(iterator2.next(), is(13));
        assertThat(iterator1.next(), is(34));
        assertThat(iterator2.next(), is(21));
    }

    @Test
    public void shouldTakeElementsFromTheIterableWhileTheSuppliedPredicateIsTrue() {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8);
        Collection<Integer> expectedOutput = listWith(1, 2, 3, 4);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.takeWhile(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedTakeWhileIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8);

        // When
        Iterable<Integer> iterable = Lazy.takeWhile(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        });
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(1));
        assertThat(iterator1.next(), is(2));
        assertThat(iterator2.next(), is(1));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(2));
        assertThat(iterator1.next(), is(4));
    }

    @Test
    public void shouldTakeElementsFromTheIterableUntilTheSuppliedPredicateIsTrue() {
        // Given
        Iterable<Integer> input = listWith(8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(8, 7, 6, 5);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.takeUntil(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedTakeUntilIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Iterable<Integer> iterable = Lazy.takeUntil(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        });
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(8));
        assertThat(iterator1.next(), is(7));
        assertThat(iterator2.next(), is(8));
        assertThat(iterator1.next(), is(6));
        assertThat(iterator2.next(), is(7));
        assertThat(iterator1.next(), is(5));
    }

    @Test
    public void shouldDropElementsFromTheIterableWhileTheSuppliedPredicateIsTrue() throws Exception {
        // Given
        Iterable<Integer> input = listWith(8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(4, 3, 2, 1);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.dropWhile(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input > 4;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedDropWhileIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Iterable<Integer> iterable = Lazy.dropWhile(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input > 4;
            }
        });
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(4));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(4));
        assertThat(iterator1.next(), is(2));
        assertThat(iterator2.next(), is(3));
        assertThat(iterator1.next(), is(1));
    }

    @Test
    public void shouldDropElementsFromTheIterableUntilTheSuppliedPredicateIsTrue() throws Exception {
        // Given
        Iterable<Integer> input = listWith(8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(4, 3, 2, 1);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.dropUntil(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedDropUntilIterable() throws Exception {
        // Given
        Iterable<Integer> input = listWith(8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Iterable<Integer> iterable = Lazy.dropUntil(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        });
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(4));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(4));
        assertThat(iterator1.next(), is(2));
        assertThat(iterator2.next(), is(3));
        assertThat(iterator1.next(), is(1));
    }
}
