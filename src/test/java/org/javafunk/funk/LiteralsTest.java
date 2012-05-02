/*
 * Copyright (C) 2011 Funk committers.
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
import org.javafunk.funk.testclasses.Age;
import org.javafunk.funk.testclasses.Colour;
import org.javafunk.funk.testclasses.Location;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.builders.IterableBuilder.iterableBuilder;
import static org.javafunk.funk.testclasses.Age.age;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Location.location;
import static org.javafunk.funk.testclasses.Name.name;
import static org.junit.Assert.assertThat;

public class LiteralsTest {

    @Test
    public void shouldReturnAnEmptyIterable() throws Exception {
        // Given
        Iterable<Integer> expected = new ArrayList<Integer>();

        // When
        Iterable<Integer> actual = iterable();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyIterableWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Iterable<Integer> expected = new ArrayList<Integer>();

        // Then
        assertThat(iterableOf(Integer.class), is(expected));
    }

    @Test
    public void shouldReturnAnIterableContainingTheSuppliedElements() {
        // Given
        Iterable<Integer> expected = asList(5, 10, 15);

        // When
        Iterable<Integer> actual = iterableWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnIterableContainingAllElementsInTheSuppliedIterable() {
        // Given
        Iterable<Integer> expected = asList(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        Iterable<Integer> actual = iterableFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnIterableContainingAllElementsInTheSuppliedArray() {
        // Given
        Iterable<Integer> expected = asList(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        Iterable<Integer> actual = iterableFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyIterableBuilder() throws Exception {
        // Given
        IterableBuilder<Integer> expected = new IterableBuilder<Integer>();

        // When
        IterableBuilder<Integer> actual = iterableBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyIterableBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        IterableBuilder<Integer> expected = new IterableBuilder<Integer>();

        // Then
        assertThat(iterableBuilderOf(Integer.class), is(expected));
    }

    @Test
    public void shouldReturnAnIterableBuilderWithTheSuppliedElements() {
        // Given
        IterableBuilder<Integer> expected = new IterableBuilder<Integer>().with(5, 10, 15);

        // When
        IterableBuilder<Integer> actual = iterableBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnIterableBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        IterableBuilder<Integer> expected = new IterableBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        IterableBuilder<Integer> actual = iterableBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnIterableBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        IterableBuilder<Integer> expected = new IterableBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        IterableBuilder<Integer> actual = iterableBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyCollection() throws Exception {
        // Given
        Collection<Integer> expected = new ArrayList<Integer>();

        // When
        Collection<Integer> actual = collection();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyCollectionWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Collection<Integer> expected = new ArrayList<Integer>();

        // Then
        assertThat(collectionOf(Integer.class), is(expected));
    }

    @Test
    public void shouldReturnAnCollectionContainingTheSuppliedElements() {
        // Given
        Collection<Integer> expected = asList(5, 10, 15);

        // When
        Collection<Integer> actual = collectionWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnCollectionContainingAllElementsInTheSuppliedIterable() {
        // Given
        Collection<Integer> expected = asList(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        Collection<Integer> actual = collectionFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnCollectionContainingAllElementsInTheSuppliedArray() {
        // Given
        Collection<Integer> expected = asList(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        Collection<Integer> actual = collectionFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyCollectionBuilder() throws Exception {
        // Given
        CollectionBuilder<Integer> expected = new CollectionBuilder<Integer>();

        // When
        CollectionBuilder<Integer> actual = collectionBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyCollectionBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        CollectionBuilder<Integer> expected = new CollectionBuilder<Integer>();

        // Then
        assertThat(collectionBuilderOf(Integer.class), is(expected));
    }

    @Test
    public void shouldReturnAnCollectionBuilderWithTheSuppliedElements() {
        // Given
        CollectionBuilder<Integer> expected = new CollectionBuilder<Integer>().with(5, 10, 15);

        // When
        CollectionBuilder<Integer> actual = collectionBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnCollectionBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        CollectionBuilder<Integer> expected = new CollectionBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        CollectionBuilder<Integer> actual = collectionBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnCollectionBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        CollectionBuilder<Integer> expected = new CollectionBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        CollectionBuilder<Integer> actual = collectionBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyList() throws Exception {
        // Given
        List<Integer> expected = new ArrayList<Integer>();

        // When
        List<Integer> actual = list();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyListWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        List<Integer> expected = new ArrayList<Integer>();

        // Then
        assertThat(listOf(Integer.class), is(expected));
    }

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
    public void shouldReturnAListContainingAllElementsInTheSuppliedIterable() {
        // Given
        List<Integer> expected = asList(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        List<Integer> actual = listFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAListContainingAllElementsInTheSuppliedArray() {
        // Given
        List<Integer> expected = asList(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        List<Integer> actual = listFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyListBuilder() throws Exception {
        // Given
        ListBuilder<Integer> expected = new ListBuilder<Integer>();

        // When
        ListBuilder<Integer> actual = listBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyListBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        ListBuilder<Integer> expected = new ListBuilder<Integer>();

        // Then
        assertThat(listBuilderOf(Integer.class), is(expected));
    }

    @Test
    public void shouldReturnAListBuilderWithTheSuppliedElements() {
        // Given
        ListBuilder<Integer> expected = new ListBuilder<Integer>().with(5, 10, 15);

        // When
        ListBuilder<Integer> actual = listBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAListBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        ListBuilder<Integer> expected = new ListBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        ListBuilder<Integer> actual = listBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAListBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        ListBuilder<Integer> expected = new ListBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        ListBuilder<Integer> actual = listBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyMultiset() throws Exception {
        // Given
        Multiset<Integer> expected = HashMultiset.create();

        // When
        Multiset<Integer> actual = multiset();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyMultisetWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Multiset<Integer> expected = HashMultiset.create();

        // Then
        assertThat(multisetOf(Integer.class), is(expected));
    }

    @Test
    public void shouldReturnAMultisetContainingTheSuppliedElements() {
        // Given
        Multiset<Integer> expectedMultiset = HashMultiset.create(asList(5, 10, 15));

        // When
        Multiset<Integer> actualMultiset = multisetWith(5, 10, 15);

        // Then
        assertThat(actualMultiset, is(expectedMultiset));
    }

    @Test
    public void shouldReturnAMultisetContainingAllElementsInTheSuppliedIterable() {
        // Given
        Multiset<Integer> expected = HashMultiset.create(asList(5, 10, 15));
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        Multiset<Integer> actual = multisetFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAMultisetContainingAllElementsInTheSuppliedArray() {
        // Given
        Multiset<Integer> expected = HashMultiset.create(asList(5, 10, 15));
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        Multiset<Integer> actual = multisetFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyMultisetBuilder() throws Exception {
        // Given
        MultisetBuilder<Integer> expected = new MultisetBuilder<Integer>();

        // When
        MultisetBuilder<Integer> actual = multisetBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptyMultisetBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        MultisetBuilder<Integer> expected = new MultisetBuilder<Integer>();

        // Then
        assertThat(multisetBuilderOf(Integer.class), is(expected));
    }

    @Test
    public void shouldReturnAMultisetBuilderWithTheSuppliedElements() {
        // Given
        MultisetBuilder<Integer> expected = new MultisetBuilder<Integer>().with(5, 10, 15);

        // When
        MultisetBuilder<Integer> actual = multisetBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAMultisetBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        MultisetBuilder<Integer> expected = new MultisetBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        MultisetBuilder<Integer> actual = multisetBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAMultisetBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        MultisetBuilder<Integer> expected = new MultisetBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        MultisetBuilder<Integer> actual = multisetBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptySet() throws Exception {
        // Given
        Set<Integer> expected = new HashSet<Integer>();

        // When
        Set<Integer> actual = set();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptySetWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        Set<Integer> expected = new HashSet<Integer>();

        // Then
        assertThat(setOf(Integer.class), is(expected));
    }

    @Test
    public void shouldReturnASetContainingTheSuppliedElements() {
        // Given
        Set<Integer> expectedSet = new HashSet<Integer>(asList(5, 10, 15));

        // When
        Set<Integer> actualSet = setWith(5, 10, 15);

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldReturnASetContainingAllElementsInTheSuppliedIterable() {
        // Given
        Set<Integer> expected = new HashSet<Integer>(asList(5, 10, 15));
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        Set<Integer> actual = setFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnASetContainingAllElementsInTheSuppliedArray() {
        // Given
        Set<Integer> expected = new HashSet<Integer>(asList(5, 10, 15));
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        Set<Integer> actual = setFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptySetBuilder() throws Exception {
        // Given
        SetBuilder<Integer> expected = new SetBuilder<Integer>();

        // When
        SetBuilder<Integer> actual = setBuilder();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAnEmptySetBuilderWithElementsOfTheSpecifiedType() throws Exception {
        // Given
        SetBuilder<Integer> expected = new SetBuilder<Integer>();

        // Then
        assertThat(setBuilderOf(Integer.class), is(expected));
    }

    @Test
    public void shouldReturnASetBuilderWithTheSuppliedElements() {
        // Given
        SetBuilder<Integer> expected = new SetBuilder<Integer>().with(5, 10, 15);

        // When
        SetBuilder<Integer> actual = setBuilderWith(5, 10, 15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnASetBuilderContainingAllElementsInTheSuppliedIterable() {
        // Given
        SetBuilder<Integer> expected = new SetBuilder<Integer>().with(5, 10, 15);
        Iterable<Integer> elements = asList(5, 10, 15);

        // When
        SetBuilder<Integer> actual = setBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnASetBuilderContainingAllElementsInTheSuppliedArray() {
        // Given
        SetBuilder<Integer> expected = new SetBuilder<Integer>().with(5, 10, 15);
        Integer[] elements = new Integer[]{5, 10, 15};

        // When
        SetBuilder<Integer> actual = setBuilderFrom(elements);

        // Then
        assertThat(actual, is(expected));
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
    public void shouldReturnASingleContainingTheSuppliedElement() {
        // Given
        Single<Integer> expectedSingle = new Single<Integer>(5);

        // When
        Single<Integer> actualSingle = tuple(5);

        // Then
        assertThat(actualSingle, is(expectedSingle));
    }

    @Test
    public void shouldReturnAPairContainingTheSuppliedElements() {
        // Given
        Pair<Integer, String> expectedPair = new Pair<Integer, String>(5, "Five");

        // When
        Pair<Integer, String> actualPair = tuple(5, "Five");

        // Then
        assertThat(actualPair, is(expectedPair));
    }

    @Test
    public void shouldReturnATripleContainingTheSuppliedElements() {
        // Given
        Triple<Integer, String, Boolean> expectedTriple =
                new Triple<Integer, String, Boolean>(5, "Five", true);

        // When
        Triple<Integer, String, Boolean> actualTriple = tuple(5, "Five", true);

        // Then
        assertThat(actualTriple, is(expectedTriple));
    }

    @Test
    public void shouldReturnAQuadrupleContainingTheSuppliedElements() {
        // Given
        Quadruple<Integer, String, Boolean, Double> expectedQuadruple =
                new Quadruple<Integer, String, Boolean, Double>(5, "Five", true, 1.6);

        // When
        Quadruple<Integer, String, Boolean, Double> actualQuadruple = tuple(5, "Five", true, 1.6);

        // Then
        assertThat(actualQuadruple, is(expectedQuadruple));
    }

    @Test
    public void shouldReturnAQuintupleContainingTheSuppliedElements() {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> expectedQuintuple =
                new Quintuple<Integer, String, Boolean, Double, Long>(5, "Five", true, 1.6, 26L);

        // When
        Quintuple<Integer, String, Boolean, Double, Long> actualQuintuple = tuple(5, "Five", true, 1.6, 26L);

        // Then
        assertThat(actualQuintuple, is(expectedQuintuple));
    }

    @Test
    public void shouldReturnASextupleContainingTheSuppliedElements() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> expectedSextuple =
                new Sextuple<Integer, String, Boolean, Double, Long, Name>(5, "Five", true, 1.6, 26L, name("fred"));

        // When
        Sextuple<Integer, String, Boolean, Double, Long, Name> actualSextuple = tuple(5, "Five", true, 1.6, 26L, name("fred"));

        // Then
        assertThat(actualSextuple, is(expectedSextuple));
    }

    @Test
    public void shouldReturnASeptupleContainingTheSuppliedElements() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> expectedSeptuple =
                new Septuple<Integer, String, Boolean, Double, Long, Name, Colour>(5, "Five", true, 1.6, 26L, name("fred"), colour("red"));

        // When
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> actualSeptuple =
                tuple(5, "Five", true, 1.6, 26L, name("fred"), colour("red"));

        // Then
        assertThat(actualSeptuple, is(expectedSeptuple));
    }

    @Test
    public void shouldReturnAOctupleContainingTheSuppliedElements() {
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

    @Test
    public void shouldReturnANonupleContainingTheSuppliedElements() {
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
