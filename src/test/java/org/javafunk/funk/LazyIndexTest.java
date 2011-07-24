package org.javafunk.funk;

import org.javafunk.funk.Lazy;
import org.javafunk.funk.Lazy;
import org.javafunk.funk.datastructures.TwoTuple;
import org.javafunk.funk.functors.Indexer;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Literals.tuple;

public class LazyIndexTest {
    @Test
    public void shouldReturnTwoTuplesWithTheIndexFirstAndTheElementSecond() throws Exception {
        // Given
        Iterable<String> input = listWith("apple", "pear", "lemon");

        // When
        Iterable<TwoTuple<Integer, String>> outputIterable = Lazy.index(input, new Indexer<String, Integer>(){
            public Integer index(String item) {
                return item.length();
            }
        });
        Iterator<TwoTuple<Integer,String>> outputIterator = outputIterable.iterator();

        // Then
        assertThat(outputIterator.hasNext(), is(true));
        assertThat(outputIterator.next(), is(tuple(5, "apple")));
        assertThat(outputIterator.hasNext(), is(true));
        assertThat(outputIterator.next(), is(tuple(4, "pear")));
        assertThat(outputIterator.hasNext(), is(true));
        assertThat(outputIterator.next(), is(tuple(5, "lemon")));
        assertThat(outputIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<String> input = listWith("apple", "pear", "lemon");

        // When
        Iterable<TwoTuple<Integer, String>> iterable = Lazy.index(input, new Indexer<String, Integer>() {
            public Integer index(String item) {
                return item.length();
            }
        });

        Iterator<TwoTuple<Integer, String>> iterator1 = iterable.iterator();
        Iterator<TwoTuple<Integer, String>> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(tuple(5, "apple")));
        assertThat(iterator1.next(), is(tuple(4, "pear")));
        assertThat(iterator2.next(), is(tuple(5, "apple")));
        assertThat(iterator1.next(), is(tuple(5, "lemon")));
        assertThat(iterator2.next(), is(tuple(4, "pear")));
        assertThat(iterator2.next(), is(tuple(5, "lemon")));
    }
}
