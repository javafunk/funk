package org.javafunk;

import org.javafunk.collections.Bag;
import org.javafunk.collections.HashBag;
import org.javafunk.datastructures.FourTuple;
import org.javafunk.datastructures.ThreeTuple;
import org.javafunk.datastructures.TwoTuple;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.*;
import static org.junit.Assert.assertThat;

public class LiteralsTest {
    @Test
    public void shouldReturnAListContainingTheSuppliedElements() {
        // Given
        List<Integer> expectedList = asList(5, 10, 15);

        // When
        List<Integer> actualList = listWith(5, 10, 15);

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldAllowMoreElementsToBeAddedToTheListWithAnd() {
        // Given
        List<Integer> expectedList = asList(5, 10, 15, 20, 25, 30);

        // When
        List<Integer> actualList = listWith(5, 10, 15).and(20, 25, 30);

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheListWithAnd() throws Exception {
        // Given
        List<Integer> expectedList = asList(1, 2, 3, 4, 5, 6);
        Iterable<Integer> someOtherElements = asList(4, 5, 6);

        // When
        List<Integer> actualList = listWith(1, 2, 3).and(someOtherElements);

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldAllowElementsToBeAddedToAListWithWith() throws Exception {
        // Given
        List<Integer> expectedList = asList(1, 2, 3, 4, 5, 6);

        // When
        List<Integer> actualList = listOf(Integer.class)
                .with(1, 2, 3)
                .with(4, 5, 6)
                .build();

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToAListWithWith() throws Exception {
        // Given
        List<Integer> expectedList = asList(1, 2, 3, 4, 5, 6);
        Iterable<Integer> firstInputIterable = asList(1, 2, 3);
        Iterable<Integer> secondInputIterable = asList(4, 5, 6);

        // When
        List<Integer> actualList = listOf(Integer.class)
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldReturnAListContainingAllElementsInTheSuppliedIterable() throws Exception {
        // Given
        List<Integer> expectedList = asList(1, 2, 3, 4, 5, 6);
        Iterable<Integer> firstInputIterable = asList(1, 2, 3);
        Iterable<Integer> secondInputIterable = asList(4, 5, 6);

        // When
        List<Integer> actualList = listFrom(firstInputIterable).with(secondInputIterable);

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldReturnABagContainingTheSuppliedElements() {
        // Given
        Bag<String> expectedBag = new HashBag<String>(listWith("a", "a", "b", "c"));

        // When
        Bag<String> actualBag = bagWith("a", "a", "b", "c");

        // Then
        assertThat(actualBag, is(expectedBag));
    }

    @Test
    public void shouldAllowMoreElementsToBeAddedToTheBagWithAnd() {
        // Given
        Bag<String> expectedBag = new HashBag<String>(listWith("a", "a", "b", "c", "c", "d"));

        // When
        Bag<String> actualBag = bagWith("a", "a", "b").and("c", "c", "d");

        // Then
        assertThat(actualBag, is(expectedBag));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheBagWithAnd() throws Exception {
        // Given
        Bag<String> expectedBag = new HashBag<String>(listWith("a", "a", "b", "c"));
        Iterable<String> someOtherElements = listWith("b", "c");

        // When
        Bag<String> actualBag = bagWith("a", "a").and(someOtherElements);

        // Then
        assertThat(actualBag, is(expectedBag));
    }

    @Test
    public void shouldAllowElementsToBeAddedToABagWithWith() throws Exception {
        // Given
        Bag<Integer> expectedBag = new HashBag<Integer>(asList(1, 1, 2, 2, 3, 3));

        // When
        Bag<Integer> actualBag = bagOf(Integer.class)
                .with(1, 1, 2)
                .with(2, 3, 3)
                .build();

        // Then
        assertThat(actualBag, is(expectedBag));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToABagWithWith() throws Exception {
        // Given
        Bag<Integer> expectedbag = new HashBag<Integer>(asList(1, 2, 3, 4, 5, 6));
        Iterable<Integer> firstInputIterable = asList(1, 2, 3);
        Iterable<Integer> secondInputIterable = asList(4, 5, 6);

        // When
        Bag<Integer> actualBag = bagOf(Integer.class)
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(actualBag, is(expectedbag));
    }

    @Test
    public void shouldReturnABagContainingAllElementsInTheSuppliedIterable() throws Exception {
        // Given
        Bag<Integer> expectedBag = new HashBag<Integer>(asList(1, 2, 3, 4, 5, 6));
        Iterable<Integer> firstInputIterable = asList(1, 2, 3);
        Iterable<Integer> secondInputIterable = asList(4, 5, 6);

        // When
        Bag<Integer> actualBag = bagFrom(firstInputIterable).with(secondInputIterable);

        // Then
        assertThat(actualBag, is(expectedBag));
    }

    @Test
    public void shouldReturnASetContainingTheSuppliedElements() {
        // Given
        Set<String> expectedSet = new HashSet<String>(listWith("a", "b"));

        // When
        Set<String> actualSet = setWith("a", "a", "b");

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldAllowMoreElementsToBeAddedToTheSetWithAnd() {
        // Given
        Set<String> expectedSet = new HashSet<String>(listWith("a", "b", "c"));

        // When
        Set<String> actualSet = setWith("a", "a", "b").and("b", "c", "c");

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheSetWithAnd() throws Exception {
        // Given
        Set<String> expectedSet = new HashSet<String>(listWith("a", "b", "c"));
        Iterable<String> someOtherElements = listWith("b", "c", "c");

        // When
        Set<String> actualSet = setWith("a", "b").and(someOtherElements);

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldAllowElementsToBeAddedToASetWithWith() throws Exception {
        // Given
        Set<String> expectedSet = new HashSet<String>(listWith("a", "b", "c"));

        // When
        Set<String> actualSet = setOf(String.class)
                .with("a", "a")
                .with("b", "c")
                .build();

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToASetWithWith() throws Exception {
        // Given
        Set<String> expectedSet = new HashSet<String>(listWith("a", "b", "c"));
        Iterable<String> firstInputIterable = listWith("a", "a");
        Iterable<String> secondInputIterable = listWith("b", "c", "c");

        // When
        Set<String> actualSet = setOf(String.class)
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(actualSet, is(expectedSet));
    }


    @Test
    public void shouldReturnASetContainingAllElementsInTheSuppliedIterable() throws Exception {
        // Given
        Set<String> expectedSet = new HashSet<String>(listWith("a", "b", "c"));
        Iterable<String> firstInputIterable = listWith("a", "a");
        Iterable<String> secondInputIterable = listWith("b", "c", "c");

        // When
        Set<String> actualSet = setFrom(firstInputIterable).with(secondInputIterable);

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldReturnAMapContainingTheSuppliedKeyAndValue() {
        // Given
        Map<Integer, String> expectedMap = new HashMap<Integer, String>();
        expectedMap.put(5, "a");

        // When
        Map<Integer, String> actualMap = mapWith(5, "a");

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldAllowMoreKeysAndValuesToBeAddedToTheMapWithAnd() {
        // Given
        Map<Integer, String> expectedMap = new HashMap<Integer, String>();
        expectedMap.put(5, "a");
        expectedMap.put(10, "b");
        expectedMap.put(15, "c");

        // When
        Map<Integer, String> actualMap = mapWith(5, "a").and(10, "b").and(15, "c");

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldAllowKeysAndValuesToBeAddedToAMapWithWith() throws Exception {
        // Given
        Map<Integer, String> expectedMap = new HashMap<Integer, String>();
        expectedMap.put(5, "a");
        expectedMap.put(10, "b");
        expectedMap.put(15, "c");

        // When
        Map<Integer, String> actualMap = mapOf(Integer.class, String.class)
                .with(5, "a")
                .with(10, "b")
                .with(15, "c")
                .build();

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldReturnATwoTupleContainingTheSuppliedElements() {
        // Given
        TwoTuple<Integer, String> expectedTwoTuple = new TwoTuple<Integer, String>(5, "Five");

        // When
        TwoTuple<Integer, String> actualTwoTuple = tuple(5, "Five");

        // Then
        assertThat(actualTwoTuple, is(expectedTwoTuple));
    }

    @Test
    public void shouldReturnAThreeTupleContainingTheSuppliedElements() {
        // Given
        ThreeTuple<Integer, String, Boolean> expectedThreeTuple =
                new ThreeTuple<Integer, String, Boolean>(5, "Five", true);

        // When
        ThreeTuple<Integer, String, Boolean> actualThreeTuple = tuple(5, "Five", true);

        // Then
        assertThat(actualThreeTuple, is(expectedThreeTuple));
    }

    @Test
    public void shouldReturnAFourTupleContainingTheSuppliedElements() {
        // Given
        FourTuple<Integer, String, Boolean, Double> expectedFourTuple =
                new FourTuple<Integer, String, Boolean, Double>(5, "Five", true, 1.6);

        // When
        FourTuple<Integer, String, Boolean, Double> actualFourTuple = tuple(5, "Five", true, 1.6);

        // Then
        assertThat(actualFourTuple, is(expectedFourTuple));
    }
}
