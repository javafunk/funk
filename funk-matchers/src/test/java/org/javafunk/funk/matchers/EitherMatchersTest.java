package org.javafunk.funk.matchers;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.javafunk.funk.monads.Either;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.matchers.EitherMatchers.left;
import static org.javafunk.funk.matchers.EitherMatchers.leftOver;
import static org.javafunk.funk.matchers.EitherMatchers.leftOverValueAndType;
import static org.javafunk.funk.matchers.EitherMatchers.right;
import static org.javafunk.funk.matchers.EitherMatchers.rightOver;
import static org.javafunk.funk.matchers.EitherMatchers.rightOverTypeAndValue;
import static org.javafunk.matchbox.Matchers.mismatchesSampleWithMessage;
import static org.javafunk.matchbox.Matchers.successfullyMatches;

public class EitherMatchersTest {
    @Test
    public void shouldMatchWhenLeftMatcherEvaluatesEitherWhichIsALeft() throws Exception {
        // Given
        Either<String, Integer> either = Either.left("value");

        // When
        Matcher<? super Either<String, Integer>> matcher = left();

        // Then
        assertThat(matcher, successfullyMatches(either));
    }

    @Test
    public void shouldMismatchWithMessageWhenLeftMatcherEvaluatesEitherWhichIsARight() throws Exception {
        // Given
        Either<String, Integer> either = Either.right(10);

        // When
        Matcher<? super Either<String, Integer>> matcher = left();

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(either, "got Right."));
    }

    @Test
    public void shouldProvideAdequateDescriptionWhenLeftIsAskedToDescribeItself() throws Exception {
        // Given
        Matcher<? super Either<String, Integer>> matcher = left();
        StringDescription description = new StringDescription();

        // When
        matcher.describeTo(description);

        // Then
        assertThat(description.toString(), is("Either to be Left."));
    }

    @Test
    public void shouldReturnEitherLeftMatcherOverTheSuppliedTypes() throws Exception {
        // Given
        Matcher<? super Either<String, Integer>> expected = left();

        // When
        Matcher<? super Either<String, Integer>> actual = leftOver(String.class, Integer.class);

        // Then
        assertThat(actual.equals(expected), is(true));
    }

    @Test
    public void shouldMatchWhenRightMatcherEvaluatesEitherWhichIsARight() throws Exception {
        // Given
        Either<String, Integer> either = Either.right(10);

        // When
        Matcher<? super Either<String, Integer>> matcher = right();

        // Then
        assertThat(matcher, successfullyMatches(either));
    }

    @Test
    public void shouldMismatchWithMessageWhenRightMatcherEvaluatesEitherWhichIsALeft() throws Exception {
        // Given
        Either<String, Integer> either = Either.left("value");

        // When
        Matcher<? super Either<String, Integer>> matcher = right();

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(either, "got Left."));
    }

    @Test
    public void shouldProvideAdequateDescriptionWhenRightIsAskedToDescribeItself() throws Exception {
        // Given
        Matcher<? super Either<String, Integer>> matcher = right();
        StringDescription description = new StringDescription();

        // When
        matcher.describeTo(description);

        // Then
        assertThat(description.toString(), is("Either to be Right."));
    }

    @Test
    public void shouldReturnEitherRightMatcherOverTheSuppliedTypes() throws Exception {
        // Given
        Matcher<? super Either<String, Integer>> expected = right();

        // When
        Matcher<? super Either<String, Integer>> actual = rightOver(String.class, Integer.class);

        // Then
        assertThat(actual.equals(expected), is(true));
    }

    @Test
    public void shouldMatchWhenLeftMatcherEvaluatesEitherWhichIsALeftOverTheSpecificValue() throws Exception {
        // Given
        Either<String, Integer> either = Either.left("value");

        // When
        Matcher<? super Either<String, Integer>> matcher = left("value");

        // Then
        assertThat(matcher, successfullyMatches(either));
    }

    @Test
    public void shouldMatchWhenLeftMatcherEvaluatesEitherWhichIsALeftOverNullAndExpectedIsNull() throws Exception {
        // Given
        Either<String, Integer> either = Either.left(null);

        // When
        Matcher<? super Either<String, Integer>> matcher = left(null);

        // Then
        assertThat(matcher, successfullyMatches(either));
    }

    @Test
    public void shouldMismatchWithMessageWhenLeftMatcherEvaluatesEitherWhichIsALeftOverADifferentValue() throws Exception {
        // Given
        Either<String, Integer> either = Either.left("actual value");

        // When
        Matcher<? super Either<String, Integer>> matcher = left("expected value");

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(either, "got Left with value: \"actual value\""));
    }

    @Test
    public void shouldMismatchWithMessageWhenLeftMatcherEvaluatesEitherWhichIsLeftOverAValueOtherThanNullButNullExpected() throws Exception {
        // Given
        Either<String, Integer> either = Either.left("actual value");

        // When
        Matcher<? super Either<String, Integer>> matcher = left(null);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(either, "got Left with value: \"actual value\""));
    }

    @Test
    public void shouldMismatchWithMessageWhenLeftMatcherEvaluatesEitherWhichIsALeftOverNull() throws Exception {
        // Given
        Either<String, Integer> either = Either.left(null);

        // When
        Matcher<? super Either<String, Integer>> matcher = left("expected value");

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(either, "got Left with value: null"));
    }

    @Test
    public void shouldProvideAdequateDescriptionWhenLeftWithSpecificValueIsAskedToDescribeItself() throws Exception {
        // Given
        Matcher<? super Either<String, Integer>> matcher = left("value");
        StringDescription description = new StringDescription();

        // When
        matcher.describeTo(description);

        // Then
        assertThat(description.toString(), is("Either to be Left with value: \"value\""));
    }

    @Test
    public void shouldReturnEitherLeftMatcherOverTheSuppliedValueAndType() throws Exception {
        // Given
        Matcher<? super Either<String, Integer>> expected = left("value");

        // When
        Matcher<? super Either<String, Integer>> actual = leftOverValueAndType("value", Integer.class);

        // Then
        assertThat(actual.equals(expected), is(true));
    }

    @Test
    public void shouldMatchWhenRightMatcherEvaluatesEitherWhichIsARightOverTheSpecificValue() throws Exception {
        // Given
        Either<String, Integer> either = Either.right(10);

        // When
        Matcher<? super Either<String, Integer>> matcher = right(10);

        // Then
        assertThat(matcher, successfullyMatches(either));
    }

    @Test
    public void shouldMatchWhenRightMatcherEvaluatesEitherWhichIsARightOverNullAndExpectedIsNull() throws Exception {
        // Given
        Either<String, Integer> either = Either.right(null);

        // When
        Matcher<? super Either<String, Integer>> matcher = right(null);

        // Then
        assertThat(matcher, successfullyMatches(either));
    }

    @Test
    public void shouldMismatchWithMessageWhenRightMatcherEvaluatesEitherWhichIsARightOverADifferentValue() throws Exception {
        // Given
        Either<String, Integer> either = Either.right(30);

        // When
        Matcher<? super Either<String, Integer>> matcher = right(20);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(either, "got Right with value: <30>"));
    }

    @Test
    public void shouldMismatchWithMessageWhenRightMatcherEvaluatesEitherWhichIsRightOverAValueOtherThanNullButNullExpected() throws Exception {
        // Given
        Either<String, Integer> either = Either.right(20);

        // When
        Matcher<? super Either<String, Integer>> matcher = right(null);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(either, "got Right with value: <20>"));
    }

    @Test
    public void shouldMismatchWithMessageWhenRightMatcherEvaluatesEitherWhichIsARightOverNull() throws Exception {
        // Given
        Either<String, Integer> either = Either.right(null);

        // When
        Matcher<? super Either<String, Integer>> matcher = right(20);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(either, "got Right with value: null"));
    }

    @Test
    public void shouldProvideAdequateDescriptionWhenRightWithSpecificValueIsAskedToDescribeItself() throws Exception {
        // Given
        Matcher<? super Either<String, Integer>> matcher = right(20);
        StringDescription description = new StringDescription();

        // When
        matcher.describeTo(description);

        // Then
        assertThat(description.toString(), is("Either to be Right with value: <20>"));
    }

    @Test
    public void shouldReturnEitherRightMatcherOverTheSuppliedValueAndType() throws Exception {
        // Given
        Matcher<? super Either<String, Integer>> expected = right(20);

        // When
        Matcher<? super Either<String, Integer>> actual = rightOverTypeAndValue(String.class, 20);

        // Then
        assertThat(actual.equals(expected), is(true));
    }
}
