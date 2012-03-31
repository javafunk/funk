package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.Action;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Eager.times;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Lazy.cartesianProduct;
import static org.javafunk.funk.Lazy.cycle;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.matchers.Matchers.hasOnlyItemsInAnyOrder;

public class LazyCartesianProductTest {
    @Test
    public void shouldReturnTheCartesianProductOfTheSuppliedIterablesAsAnIterableOfTuples() throws Exception {
        // Given
        Iterable<Integer> input1 = listWith(1, 2, 3);
        Iterable<String> input2 = listWith("a", "b", "c");
        Collection<Pair<Integer, String>> expectedCartesianProduct = listWith(
                tuple(1, "a"), tuple(1, "b"), tuple(1, "c"),
                tuple(2, "a"), tuple(2, "b"), tuple(2, "c"),
                tuple(3, "a"), tuple(3, "b"), tuple(3, "c"));

        // When
        Collection<Pair<Integer, String>> actualCartesianProduct = materialize(Lazy.cartesianProduct(input1, input2));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input1 = listWith(1, 2, 3);
        Iterable<String> input2 = listWith("a", "b", "c");

        // When
        Iterable<Pair<Integer, String>> iterable = Lazy.cartesianProduct(input1, input2);

        Iterator<Pair<Integer, String>> iterator1 = iterable.iterator();
        Iterator<Pair<Integer, String>> iterator2 = iterable.iterator();

        // Then
        Assert.assertThat(iterator1.next(), is(tuple(1, "a")));
        Assert.assertThat(iterator1.next(), is(tuple(1, "b")));
        Assert.assertThat(iterator2.next(), is(tuple(1, "a")));
        Assert.assertThat(iterator2.next(), is(tuple(1, "b")));
        Assert.assertThat(iterator1.next(), is(tuple(1, "c")));
        Assert.assertThat(iterator2.next(), is(tuple(1, "c")));
        Assert.assertThat(iterator2.next(), is(tuple(2, "a")));
    }

    @Test
    public void shouldAllowSecondIterableToBeLongerThanFirst() throws Exception {
        // Given
        Iterable<Long> firstIterable = listWith(90L, 80L);
        Iterable<String> secondIterable = listWith("a", "b", "c", "d");
        Collection<Pair<Long, String>> expectedCartesianProduct = listWith(
                tuple(90L, "a"), tuple(90L, "b"), tuple(90L, "c"), tuple(90L, "d"),
                tuple(80L, "a"), tuple(80L, "b"), tuple(80L, "c"), tuple(80L, "d"));

        // When
        Iterable<Pair<Long, String>> actualCartesianProduct = cartesianProduct(firstIterable, secondIterable);

        // Then
        assertThat(materialize(actualCartesianProduct), hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldAllowInfiniteIterableInEitherSlot() throws Exception {
        // Given
        Iterable<Integer> firstIterable = cycle(listWith(1, 2, 3));
        Iterable<String> secondIterable = cycle(listWith("a", "b", "c"));

        // When
        final Iterator<Pair<Integer, String>> iterator = cartesianProduct(firstIterable, secondIterable).iterator();

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
        Iterator<Pair<String, Integer>> iterator = cartesianProduct(firstIterable, secondIterable).iterator();

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
        Iterator<Pair<String, Integer>> iterator = cartesianProduct(firstIterable, secondIterable).iterator();

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
        Iterator<Pair<String, Integer>> iterator = cartesianProduct(firstIterable, secondIterable).iterator();

        iterator.next();
        iterator.remove();

        // Then an UnsupportedOperationException is thrown
    }

}
