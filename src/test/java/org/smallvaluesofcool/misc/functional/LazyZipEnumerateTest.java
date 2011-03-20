package org.smallvaluesofcool.misc.functional;

import org.junit.Test;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.smallvaluesofcool.misc.IterableUtils.toList;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.Literals.twoTuple;
import static org.smallvaluesofcool.misc.matchers.Matchers.hasOnlyItemsInOrder;

public class LazyZipEnumerateTest {
    @Test
    public void shouldEnumerateSequence() {
        // Given
        Iterable<String> iterable = listWith("A", "B", "C");
        Collection<TwoTuple<Integer, String>> expected = listWith(twoTuple(0, "A"), twoTuple(1, "B"), twoTuple(2, "C"));

        // When
        Iterable<TwoTuple<Integer, String>> actual = Lazy.enumerate(iterable);

        // Then
        assertThat(toList(actual), hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedEnumeratedIterable() throws Exception {
        // Given
        Iterable<String> input = listWith("A", "B", "C");

        // When
        Iterable<TwoTuple<Integer, String>> iterable = Lazy.enumerate(input);
        Iterator<TwoTuple<Integer, String>> iterator1 = iterable.iterator();
        Iterator<TwoTuple<Integer, String>> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator2.next(), is(twoTuple(0, "A")));
        assertThat(iterator2.next(), is(twoTuple(1, "B")));
        assertThat(iterator1.next(), is(twoTuple(0, "A")));
        assertThat(iterator2.next(), is(twoTuple(2, "C")));
        assertThat(iterator1.next(), is(twoTuple(1, "B")));
        assertThat(iterator1.next(), is(twoTuple(2, "C")));
    }

    @Test
    public void shouldZipIterables() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C");
        Iterable<Integer> iterable2 = listWith(1, 2, 3);

        Collection<TwoTuple<String, Integer>> expected = listWith(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Collection<TwoTuple<String, Integer>> actual = toList(Lazy.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterablesWithLongerFirstIterable() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C", "D");
        Iterable<Integer> iterable2 = listWith(1, 2, 3);
        Collection<TwoTuple<String, Integer>> expected = listWith(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Collection<TwoTuple<String, Integer>> actual = toList(Lazy.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterablesWithShorterFirstIterable() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C");
        Iterable<Integer> iterable2 = listWith(1, 2, 3, 4);
        Collection<TwoTuple<String, Integer>> expected = listWith(twoTuple("A", 1), twoTuple("B", 2), twoTuple("C", 3));

        // When
        Iterable<TwoTuple<String, Integer>> actual = Lazy.zip(iterable1, iterable2);

        // Then
        assertThat(toList(actual), hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedZippedIterable() throws Exception {
        // Given
        Iterable<String> inputIterable1 = listWith("A", "B", "C");
        Iterable<Integer> inputIterable2 = listWith(1, 2, 3);

        // When
        Iterable<TwoTuple<String, Integer>> outputIterable = Lazy.zip(inputIterable1, inputIterable2);
        Iterator<TwoTuple<String, Integer>> firstIterator = outputIterable.iterator();
        Iterator<TwoTuple<String, Integer>> secondIterator = outputIterable.iterator();

        // Then
        assertThat(secondIterator.next(), is(twoTuple("A", 1)));
        assertThat(secondIterator.next(), is(twoTuple("B", 2)));
        assertThat(firstIterator.next(), is(twoTuple("A", 1)));
        assertThat(secondIterator.next(), is(twoTuple("C", 3)));
        assertThat(firstIterator.next(), is(twoTuple("B", 2)));
    }
}
