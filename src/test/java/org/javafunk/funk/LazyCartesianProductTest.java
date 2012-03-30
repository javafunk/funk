package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
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
}
