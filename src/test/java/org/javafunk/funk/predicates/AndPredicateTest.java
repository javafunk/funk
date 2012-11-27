package org.javafunk.funk.predicates;

import org.javafunk.funk.Literals;
import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.functors.predicates.UnaryPredicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Predicates.alwaysFalse;
import static org.javafunk.funk.predicates.AndPredicate.and;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AndPredicateTest {
    @Mock private Predicate<Object> firstPredicate;
    @Mock private Predicate<Object> secondPredicate;
    @Mock private Predicate<Object> thirdPredicate;

    @Test
    public void shouldReturnTrueIfAllSuppliedPredicatesReturnTrue() throws Exception {
        // Given
        given(firstPredicate.evaluate(anyObject())).willReturn(true);
        given(secondPredicate.evaluate(anyObject())).willReturn(true);
        given(thirdPredicate.evaluate(anyObject())).willReturn(true);

        // When
        Predicate<Object> andPredicate = and(firstPredicate, secondPredicate, thirdPredicate);

        // Then
        assertThat(andPredicate.evaluate(new Object()), is(true));
    }

    @Test
    public void shouldReturnFalseIfAnySuppliedPredicatesReturnFalse() throws Exception {
        // Given
        given(firstPredicate.evaluate(anyObject())).willReturn(true);
        given(secondPredicate.evaluate(anyObject())).willReturn(false);
        given(thirdPredicate.evaluate(anyObject())).willReturn(true);

        // When
        Predicate<Object> andPredicate = and(firstPredicate, secondPredicate, thirdPredicate);

        // Then
        assertThat(andPredicate.evaluate(new Object()), is(false));
    }

    @Test
    public void shouldNotCallSubsequentPredicatesIfEarlierPredicateReturnsFalse() throws Exception {
        // Given
        given(firstPredicate.evaluate(anyObject())).willReturn(true);
        given(secondPredicate.evaluate(anyObject())).willReturn(false);

        // When
        and(firstPredicate, secondPredicate, thirdPredicate);

        // Then
        verify(thirdPredicate, never()).evaluate(anyObject());
    }

    @Test
    public void shouldAlwaysReturnTrueIfNoDelegatePredicatesSuppliedAtConstruction() throws Exception {
        // Given
        AndPredicate<Object> andPredicate = new AndPredicate<Object>(Literals.<UnaryPredicate<? super Object>>iterable());

        // When
        boolean result = andPredicate.evaluate(anyObject());

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldBeEqualIfOtherIsAnAndPredicateAndAllPredicatesSuppliedAtInitialisationAreEqual() throws Exception {
        // Given
        Predicate<Object> first = and(firstPredicate, secondPredicate);
        Predicate<Object> second = and(firstPredicate, secondPredicate);

        // When
        boolean equal = first.equals(second);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfOtherIsAnAndPredicateButPredicatesSuppliedAtInitialisationAreDifferent() throws Exception {
        // Given
        Predicate<Object> first = and(firstPredicate, secondPredicate);
        Predicate<Object> second = and(firstPredicate, thirdPredicate);

        // When
        boolean equal = first.equals(second);

        // Then
        assertThat(equal, is(false));
    }

    @Test
    public void shouldNotBeEqualIfOtherIsNotAnAndPredicate() throws Exception {
        // Given
        Predicate<Object> first = and(firstPredicate, secondPredicate);
        Predicate<Object> second = alwaysFalse();

        // When
        boolean equal = first.equals(second);

        // Then
        assertThat(equal, is(false));
    }
}
