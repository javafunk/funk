package org.javafunk.funk.matchers;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.javafunk.funk.monads.Option;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.matchers.OptionMatchers.hasNoValue;
import static org.javafunk.funk.matchers.OptionMatchers.hasNoValueOf;
import static org.javafunk.funk.matchers.OptionMatchers.hasValue;
import static org.javafunk.funk.matchers.OptionMatchers.hasValueOf;
import static org.javafunk.matchbox.Matchers.mismatchesSampleWithMessage;
import static org.javafunk.matchbox.Matchers.successfullyMatches;

public class OptionMatchersTest {
    @Test
    public void shouldMatchWhenHasValueMatcherEvaluatesOptionWithAnyValue() throws Exception {
        // Given
        Option<String> option = Option.option("value");

        // When
        Matcher<? super Option<String>> matcher = hasValue();

        // Then
        assertThat(matcher, successfullyMatches(option));
    }

    @Test
    public void shouldMismatchWithMessageWhenHasValueMatcherEvaluatesOptionWithNoValue() throws Exception {
        // Given
        Option<String> option = Option.none();

        // When
        Matcher<? super Option<String>> matcher = hasValue();

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(option, "Option with no value."));
    }

    @Test
    public void shouldProvideAdequateDescriptionWhenHasValueAskedToDescribeItself() throws Exception {
        // Given
        Matcher<? super Option<String>> matcher = hasValue();
        StringDescription description = new StringDescription();

        // When
        matcher.describeTo(description);

        // Then
        assertThat(description.toString(), is("Option with value."));
    }

    @Test
    public void shouldMatchWhenHasValueMatcherEvaluatesOptionWithSpecificValue() throws Exception {
        // Given
        Option<String> option = Option.option("matching value");

        // When
        Matcher<? super Option<String>> matcher = hasValue("matching value");

        // Then
        assertThat(matcher, successfullyMatches(option));
    }

    @Test
    public void shouldMatchWhenHasValueMatcherEvaluatesOptionWithNullValueAndOptionWithNullValueExpected() throws Exception {
        // Given
        Option<String> option = Option.some(null);

        // When
        Matcher<? super Option<String>> matcher = hasValue(null);

        // Then
        assertThat(matcher, successfullyMatches(option));
    }

    @Test
    public void shouldMismatchWithMessageWhenHasValueMatcherEvaluatesOptionWithDifferentValue() throws Exception {
        // Given
        Option<String> option = Option.option("actual value");

        // When
        Matcher<? super Option<String>> matcher = hasValue("expected value");

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(option, "Option with value: \"actual value\""));
    }

    @Test
    public void shouldMismatchWithMessageWhenHasValueMatcherEvaluatesOptionWithNullValueButAValueIsExpected() throws Exception {
        // Given
        Option<String> option = Option.some(null);

        // When
        Matcher<? super Option<String>> matcher = hasValue("expected value");

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(option, "Option with value: null"));
    }

    @Test
    public void shouldMismatchWithMessageWhenHasValueMatcherEvaluatesOptionWithAValueButNullValueIsExpected() throws Exception {
        // Given
        Option<String> option = Option.some("actual value");

        // When
        Matcher<? super Option<String>> matcher = hasValue(null);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(option, "Option with value: \"actual value\""));
    }

    @Test
    public void shouldMismatchWithMessageWhenHasValueMatcherExpectingSpecificValueEvaluatesOptionWithNoValue() throws Exception {
        // Given
        Option<String> option = Option.none();

        // When
        Matcher<? super Option<String>> matcher = hasValue("value");

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(option, "Option with no value."));
    }

    @Test
    public void shouldProvideAdequateDescriptionWhenHasValueWithSpecificValueAskedToDescribeItself() throws Exception {
        // Given
        Matcher<? super Option<String>> matcher = hasValue("value");
        StringDescription description = new StringDescription();

        // When
        matcher.describeTo(description);

        // Then
        assertThat(description.toString(), is("Option with value: \"value\""));
    }

    @Test
    public void shouldReturnOptionHasAnyValueMatcherOverTheSuppliedType() throws Exception {
        // Given
        Matcher<? super Option<String>> expected = hasValue();

        // When
        Matcher<? super Option<String>> actual = hasValueOf(String.class);

        // Then
        assertThat(actual.equals(expected), is(true));
    }

    @Test
    public void shouldMatchWhenHasNoValueMatcherEvaluatesOptionWithNoValue() throws Exception {
        // Given
        Option<String> option = Option.none();

        // When
        Matcher<? super Option<String>> matcher = hasNoValue();

        // Then
        assertThat(matcher, successfullyMatches(option));
    }

    @Test
    public void shouldMismatchWhenHasNoValueMatcherEvaluatesOptionWithAnyValue() throws Exception {
        // Given
        Option<String> option = Option.option("value");

        // When
        Matcher<? super Option<String>> matcher = hasNoValue();

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(option, "Option with value: \"value\""));
    }

    @Test
    public void shouldProvideAdequateDescriptionWhenHasNoValueAskedToDescribeItself() throws Exception {
        // Given
        Matcher<? super Option<String>> matcher = hasNoValue();
        StringDescription description = new StringDescription();

        // When
        matcher.describeTo(description);

        // Then
        assertThat(description.toString(), is("Option with no value."));
    }

    @Test
    public void shouldReturnOptionHasNoValueMatcherOverTheSuppliedType() throws Exception {
        // Given
        Matcher<? super Option<String>> expected = hasNoValue();

        // When
        Matcher<? super Option<String>> actual = hasNoValueOf(String.class);

        // Then
        assertThat(actual.equals(expected), is(true));
    }
}
