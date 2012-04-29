/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.google.common.collect.Multiset;
import org.javafunk.funk.testclasses.Animal;
import org.javafunk.funk.testclasses.Cat;
import org.javafunk.funk.testclasses.Dog;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.testclasses.Cat.cat;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Dog.dog;
import static org.javafunk.funk.testclasses.Name.name;

public class MultisetsTest {
    @Test
    public void shouldReturnTheMultisetConcatenationOfAllIterablesInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<String> firstIterable = listWith("a", "b", "a", "c");
        Iterable<String> secondIterable = setWith("c", "d", "e");
        Iterable<String> thirdIterable = multisetWith("a", "a", "c", "b");
        Iterable<Iterable<String>> iterables = iterableWith(firstIterable, secondIterable, thirdIterable);
        Multiset<String> expectedConcatenatedMultiset = multisetWith(
                "a", "a", "a", "a", "b", "b", "c", "c", "c", "d", "e");

        // When
        Multiset<String> actualConcatenatedMultiset = Multisets.concatenate(iterables);

        // Then
        assertThat(actualConcatenatedMultiset, is(expectedConcatenatedMultiset));
    }

    @Test
    public void shouldReturnTheMultisetConcatenationOfTheSuppliedIterables() throws Exception {
        // Given
        Iterable<String> firstIterable = listWith("a", "b", "a", "c");
        Iterable<String> secondIterable = setWith("c", "d", "e");
        Iterable<String> thirdIterable = multisetWith("a", "a", "c", "b");
        Multiset<String> expectedConcatenationMultiset = multisetWith(
                "a", "a", "a", "a", "b", "b", "c", "c", "c", "d", "e");

        // When
        Multiset<String> actualConcatenationMultiset = Multisets.concatenate(
                firstIterable, secondIterable, thirdIterable);

        // Then
        assertThat(actualConcatenationMultiset, is(expectedConcatenationMultiset));
    }

    @Test
    public void shouldAllowMultisetConcatenationOfIterablesWithDifferentConcreteTypes() throws Exception {
        // Given
        Dog fido = dog(colour("black"), name("fido"));
        Dog spud = dog(colour("brown"), name("spud"));
        Cat snowy = cat(colour("white"), name("snowy"));
        Cat smudge = cat(colour("grey"), name("smudge"));
        Iterable<Dog> dogs = multisetWith(fido, spud, fido);
        Iterable<Cat> cats = listWith(snowy, snowy, smudge);
        Multiset<Animal> expectedMenagerie = multisetOf(Animal.class).with(fido, fido, smudge, snowy, spud, snowy);

        // When
        Multiset<Animal> actualMenagerie = Multisets.concatenate(dogs, cats);

        // Then
        assertThat(actualMenagerie, is(expectedMenagerie));
    }

    @Test
    public void shouldReturnTheMultisetUnionOfAllIterablesInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<String> firstIterable = listWith("a", "b", "a", "a", "c");
        Iterable<String> secondIterable = setWith("c", "d", "e");
        Iterable<String> thirdIterable = multisetWith("a", "a", "c", "c", "b");
        Iterable<Iterable<String>> iterables = iterableWith(firstIterable, secondIterable, thirdIterable);
        Multiset<String> expectedUnionMultiset = multisetWith("a", "a", "a", "b", "c", "c", "d", "e");

        // When
        Multiset<String> actualUnionMultiset = Multisets.union(iterables);

        // Then
        assertThat(actualUnionMultiset, is(expectedUnionMultiset));
    }

    @Test
    public void shouldReturnTheMultisetUnionOfTheSuppliedIterables() throws Exception {
        // Given
        Iterable<String> firstIterable = listWith("a", "b", "a", "a", "c");
        Iterable<String> secondIterable = setWith("c", "d", "e");
        Iterable<String> thirdIterable = multisetWith("a", "a", "c", "c", "b");
        Multiset<String> expectedUnionMultiset = multisetWith("a", "a", "a", "b", "c", "c", "d", "e");

        // When
        Multiset<String> actualUnionMultiset = Multisets.union(firstIterable, secondIterable, thirdIterable);

        // Then
        assertThat(actualUnionMultiset, is(expectedUnionMultiset));
    }

    @Test
    public void shouldAllowMultisetUnionOfIterablesWithDifferentConcreteTypes() throws Exception {
        // Given
        Dog fido = dog(colour("black"), name("fido"));
        Dog spud = dog(colour("brown"), name("spud"));
        Dog rex = dog(colour("white"), name("rex"));
        Cat snowy = cat(colour("white"), name("snowy"));
        Cat smudge = cat(colour("grey"), name("smudge"));
        Iterable<Dog> firstDogs = multisetWith(fido, spud, fido);
        Iterable<Dog> secondDogs = multisetWith(rex, spud, spud);
        Iterable<Cat> cats = iterableWith(snowy, snowy, smudge);
        Multiset<Animal> expectedMenagerie = multisetOf(Animal.class)
                .with(fido, spud, fido, smudge, snowy, spud, snowy, rex);

        // When
        Multiset<Animal> actualMenagerie = Multisets.union(firstDogs, secondDogs, cats);

        // Then
        assertThat(actualMenagerie, is(expectedMenagerie));
    }

    @Test
    public void shouldReturnTheMultisetIntersectionOfTheSuppliedIterables() throws Exception {
        // Given
        Iterable<String> firstIterable = multisetWith("a", "b", "c", "c", "q", "q");
        Iterable<String> secondIterable = listWith("c", "c", "c", "d", "q", "e");
        Iterable<String> thirdIterable = multisetWith("a", "c", "c", "f", "f", "q", "q");
        Multiset<String> expectedIntersectionMultiset = multisetWith("c", "c", "q");

        // When
        Multiset<String> actualIntersectionMultiset = Multisets.intersection(
                firstIterable, secondIterable, thirdIterable);

        // Then
        assertThat(actualIntersectionMultiset, is(expectedIntersectionMultiset));
    }

    @Test
    public void shouldReturnTheMultisetIntersectionOfAllIterablesInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<String> firstIterable = multisetWith("a", "b", "c", "c", "q", "q");
        Iterable<String> secondIterable = listWith("c", "c", "c", "d", "q", "e");
        Iterable<String> thirdIterable = multisetWith("a", "c", "f", "c", "f", "q", "q");
        Iterable<Iterable<String>> iterables = iterableWith(firstIterable, secondIterable, thirdIterable);
        Multiset<String> expectedIntersectionMultiset = multisetWith("c", "q", "c");

        // When
        Multiset<String> actualIntersectionMultiset = Multisets.intersection(iterables);

        // Then
        assertThat(actualIntersectionMultiset, is(expectedIntersectionMultiset));
    }
    
    @Test
    public void shouldReturnTheMultisetDifferenceOfAllIterablesInTheSuppliedIterable() {
        // Given
        Iterable<String> firstIterable = listWith("a", "b", "b", "b", "c", "c", "c", "d");
        Iterable<String> secondIterable = multisetWith("a", "a", "a", "b", "b", "c");
        Iterable<Iterable<String>> iterables = iterableWith(firstIterable, secondIterable);
        Multiset<String> expectedDifference = multisetWith("c", "c", "b", "d");

        // When
        Multiset<String> actualDifference = Multisets.difference(iterables);

        // Then
        assertThat(actualDifference, is(expectedDifference));
    }

    @Test
    public void shouldReturnTheMultisetDifferenceOfTheSuppliedIterables() {
        // Given
        Iterable<String> firstIterable = listWith("a", "b", "b", "b", "c", "c", "c", "d");
        Iterable<String> secondIterable = multisetWith("a", "a", "a", "b", "b", "c");
        Iterable<String> thirdIterable = listWith("d", "e", "f");
        Multiset<String> expectedDifference = multisetWith("c", "c", "b");

        // When
        Multiset<String> actualDifference = Multisets.difference(firstIterable, secondIterable, thirdIterable);

        // Then
        assertThat(actualDifference, is(expectedDifference));
    }
}
