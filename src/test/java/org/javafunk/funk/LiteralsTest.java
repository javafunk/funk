/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.javafunk.funk.builders.*;
import org.javafunk.funk.datastructures.tuples.*;
import org.javafunk.funk.testclasses.*;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.builders.IterableBuilder.iterableBuilder;
import static org.javafunk.funk.testclasses.Age.age;
import static org.javafunk.funk.testclasses.Animal.animal;
import static org.javafunk.funk.testclasses.Cat.cat;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Dog.dog;
import static org.javafunk.funk.testclasses.Location.location;
import static org.javafunk.funk.testclasses.Name.name;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.fail;

public class LiteralsTest {
    @Test public void shouldReturnAnEmptyIterable() throws Exception {
        // Given
        Iterable<Integer> expected = new ArrayList<Integer>();

        // When
        Iterable<Integer> actual = iterable();

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test public void shouldReturnAnEmptyIterableWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Iterable<Integer> expected = new ArrayList<Integer>();

        // Then
        assertThat(iterableOf(Integer.class), hasOnlyItemsInOrder(expected));
    }

    @Test public void shouldReturnAnIterableContainingTheSuppliedElements() {
        // Given
        Iterable<Integer> expected = asList(5, 10, 15);

        // When
        Iterable<Integer> actual = iterableWith(5, 10, 15);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test public void shouldReturnAnIterableContainingAllElementsInTheSuppliedIterable() {
        // Given
        Iterable<Integer> expected = asList(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        Iterable<Integer> actual = iterableFrom(elements);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test public void shouldReturnAnIterableContainingAllElementsInTheSuppliedArray() {
        // Given
        Iterable<Integer> expected = asList(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        Iterable<Integer> actual = iterableFrom(elements);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test public void shouldReturnAnEmptyIterableBuilder() throws Exception {
        // Given
        IterableBuilder<Integer> expected = new IterableBuilder<Integer>();

        // When
        IterableBuilder<Integer> actual = iterableBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyIterableBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        IterableBuilder<Integer> expected = new IterableBuilder<Integer>();

        // Then
        assertThat(iterableBuilderOf(Integer.class), is(expected));
    }

    @Test public void shouldReturnAnIterableBuilderWithTheSuppliedElements() {
        // Given
        IterableBuilder<Integer> expected = new IterableBuilder<Integer>().with(5, 10, 15);

        // When
        IterableBuilder<Integer> actual = iterableBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnIterableBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        IterableBuilder<Integer> expected = new IterableBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        IterableBuilder<Integer> actual = iterableBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnIterableBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        IterableBuilder<Integer> expected = new IterableBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        IterableBuilder<Integer> actual = iterableBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyIterator() throws Exception {
        // Given
        Iterator<Integer> expected = new ArrayList<Integer>().iterator();

        // When
        Iterator<Integer> actual = iterator();

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test public void shouldReturnAnEmptyIteratorWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Iterator<Integer> expected = new ArrayList<Integer>().iterator();

        // Then
        assertThat(Iterators.asList(iteratorOf(Integer.class)), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test public void shouldReturnAnIteratorContainingTheSuppliedElements() {
        // Given
        Iterator<Integer> expected = asList(5, 10, 15).iterator();

        // When
        Iterator<Integer> actual = iteratorWith(5, 10, 15);

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test public void shouldReturnAnIteratorContainingAllElementsInTheSuppliedIterable() {
        // Given
        Iterator<Integer> expected = asList(5, 10, 15).iterator();
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        Iterator<Integer> actual = iteratorFrom(elements);

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test public void shouldReturnAnIteratorContainingAllElementsInTheSuppliedArray() {
        // Given
        Iterator<Integer> expected = asList(5, 10, 15).iterator();
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        Iterator<Integer> actual = iteratorFrom(elements);

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test public void shouldReturnAnEmptyIteratorBuilder() throws Exception {
        // Given
        IteratorBuilder<Integer> expected = new IteratorBuilder<Integer>();

        // When
        IteratorBuilder<Integer> actual = iteratorBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyIteratorBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        IteratorBuilder<Integer> expected = new IteratorBuilder<Integer>();

        // Then
        assertThat(iteratorBuilderOf(Integer.class), is(expected));
    }

    @Test public void shouldReturnAnIteratorBuilderWithTheSuppliedElements() {
        // Given
        IteratorBuilder<Integer> expected = new IteratorBuilder<Integer>().with(5, 10, 15);

        // When
        IteratorBuilder<Integer> actual = iteratorBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnIteratorBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        IteratorBuilder<Integer> expected = new IteratorBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        IteratorBuilder<Integer> actual = iteratorBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnIteratorBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        IteratorBuilder<Integer> expected = new IteratorBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        IteratorBuilder<Integer> actual = iteratorBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyCollection() throws Exception {
        // Given
        Collection<Integer> expected = new ArrayList<Integer>();

        // When
        Collection<Integer> actual = collection();

        // Then
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
    }

    @Test public void shouldReturnAnEmptyCollectionWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Collection<Integer> expected = new ArrayList<Integer>();

        // Then
        assertThat(collectionOf(Integer.class), hasOnlyItemsInAnyOrder(expected));
    }

    @Test public void shouldReturnAnCollectionContainingTheSuppliedElements() {
        // Given
        Collection<Integer> expected = asList(5, 10, 15);

        // When
        Collection<Integer> actual = collectionWith(5, 10, 15);

        // Then
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
    }

    @Test public void shouldReturnAnCollectionContainingAllElementsInTheSuppliedIterable() {
        // Given
        Collection<Integer> expected = asList(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        Collection<Integer> actual = collectionFrom(elements);

        // Then
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
    }

    @Test public void shouldReturnAnCollectionContainingAllElementsInTheSuppliedArray() {
        // Given
        Collection<Integer> expected = asList(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        Collection<Integer> actual = collectionFrom(elements);

        // Then
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
    }

    @Test public void shouldReturnAnEmptyCollectionBuilder() throws Exception {
        // Given
        CollectionBuilder<Integer> expected = new CollectionBuilder<Integer>();

        // When
        CollectionBuilder<Integer> actual = collectionBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyCollectionBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        CollectionBuilder<Integer> expected = new CollectionBuilder<Integer>();

        // Then
        assertThat(collectionBuilderOf(Integer.class), is(expected));
    }

    @Test public void shouldReturnAnCollectionBuilderWithTheSuppliedElements() {
        // Given
        CollectionBuilder<Integer> expected = new CollectionBuilder<Integer>().with(5, 10, 15);

        // When
        CollectionBuilder<Integer> actual = collectionBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnCollectionBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        CollectionBuilder<Integer> expected = new CollectionBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        CollectionBuilder<Integer> actual = collectionBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnCollectionBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        CollectionBuilder<Integer> expected = new CollectionBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        CollectionBuilder<Integer> actual = collectionBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyList() throws Exception {
        // Given
        List<Integer> expected = new ArrayList<Integer>();

        // When
        List<Integer> actual = list();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyListWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        List<Integer> expected = new ArrayList<Integer>();

        // Then
        assertThat(listOf(Integer.class), is(expected));
    }

    @Test public void shouldReturnAListContainingTheSuppliedElements() {
        // Given
        List<Integer> expectedList = asList(5, 10, 15);

        // When
        List<Integer> actualList = listWith(5, 10, 15);

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test public void shouldReturnAListContainingAllElementsInTheSuppliedIterable() {
        // Given
        List<Integer> expected = asList(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        List<Integer> actual = listFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAListContainingAllElementsInTheSuppliedArray() {
        // Given
        List<Integer> expected = asList(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        List<Integer> actual = listFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyListBuilder() throws Exception {
        // Given
        ListBuilder<Integer> expected = new ListBuilder<Integer>();

        // When
        ListBuilder<Integer> actual = listBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyListBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        ListBuilder<Integer> expected = new ListBuilder<Integer>();

        // Then
        assertThat(listBuilderOf(Integer.class), is(expected));
    }

    @Test public void shouldReturnAListBuilderWithTheSuppliedElements() {
        // Given
        ListBuilder<Integer> expected = new ListBuilder<Integer>().with(5, 10, 15);

        // When
        ListBuilder<Integer> actual = listBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAListBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        ListBuilder<Integer> expected = new ListBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        ListBuilder<Integer> actual = listBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAListBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        ListBuilder<Integer> expected = new ListBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        ListBuilder<Integer> actual = listBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyMultiset() throws Exception {
        // Given
        Multiset<Integer> expected = HashMultiset.create();

        // When
        Multiset<Integer> actual = multiset();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyMultisetWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Multiset<Integer> expected = HashMultiset.create();

        // Then
        assertThat(multisetOf(Integer.class), is(expected));
    }

    @Test public void shouldReturnAMultisetContainingTheSuppliedElements() {
        // Given
        Multiset<Integer> expectedMultiset = HashMultiset.create(asList(5, 10, 15));

        // When
        Multiset<Integer> actualMultiset = multisetWith(5, 10, 15);

        // Then
        assertThat(actualMultiset, is(expectedMultiset));
    }

    @Test public void shouldReturnAMultisetContainingAllElementsInTheSuppliedIterable() {
        // Given
        Multiset<Integer> expected = HashMultiset.create(asList(5, 10, 15));
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        Multiset<Integer> actual = multisetFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMultisetContainingAllElementsInTheSuppliedArray() {
        // Given
        Multiset<Integer> expected = HashMultiset.create(asList(5, 10, 15));
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        Multiset<Integer> actual = multisetFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyMultisetBuilder() throws Exception {
        // Given
        MultisetBuilder<Integer> expected = new MultisetBuilder<Integer>();

        // When
        MultisetBuilder<Integer> actual = multisetBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyMultisetBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        MultisetBuilder<Integer> expected = new MultisetBuilder<Integer>();

        // Then
        assertThat(multisetBuilderOf(Integer.class), is(expected));
    }

    @Test public void shouldReturnAMultisetBuilderWithTheSuppliedElements() {
        // Given
        MultisetBuilder<Integer> expected = new MultisetBuilder<Integer>().with(5, 10, 15);

        // When
        MultisetBuilder<Integer> actual = multisetBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMultisetBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        MultisetBuilder<Integer> expected = new MultisetBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        MultisetBuilder<Integer> actual = multisetBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMultisetBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        MultisetBuilder<Integer> expected = new MultisetBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        MultisetBuilder<Integer> actual = multisetBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptySet() throws Exception {
        // Given
        Set<Integer> expected = new HashSet<Integer>();

        // When
        Set<Integer> actual = set();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptySetWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Set<Integer> expected = new HashSet<Integer>();

        // Then
        assertThat(setOf(Integer.class), is(expected));
    }

    @Test public void shouldReturnASetContainingTheSuppliedElements() {
        // Given
        Set<Integer> expectedSet = new HashSet<Integer>(asList(5, 10, 15));

        // When
        Set<Integer> actualSet = setWith(5, 10, 15);

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test public void shouldReturnASetContainingAllElementsInTheSuppliedIterable() {
        // Given
        Set<Integer> expected = new HashSet<Integer>(asList(5, 10, 15));
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        Set<Integer> actual = setFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnASetContainingAllElementsInTheSuppliedArray() {
        // Given
        Set<Integer> expected = new HashSet<Integer>(asList(5, 10, 15));
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        Set<Integer> actual = setFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptySetBuilder() throws Exception {
        // Given
        SetBuilder<Integer> expected = new SetBuilder<Integer>();

        // When
        SetBuilder<Integer> actual = setBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptySetBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        SetBuilder<Integer> expected = new SetBuilder<Integer>();

        // Then
        assertThat(setBuilderOf(Integer.class), is(expected));
    }

    @Test public void shouldReturnASetBuilderWithTheSuppliedElements() {
        // Given
        SetBuilder<Integer> expected = new SetBuilder<Integer>().with(5, 10, 15);

        // When
        SetBuilder<Integer> actual = setBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnASetBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        SetBuilder<Integer> expected = new SetBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        SetBuilder<Integer> actual = setBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnASetBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        SetBuilder<Integer> expected = new SetBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        SetBuilder<Integer> actual = setBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyMap() throws Exception {
        // Given
        Map<String, Integer> expected = new HashMap<String, Integer>();

        // When
        Map<String, Integer> actual = map();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyMapWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Map<String, Integer> expected = new HashMap<String, Integer>();

        // Then
        assertThat(mapOf(String.class, Integer.class), is(expected));
    }

    @Test public void shouldReturnAMapContainingTheSuppliedMapEntries() {
        // Given
        Map<Integer, Boolean> expectedMap = new HashMap<Integer, Boolean>();
        expectedMap.put(1, true);
        expectedMap.put(2, false);

        // When
        Map<Integer, Boolean> actualMap = mapWith(mapEntryFor(1, true), mapEntryFor(2, false));

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test public void shouldReturnAMapContainingTheSuppliedTuples() throws Exception {
        // Given
        Map<Integer, Boolean> expectedMap = new HashMap<Integer, Boolean>();
        expectedMap.put(1, true);
        expectedMap.put(2, false);
        expectedMap.put(3, true);

        // When
        Map<Integer, Boolean> actualMap = mapWith(tuple(1, true), tuple(2, false), tuple(3, true));

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test public void shouldReturnAMapContainingAllElementsInTheSuppliedIterableOfMapEntryInstances() {
        // Given
        Map<Integer, Boolean> expected = new HashMap<Integer, Boolean>();
        expected.put(1, false);
        expected.put(2, false);
        Iterable<Map.Entry<Integer, Boolean>> elements = listWith(mapEntryFor(1, false), mapEntryFor(2, false));

        // When
        Map<Integer, Boolean> actual = mapFromEntries(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMapContainingAllElementsInTheSuppliedIterableOfTupleInstances() {
        // Given
        Map<Integer, Boolean> expected = new HashMap<Integer, Boolean>();
        expected.put(1, false);
        expected.put(2, false);
        Iterable<? extends Pair<Integer, Boolean>> tuples = iterableWith(tuple(1, false), tuple(2, false));

        // When
        Map<Integer, Boolean> actual = mapFromPairs(tuples);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMapContainingAllElementsInTheSuppliedArrayOfMapEntryInstances() {
        // Given
        Map<Integer, Boolean> expected = new HashMap<Integer, Boolean>();
        expected.put(1, false);
        expected.put(2, true);
        @SuppressWarnings("unchecked") Map.Entry<Integer, Boolean>[] elements = new Map.Entry[]{
                mapEntryFor(1, false),
                mapEntryFor(2, true)};

        // When
        Map<Integer, Boolean> actual = mapFromEntries(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMapContainingAllElementsInTheSuppliedArrayOfTupleInstances() {
        // Given
        Map<Integer, Boolean> expected = new HashMap<Integer, Boolean>();
        expected.put(1, false);
        expected.put(2, true);
        @SuppressWarnings("unchecked") Pair<Integer, Boolean>[] tuples = new Pair[]{
                tuple(1, false),
                tuple(2, true)};

        // When
        Map<Integer, Boolean> actual = mapFromPairs(tuples);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyMapBuilder() throws Exception {
        // Given
        MapBuilder<String, Integer> expected = new MapBuilder<String, Integer>();

        // When
        MapBuilder<String, Integer> actual = mapBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyMapBuilderWithKeysAndValuesOfTheSpecifiedType() throws Exception {
        // Given
        MapBuilder<String, Integer> expected = new MapBuilder<String, Integer>();

        // Then
        assertThat(mapBuilderOf(String.class, Integer.class), is(expected));
    }

    @Test public void shouldReturnAMapBuilderWithTheSuppliedElements() {
        // Given
        MapBuilder<String, Integer> expected = new MapBuilder<String, Integer>().withKeyValuePairs("five", 5, "ten", 10);

        // When
        MapBuilder<String, Integer> actual = mapBuilderWithKeyValuePairs("five", 5, "ten", 10);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMapBuilderWithTheSuppliedMapEntryInstances() {
        // Given
        MapBuilder<String, Integer> expected = new MapBuilder<String, Integer>()
                .withKeyValuePairs("five", 5, "ten", 10);

        // When
        MapBuilder<String, Integer> actual = mapBuilderWith(mapEntryFor("five", 5), mapEntryFor("ten", 10));

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMapBuilderWithTheSuppliedTupleInstances() {
        // Given
        MapBuilder<String, Integer> expected = new MapBuilder<String, Integer>()
                .withKeyValuePairs("five", 5, "ten", 10, "fifteen", 15);

        // When
        MapBuilder<String, Integer> actual = mapBuilderWith(tuple("five", 5), tuple("ten", 10), tuple("fifteen", 15));

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMapBuilderContainingAllMapEntryInstancesInTheSuppliedIterable() {
        // Given
        MapBuilder<Integer, Boolean> expected = new MapBuilder<Integer, Boolean>().withKeyValuePairs(5, true, 10, false);
        Iterable<Map.Entry<Integer, Boolean>> elements = iterableWith(
                mapEntryFor(5, true), mapEntryFor(10, false));

        // When
        MapBuilder<Integer, Boolean> actual = mapBuilderFromEntries(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMapBuilderContainingAllTupleInstancesInTheSuppliedIterable() {
        // Given
        MapBuilder<Integer, Boolean> expected = new MapBuilder<Integer, Boolean>().withKeyValuePairs(5, true, 10, false);
        Iterable<? extends Pair<Integer, Boolean>> tuples = iterableWith(
                tuple(5, true), tuple(10, false));

        // When
        MapBuilder<Integer, Boolean> actual = mapBuilderFromPairs(tuples);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMapBuilderContainingAllMapEntryInstancesInTheSuppliedArray() {
        // Given
        MapBuilder<Integer, Boolean> expected = new MapBuilder<Integer, Boolean>().withKeyValuePairs(5, true, 10, false);
        @SuppressWarnings("unchecked") Map.Entry<Integer, Boolean>[] elements = new Map.Entry[]{
                mapEntryFor(5, true),
                mapEntryFor(10, false)
        };

        // When
        MapBuilder<Integer, Boolean> actual = mapBuilderFromEntries(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMapBuilderContainingAllTupleInstancesInTheSuppliedArray() {
        // Given
        MapBuilder<Integer, Boolean> expected = new MapBuilder<Integer, Boolean>().withKeyValuePairs(5, true, 10, false);
        @SuppressWarnings("unchecked") Pair<Integer, Boolean>[] tuples = new Pair[]{
                tuple(5, true),
                tuple(10, false)
        };

        // When
        MapBuilder<Integer, Boolean> actual = mapBuilderFromPairs(tuples);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAMapEntryWithTheSpecifiedKeyAndValue() throws Exception {
        // Given
        String key = "key";
        Integer value = 36;

        // When
        Map.Entry<String, Integer> mapEntry = mapEntryFor(key, value);

        // Then
        assertThat(mapEntry.getKey(), is(key));
        assertThat(mapEntry.getValue(), is(value));
    }

    @Test public void shouldReturnAMapEntryWithKeyAndValueTakenFromTheSpecifiedTuple() throws Exception {
        // Given
        Pair<String, String> keyValuePair = tuple("key", "value");

        // When
        Map.Entry<String, String> mapEntry = mapEntryFor(keyValuePair);

        // Then
        assertThat(mapEntry.getKey(), is("key"));
        assertThat(mapEntry.getValue(), is("value"));
    }

    @Test public void shouldReturnAnEmptyArrayWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Integer[] expected = new Integer[]{};

        // When
        Integer[] actual = arrayOf(Integer.class);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnArrayContainingTheSuppliedElements() {
        // Given
        String first = "first";
        String second = "second";
        String[] expected = new String[]{first, second};

        // When
        String[] actual = arrayWith(first, second);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnArrayContainingAllElementsInTheSuppliedIterable() {
        // Given
        Iterable<Name> elements = iterableWith(name("Tim"), name("Jeremy"), name("Fred"));
        Name[] expected = new Name[]{name("Tim"), name("Jeremy"), name("Fred")};

        // When
        Name[] actual = arrayFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldThrowIllegalArgumentExceptionIfTheSuppliedIterableIsEmpty() throws Exception {
        // Given
        Iterable<Integer> iterable = iterable();

        try {
            // When
            arrayFrom(iterable);
            fail("Expected IllegalArgumentException to be thrown but nothing was.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString("Cannot construct empty array without knowing desired element class."));
        }
    }

    @Test public void shouldThrowIllegalArgumentExceptionIfIterableContainsInstancesOfDifferentConcreteTypes() throws Exception {
        // Given
        Dog animal1 = dog(colour("Brown"), name("Fido"));
        Cat animal2 = cat(colour("White"), name("Fluff"));
        Animal animal3 = animal(colour("Green"), name("Fishy"));
        Iterable<Animal> input = iterableWith(animal1, animal2, animal3);

        try {
            // When
            arrayFrom(input);
            fail("Expected IllegalArgumentException to be thrown but nothing was.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString("Cannot construct array containing instances of different classes without knowing desired element class."));
        }
    }

    @Test public void shouldReturnAnArrayContainingAllElementsInTheSuppliedIterableOfTheSuppliedType() throws Exception {
        // Given
        Dog animal1 = dog(colour("Brown"), name("Fido"));
        Cat animal2 = cat(colour("White"), name("Fluff"));
        Animal animal3 = animal(colour("Green"), name("Fishy"));
        Iterable<Animal> input = iterableWith(animal1, animal2, animal3);
        Animal[] expected = new Animal[]{animal1, animal2, animal3};

        // When
        Animal[] actual = arrayFrom(input, Animal.class);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnArrayContainingAllElementsInTheSuppliedArray() {
        // Given
        Integer[] elements = new Integer[]{5, 10, 15};
        Integer[] expected = new Integer[]{5, 10, 15};

        // When
        Integer[] array = arrayFrom(elements);

        // Then
        assertThat(array, is(expected));
    }

    @Test public void shouldThrowIllegalArgumentExceptionIfTheSuppliedArrayIsEmpty() throws Exception {
        // Given
        Integer[] array = new Integer[]{};

        try {
            // When
            arrayFrom(array);
            fail("Expected IllegalArgumentException to be thrown but nothing was.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString("Cannot construct empty array without knowing desired element class."));
        }
    }

    @Test public void shouldThrowIllegalArgumentExceptionIfArrayContainsInstancesOfDifferentConcreteTypes() throws Exception {
        // Given
        Dog animal1 = dog(colour("Brown"), name("Fido"));
        Cat animal2 = cat(colour("White"), name("Fluff"));
        Animal animal3 = animal(colour("Green"), name("Fishy"));
        Animal[] input = new Animal[]{animal1, animal2, animal3};

        try {
            // When
            arrayFrom(input);
            fail("Expected IllegalArgumentException to be thrown but nothing was.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString("Cannot construct array containing instances of different classes without knowing desired element class."));
        }
    }

    @Test public void shouldReturnAnArrayContainingAllElementsInTheSuppliedArrayOfTheSuppliedType() throws Exception {
        // Given
        Dog animal1 = dog(colour("Brown"), name("Fido"));
        Cat animal2 = cat(colour("White"), name("Fluff"));
        Animal animal3 = animal(colour("Green"), name("Fishy"));
        Animal[] input = new Animal[]{animal1, animal2, animal3};
        Animal[] expected = new Animal[]{animal1, animal2, animal3};

        // When
        Animal[] actual = arrayFrom(input, Animal.class);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyArrayBuilder() throws Exception {
        // Given
        ArrayBuilder<Integer> expected = new ArrayBuilder<Integer>();

        // When
        ArrayBuilder<Integer> actual = arrayBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnEmptyArrayBuilderOverElementsOfTheSpecifiedType() throws Exception {
        // Given
        ArrayBuilder<Integer> expected = new ArrayBuilder<Integer>(Integer.class);

        // Then
        assertThat(arrayBuilderOf(Integer.class), is(expected));
    }

    @Test public void shouldReturnAnArrayBuilderWithTheSuppliedElements() {
        // Given
        ArrayBuilder<Integer> expected = new ArrayBuilder<Integer>().with(5, 10, 15);

        // When
        ArrayBuilder<Integer> actual = arrayBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnArrayBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        ArrayBuilder<Integer> expected = new ArrayBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = listWith(5, 10, 15);

        // When
        ArrayBuilder<Integer> actual = arrayBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnArrayBuilderOverTheSpecifiedElementTypeContainingAllElementsInTheSuppliedIterable() throws Exception {
        // Given
        ArrayBuilder<Integer> expected = new ArrayBuilder<Integer>(Integer.class).with(5, 10, 15);
        Iterable<Integer> elements = listWith(5, 10, 15);

        // When
        ArrayBuilder<Integer> actual = arrayBuilderFrom(elements, Integer.class);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnArrayBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        ArrayBuilder<Integer> expected = new ArrayBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        ArrayBuilder<Integer> actual = arrayBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnAnArrayBuilderContainingAllElementsInTheSuppliedArrayOverTheSpecifiedType() {
        // Given
        ArrayBuilder<Integer> expected = new ArrayBuilder<Integer>(Integer.class).with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        ArrayBuilder<Integer> actual = arrayBuilderFrom(elements, Integer.class);

        // Then
        assertThat(actual, is(expected));
    }

    @Test public void shouldReturnASingleContainingTheSuppliedElement() {
        // Given
        Single<Integer> expectedSingle = new Single<Integer>(5);

        // When
        Single<Integer> actualSingle = tuple(5);

        // Then
        assertThat(actualSingle, is(expectedSingle));
    }

    @Test public void shouldReturnAPairContainingTheSuppliedElements() {
        // Given
        Pair<Integer, String> expectedPair = new Pair<Integer, String>(5, "Five");

        // When
        Pair<Integer, String> actualPair = tuple(5, "Five");

        // Then
        assertThat(actualPair, is(expectedPair));
    }

    @Test public void shouldReturnATripleContainingTheSuppliedElements() {
        // Given
        Triple<Integer, String, Boolean> expectedTriple =
                new Triple<Integer, String, Boolean>(5, "Five", true);

        // When
        Triple<Integer, String, Boolean> actualTriple = tuple(5, "Five", true);

        // Then
        assertThat(actualTriple, is(expectedTriple));
    }

    @Test public void shouldReturnAQuadrupleContainingTheSuppliedElements() {
        // Given
        Quadruple<Integer, String, Boolean, Double> expectedQuadruple =
                new Quadruple<Integer, String, Boolean, Double>(5, "Five", true, 1.6);

        // When
        Quadruple<Integer, String, Boolean, Double> actualQuadruple = tuple(5, "Five", true, 1.6);

        // Then
        assertThat(actualQuadruple, is(expectedQuadruple));
    }

    @Test public void shouldReturnAQuintupleContainingTheSuppliedElements() {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> expectedQuintuple =
                new Quintuple<Integer, String, Boolean, Double, Long>(5, "Five", true, 1.6, 26L);

        // When
        Quintuple<Integer, String, Boolean, Double, Long> actualQuintuple = tuple(5, "Five", true, 1.6, 26L);

        // Then
        assertThat(actualQuintuple, is(expectedQuintuple));
    }

    @Test public void shouldReturnASextupleContainingTheSuppliedElements() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> expectedSextuple =
                new Sextuple<Integer, String, Boolean, Double, Long, Name>(5, "Five", true, 1.6, 26L, name("fred"));

        // When
        Sextuple<Integer, String, Boolean, Double, Long, Name> actualSextuple = tuple(5, "Five", true, 1.6, 26L, name("fred"));

        // Then
        assertThat(actualSextuple, is(expectedSextuple));
    }

    @Test public void shouldReturnASeptupleContainingTheSuppliedElements() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> expectedSeptuple =
                new Septuple<Integer, String, Boolean, Double, Long, Name, Colour>(5, "Five", true, 1.6, 26L, name("fred"), colour("red"));

        // When
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> actualSeptuple =
                tuple(5, "Five", true, 1.6, 26L, name("fred"), colour("red"));

        // Then
        assertThat(actualSeptuple, is(expectedSeptuple));
    }

    @Test public void shouldReturnAnOctupleContainingTheSuppliedElements() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> expectedOctuple =
                new Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age>(
                        5, "Five", true, 1.6, 26L, name("fred"), colour("red"), age(25));

        // When
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> actualOctuple =
                tuple(5, "Five", true, 1.6, 26L, name("fred"), colour("red"), age(25));

        // Then
        assertThat(actualOctuple, is(expectedOctuple));
    }

    @Test public void shouldReturnANonupleContainingTheSuppliedElements() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> expectedNonuple =
                new Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location>(
                        5, "Five", true, 1.6, 26L, name("fred"), colour("red"), age(25), location("London"));

        // When
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> actualNonuple =
                tuple(5, "Five", true, 1.6, 26L, name("fred"), colour("red"), age(25), location("London"));

        // Then
        assertThat(actualNonuple, is(expectedNonuple));
    }
}
