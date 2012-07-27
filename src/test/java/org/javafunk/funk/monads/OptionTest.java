/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.monads;

import org.hamcrest.Matcher;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.NullaryFunction;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.matchers.SelfDescribingPredicate;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.matchers.IterableMatchers.hasAllElementsSatisfying;
import static org.javafunk.funk.monads.Option.*;

public class OptionTest {
    @Test
    public void shouldReturnSomeOfValueForOptionIfValueNotNull() throws Exception {
        // Given
        String value = "value";
        Option<String> expected = some(value);

        // When
        Option<String> actual = option(value);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnNoneForOptionIfValueNull() throws Exception {
        // Given
        String value = null;
        Option<String> expected = none();

        // When
        Option<String> actual = option(value);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldInferTypeFromSuppliedClassWhenClassPassedToNone() throws Exception {
        // Given
        Option<String> expected = none();

        // Then
        assertThat(none(String.class), is(expected));
    }

    @Test
    public void shouldNotHaveValueIfNone() throws Exception {
        // Given
        Option<Integer> option = none();

        // When
        Boolean hasValue = option.hasValue();

        // Then
        assertThat(hasValue, is(false));
    }

    @Test
    public void shouldHaveNoValueIfNone() throws Exception {
        // Given
        Option<Integer> option = none();

        // When
        Boolean hasNoValue = option.hasNoValue();

        // Then
        assertThat(hasNoValue, is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionForGetOnNone() throws Exception {
        // Given
        Option<Integer> option = none();

        // When
        option.get();

        // Then throw
    }

    @Test
    public void shouldReturnOtherWhenOrCalledOnNoneIfSuppliedWithNonNullOption() throws Exception {
        // Given
        Option<String> initial = none();
        Option<String> expected = some("other");

        // When
        Option<String> actual = initial.or(some("other"));

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenOrCalledOnNoneIfSuppliedWithNullOption() throws Exception {
        // Given
        Option<String> initial = none();

        // When
        initial.or(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnSomeOfSuppliedValueWhenOrSomeCalledOnNone() throws Exception {
        // Given
        Option<String> initial = none();
        Option<String> expected = some("other");

        // When
        Option<String> actual = initial.orSome("other");

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnSomeOfNullWhenOrSomeCalledOnNoneWithNull() throws Exception {
        // Given
        Option<String> initial = none();
        Option<String> expected = some(null);

        // When
        Option<String> actual = initial.orSome(null);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnOptionOfSuppliedValueWhenOrOptionCalledOnNone() throws Exception {
        // Given
        Option<Integer> initial = none();
        Option<Integer> expected = option(15);

        // When
        Option<Integer> actual = initial.orOption(15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnOptionOfNullWhenOrOptionCalledOnNoneWithNull() throws Exception {
        // Given
        Option<Integer> initial = none();
        Option<Integer> expected = option(null);

        // When
        Option<Integer> actual = initial.orOption(null);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = Throwable.class)
    public void shouldThrowSuppliedExceptionIfGetOrThrowCalledOnNone() throws Throwable {
        // Given
        Throwable throwable = new Throwable();
        Option<String> option = none();

        // When
        option.getOrThrow(throwable);

        // Then the throwable is thrown
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrThrowCalledOnNoneWithNull() throws Throwable {
        // Given
        Option<String> option = none();

        // When
        option.getOrThrow(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnSuppliedValueIfGetOrElseCalledOnNone() throws Exception {
        // Given
        Option<String> option = none();
        String expected = "else";

        // When
        String actual = option.getOrElse("else");

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrElseCalledOnNoneWithNull() throws Exception {
        // Given
        Option<String> option = none();

        // When
        option.getOrElse(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnNullIfGetOrNullCalledOnNone() throws Exception {
        // Given
        Option<String> option = none();
        String expected = null;

        // When
        String actual = option.getOrNull();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnTheResultOfCallingTheSuppliedCallableIfGetOrCallCalledOnNone() throws Exception {
        // Given
        Option<String> option = none();
        TrackingCallable callable = new TrackingCallable("call result");
        String expected = "call result";

        // When
        String actual = option.getOrCall(callable);

        // Then
        assertThat(actual, is(expected));
        assertThat(callable.wasCalled(), is(true));
    }

    @Test
    public void shouldReturnTheResultOfCallingTheSuppliedNullaryFunctionIfGetOrCallCalledOnNone() throws Exception {
        // Given
        Option<String> option = none();
        TrackingNullaryFunction<String> function = new TrackingNullaryFunction<String>("call result");
        String expected = "call result";

        // When
        String actual = option.getOrCall(function);

        // Then
        assertThat(actual, is(expected));
        assertThat(function.wasCalled(), is(true));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrCallIsCalledOnNoneWithNullCallable() throws Exception {
        // Given
        Option<String> option = none();
        TrackingCallable callable = null;

        // When
        option.getOrCall(callable);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrCallIsCalledOnNoneWithNullNullaryFunction() throws Exception {
        // Given
        Option<String> option = none();
        NullaryFunction<String> callable = null;

        // When
        option.getOrCall(callable);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnNoneOverTheMappedTypeIfMapCalledOnNone() throws Exception {
        // Given
        Option<String> option = none();
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
        Option<Integer> expected = none();

        // When
        Option<Integer> actual = option.map(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfMapCalledOnNoneWithNull() throws Exception {
        // Given
        Option<String> option = none();
        Mapper<String, Integer> mapper = null;

        // When
        option.map(mapper);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnNoneOverTheTypeOfTheMappedOptionIfFlatMapCalledOnNone() throws Exception {
        // Given
        Option<String> option = none();
        Mapper<String, Option<Integer>> mapper = new Mapper<String, Option<Integer>>() {
            @Override public Option<Integer> map(String input) {
                return some(input.length());
            }
        };
        Option<Integer> expected = none();

        // When
        Option<Integer> actual = option.flatMap(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfFlatMapCalledOnNoneWithNull() throws Exception {
        // Given
        Option<String> option = none();
        Mapper<String, Option<Integer>> mapper = null;

        // When
        option.flatMap(mapper);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldHaveValueIfSome() throws Exception {
        // Given
        Option<Integer> option = some(15);

        // When
        Boolean hasValue = option.hasValue();

        // Then
        assertThat(hasValue, is(true));
    }

    @Test
    public void shouldNotHaveNoValueIfSome() throws Exception {
        // Given
        Option<Integer> option = some(15);

        // When
        Boolean hasNoValue = option.hasNoValue();

        // Then
        assertThat(hasNoValue, is(false));
    }

    @Test
    public void shouldReturnValueForSome() throws Exception {
        // Given
        Option<Integer> option = some(1);

        // When
        Integer integer = option.get();

        // Then
        assertThat(integer, equalTo(1));
    }

    @Test
    public void shouldReturnSomeWhenOrCalledOnSome() throws Exception {
        // Given
        Option<String> initial = some("thing");
        Option<String> expected = some("thing");

        // When
        Option<String> actual = initial.or(some("other"));

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenOrCalledOnSomeIfSuppliedWithNullOption() throws Exception {
        // Given
        Option<String> initial = some("thing");

        // When
        initial.or(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnSelfWhenOrSomeCalledOnSome() throws Exception {
        // Given
        Option<String> initial = some("thing");
        Option<String> expected = some("thing");

        // When
        Option<String> actual = initial.orSome("other");

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnSelfWhenOrSomeCalledOnSomeWithNull() throws Exception {
        // Given
        Option<String> initial = some("thing");
        Option<String> expected = some("thing");

        // When
        Option<String> actual = initial.orSome(null);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnSelfWhenOrOptionCalledOnSome() throws Exception {
        // Given
        Option<Integer> initial = some(10);
        Option<Integer> expected = some(10);

        // When
        Option<Integer> actual = initial.orOption(15);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnSelfWhenOrOptionCalledOnSomeWithNull() throws Exception {
        // Given
        Option<Integer> initial = some(10);
        Option<Integer> expected = some(10);

        // When
        Option<Integer> actual = initial.orOption(null);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnValueIfGetOrThrowCalledOnSome() throws Throwable {
        // Given
        Throwable throwable = new Throwable();
        String expected = "thing";
        Option<String> option = some(expected);

        // When
        String actual = option.getOrThrow(throwable);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrThrowCalledOnSomeWithNull() throws Throwable {
        // Given
        Option<String> option = some("thing");

        // When
        option.getOrThrow(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnResultOfGetIfGetOrElseCalledOnSome() throws Exception {
        // Given
        Option<String> option = some("thing");
        String expected = "thing";

        // When
        String actual = option.getOrElse("else");

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrElseCalledOnSomeWithNull() throws Exception {
        // Given
        Option<String> option = some("thing");

        // When
        option.getOrElse(null);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnResultOfCallingGetIfGetOrNullCalledOnSome() throws Exception {
        // Given
        Option<String> option = some("thing");
        String expected = "thing";

        // When
        String actual = option.getOrNull();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnTheResultOfCallingGetIfGetOrCallCalledOnSomeWhenSuppliedCallable() throws Exception {
        // Given
        Option<String> option = some("thing");
        TrackingCallable callable = new TrackingCallable("call result");
        String expected = "thing";

        // When
        String actual = option.getOrCall(callable);

        // Then
        assertThat(actual, is(expected));
        assertThat(callable.wasCalled(), is(false));
    }

    @Test
    public void shouldReturnTheResultOfCallingGetIfGetOrCallCalledOnSomeWhenSuppliedNullaryFunction() throws Exception {
        // Given
        Option<String> option = some("thing");
        TrackingNullaryFunction<String> function = new TrackingNullaryFunction<String>("call result");
        String expected = "thing";

        // When
        String actual = option.getOrCall(function);

        // Then
        assertThat(actual, is(expected));
        assertThat(function.wasCalled(), is(false));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrCallIsCalledOnSomeWithNullCallable() throws Exception {
        // Given
        Option<String> option = some("thing");
        TrackingCallable callable = null;

        // When
        option.getOrCall(callable);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfGetOrCallIsCalledOnSomeWithNullNullaryFunction() throws Exception {
        // Given
        Option<String> option = some("thing");
        NullaryFunction<String> function = null;

        // When
        option.getOrCall(function);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnSomeOverTheReturnValueOfTheSuppliedMapperIfMapCalledOnSome() throws Exception {
        // Given
        Option<String> option = some("string");
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
        Option<Integer> expected = some(6);

        // When
        Option<Integer> actual = option.map(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnSomeOverNullIfMapCalledOnSomeAndMapperReturnsNull() throws Exception {
        // Given
        Option<String> option = some("string");
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return null;
            }
        };
        Option<Integer> expected = some(null);

        // When
        Option<Integer> actual = option.map(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfMapCalledOnSomeWithNull() throws Exception {
        // Given
        Option<String> option = some("string");
        Mapper<String, Integer> mapper = null;

        // When
        option.map(mapper);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldMapSomeUsingUnaryFunction() throws Exception {
        // Given
        Option<String> option = some("string");
        UnaryFunction<String, Integer> function = new UnaryFunction<String, Integer>() {
            @Override public Integer call(String string) {
                return string.length();
            }
        };
        Option<Integer> expected = some(6);

        // When
        Option<Integer> actual = option.map(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnNoneIfMappingNoneWithUnaryFunction() throws Exception {
        // Given
        Option<String> option = none();
        UnaryFunction<String, Integer> function = new UnaryFunction<String, Integer>() {
            @Override public Integer call(String string) {
                return string.length();
            }
        };
        Option<Integer> expected = none();

        // When
        Option<Integer> actual = option.map(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfMappingUsingNullUnaryFunction() throws Exception {
        // Given
        Option<String> option = some("string");
        UnaryFunction<String, Option<Integer>> function = null;

        // When
        option.map(function);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnTheReturnValueOfTheSuppliedMapperIfFlatMapCalledOnSome() throws Exception {
        // Given
        Option<String> option = some("string");
        Mapper<String, Option<Integer>> mapper = new Mapper<String, Option<Integer>>() {
            @Override public Option<Integer> map(String input) {
                return some(input.length());
            }
        };
        Option<Integer> expected = some(6);

        // When
        Option<Integer> actual = option.flatMap(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfFlatMapCalledOnSomeWithNull() throws Exception {
        // Given
        Option<String> option = none();
        Mapper<String, Option<Integer>> mapper = null;

        // When
        option.flatMap(mapper);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldFlatMapSomeUsingUnaryFunction() throws Exception {
        // Given
        Option<String> option = some("string");
        UnaryFunction<String, Option<Integer>> function = new UnaryFunction<String, Option<Integer>>() {
            @Override public Option<Integer> call(String input) {
                return some(input.length());
            }
        };
        Option<Integer> expected = some(6);

        // When
        Option<Integer> actual = option.flatMap(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnNoneIfFlatMappingNoneWithUnaryFunction() throws Exception {
        // Given
        Option<String> option = none();
        UnaryFunction<String, Option<Integer>> function = new UnaryFunction<String, Option<Integer>>() {
            @Override public Option<Integer> call(String string) {
                return some(string.length());
            }
        };
        Option<Integer> expected = none();

        // When
        Option<Integer> actual = option.flatMap(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfFlatMappingUsingNullUnaryFunction() throws Exception {
        // Given
        Option<String> option = some("string");
        UnaryFunction<String, Option<Integer>> function = null;

        // When
        option.flatMap(function);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldBeEqualIfBothNoneOverSameType() throws Exception {
        // Given
        Option<String> firstNone = none();
        Option<String> secondNone = none();

        // When
        Boolean firstEqualsSecond = firstNone.equals(secondNone);
        Boolean secondEqualsFirst = secondNone.equals(firstNone);

        // Then
        assertThat(iterableWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(true));
    }

    @Test
    public void shouldBeEqualIfBothNoneButOverDifferentTypes() throws Exception {
        // Given
        Option<String> firstNone = none();
        Option<Integer> secondNone = none();

        // When
        Boolean firstEqualsSecond = firstNone.equals(secondNone);
        Boolean secondEqualsFirst = secondNone.equals(firstNone);

        // Then
        assertThat(iterableWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(true));
    }

    @Test
    public void shouldBeEqualIfEquatingSomesWithTheSameValue() throws Exception {
        // Given
        Option<Integer> firstOption = some(12);
        Option<Integer> secondOption = some(12);

        // When
        Boolean firstEqualsSecond = firstOption.equals(secondOption);
        Boolean secondEqualsFirst = secondOption.equals(firstOption);

        // Then
        assertThat(iterableWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(true));
    }

    @Test
    public void shouldBeEqualIfEquatingSomesWithNullValue() throws Exception {
        // Given
        Option<Integer> firstOption = some(null);
        Option<Integer> secondOption = some(null);

        // When
        Boolean firstEqualsSecond = firstOption.equals(secondOption);
        Boolean secondEqualsFirst = secondOption.equals(firstOption);

        // Then
        assertThat(iterableWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(true));
    }

    @Test
    public void shouldNotBeEqualIfEquatingSomeAndNone() throws Exception {
        // Given
        Option<Integer> firstOption = some(12);
        Option<Integer> secondOption = none();

        // When
        Boolean firstEqualsSecond = firstOption.equals(secondOption);
        Boolean secondEqualsFirst = secondOption.equals(firstOption);

        // Then
        assertThat(iterableWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(false));
    }

    @Test
    public void shouldNotBeEqualIfEquatingSomesWithDifferentValues() throws Exception {
        // Given
        Option<Integer> firstOption = some(12);
        Option<Integer> secondOption = some(15);

        // When
        Boolean firstEqualsSecond = firstOption.equals(secondOption);
        Boolean secondEqualsFirst = secondOption.equals(firstOption);

        // Then
        assertThat(iterableWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(false));
    }

    @Test
    public void shouldNotBeEqualIfEquatingSomeWithNullValueToNone() throws Exception {
        // Given
        Option<Integer> firstOption = some(null);
        Option<Integer> secondOption = none();

        // When
        Boolean firstEqualsSecond = firstOption.equals(secondOption);
        Boolean secondEqualsFirst = secondOption.equals(firstOption);

        // Then
        assertThat(iterableWith(firstEqualsSecond, secondEqualsFirst), hasAllElementsEqualTo(false));
    }

    @Test
    public void shouldBeEmptyIterableIfNone() throws Exception {
        // Given
        Option<Integer> option = none();

        // When
        Iterator<Integer> iterator = option.iterator();

        // Then
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldBeIterableOverValueIfSome() throws Exception {
        // Given
        Option<Integer> option = some(10);

        // When
        Iterator<Integer> iterator = option.iterator();

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(10));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldBeReadOnlyIterableIfSome() throws Exception {
        // Given
        Option<Integer> option = some(10);
        Iterator<Integer> iterator = option.iterator();
        iterator.next();

        // When
        iterator.remove();

        // Then an UnsupportedOperationException is thrown.
    }

    private static class TrackingNullaryFunction<R> implements NullaryFunction<R> {
        private final R result;

        private Boolean wasCalled = false;

        private TrackingNullaryFunction(R result) {
            this.result = result;
        }

        @Override public R call() {
            this.wasCalled = true;
            return result;
        }

        public Boolean wasCalled() {
            return wasCalled;
        }
    }

    private static class TrackingCallable implements Callable<String> {
        private final String callResult;

        private Boolean wasCalled = false;

        public TrackingCallable(String callResult) {
            this.callResult = callResult;
        }

        @Override public String call() throws Exception {
            wasCalled = true;
            return callResult;
        }

        public boolean wasCalled() {
            return wasCalled;
        }
    }

    private Matcher<Iterable<Boolean>> hasAllElementsEqualTo(final Boolean booleanValue) {
        return hasAllElementsSatisfying(new SelfDescribingPredicate<Boolean>() {
            @Override public String describe() {
                return "equal to " + booleanValue.toString();
            }

            @Override public boolean evaluate(Boolean item) {
                return item.equals(booleanValue);
            }
        });
    }
}
