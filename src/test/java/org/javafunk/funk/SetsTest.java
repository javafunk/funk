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
    public void shouldReturnTheSetIntersectionOfTheSuppliedIterables() throws Exception {
        // Given
        Iterable<String> firstIterable = setWith("a", "b", "c", "q");
        Iterable<String> secondIterable = listWith("c", "c", "d", "q", "e");
        Iterable<String> thirdIterable = multisetWith("a", "c", "f", "f", "q");
        Set<String> expectedIntersectionSet = setWith("c", "q");

        // When
        Set<String> actualIntersectionSet = Sets.intersection(firstIterable, secondIterable, thirdIterable);

        // Then
        assertThat(actualIntersectionSet, is(expectedIntersectionSet));
    }

    @Test
    public void shouldReturnTheSetIntersectionOfAllIterablesInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<String> firstIterable = setWith("a", "b", "c", "q");
        Iterable<String> secondIterable = listWith("c", "c", "d", "q", "e");
        Iterable<String> thirdIterable = multisetWith("a", "c", "f", "f", "q");
        Iterable<Iterable<String>> iterables = listWith(firstIterable, secondIterable, thirdIterable);
        Set<String> expectedIntersectionSet = setWith("c", "q");

        // When
        Set<String> actualIntersectionSet = Sets.intersection(iterables);

        // Then
        assertThat(actualIntersectionSet, is(expectedIntersectionSet));
    }

    @Test
    public void shouldReturnTheSetDifferenceOfTheSuppliedIterables() throws Exception {
        // Given
        Iterable<String> firstIterable = setWith("a", "b", "c", "q");
        Iterable<String> secondIterable = listWith("d", "d", "q", "e");
        Iterable<String> thirdIterable = multisetWith("a", "f", "q", "q", "q");
        Set<String> expectedDifferenceSet = setWith("b", "c");

        // When
        Set<String> actualDifferenceSet = Sets.difference(firstIterable, secondIterable, thirdIterable);

        // Then
        assertThat(actualDifferenceSet, is(expectedDifferenceSet));
    }

    @Test
    public void shouldReturnTheSetDifferenceOfAllIterablesInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<String> firstIterable = setWith("a", "b", "c", "q");
        Iterable<String> secondIterable = listWith("d", "d", "q", "e");
        Iterable<String> thirdIterable = multisetWith("a", "f", "q", "q", "q");
        Iterable<Iterable<String>> iterables = listWith(firstIterable, secondIterable, thirdIterable);
        Set<String> expectedDifferenceSet = setWith("b", "c");

        // When
        Set<String> actualDifferenceSet = Sets.difference(iterables);

        // Then
        assertThat(actualDifferenceSet, is(expectedDifferenceSet));
    }

    @Test
    public void shouldReturnTheSetSymmetricDifferenceOfAllSuppliedIterables() throws Exception {
        // Given
        Iterable<String> firstIterable = setWith("a", "b", "c", "q");
        Iterable<String> secondIterable = listWith("c", "d", "d", "q", "d", "e");
        Iterable<String> thirdIterable = multisetWith("a", "c", "d", "q");
        Set<String> expectedSymmetricDifferenceSet = setWith("b", "c", "e", "q");

        // When
        Set<String> actualSymmetricDifferenceSet = Sets.symmetricDifference(firstIterable, secondIterable, thirdIterable);

        // Then
        assertThat(actualSymmetricDifferenceSet, is(expectedSymmetricDifferenceSet));
    }

    @Test
    public void shouldReturnTheSetSymmetricDifferenceOfAllIterablesInTheSuppliedIterable() throws Exception {
        // Given
        Iterable<String> firstIterable = setWith("a", "b", "c", "q");
        Iterable<String> secondIterable = listWith("c", "d", "d", "q", "d", "e");
        Iterable<String> thirdIterable = multisetWith("a", "c", "d", "q");
        Iterable<Iterable<String>> sets = listWith(firstIterable, secondIterable, thirdIterable);
        Set<String> expectedSymmetricDifferenceSet = setWith("b", "c", "e", "q");

        // When
        Set<String> actualSymmetricDifferenceSet = Sets.symmetricDifference(sets);

        // Then
        assertThat(actualSymmetricDifferenceSet, is(expectedSymmetricDifferenceSet));
    }
}
