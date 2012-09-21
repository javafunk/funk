/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.Predicate;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class LazilyFilterRejectPartitionTest {
    @Test
    public void shouldOnlyReturnThoseElementsMatchingTheSuppliedPredicate() {
        // Given
        List<String> inputs = listWith("ac", "ab", "bc", "abc", "bcd", "bad");
        Collection<String> expectedOutputs = collectionWith("ac", "bc", "abc", "bcd");

        // When
        Collection<String> actualOutputs = materialize(Lazily.filter(inputs, new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("c");
            }
        }));

        // Then
        assertThat(actualOutputs, hasOnlyItemsInOrder(expectedOutputs));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedFilterIterable() throws Exception {
        // Given
        List<String> inputs = listWith("ac", "ab", "bc", "abc", "bcd", "bad");

        // When
        Iterable<String> iterable = Lazily.filter(inputs, new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("c");
            }
        });
        Iterator<String> iterator1 = iterable.iterator();
        Iterator<String> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is("ac"));
        assertThat(iterator1.next(), is("bc"));
        assertThat(iterator2.next(), is("ac"));
        assertThat(iterator2.next(), is("bc"));
        assertThat(iterator2.next(), is("abc"));
        assertThat(iterator1.next(), is("abc"));
    }

    @Test
    public void shouldOnlyReturnThoseElementsThatDoNotMatchTheSuppliedPredicate() {
        // Given
        List<String> inputs = listWith("ac", "ab", "bc", "abc", "bcd", "bad");
        Collection<String> expectedOutputs = collectionWith("ab", "bad");

        // When
        Collection<String> actualOutputs = materialize(Lazily.reject(inputs, new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("c");
            }
        }));

        // Then
        assertThat(actualOutputs, hasOnlyItemsInOrder(expectedOutputs));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedRejectIterable() throws Exception {
        // Given
        List<String> inputs = listWith("ac", "ab", "bc", "abc", "bcd", "bad", "gae");

        // When
        Iterable<String> iterable = Lazily.reject(inputs, new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("c");
            }
        });
        Iterator<String> iterator1 = iterable.iterator();
        Iterator<String> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is("ab"));
        assertThat(iterator2.next(), is("ab"));
        assertThat(iterator1.next(), is("bad"));
        assertThat(iterator1.next(), is("gae"));
        assertThat(iterator2.next(), is("bad"));
        assertThat(iterator2.next(), is("gae"));
    }

    @Test
    public void shouldReturnMatchingElementsFirstAndNonMatchingElementsSecond() {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6, 7, 8);
        Collection<Integer> expectedMatchingItems = collectionWith(2, 4, 6, 8);
        Collection<Integer> expectedNonMatchingItems = collectionWith(1, 3, 5, 7);

        // When
        Pair<Iterable<Integer>, Iterable<Integer>> partitionResults = Lazily.partition(input,
                new Predicate<Integer>() {
                    public boolean evaluate(Integer item) {
                        return isEven(item);
                    }

                    private boolean isEven(Integer item) {
                        return item % 2 == 0;
                    }
                });

        // Then
        Collection<Integer> actualMatchingItems = materialize(partitionResults.getFirst());
        Collection<Integer> actualNonMatchingItems = materialize(partitionResults.getSecond());

        assertThat(actualMatchingItems, hasOnlyItemsInOrder(expectedMatchingItems));
        assertThat(actualNonMatchingItems, hasOnlyItemsInOrder(expectedNonMatchingItems));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheMatchingAndNonMatchingIterables() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6, 7, 8);

        // When
        Pair<Iterable<Integer>, Iterable<Integer>> partitionResult = Lazily.partition(input,
                new Predicate<Integer>() {
                    public boolean evaluate(Integer item) {
                        return isEven(item);
                    }

                    private boolean isEven(Integer item) {
                        return item % 2 == 0;
                    }
                });
        Iterable<Integer> matchingIterable = partitionResult.getFirst();
        Iterable<Integer> nonMatchingIterable = partitionResult.getSecond();

        Iterator<Integer> matchingIterator1 = matchingIterable.iterator();
        Iterator<Integer> matchingIterator2 = matchingIterable.iterator();

        Iterator<Integer> nonMatchingIterator1 = nonMatchingIterable.iterator();
        Iterator<Integer> nonMatchingIterator2 = nonMatchingIterable.iterator();

        // Then
        assertThat(matchingIterator1.next(), is(2));
        assertThat(matchingIterator1.next(), is(4));
        assertThat(matchingIterator2.next(), is(2));
        assertThat(matchingIterator1.next(), is(6));
        assertThat(matchingIterator2.next(), is(4));

        assertThat(nonMatchingIterator1.next(), is(1));
        assertThat(nonMatchingIterator2.next(), is(1));
        assertThat(nonMatchingIterator1.next(), is(3));
        assertThat(nonMatchingIterator1.next(), is(5));
        assertThat(nonMatchingIterator2.next(), is(3));
    }
}
