package org.javafunk.functional;

import org.javafunk.datastructures.TwoTuple;
import org.javafunk.functional.functors.Indexer;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.*;

public class EagerGroupIndexTest {
    @Test
    public void shouldGroupTheElementsOfTheIterableUsingTheSpecifiedIndexer() throws Exception {
        // Given
        Iterable<String> input = listWith("apple", "pear", "lemon", "apricot", "orange", "papaya", "banana");

        Collection<String> fourLetterFruits = listWith("pear");
        Collection<String> fiveLetterFruits = listWith("apple", "lemon");
        Collection<String> sixLetterFruits = listWith("orange", "papaya", "banana");
        Collection<String> sevenLetterFruits = listWith("apricot");
        
        Map<Integer, Collection<String>> expectedOutput =
                mapWith(4, fourLetterFruits)
                   .and(5, fiveLetterFruits)
                   .and(6, sixLetterFruits)
                   .and(7, sevenLetterFruits);

        // When
        Map<Integer, Collection<String>> actualOutput = Eager.group(input, new Indexer<String, Integer>() {
            public Integer index(String string) {
                return string.length();
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnATwoTupleWithTheIndexFirstAndTheElementSecond() throws Exception {
        // Given
        Iterable<String> input = listWith("apple", "pear", "lemon", "apricot", "orange", "papaya", "banana");
        Collection<TwoTuple<Integer, String>> expectedOutput = listWith(
                twoTuple(5, "apple"),
                twoTuple(4, "pear"),
                twoTuple(5, "lemon"),
                twoTuple(7, "apricot"),
                twoTuple(6, "orange"),
                twoTuple(6, "papaya"),
                twoTuple(6, "banana"));

        // When
        Collection<TwoTuple<Integer, String>> actualOutput = Eager.index(input, new Indexer<String, Integer>() {
            public Integer index(String item) {
                return item.length();
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
