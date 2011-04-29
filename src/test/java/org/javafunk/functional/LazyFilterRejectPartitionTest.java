package org.javafunk.functional;

import org.junit.Test;
import org.javafunk.datastructures.TwoTuple;
import org.javafunk.functional.functors.PredicateFunction;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.javafunk.IterableUtils.materialize;
import static org.javafunk.Literals.listWith;

public class LazyFilterRejectPartitionTest {
    @Test
    public void shouldOnlyReturnThoseElementsMatchingTheSuppliedPredicate() {
        // Given
        List<String> inputs = listWith("ac", "ab", "bc", "abc", "bcd", "bad");
        Collection<String> expectedOutputs = listWith("ac", "bc", "abc", "bcd");

        // When
        Collection<String> actualOutputs = materialize(Lazy.filter(inputs, new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("c");
            }
        }));

        // Then
        assertThat(actualOutputs, is(expectedOutputs));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedFilterIterable() throws Exception {
        // Given
        List<String> inputs = listWith("ac", "ab", "bc", "abc", "bcd", "bad");

        // When
        Iterable<String> iterable = Lazy.filter(inputs, new PredicateFunction<String>() {
            public boolean matches(String item) {
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
        Collection<String> expectedOutputs = listWith("ab", "bad");

        // When
        Collection<String> actualOutputs = materialize(Lazy.reject(inputs, new PredicateFunction<String>() {
            public boolean matches(String item) {
                return item.contains("c");
            }
        }));

        // Then
        assertThat(actualOutputs, is(expectedOutputs));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedRejectIterable() throws Exception {
        // Given
        List<String> inputs = listWith("ac", "ab", "bc", "abc", "bcd", "bad", "gae");

        // When
        Iterable<String> iterable = Lazy.reject(inputs, new PredicateFunction<String>() {
            public boolean matches(String item) {
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
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8);
        Collection<Integer> expectedMatchingItems = listWith(2, 4, 6, 8);
        Collection<Integer> expectedNonMatchingItems = listWith(1, 3, 5, 7);

        // When
        TwoTuple<Iterable<Integer>, Iterable<Integer>> partitionResults = Lazy.partition(input,
                new PredicateFunction<Integer>(){
                    public boolean matches(Integer item) {
                        return isEven(item);
                    }

                    private boolean isEven(Integer item) {
                        return item % 2 == 0;
                    }
                });

        // Then
        Collection<Integer> actualMatchingItems = materialize(partitionResults.first());
        Collection<Integer> actualNonMatchingItems = materialize(partitionResults.second());
        
        assertThat(actualMatchingItems, is(expectedMatchingItems));
        assertThat(actualNonMatchingItems, is(expectedNonMatchingItems));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheMatchingAndNonMatchingIterables() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8);

        // When
        TwoTuple<Iterable<Integer>, Iterable<Integer>> partitionResult = Lazy.partition(input,
                new PredicateFunction<Integer>(){
                    public boolean matches(Integer item) {
                        return isEven(item);
                    }

                    private boolean isEven(Integer item) {
                        return item % 2 == 0;
                    }
                });
        Iterable<Integer> matchingIterable = partitionResult.first();
        Iterable<Integer> nonMatchingIterable = partitionResult.second();

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
