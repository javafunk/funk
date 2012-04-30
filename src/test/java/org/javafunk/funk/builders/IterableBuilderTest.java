package org.javafunk.funk.builders;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.builders.IterableBuilder.iterableBuilder;

public class IterableBuilderTest {
    @Test
    public void shouldAllowElementsToBeAddedToTheIterableWithWith() throws Exception {
        // Given
        IterableBuilder<String> iterableBuilder = iterableBuilder();
        Iterable<String> expected = asList("first", "second", "third", "fourth", "fifth", "sixth");

        // When
        Iterable<String> actual = iterableBuilder
                .with("first", "second", "third")
                .with("fourth", "fifth", "sixth")
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheIterableWithWith() throws Exception {
        // Given
        Iterable<Integer> expected = asList(5, 10, 15, 20, 25, 30);
        Integer[] firstElementArray = new Integer[]{5, 10, 15};
        Integer[] secondElementArray = new Integer[]{20, 25, 30};

        // When
        Iterable<Integer> actual = iterableBuilder(Integer.class)
                .with(firstElementArray)
                .with(secondElementArray)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheIterableWithWith() throws Exception {
        // Given
        IterableBuilder<Integer> iterableBuilder = iterableBuilder();
        Iterable<Integer> expected = asList(1, 2, 3, 4, 5, 6);
        Iterable<Integer> firstInputIterable = asList(1, 2, 3);
        Iterable<Integer> secondInputIterable = asList(4, 5, 6);

        // When
        Iterable<Integer> actual = iterableBuilder
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowElementsToBeAddedToTheIterableWithAnd() {
        // Given
        IterableBuilder<Integer> iterableBuilder = iterableBuilder();
        Iterable<Integer> expected = asList(5, 10, 15, 20, 25, 30, 35, 40, 45);

        // When
        Iterable<Integer> actual = iterableBuilder
                .with(5, 10, 15)
                .and(20, 25, 30)
                .and(35, 40, 45)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheIterableWithAnd() throws Exception {
        // Given
        IterableBuilder<Integer> iterabBuilder = iterableBuilder();
        Iterable<Integer> expected = asList(5, 10, 15, 20, 25, 30);
        Integer[] elementArray = new Integer[]{20, 25, 30};

        // When
        Iterable<Integer> actual = iterabBuilder
                .with(5, 10, 15)
                .and(elementArray)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheIterableWithAnd() throws Exception {
        // Given
        IterableBuilder<Integer> iterableBuilder = iterableBuilder();
        Iterable<Integer> expected = asList(1, 2, 3, 4, 5, 6);
        Iterable<Integer> someOtherElements = asList(4, 5, 6);

        // When
        Iterable<Integer> actual = iterableBuilder
                .with(1, 2, 3)
                .and(someOtherElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAddElementsCorrectlyWhenElementTypeIsIterable() throws Exception {
        // Given
        IterableBuilder<Iterable<Integer>> iterableBuilder = iterableBuilder();
        Iterable<Integer> firstElement = asList(1, 2);
        Iterable<Integer> secondElement = asList(3, 4, 5);
        Iterable<Integer> thirdElement = asList(6, 7, 8);
        Iterable<Integer> fourthElement = asList(9, 10);

        @SuppressWarnings("unchecked") Iterable<Iterable<Integer>> iterableOfElements = asList(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Iterable<Integer>[] arrayOfElements = new Iterable[]{fourthElement};
        @SuppressWarnings("unchecked") Iterable<Iterable<Integer>> expected = asList(firstElement, secondElement, thirdElement, fourthElement);
        
        // When
        Iterable<Iterable<Integer>> actual = iterableBuilder
                .with(firstElement)
                .and(iterableOfElements)
                .and(arrayOfElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAddElementsCorrectlyWhenElementTypeIsArray() throws Exception {
        // Given
        IterableBuilder<Integer[]> iterableBuilder = iterableBuilder();
        Integer[] firstElement = new Integer[]{1, 2};
        Integer[] secondElement = new Integer[]{3, 4, 5};
        Integer[] thirdElement = new Integer[]{6, 7, 8};
        Integer[] fourthElement = new Integer[]{9, 10};

        Iterable<Integer[]> iterableOfElements = asList(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Integer[][] arrayOfElements = new Integer[][]{fourthElement};
        Iterable<Integer[]> expected = asList(firstElement, secondElement, thirdElement, fourthElement);

        // When
        Iterable<Integer[]> actual = iterableBuilder
                .with(firstElement)
                .and(iterableOfElements)
                .and(arrayOfElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }
}
