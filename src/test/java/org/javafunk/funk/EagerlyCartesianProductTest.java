package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.*;
import org.javafunk.funk.testclasses.Age;
import org.javafunk.funk.testclasses.Colour;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.testclasses.Age.age;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Name.name;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;

public class EagerlyCartesianProductTest {
    @Test
    public void shouldReturnTheCartesianProductOfTwoIterablesAsACollectionOfPairs() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2, 3);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Collection<Pair<Integer, String>> expectedCartesianProduct = collectionWith(
                tuple(1, "a"), tuple(1, "b"), tuple(1, "c"),
                tuple(2, "a"), tuple(2, "b"), tuple(2, "c"),
                tuple(3, "a"), tuple(3, "b"), tuple(3, "c"));

        // When
        Collection<Pair<Integer, String>> actualCartesianProduct = Eagerly.cartesianProduct(input1, input2);

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfThreeIterablesAsACollectionOfTriples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2, 3);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Iterable<Boolean> input3 = iterableWith(true, false);
        Collection<Triple<Integer, String, Boolean>> expectedCartesianProduct =
                Literals.<Triple<Integer, String, Boolean>>collectionBuilder()
                        .with(tuple(1, "a", true )).with(tuple(1, "b", true )).with(tuple(1, "c", true ))
                        .with(tuple(2, "a", true )).with(tuple(2, "b", true )).with(tuple(2, "c", true ))
                        .with(tuple(3, "a", true )).with(tuple(3, "b", true )).with(tuple(3, "c", true ))
                        .with(tuple(1, "a", false)).with(tuple(1, "b", false)).with(tuple(1, "c", false))
                        .with(tuple(2, "a", false)).with(tuple(2, "b", false)).with(tuple(2, "c", false))
                        .with(tuple(3, "a", false)).with(tuple(3, "b", false)).with(tuple(3, "c", false))
                        .build();

        // When
        Collection<Triple<Integer, String, Boolean>> actualCartesianProduct = Eagerly.cartesianProduct(input1, input2, input3);

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfFourIterablesAsACollectionOfQuadruples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2, 3);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Iterable<Boolean> input3 = iterableWith(true, false);
        Iterable<Long> input4 = iterableWith(10L);
        Collection<Quadruple<Integer, String, Boolean, Long>> expectedCartesianProduct =
                Literals.<Quadruple<Integer, String, Boolean, Long>>collectionBuilder()
                        .with(tuple(1, "a", true,  10L)).with(tuple(1, "b", true,  10L)).with(tuple(1, "c", true,  10L))
                        .with(tuple(2, "a", true,  10L)).with(tuple(2, "b", true,  10L)).with(tuple(2, "c", true,  10L))
                        .with(tuple(3, "a", true,  10L)).with(tuple(3, "b", true,  10L)).with(tuple(3, "c", true,  10L))
                        .with(tuple(1, "a", false, 10L)).with(tuple(1, "b", false, 10L)).with(tuple(1, "c", false, 10L))
                        .with(tuple(2, "a", false, 10L)).with(tuple(2, "b", false, 10L)).with(tuple(2, "c", false, 10L))
                        .with(tuple(3, "a", false, 10L)).with(tuple(3, "b", false, 10L)).with(tuple(3, "c", false, 10L))
                        .build();

        // When
        Collection<Quadruple<Integer, String, Boolean, Long>> actualCartesianProduct =
                Eagerly.cartesianProduct(input1, input2, input3, input4);

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfFiveIterablesAsACollectionOfQuintuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2, 3);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Iterable<Boolean> input3 = iterableWith(true, false);
        Iterable<Long> input4 = iterableWith(10L);
        Iterable<Character> input5 = iterableWith('a', 'b');
        Collection<Quintuple<Integer, String, Boolean, Long, Character>> expectedCartesianProduct =
                Literals.<Quintuple<Integer, String, Boolean, Long, Character>>collectionBuilder()
                        .with(tuple(1, "a", true,  10L, 'a')).with(tuple(1, "b", true,  10L, 'a')).with(tuple(1, "c", true,  10L, 'a'))
                        .with(tuple(2, "a", true,  10L, 'a')).with(tuple(2, "b", true,  10L, 'a')).with(tuple(2, "c", true,  10L, 'a'))
                        .with(tuple(3, "a", true,  10L, 'a')).with(tuple(3, "b", true,  10L, 'a')).with(tuple(3, "c", true,  10L, 'a'))
                        .with(tuple(1, "a", false, 10L, 'a')).with(tuple(1, "b", false, 10L, 'a')).with(tuple(1, "c", false, 10L, 'a'))
                        .with(tuple(2, "a", false, 10L, 'a')).with(tuple(2, "b", false, 10L, 'a')).with(tuple(2, "c", false, 10L, 'a'))
                        .with(tuple(3, "a", false, 10L, 'a')).with(tuple(3, "b", false, 10L, 'a')).with(tuple(3, "c", false, 10L, 'a'))
                        .with(tuple(1, "a", true,  10L, 'b')).with(tuple(1, "b", true,  10L, 'b')).with(tuple(1, "c", true,  10L, 'b'))
                        .with(tuple(2, "a", true,  10L, 'b')).with(tuple(2, "b", true,  10L, 'b')).with(tuple(2, "c", true,  10L, 'b'))
                        .with(tuple(3, "a", true,  10L, 'b')).with(tuple(3, "b", true,  10L, 'b')).with(tuple(3, "c", true,  10L, 'b'))
                        .with(tuple(1, "a", false, 10L, 'b')).with(tuple(1, "b", false, 10L, 'b')).with(tuple(1, "c", false, 10L, 'b'))
                        .with(tuple(2, "a", false, 10L, 'b')).with(tuple(2, "b", false, 10L, 'b')).with(tuple(2, "c", false, 10L, 'b'))
                        .with(tuple(3, "a", false, 10L, 'b')).with(tuple(3, "b", false, 10L, 'b')).with(tuple(3, "c", false, 10L, 'b'))
                        .build();

        // When
        Collection<Quintuple<Integer, String, Boolean, Long, Character>> actualCartesianProduct =
                Eagerly.cartesianProduct(input1, input2, input3, input4, input5);

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfSixIterablesAsACollectionOfSextuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Iterable<Boolean> input3 = iterableWith(true, false);
        Iterable<Long> input4 = iterableWith(10L);
        Iterable<Character> input5 = iterableWith('a', 'b');
        Iterable<Double> input6 = iterableWith(1.2);
        Collection<Sextuple<Integer, String, Boolean, Long, Character, Double>> expectedCartesianProduct =
                Literals.<Sextuple<Integer, String, Boolean, Long, Character, Double>>collectionBuilder()
                        .with(tuple(1, "a", true,  10L, 'a', 1.2)).with(tuple(1, "b", true,  10L, 'a', 1.2)).with(tuple(1, "c", true,  10L, 'a', 1.2))
                        .with(tuple(2, "a", true,  10L, 'a', 1.2)).with(tuple(2, "b", true,  10L, 'a', 1.2)).with(tuple(2, "c", true,  10L, 'a', 1.2))
                        .with(tuple(1, "a", false, 10L, 'a', 1.2)).with(tuple(1, "b", false, 10L, 'a', 1.2)).with(tuple(1, "c", false, 10L, 'a', 1.2))
                        .with(tuple(2, "a", false, 10L, 'a', 1.2)).with(tuple(2, "b", false, 10L, 'a', 1.2)).with(tuple(2, "c", false, 10L, 'a', 1.2))
                        .with(tuple(1, "a", true,  10L, 'b', 1.2)).with(tuple(1, "b", true,  10L, 'b', 1.2)).with(tuple(1, "c", true,  10L, 'b', 1.2))
                        .with(tuple(2, "a", true,  10L, 'b', 1.2)).with(tuple(2, "b", true,  10L, 'b', 1.2)).with(tuple(2, "c", true,  10L, 'b', 1.2))
                        .with(tuple(1, "a", false, 10L, 'b', 1.2)).with(tuple(1, "b", false, 10L, 'b', 1.2)).with(tuple(1, "c", false, 10L, 'b', 1.2))
                        .with(tuple(2, "a", false, 10L, 'b', 1.2)).with(tuple(2, "b", false, 10L, 'b', 1.2)).with(tuple(2, "c", false, 10L, 'b', 1.2))
                        .build();

        // When
        Collection<Sextuple<Integer, String, Boolean, Long, Character, Double>> actualCartesianProduct =
                Eagerly.cartesianProduct(input1, input2, input3, input4, input5, input6);

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfSevenIterablesAsACollectionOfSeptuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2);
        Iterable<String> input2 = iterableWith("a", "b");
        Iterable<Boolean> input3 = iterableWith(true, false);
        Iterable<Long> input4 = iterableWith(10L);
        Iterable<Character> input5 = iterableWith('a', 'b');
        Iterable<Double> input6 = iterableWith(1.2);
        Iterable<Name> input7 = iterableWith(name("Adam"));
        Collection<Septuple<Integer, String, Boolean, Long, Character, Double, Name>> expectedCartesianProduct =
                Literals.<Septuple<Integer, String, Boolean, Long, Character, Double, Name>>collectionBuilder()
                        .with(tuple(1, "a", true,  10L, 'a', 1.2, name("Adam"))).with(tuple(1, "b", true,  10L, 'a', 1.2, name("Adam")))
                        .with(tuple(2, "a", true,  10L, 'a', 1.2, name("Adam"))).with(tuple(2, "b", true,  10L, 'a', 1.2, name("Adam")))
                        .with(tuple(1, "a", false, 10L, 'a', 1.2, name("Adam"))).with(tuple(1, "b", false, 10L, 'a', 1.2, name("Adam")))
                        .with(tuple(2, "a", false, 10L, 'a', 1.2, name("Adam"))).with(tuple(2, "b", false, 10L, 'a', 1.2, name("Adam")))
                        .with(tuple(1, "a", true,  10L, 'b', 1.2, name("Adam"))).with(tuple(1, "b", true,  10L, 'b', 1.2, name("Adam")))
                        .with(tuple(2, "a", true,  10L, 'b', 1.2, name("Adam"))).with(tuple(2, "b", true,  10L, 'b', 1.2, name("Adam")))
                        .with(tuple(1, "a", false, 10L, 'b', 1.2, name("Adam"))).with(tuple(1, "b", false, 10L, 'b', 1.2, name("Adam")))
                        .with(tuple(2, "a", false, 10L, 'b', 1.2, name("Adam"))).with(tuple(2, "b", false, 10L, 'b', 1.2, name("Adam")))
                        .build();

        // When
        Collection<Septuple<Integer, String, Boolean, Long, Character, Double, Name>> actualCartesianProduct =
                Eagerly.cartesianProduct(input1, input2, input3, input4, input5, input6, input7);

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfEightIterablesAsACollectionOfOctuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2);
        Iterable<String> input2 = iterableWith("a", "b");
        Iterable<Boolean> input3 = iterableWith(true, false);
        Iterable<Long> input4 = iterableWith(10L);
        Iterable<Character> input5 = iterableWith('a', 'b');
        Iterable<Double> input6 = iterableWith(1.2);
        Iterable<Name> input7 = iterableWith(name("Adam"));
        Iterable<Age> input8 = iterableWith(age(20), age(30));
        Collection<Octuple<Integer, String, Boolean, Long, Character, Double, Name, Age>> expectedCartesianProduct =
                Literals.<Octuple<Integer, String, Boolean, Long, Character, Double, Name, Age>>collectionBuilder()
                        .with(tuple(1, "a", true,  10L, 'a', 1.2, name("Adam"), age(20)))
                        .with(tuple(1, "b", true, 10L, 'a', 1.2, name("Adam"), age(20)))
                        .with(tuple(2, "a", true,  10L, 'a', 1.2, name("Adam"), age(20)))
                        .with(tuple(2, "b", true, 10L, 'a', 1.2, name("Adam"), age(20)))
                        .with(tuple(1, "a", false, 10L, 'a', 1.2, name("Adam"), age(20)))
                        .with(tuple(1, "b", false, 10L, 'a', 1.2, name("Adam"), age(20)))
                        .with(tuple(2, "a", false, 10L, 'a', 1.2, name("Adam"), age(20)))
                        .with(tuple(2, "b", false, 10L, 'a', 1.2, name("Adam"), age(20)))
                        .with(tuple(1, "a", true,  10L, 'b', 1.2, name("Adam"), age(20)))
                        .with(tuple(1, "b", true, 10L, 'b', 1.2, name("Adam"), age(20)))
                        .with(tuple(2, "a", true,  10L, 'b', 1.2, name("Adam"), age(20)))
                        .with(tuple(2, "b", true, 10L, 'b', 1.2, name("Adam"), age(20)))
                        .with(tuple(1, "a", false, 10L, 'b', 1.2, name("Adam"), age(20)))
                        .with(tuple(1, "b", false, 10L, 'b', 1.2, name("Adam"), age(20)))
                        .with(tuple(2, "a", false, 10L, 'b', 1.2, name("Adam"), age(20)))
                        .with(tuple(2, "b", false, 10L, 'b', 1.2, name("Adam"), age(20)))
                        .with(tuple(1, "a", true,  10L, 'a', 1.2, name("Adam"), age(30)))
                        .with(tuple(1, "b", true, 10L, 'a', 1.2, name("Adam"), age(30)))
                        .with(tuple(2, "a", true, 10L, 'a', 1.2, name("Adam"), age(30)))
                        .with(tuple(2, "b", true, 10L, 'a', 1.2, name("Adam"), age(30)))
                        .with(tuple(1, "a", false, 10L, 'a', 1.2, name("Adam"), age(30)))
                        .with(tuple(1, "b", false, 10L, 'a', 1.2, name("Adam"), age(30)))
                        .with(tuple(2, "a", false, 10L, 'a', 1.2, name("Adam"), age(30)))
                        .with(tuple(2, "b", false, 10L, 'a', 1.2, name("Adam"), age(30)))
                        .with(tuple(1, "a", true, 10L, 'b', 1.2, name("Adam"), age(30)))
                        .with(tuple(1, "b", true, 10L, 'b', 1.2, name("Adam"), age(30)))
                        .with(tuple(2, "a", true, 10L, 'b', 1.2, name("Adam"), age(30)))
                        .with(tuple(2, "b", true, 10L, 'b', 1.2, name("Adam"), age(30)))
                        .with(tuple(1, "a", false, 10L, 'b', 1.2, name("Adam"), age(30)))
                        .with(tuple(1, "b", false, 10L, 'b', 1.2, name("Adam"), age(30)))
                        .with(tuple(2, "a", false, 10L, 'b', 1.2, name("Adam"), age(30)))
                        .with(tuple(2, "b", false, 10L, 'b', 1.2, name("Adam"), age(30)))
                        .build();

        // When
        Collection<Octuple<Integer, String, Boolean, Long, Character, Double, Name, Age>> actualCartesianProduct =
                Eagerly.cartesianProduct(input1, input2, input3, input4, input5, input6, input7, input8);

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

@Test
    public void shouldReturnTheCartesianProductOfNineIterablesAsACollectionOfNonuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2);
        Iterable<String> input2 = iterableWith("a", "b");
        Iterable<Boolean> input3 = iterableWith(true, false);
        Iterable<Long> input4 = iterableWith(10L);
        Iterable<Character> input5 = iterableWith('a', 'b');
        Iterable<Double> input6 = iterableWith(1.2);
        Iterable<Name> input7 = iterableWith(name("Adam"));
        Iterable<Age> input8 = iterableWith(age(20), age(30));
        Iterable<Colour> input9 = iterableWith(colour("Amber"));
        Collection<Nonuple<Integer, String, Boolean, Long, Character, Double, Name, Age, Colour>> expectedCartesianProduct =
                Literals.<Nonuple<Integer, String, Boolean, Long, Character, Double, Name, Age, Colour>>collectionBuilder()
                        .with(tuple(1, "a", true,  10L, 'a', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(1, "b", true,  10L, 'a', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(2, "a", true,  10L, 'a', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(2, "b", true,  10L, 'a', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(1, "a", false, 10L, 'a', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(1, "b", false, 10L, 'a', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(2, "a", false, 10L, 'a', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(2, "b", false, 10L, 'a', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(1, "a", true,  10L, 'b', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(1, "b", true,  10L, 'b', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(2, "a", true,  10L, 'b', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(2, "b", true,  10L, 'b', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(1, "a", false, 10L, 'b', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(1, "b", false, 10L, 'b', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(2, "a", false, 10L, 'b', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(2, "b", false, 10L, 'b', 1.2, name("Adam"), age(20), colour("Amber")))
                        .with(tuple(1, "a", true,  10L, 'a', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(1, "b", true,  10L, 'a', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(2, "a", true,  10L, 'a', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(2, "b", true,  10L, 'a', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(1, "a", false, 10L, 'a', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(1, "b", false, 10L, 'a', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(2, "a", false, 10L, 'a', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(2, "b", false, 10L, 'a', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(1, "a", true,  10L, 'b', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(1, "b", true,  10L, 'b', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(2, "a", true,  10L, 'b', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(2, "b", true,  10L, 'b', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(1, "a", false, 10L, 'b', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(1, "b", false, 10L, 'b', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(2, "a", false, 10L, 'b', 1.2, name("Adam"), age(30), colour("Amber")))
                        .with(tuple(2, "b", false, 10L, 'b', 1.2, name("Adam"), age(30), colour("Amber")))
                        .build();

        // When
        Collection<Nonuple<Integer, String, Boolean, Long, Character, Double, Name, Age, Colour>> actualCartesianProduct =
                Eagerly.cartesianProduct(input1, input2, input3, input4, input5, input6, input7, input8, input9);

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }
}
