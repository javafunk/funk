package org.javafunk.funk.iterators;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.Action;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Eager.times;
import static org.javafunk.funk.Iterators.asList;
import static org.javafunk.funk.Lazy.cycle;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.matchers.Matchers.hasOnlyItemsInAnyOrder;

public class TwoCartesianProductIteratorTest {
    @Test
    public void shouldTakeTheCartesianProductOfTheSuppliedIterators() throws Exception {
        // Given
        Iterable<Integer> firstIterable = listWith(1, 2, 2, 3);
        Iterable<String> secondIterable = listWith("a", "b");
        Iterator<Pair<Integer, String>> iterator = new TwoCartesianProductIterator<Integer, String>(firstIterable, secondIterable);
        Collection<Pair<Integer, String>> expectedCartesianProduct = listWith(
                tuple(1, "a"), tuple(1, "b"),
                tuple(2, "a"), tuple(2, "b"),
                tuple(2, "a"), tuple(2, "b"),
                tuple(3, "a"), tuple(3, "b"));

        // When
        Collection<Pair<Integer, String>> actualCartesianProduct = asList(iterator);

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldAllowSecondIterableToBeLongerThanFirst() throws Exception {
        // Given
        Iterable<Long> firstIterable = listWith(90L, 80L);
        Iterable<String> secondIterable = listWith("a", "b", "c", "d");
        Iterator<Pair<Long, String>> iterator = new TwoCartesianProductIterator<Long, String>(firstIterable, secondIterable);
        Collection<Pair<Long, String>> expectedCartesianProduct = listWith(
                tuple(90L, "a"), tuple(90L, "b"), tuple(90L, "c"), tuple(90L, "d"),
                tuple(80L, "a"), tuple(80L, "b"), tuple(80L, "c"), tuple(80L, "d"));

        // When
        Collection<Pair<Long, String>> actualCartesianProduct = asList(iterator);

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldAllowInfiniteIterableInEitherSlot() throws Exception {
        // Given
        Iterable<Integer> firstIterable = cycle(listWith(1, 2, 3));
        Iterable<String> secondIterable = cycle(listWith("a", "b", "c"));

        // When
        final Iterator<Pair<Integer, String>> iterator = new TwoCartesianProductIterator<Integer, String>(firstIterable, secondIterable);

        // Then
        times(1000000, new Action<Integer>() {
            public void on(Integer input) {
                assertThat(iterator.hasNext(), is(true));
                assertThat(iterator.next(), is(tuple(1, "a")));
                assertThat(iterator.hasNext(), is(true));
                assertThat(iterator.next(), is(tuple(1, "b")));
                assertThat(iterator.hasNext(), is(true));
                assertThat(iterator.next(), is(tuple(1, "c")));
            }
        });
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutCallingHasNext() throws Exception {
        // Given
        Iterable<String> firstIterable = listWith("a", "b", "c");
        Iterable<Integer> secondIterable = listWith(1, 2, 3);

        // When
        Iterator<Pair<String, Integer>> iterator = new TwoCartesianProductIterator<String, Integer>(firstIterable, secondIterable);

        // Then
        assertThat(iterator.next(), is(tuple("a", 1)));
        assertThat(iterator.next(), is(tuple("a", 2)));
        assertThat(iterator.next(), is(tuple("a", 3)));
        assertThat(iterator.next(), is(tuple("b", 1)));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() throws Exception {
        // Given
        Iterable<String> firstIterable = listWith("a", "b", "c");
        Iterable<Integer> secondIterable = listWith(1, 2);

        // When
        Iterator<Pair<String, Integer>> iterator = new TwoCartesianProductIterator<String, Integer>(firstIterable, secondIterable);

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(tuple("a", 1)));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(tuple("a", 2)));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(tuple("b", 1)));
        assertThat(iterator.hasNext(), is(true));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowAnUnsupportedOperationExceptionWhenTryingToRemoveElements() throws Exception {
        // Given
        Iterable<String> firstIterable = listWith("a", "b");
        Iterable<Integer> secondIterable = listWith(1, 2, 3);

        // When
        Iterator<Pair<String, Integer>> iterator = new TwoCartesianProductIterator<String, Integer>(firstIterable, secondIterable);

        iterator.next();
        iterator.remove();

        // Then an UnsupportedOperationException is thrown
    }
}
