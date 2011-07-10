package org.javafunk;

import org.javafunk.testclasses.Animal;
import org.javafunk.testclasses.Cat;
import org.javafunk.testclasses.Dog;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.*;
import static org.javafunk.testclasses.Cat.cat;
import static org.javafunk.testclasses.Colour.colour;
import static org.javafunk.testclasses.Dog.dog;
import static org.javafunk.testclasses.Name.name;

public class SetsTest {
    @Test
    public void shouldReturnTheUnionOfTheSuppliedSets() throws Exception {
        // Given
        Set<String> firstSet = setWith("a", "b", "c");
        Set<String> secondSet = setWith("c", "d", "e");
        Set<String> thirdSet = setWith("a", "c", "f");
        Set<String> expectedUnionSet = setWith("a", "b", "c", "d", "e", "f");

        // When
        Set<String> actualUnionSet = Sets.union(firstSet, secondSet, thirdSet);

        // Then
        assertThat(actualUnionSet, is(expectedUnionSet));
    }

    @Test
    public void shouldReturnTheUnionOfAllSetsInTheSuppliedIterable() throws Exception {
        // Given
        Set<String> firstSet = setWith("a", "b", "c");
        Set<String> secondSet = setWith("c", "d", "e");
        Set<String> thirdSet = setWith("a", "c", "f");
        Iterable<Set<String>> sets = listWith(firstSet, secondSet, thirdSet);
        Set<String> expectedUnionSet = setWith("a", "b", "c", "d", "e", "f");

        // When
        Set<String> actualUnionSet = Sets.union(sets);

        // Then
        assertThat(actualUnionSet, is(expectedUnionSet));
    }

    @Test
    public void shouldAllowUnionOfSetsWithDifferentConcreteTypes() throws Exception {
        // Given
        Dog fido = dog(colour("black"), name("fido"));
        Dog spud = dog(colour("brown"), name("spud"));
        Cat snowy = cat(colour("white"), name("snowy"));
        Cat smudge = cat(colour("grey"), name("smudge"));
        Set<Dog> dogs = setWith(fido, spud);
        Set<Cat> cats = setWith(snowy, smudge);
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
