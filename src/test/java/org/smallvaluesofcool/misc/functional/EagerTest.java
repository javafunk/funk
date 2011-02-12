package org.smallvaluesofcool.misc.functional;

import org.junit.Assert;
import org.junit.Test;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.Literals.twoTuple;

public class EagerTest {
    @Test
    public void shouldSumSuppliedInputUsingLongAccumulator() throws Exception {
        // Given
        Collection<Long> input = listWith(2L, 3L, 4L);

        // When
        Long actual = Eager.reduce(input, Eager.longAdditionAccumulator());

        // Then
        Assert.assertThat(actual, equalTo(9L));
    }

    @Test
    public void shouldReduceOtherTypesUsingCustomFunction() throws Exception {
        // Given
        List<List<Integer>> inputLists = listWith(
                listWith(1, 2, 3).build(),
                listWith(4, 5, 6).build(),
                listWith(7, 8, 9).build());

        // When
        List<Integer> actual = Eager.reduce(inputLists, new ReduceFunction<List<Integer>>() {
            // Example flattening accumulator.
            public List<Integer> accumulate(List<Integer> accumulator, List<Integer> element) {
                accumulator.addAll(element);
                return accumulator;
            }
        });

        // Then
        Assert.assertThat(actual, hasItems(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test
    public void shouldReturnTrueIfAnyElementsSatisfyThePredicateFunction() {
        // Given
        List<Integer> inputNumbers = listWith(5, 10, 15, 20);

        // When
        Boolean result = Eager.any(inputNumbers, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer item) {
                return item > 15;
            }
        });

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfNoElementsSatisfyThePredicateFunction() {
        // Given
        List<Integer> items = listWith(5, 10, 15, 20);

        // When
        Boolean result = Eager.any(items, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer item) {
                return item > 25;
            }
        });

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrueIfAllElementsSatisfyThePredicateFunction() {
        // Given
        List<String> items = listWith("dog", "cat", "fish", "budgie");

        // When
        Boolean result = Eager.all(items, new PredicateFunction<String>() {
            @Override
            public boolean matches(String item) {
                return item.length() > 2;
            }
        });

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfAnyOfTheElementsDoNotSatisyThePredicateFunction() {
        // Given
        List<String> items = listWith("dog", "cat", "fish", "budgie");

        // When
        Boolean result = Eager.all(items, new PredicateFunction<String>() {
            @Override
            public boolean matches(String item) {
                return item.length() > 3;
            }
        });

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrueIfNoneOfTheElementsMatchesThePredicateFunction() {
        // Given
        List<Integer> items = listWith(1, 3, 5, 7);

        // When
        Boolean result = Eager.none(items, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfAnyOfTheElementsMatchesTePredicateFunction() {
        // Given
        List<Integer> items = listWith(1, 3, 6, 7);

        // When
        Boolean result = Eager.none(items, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnMinValue() throws Exception {
        // Given
        List<String> list = listWith("a", "b", "c");

        // When
        String actual = Eager.min(list);

        // Then
        assertThat(actual, is("a"));
    }

    @Test
    public void shouldReturnMaxValue() throws Exception {
        // Given
        List<Integer> list = listWith(2, 6, 3);

        // When
        Integer actual = Eager.max(list);

        // Then
        assertThat(actual, is(6));
    }

    @Test
    public void shouldMapIterableUsingCustomMapFunction() {
        // Given
        Iterable<Integer> inputs = listWith(1, 2, 3);
        Collection<Integer> expectedOutputs = listWith(2, 4, 6);

        // When
        Collection<Integer> actualOutputs = Eager.map(inputs, new MapFunction<Integer, Integer>(){
            @Override
            public Integer map(Integer input) {
                return input * 2;
            }
        });

        // Then
        assertThat(actualOutputs, is(expectedOutputs));
    }

    @Test
    public void shouldZipIterables() {
        // Given
        Iterable<Integer> iterable1 = listWith(1, 2, 3);
        Iterable<String> iterable2 = listWith("First", "Second", "Third");
        Collection<TwoTuple<Integer, String>> expectedOutput = listWith(
                twoTuple(1, "First"),
                twoTuple(2, "Second"),
                twoTuple(3, "Third"));

        // When
        Collection<TwoTuple<Integer, String>> actualOutput = Eager.zip(iterable1, iterable2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldEnumerateIterable() {
        // Given
        Iterable<String> iterable = listWith("a", "b", "c");
        Collection<TwoTuple<Integer, String>> expectedOutput = listWith(
                twoTuple(0, "a"),
                twoTuple(1, "b"),
                twoTuple(2, "c"));

        // When
        Collection<TwoTuple<Integer, String>> actualOutput = Eager.enumerate(iterable);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldExecuteSuppliedFunctionOnEachElement() {
        // Given
        Iterable<Target> targets = listWith(mock(Target.class), mock(Target.class), mock(Target.class));

        // When
        Eager.each(targets, new DoFunction<Target>() {
            @Override
            public void actOn(Target input) {
                input.doSomething();
            }
        });

        // Then
        for(Target target : targets) {
            verify(target).doSomething();
        }
    }

    private interface Target {
        void doSomething();
    }
}
