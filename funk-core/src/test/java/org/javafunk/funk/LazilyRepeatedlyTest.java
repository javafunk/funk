package org.javafunk.funk;

import org.javafunk.funk.functors.functions.NullaryFunction;
import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.javafunk.funk.Literals.iterableWith;
import static org.junit.Assert.assertThat;

public class LazilyRepeatedlyTest {
    @Test
    public void shouldReturnALazyIterableWhichRepeatedlyCallsTheSuppliedFunctionForElements() {
        // Given
        final AtomicInteger callCount = new AtomicInteger();
        final NullaryFunction<Integer> function = new NullaryFunction<Integer>() {
            @Override public Integer call() {
                return callCount.getAndIncrement();
            }
        };

        // When
        Iterable<Integer> repeatedlyIterable = Lazily.repeatedly(function);
        Iterator<Integer> repeatedlyIterator = repeatedlyIterable.iterator();

        // Then
        for(int i = 0; i < 20; i++) {
            assertThat(repeatedlyIterator.next(), is(i));
        }
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnRepeatedlyIterableReturningDifferentButCoupledIterators() throws Exception {
        // Given
        final AtomicInteger callCount = new AtomicInteger();
        final NullaryFunction<Integer> function = new NullaryFunction<Integer>() {
            @Override public Integer call() {
                return callCount.getAndIncrement();
            }
        };

        // When
        Iterable<Integer> iterable = Lazily.repeatedly(function);

        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(0));
        assertThat(iterator1.next(), is(1));
        assertThat(iterator2.next(), is(2));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(4));
        assertThat(iterator1.next(), is(5));
        assertThat(iterator1, is(not(sameInstance(iterator2))));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSuppliedFunctionIsNull() {
        // Given
        NullaryFunction<Integer> function = null;

        // When
        Lazily.repeatedly(function);

        // Then a NullPointerException is thrown
    }
}