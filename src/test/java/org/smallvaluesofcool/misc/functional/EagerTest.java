package org.smallvaluesofcool.misc.functional;

import org.junit.Test;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;
import org.smallvaluesofcool.misc.functional.functors.DoFunction;
import org.smallvaluesofcool.misc.functional.functors.MapFunction;
import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;
import org.smallvaluesofcool.misc.functional.functors.ReduceFunction;

import java.util.Collection;
import java.util.Collections;
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
        assertThat(actual, equalTo(9L));
    }

    @Test
    public void shouldReduceToTheSameTypeUsingCustomReduceFunction() throws Exception {
        // Given
        List<List<Integer>> inputLists = listWith(
                listWith(1, 2, 3).build(),
                listWith(4, 5, 6).build(),
                listWith(7, 8, 9).build());

        // When
        List<Integer> actual = Eager.reduce(inputLists, new ReduceFunction<List<Integer>, List<Integer>>() {
            // Example flattening accumulator.
            public List<Integer> accumulate(List<Integer> accumulator, List<Integer> element) {
                accumulator.addAll(element);
                return accumulator;
            }
        });

        // Then
        assertThat(actual, hasItems(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test
    public void shouldBeAbleToReduceToATypeOtherThanThatOfTheInputElements() {
        // Given
        List<Integer> inputs = listWith(1, 2, 3);

        // When
        String output = Eager.reduce(inputs, "", new ReduceFunction<Integer, String>() {
            public String accumulate(String accumulator, Integer element) {
                accumulator += element.toString();
                return accumulator;
            }
        });

        // Then
        assertThat(output, is("123"));
    }

    @Test
    public void shouldSumTheSuppliedIntegers() {
        // Given
        List<Integer> inputs = listWith(1, 2, 3);

        // When
        Integer sum = Eager.sum(inputs);

        // Then
        assertThat(sum, is(6));
    }

    @Test
    public void shouldSumTheSuppliedLongs() {
        // Given
        List<Long> inputs = listWith(1L, 2L, 3L);

        // When
        Long sum = Eager.sum(inputs);

        // Then
        assertThat(sum, is(6L));
    }

    @Test
    public void shouldSumTheSuppliedDoubles() {
        // Given
        List<Double> inputs = listWith(1.6D, 2.2D, 3.5D);

        // When
        Double sum = Eager.sum(inputs);

        // Then
        assertThat(sum, is(closeTo(7.3D, 0.01D)));
    }

    @Test
    public void shouldSumTheSuppliedFloats() {
        // Given
        List<Float> inputs = listWith(1.6F, 2.2F, 3.5F);

        // When
        Float sum = Eager.sum(inputs);

        // Then
        assertThat(sum.doubleValue(), is(closeTo(7.3, 0.01)));
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
        Collection<Integer> actualOutputs = Eager.map(inputs, new MapFunction<Integer, Integer>() {
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

    @Test
    public void shouldOnlyReturnThoseElementsMatchingTheSuppliedPredicate() {
        // Given
        Iterable<Integer> inputs = listWith(1, 2, 3, 4, 5, 6);
        Collection<Integer> expectedOutput = listWith(2, 4, 6);

        // When
        Collection<Integer> actualOutput = Eager.filter(inputs, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldOnlyReturnThoseElementsThatDontMatchTheSuppliedPredicate() {
        // Given
        Iterable<Integer> inputs = listWith(1, 2, 3, 4, 5, 6);
        Collection<Integer> expectedOutput = listWith(1, 3, 5);

        // When
        Collection<Integer> actualOutput = Eager.reject(inputs, new PredicateFunction<Integer>() {
            @Override
            public boolean matches(Integer item) {
                return isEven(item);
            }

            private boolean isEven(Integer item) {
                return item % 2 == 0;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldTakeTheSpecifiedNumberOfElements() {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        Collection<String> expectedOutput = listWith("a", "b", "c", "d", "e");

        // When
        Collection<String> actualOutput = Eager.take(input, 5);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDropTheSpecifiedNumberOfElements() {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        Collection<String> expectedOutput = listWith("f", "g", "h", "i", "j");

        // When
        Collection<String> actualOutput = Eager.drop(input, 5);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnTheMatchingElementsFirstAndTheNonMatchingElementsSecond() {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h");
        Collection<String> expectedMatchingItems = listWith("a", "b", "c", "d");
        Collection<String> expectedNonMatchingItems = listWith("e", "f", "g", "h");

        // When
        TwoTuple<Collection<String>, Collection<String>> partitionResults = Eager.partition(input,
                new PredicateFunction<String>() {
                    public boolean matches(String item) {
                        return item.compareTo("e") < 0;
                    }
                });

        // Then
        Collection<String> actualMatchingItems = partitionResults.first();
        Collection<String> actualNonMatchingItems = partitionResults.second();

        assertThat(actualMatchingItems, is(expectedMatchingItems));
        assertThat(actualNonMatchingItems, is(expectedNonMatchingItems));
    }

    @Test
    public void shouldReturnTheElementsOfTheSuppliedIterableInBatchesOfTheSpecifiedSize() {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5);
        Collection<Integer> expectedFirstBatch = listWith(1, 2, 3);
        Collection<Integer> expectedSecondBatch = listWith(4, 5);
        Collection<Collection<Integer>> expectedBatches = listWith(expectedFirstBatch, expectedSecondBatch);

        // When
        Collection<Collection<Integer>> actualBatches = Eager.batch(input, 3);

        // Then
        assertThat(actualBatches, is(expectedBatches));
    }

    @Test
    public void shouldExecuteTheDoFunctionPassingInEachNaturalNumberUpToTheSpecifiedNumber() {
        // Given
        final Target<Integer> target = mock(Target.class);

        // When
        Eager.times(5, new DoFunction<Integer>(){
            public void actOn(Integer input) {
                target.doSomethingWith(input);
            }
        });

        // Then
        for (int i = 0; i < 5; i++) {
            verify(target).doSomethingWith(i);
        }
    }

    @Test
    public void shouldTakeElementsWhileTheSuppliedPredicateIsTrue() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 1, 2, 3);
        Collection<Integer> expectedOutput = listWith(1, 2, 1, 2);

        // When
        Collection<Integer> actualOutput = Eager.takeWhile(input, new PredicateFunction<Integer>(){
            public boolean matches(Integer item) {
                return item < 3;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldTakeElementsUntilTheSuppliedPredicateBecomesTrue() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5);
        Collection<Integer> expectedOutput = listWith(1, 2, 3);

        // When
        Collection<Integer> actualOutput = Eager.takeUntil(input, new PredicateFunction<Integer>(){
            public boolean matches(Integer item) {
                return item > 3;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDropElementsWhileTheSuppliedPredicateIsTrue() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aaa", "aaaa");
        Collection<String> expectedOutput = listWith("aaa", "aaaa");

        // When
        Collection<String> actualOutput = Eager.dropWhile(input, new PredicateFunction<String>(){
            @Override
            public boolean matches(String item) {
                return item.length() < 3;
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDropElementsUntilTheSuppliedPredicateBecomesTrue() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "aa", "aab", "aba", "aba");
        Collection<String> expectedOutput = listWith("aab", "aba", "aba");

        // When
        Collection<String> actualOutput = Eager.dropUntil(input, new PredicateFunction<String>() {
            @Override
            public boolean matches(String item) {
                return item.contains("b");
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldSliceTheSuppliedIterableAsSpecifiedByStartStopAndStep() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(8, 6, 4, 2);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 2, 9, 2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToZeroForStartIfNullSupplied() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(10, 8, 6);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, null, 6, 2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheEndOfTheIterableForStopIfNullSupplied() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(6, 4, 2);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 4, null, 2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToAStepSizeOfOneIfNullSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e");
        Collection<String> expectedOutput = listWith("b", "c", "d");

        // When
        Collection<String> actualOutput = Eager.slice(input, 1, 4, null);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnACollectionEqualToTheInputIterableIfAllIndicesAreNull() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, null, null, null);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfZeroSuppliedForStep() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e");

        // When
        Eager.slice(input, 1, 4, 0);

        // Then an IllegalArgumentException should be thrown
    }

    @Test
    public void shouldHaveADefaultStepSizeOfOne() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e");
        Collection<String> expectedOutput = listWith("b", "c", "d");

        // When
        Collection<String> actualOutput = Eager.slice(input, 1, 4);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    // Different behaviour to python
    @Test
    public void shouldWorkForwardFromTheEndOfTheIterableIfANegativeStartIsSupplied() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = listWith(8, 9, 10, 1, 2, 3);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, -3, 3);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    // Different behaviour to python
    @Test
    public void shouldWorkBackwardsIfStartIsLessThanStopAndStepIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = listWith(3, 2, 1, 10, 9, 8);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 2, 6, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldCountBackFromTheEndOfTheIterableIfANegativeStopIsSupplied() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = listWith(3, 4, 5, 6, 7, 8);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 2, -2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldWorkBackwardsIfStartIsGreaterThanStopAndStepIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = listWith(8, 6, 4, 2);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 7, 0, -2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldAllowNegativeStartWhenWorkingBackwardsWithANegativeStep() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = listWith(9, 8, 7, 6, 5, 4);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, -2, 2, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyListIfStartIsOutOfRange() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eager.slice(input, -15, 2, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    private interface Target<T> {
        void doSomething();
        void doSomethingWith(T input);
    }
}
