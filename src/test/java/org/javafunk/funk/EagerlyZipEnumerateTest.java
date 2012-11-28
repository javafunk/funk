/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.*;
import org.javafunk.funk.testclasses.Age;
import org.javafunk.funk.testclasses.Colour;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.testclasses.Age.age;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Name.name;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class EagerlyZipEnumerateTest {
    @Test
    public void shouldEnumerateIterable() {
        // Given
        Iterable<String> iterable = iterableWith("a", "b", "c");
        Collection<Pair<Integer, String>> expectedOutput = collectionWith(
                tuple(0, "a"),
                tuple(1, "b"),
                tuple(2, "c"));

        // When
        Collection<Pair<Integer, String>> actualOutput = Eagerly.enumerate(iterable);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldZipTwoIterables() {
        // Given
        Iterable<Integer> iterable1 = iterableWith(1, 2, 3);
        Iterable<String> iterable2 = iterableWith("A", "B");
        Collection<Pair<Integer, String>> expectedOutput = collectionWith(
                tuple(1, "A"),
                tuple(2, "B"));

        // When
        Collection<Pair<Integer, String>> actualOutput = Eagerly.zip(
                iterable1,
                iterable2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldZipThreeIterables() {
        // Given
        Iterable<Integer> iterable1 = iterableWith(1, 2, 3);
        Iterable<String> iterable2 = iterableWith("A", "B");
        Iterable<Boolean> iterable3 = iterableWith(true, false, true, false);
        Collection<Triple<Integer, String, Boolean>> expectedOutput = collectionWith(
                tuple(1, "A", true),
                tuple(2, "B", false));

        // When
        Collection<Triple<Integer, String, Boolean>> actualOutput = Eagerly.zip(
                iterable1,
                iterable2,
                iterable3);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldZipFourIterables() {
        // Given
        Iterable<Integer> iterable1 = iterableWith(1, 2, 3);
        Iterable<String> iterable2 = iterableWith("A", "B", "C", "D");
        Iterable<Boolean> iterable3 = iterableWith(true, false);
        Iterable<Character> iterable4 = iterableWith('a', 'b', 'c', 'd', 'e', 'f');
        Collection<Quadruple<Integer, String, Boolean, Character>> expectedOutput = collectionWith(
                tuple(1, "A", true, 'a'),
                tuple(2, "B", false, 'b'));

        // When
        Collection<Quadruple<Integer, String, Boolean, Character>> actualOutput = Eagerly.zip(
                iterable1,
                iterable2,
                iterable3,
                iterable4);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldZipFiveIterables() {
        // Given
        Iterable<Integer> iterable1 = iterableWith(1, 2, 3);
        Iterable<String> iterable2 = iterableWith("A", "B", "C", "D");
        Iterable<Boolean> iterable3 = iterableWith(true, false);
        Iterable<Character> iterable4 = iterableWith('a', 'b', 'c', 'd', 'e', 'f');
        Iterable<Double> iterable5 = iterableWith(1.2, 3.4, 5.6);
        Collection<Quintuple<Integer, String, Boolean, Character, Double>> expectedOutput = collectionWith(
                tuple(1, "A", true, 'a', 1.2),
                tuple(2, "B", false, 'b', 3.4));

        // When
        Collection<Quintuple<Integer, String, Boolean, Character, Double>> actualOutput = Eagerly.zip(
                iterable1,
                iterable2,
                iterable3,
                iterable4,
                iterable5);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldZipSixIterables() {
        // Given
        Iterable<Integer> iterable1 = iterableWith(1, 2, 3);
        Iterable<String> iterable2 = iterableWith("A", "B", "C", "D");
        Iterable<Boolean> iterable3 = iterableWith(true, false);
        Iterable<Character> iterable4 = iterableWith('a', 'b', 'c', 'd', 'e', 'f');
        Iterable<Double> iterable5 = iterableWith(1.2, 3.4, 5.6);
        Iterable<Long> iterable6 = iterableWith(1L, 2L, 3L);
        Collection<Sextuple<Integer, String, Boolean, Character, Double, Long>> expectedOutput = collectionWith(
                tuple(1, "A", true, 'a', 1.2, 1L),
                tuple(2, "B", false, 'b', 3.4, 2L));

        // When
        Collection<Sextuple<Integer, String, Boolean, Character, Double, Long>> actualOutput = Eagerly.zip(
                iterable1,
                iterable2,
                iterable3,
                iterable4,
                iterable5,
                iterable6);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldZipSevenIterables() {
        // Given
        Iterable<Integer> iterable1 = iterableWith(1, 2, 3);
        Iterable<String> iterable2 = iterableWith("A", "B", "C", "D");
        Iterable<Boolean> iterable3 = iterableWith(true, false);
        Iterable<Character> iterable4 = iterableWith('a', 'b', 'c', 'd', 'e', 'f');
        Iterable<Double> iterable5 = iterableWith(1.2, 3.4, 5.6);
        Iterable<Long> iterable6 = iterableWith(1L, 2L, 3L);
        Iterable<Name> iterable7 = iterableWith(name("Adam"), name("Barry"), name("Clive"));
        Collection<Septuple<Integer, String, Boolean, Character, Double, Long, Name>> expectedOutput = collectionWith(
                tuple(1, "A", true, 'a', 1.2, 1L, name("Adam")),
                tuple(2, "B", false, 'b', 3.4, 2L, name("Barry")));

        // When
        Collection<Septuple<Integer, String, Boolean, Character, Double, Long, Name>> actualOutput = Eagerly.zip(
                iterable1,
                iterable2,
                iterable3,
                iterable4,
                iterable5,
                iterable6,
                iterable7);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldZipEightIterables() {
        // Given
        Iterable<Integer> iterable1 = iterableWith(1, 2, 3);
        Iterable<String> iterable2 = iterableWith("A", "B", "C", "D");
        Iterable<Boolean> iterable3 = iterableWith(true, false);
        Iterable<Character> iterable4 = iterableWith('a', 'b', 'c', 'd', 'e', 'f');
        Iterable<Double> iterable5 = iterableWith(1.2, 3.4, 5.6);
        Iterable<Long> iterable6 = iterableWith(1L, 2L, 3L);
        Iterable<Name> iterable7 = iterableWith(name("Adam"), name("Barry"), name("Clive"));
        Iterable<Colour> iterable8 = iterableWith(colour("Amber"), colour("Blue"), colour("Cyan"));
        Collection<Octuple<Integer, String, Boolean, Character, Double, Long, Name, Colour>> expectedOutput = collectionWith(
                tuple(1, "A", true, 'a', 1.2, 1L, name("Adam"), colour("Amber")),
                tuple(2, "B", false, 'b', 3.4, 2L, name("Barry"), colour("Blue")));

        // When
        Collection<Octuple<Integer, String, Boolean, Character, Double, Long, Name, Colour>> actualOutput = Eagerly.zip(
                iterable1,
                iterable2,
                iterable3,
                iterable4,
                iterable5,
                iterable6,
                iterable7,
                iterable8);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldZipNineIterables() {
        // Given
        Iterable<Integer> iterable1 = iterableWith(1, 2, 3);
        Iterable<String> iterable2 = iterableWith("A", "B", "C", "D");
        Iterable<Boolean> iterable3 = iterableWith(true, false);
        Iterable<Character> iterable4 = iterableWith('a', 'b', 'c', 'd', 'e', 'f');
        Iterable<Double> iterable5 = iterableWith(1.2, 3.4, 5.6);
        Iterable<Long> iterable6 = iterableWith(1L, 2L, 3L);
        Iterable<Name> iterable7 = iterableWith(name("Adam"), name("Barry"), name("Clive"));
        Iterable<Colour> iterable8 = iterableWith(colour("Amber"), colour("Blue"), colour("Cyan"));
        Iterable<Age> iterable9 = iterableWith(age(20), age(30), age(40));
        Collection<Nonuple<Integer, String, Boolean, Character, Double, Long, Name, Colour, Age>> expectedOutput = collectionWith(
                tuple(1, "A", true, 'a', 1.2, 1L, name("Adam"), colour("Amber"), age(20)),
                tuple(2, "B", false, 'b', 3.4, 2L, name("Barry"), colour("Blue"), age(30)));

        // When
        Collection<Nonuple<Integer, String, Boolean, Character, Double, Long, Name, Colour, Age>> actualOutput = Eagerly.zip(
                iterable1,
                iterable2,
                iterable3,
                iterable4,
                iterable5,
                iterable6,
                iterable7,
                iterable8,
                iterable9);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldZipIterableOfIterables() {
        // Given
        Iterable<Integer> iterable1 = iterableWith(1, 2, 3);
        Iterable<String> iterable2 = iterableWith("A", "B");
        Iterable<Boolean> iterable3 = iterableWith(true, false, true, false);
        Collection<Collection<?>> expectedOutput = Literals.<Collection<?>>collectionWith(
                collectionWith(1, "A", true),
                collectionWith(2, "B", false));

        // When
        Collection<Collection<?>> actualOutput = Eagerly.zip(
                iterableWith(
                        iterable1,
                        iterable2,
                        iterable3));

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }
}
