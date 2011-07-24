package org.javafunk.funk;

import org.javafunk.funk.Sequences;
import org.javafunk.funk.Sequences;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Sequences.increasing;
import static org.javafunk.funk.Sequences.decreasing;

public class SequencesTest {
    @Test
    public void shouldReturnASequenceOfIncreasingIntegersStartingAtZero() throws Exception {
        // Given
        Iterable<Integer> sequence = Sequences.integers(increasing());
        Collection<Integer> expectedOutput = new ArrayList<Integer>();
        Collection<Integer> actualOutput = new ArrayList<Integer>();

        // When
        Iterator<Integer> sequenceIterator = sequence.iterator();
        for (int i = 0; i <= 200; i++) {
            expectedOutput.add(i);
            actualOutput.add(sequenceIterator.next());
        }

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnIncreasingIntegersIterableReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> sequence = Sequences.integers(increasing());

        // When
        Iterator<Integer> firstIterator = sequence.iterator();
        Iterator<Integer> secondIterator = sequence.iterator();

        // Then
        assertThat(firstIterator.next(), is(0));
        assertThat(firstIterator.next(), is(1));
        assertThat(firstIterator.next(), is(2));
        assertThat(secondIterator.next(), is(0));
        assertThat(secondIterator.next(), is(1));
        assertThat(firstIterator.next(), is(3));
        assertThat(secondIterator.next(), is(2));
    }

    @Test
    public void shouldReturnASequenceOfIncreasingIntegersStartingAtTheSpecifiedValue() throws Exception {
        // Given
        Iterable<Integer> sequence = Sequences.integersFrom(-10, increasing());
        Collection<Integer> expectedOutput = new ArrayList<Integer>();
        Collection<Integer> actualOutput = new ArrayList<Integer>();

        // When
        Iterator<Integer> sequenceIterator = sequence.iterator();
        for (int i = -10; i <= 200; i++) {
            expectedOutput.add(i);
            actualOutput.add(sequenceIterator.next());
        }

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnIncreasingIntegersFromIterableReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> sequence = Sequences.integersFrom(15, increasing());

        // When
        Iterator<Integer> firstIterator = sequence.iterator();
        Iterator<Integer> secondIterator = sequence.iterator();

        // Then
        assertThat(firstIterator.next(), is(15));
        assertThat(firstIterator.next(), is(16));
        assertThat(firstIterator.next(), is(17));
        assertThat(secondIterator.next(), is(15));
        assertThat(secondIterator.next(), is(16));
        assertThat(firstIterator.next(), is(18));
        assertThat(secondIterator.next(), is(17));
    }

    @Test
    public void shouldReturnASequenceOfDecreasingIntegersStartingAtZero() throws Exception {
        // Given
        Iterable<Integer> sequence = Sequences.integers(decreasing());
        Collection<Integer> expectedOutput = new ArrayList<Integer>();
        Collection<Integer> actualOutput = new ArrayList<Integer>();

        // When
        Iterator<Integer> sequenceIterator = sequence.iterator();
        for (int i = 0; i >= -200; i--) {
            expectedOutput.add(i);
            actualOutput.add(sequenceIterator.next());
        }

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnDecreasingIntegersIterableReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> sequence = Sequences.integers(decreasing());

        // When
        Iterator<Integer> firstIterator = sequence.iterator();
        Iterator<Integer> secondIterator = sequence.iterator();

        // Then
        assertThat(firstIterator.next(), is(0));
        assertThat(firstIterator.next(), is(-1));
        assertThat(firstIterator.next(), is(-2));
        assertThat(secondIterator.next(), is(0));
        assertThat(secondIterator.next(), is(-1));
        assertThat(firstIterator.next(), is(-3));
        assertThat(secondIterator.next(), is(-2));
    }

    @Test
    public void shouldReturnASequenceOfDecreasingIntegersStartingAtTheSpecifiedValue() throws Exception {
        // Given
        Iterable<Integer> sequence = Sequences.integersFrom(-10, decreasing());
        Collection<Integer> expectedOutput = new ArrayList<Integer>();
        Collection<Integer> actualOutput = new ArrayList<Integer>();

        // When
        Iterator<Integer> sequenceIterator = sequence.iterator();
        for (int i = -10; i >= -200; i--) {
            expectedOutput.add(i);
            actualOutput.add(sequenceIterator.next());
        }

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnDecreasingIntegersFromIterableReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> sequence = Sequences.integersFrom(15, decreasing());

        // When
        Iterator<Integer> firstIterator = sequence.iterator();
        Iterator<Integer> secondIterator = sequence.iterator();

        // Then
        assertThat(firstIterator.next(), is(15));
        assertThat(firstIterator.next(), is(14));
        assertThat(firstIterator.next(), is(13));
        assertThat(secondIterator.next(), is(15));
        assertThat(secondIterator.next(), is(14));
        assertThat(firstIterator.next(), is(12));
        assertThat(secondIterator.next(), is(13));
    }
}
