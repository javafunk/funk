package org.javafunk.funk.builders;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.builders.ArrayBuilder.arrayBuilder;

public class ArrayBuilderTest {
    @Test
    public void shouldAllowElementsToBeAddedToTheArrayWithWith() throws Exception {
        // Given
        ArrayBuilder<String> arrayBuilder = arrayBuilder();
        String[] expected = new String[]{"first", "second", "third", "fourth", "fifth", "sixth"};

        // When
        String[] actual = arrayBuilder
                .with("first", "second", "third")
                .with("fourth", "fifth", "sixth")
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheArrayWithWith() throws Exception {
        // Given
        ArrayBuilder<Integer> arrayBuilder = arrayBuilder();
        Integer[] expected = new Integer[]{5, 10, 15, 20, 25, 30};
        Integer[] firstElementArray = new Integer[]{5, 10, 15};
        Integer[] secondElementArray = new Integer[]{20, 25, 30};

        // When
        Integer[] actual = arrayBuilder
                .with(firstElementArray)
                .with(secondElementArray)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheArrayWithWith() throws Exception {
        // Given
        ArrayBuilder<Integer> arrayBuilder = arrayBuilder();
        Integer[] expected = new Integer[]{1, 2, 3, 4, 5, 6};
        Iterable<Integer> firstInputIterable = asList(1, 2, 3);
        Iterable<Integer> secondInputIterable = asList(4, 5, 6);

        // When
        Integer[] actual = arrayBuilder
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowElementsToBeAddedToTheArrayWithAnd() {
        // Given
        ArrayBuilder<Integer> arrayBuilder = arrayBuilder();
        Integer[] expected = new Integer[]{5, 10, 15, 20, 25, 30, 35, 40, 45};

        // When
        Integer[] actual = arrayBuilder
                .with(5, 10, 15)
                .and(20, 25, 30)
                .and(35, 40, 45)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheArrayWithAnd() throws Exception {
        // Given
        ArrayBuilder<Integer> arrayBuilder = arrayBuilder();
        Integer[] expected = new Integer[]{5, 10, 15, 20, 25, 30};
        Integer[] elementArray = new Integer[]{20, 25, 30};

        // When
        Integer[] actual = arrayBuilder
                .with(5, 10, 15)
                .and(elementArray)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheArrayWithAnd() throws Exception {
        // Given
        ArrayBuilder<Integer> arrayBuilder = arrayBuilder();
        Integer[] expected = new Integer[]{1, 2, 3, 4, 5, 6};
        Iterable<Integer> someOtherElements = asList(4, 5, 6);

        // When
        Integer[] actual = arrayBuilder
                .with(1, 2, 3)
                .and(someOtherElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAddElementsCorrectlyWhenElementTypeIsIterable() throws Exception {
        // Given
        ArrayBuilder<Iterable<Integer>> arrayBuilder = arrayBuilder();
        Iterable<Integer> firstElement = asList(1, 2);
        Iterable<Integer> secondElement = asList(3, 4, 5);
        Iterable<Integer> thirdElement = asList(6, 7, 8);
        Iterable<Integer> fourthElement = asList(9, 10);

        @SuppressWarnings("unchecked") Iterable<Iterable<Integer>> iterableOfElements = asList(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Iterable<Integer>[] arrayOfElements = new Iterable[]{fourthElement};
        @SuppressWarnings("unchecked") Iterable<Integer>[] expected = new Iterable[]{firstElement, secondElement, thirdElement, fourthElement};

        // When
        Iterable[] actual = arrayBuilder
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
        ArrayBuilder<Integer[]> arrayBuilder = arrayBuilder();
        Integer[] firstElement = new Integer[]{1, 2};
        Integer[] secondElement = new Integer[]{3, 4, 5};
        Integer[] thirdElement = new Integer[]{6, 7, 8};
        Integer[] fourthElement = new Integer[]{9, 10};

        Iterable<Integer[]> iterableOfElements = asList(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Integer[][] arrayOfElements = new Integer[][]{fourthElement};
        Integer[][] expected = new Integer[][]{firstElement, secondElement, thirdElement, fourthElement};

        // When
        Integer[][] actual = arrayBuilder
                .with(firstElement)
                .and(iterableOfElements)
                .and(arrayOfElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }
}
