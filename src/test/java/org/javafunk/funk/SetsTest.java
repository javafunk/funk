package org.javafunk.funk;

import org.javafunk.funk.testclasses.Animal;
import org.javafunk.funk.testclasses.Cat;
import org.javafunk.funk.testclasses.Dog;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.testclasses.Cat.cat;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Dog.dog;
import static org.javafunk.funk.testclasses.Name.name;

public class SetsTest {
    @Test
    public void shouldReturnTheSetUnionOfTheSuppliedIterables() throws Exception {
        // Given
        Iterable<String> firstIterable = setWith("a", "b", "c");
        Iterable<String> secondIterable = listWith("c", "d", "c", "e");
        Iterable<String> thirdIterable = multisetWith("a", "c", "f", "f");
        Set<String> expectedUnionSet = setWith("a", "b", "c", "d", "e", "f");

        // When
        Set<String> actualUnionSet = Sets.union(firstIterable, secondIterable, thirdIterable);

        // Then
        assertThat(actualUnionSet, is(expectedUnionSet));
    }

    @Test
    public void shouldReturnTheSetUnionOfAllIterablesInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<String> firstIterable = setWith("a", "b", "c");
        Iterable<String> secondIterable = listWith("c", "d", "c", "e");
        Iterable<String> thirdIterable = multisetWith("a", "c", "f", "f");
        Iterable<Iterable<String>> sets = listWith(firstIterable, secondIterable, thirdIterable);
        Set<String> expectedUnionSet = setWith("a", "b", "c", "d", "e", "f");

        // When
        Set<String> actualUnionSet = Sets.union(sets);

        // Then
        assertThat(actualUnionSet, is(expectedUnionSet));
    }

    @Test
    public void shouldAllowSetUnionOfIterablesWithDifferentConcreteTypes() throws Exception {
        // Given
        Dog fido = dog(colour("black"), name("fido"));
        Dog spud = dog(colour("brown"), name("spud"));
        Cat snowy = cat(colour("white"), name("snowy"));
        Cat smudge = cat(colour("grey"), name("smudge"));
        Iterable<Dog> dogs = setWith(fido, spud);
        Iterable<Cat> cats = listWith(snowy, smudge, snowy);
        Set<Animal> expectedMenagerie = setOf(Animal.class).with(fido, smudge, spud, snowy);

        // When
        Set<Animal> actualMenagerie = Sets.union(dogs, cats);

        // Then
        assertThat(actualMenagerie, is(expectedMenagerie));
    }

    @Test
    public void shouldReturnTheIntersectionOfTheSuppliedSets() throws Exception {
        // Given
        Set<String> firstSet = setWith("a", "b", "c", "q");
        Set<String> secondSet = setWith("c", "d", "q", "e");
        Set<String> thirdSet = setWith("a", "c", "f", "q");
        Set<String> expectedIntersectionSet = setWith("c", "q");

        // When
        Set<String> actualIntersectionSet = Sets.intersection(firstSet, secondSet, thirdSet);

        // Then
        assertThat(actualIntersectionSet, is(expectedIntersectionSet));
    }

    @Test
    public void shouldReturnTheIntersectionOfAllSetsInTheSuppliedIterable() throws Exception {
        // Given
        Set<String> firstSet = setWith("a", "b", "c", "q");
        Set<String> secondSet = setWith("c", "d", "q", "e");
        Set<String> thirdSet = setWith("a", "c", "f", "q");
        Iterable<Set<String>> sets = setWith(firstSet, secondSet, thirdSet);
        Set<String> expectedIntersectionSet = setWith("c", "q");

        // When
        Set<String> actualIntersectionSet = Sets.intersection(sets);

        // Then
        assertThat(actualIntersectionSet, is(expectedIntersectionSet));
    }

    @Test
    public void shouldReturnTheDifferenceBetweenTheFirstSetAndAllOtherSets() throws Exception {
        // Given
        Set<String> firstSet = setWith("a", "b", "c", "q");
        Set<String> secondSet = setWith("d", "q", "e");
        Set<String> thirdSet = setWith("a", "f", "q");
        Set<String> expectedDifferenceSet = setWith("b", "c");

        // When
        Set<String> actualDifferenceSet = Sets.difference(firstSet, secondSet, thirdSet);

        // Then
        assertThat(actualDifferenceSet, is(expectedDifferenceSet));
    }

    @Test
    public void shouldReturnTheDifferenceBetweenTheFirstSetInTheIterableAndAllOtherSets() throws Exception {
        // Given
        Set<String> firstSet = setWith("a", "b", "c", "q");
        Set<String> secondSet = setWith("d", "q", "e");
        Set<String> thirdSet = setWith("a", "f", "q");
        Iterable<Set<String>> sets = listWith(firstSet, secondSet, thirdSet);
        Set<String> expectedDifferenceSet = setWith("b", "c");

        // When
        Set<String> actualDifferenceSet = Sets.difference(sets);

        // Then
        assertThat(actualDifferenceSet, is(expectedDifferenceSet));
    }

    @Test
    public void shouldReturnTheSymmetricDifferenceOfAllSuppliedSets() throws Exception {
        // Given
        Set<String> firstSet = setWith("a", "b", "c", "q");
        Set<String> secondSet = setWith("c", "d", "q", "e");
        Set<String> thirdSet = setWith("a", "c", "d", "q");
        Set<String> expectedSymmetricDifferenceSet = setWith("b", "c", "e", "q");

        // When
        Set<String> actualSymmetricDifferenceSet = Sets.symmetricDifference(firstSet, secondSet, thirdSet);

        // Then
        assertThat(actualSymmetricDifferenceSet, is(expectedSymmetricDifferenceSet));
    }

    @Test
    public void shouldReturnTheSymmetricDifferenceOfAllSetsInTheSuppliedIterable() throws Exception {
        // Given
        Set<String> firstSet = setWith("a", "b", "c", "q");
        Set<String> secondSet = setWith("c", "d", "q", "e");
        Set<String> thirdSet = setWith("a", "c", "d", "q");
        Iterable<Set<String>> sets = listWith(firstSet, secondSet, thirdSet);
        Set<String> expectedSymmetricDifferenceSet = setWith("b", "c", "e", "q");

        // When
        Set<String> actualSymmetricDifferenceSet = Sets.symmetricDifference(sets);

        // Then
        assertThat(actualSymmetricDifferenceSet, is(expectedSymmetricDifferenceSet));
    }
}
