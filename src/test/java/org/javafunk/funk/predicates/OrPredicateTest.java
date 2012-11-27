package org.javafunk.funk.predicates;

import org.javafunk.funk.Literals;
import org.javafunk.funk.functors.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Predicates.alwaysFalse;
import static org.javafunk.funk.predicates.OrPredicate.or;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrPredicateTest {
    @Mock private Predicate<Object> firstPredicate;
    @Mock private Predicate<Object> secondPredicate;
    @Mock private Predicate<Object> thirdPredicate;

    @Test
    public void shouldReturnTrueIfAnySuppliedPredicateReturnTrue() throws Exception {
        // Given
        given(firstPredicate.evaluate(anyObject())).willReturn(false);
        given(secondPredicate.evaluate(anyObject())).willReturn(false);
        given(thirdPredicate.evaluate(anyObject())).willReturn(true);

        // When
        Predicate<Object> orPredicate = or(firstPredicate, secondPredicate, thirdPredicate);

        // Then
        assertThat(orPredicate.evaluate(new Object()), is(true));
    }

    @Test
    public void shouldReturnFalseIfNoneOfTheSuppliedPredicatesReturnTrue() throws Exception {
        // Given
        given(firstPredicate.evaluate(anyObject())).willReturn(false);
        given(secondPredicate.evaluate(anyObject())).willReturn(false);
        given(thirdPredicate.evaluate(anyObject())).willReturn(false);

        // When
        Predicate<Object> orPredicate = or(firstPredicate, secondPredicate, thirdPredicate);

        // Then
        assertThat(orPredicate.evaluate(new Object()), is(false));
    }

    @Test
    public void shouldNotCallSubsequentPredicatesIfEarlierPredicateReturnsTrue() throws Exception {
        // Given
        given(firstPredicate.evaluate(anyObject())).willReturn(false);
        given(secondPredicate.evaluate(anyObject())).willReturn(true);

        // When
        or(firstPredicate, secondPredicate, thirdPredicate);

        // Then
        verify(thirdPredicate, never()).evaluate(anyObject());
    }

    @Test
    public void shouldAlwaysReturnFalseIfNoDelegatePredicatesSuppliedAtConstruction() throws Exception {
        // Given
        OrPredicate<Object> orPredicate = new OrPredicate<Object>(Literals.<Predicate<? super Object>>iterable());

        // When
        boolean result = orPredicate.evaluate(anyObject());

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldBeEqualIfOtherIsAnOrPredicateAndAllPredicatesSuppliedAtInitialisationAreEqual() throws Exception {
        // Given
        Predicate<Object> first = or(firstPredicate, secondPredicate);
        Predicate<Object> second = or(firstPredicate, secondPredicate);

        // When
        boolean equal = first.equals(second);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfOtherIsAnOrPredicateButPredicatesSuppliedAtInitialisationAreDifferent() throws Exception {
        // Given
        Predicate<Object> first = or(firstPredicate, secondPredicate);
        Predicate<Object> second = or(firstPredicate, thirdPredicate);

        // When
        boolean equal = first.equals(second);

        // Then
        assertThat(equal, is(false));
    }

    @Test
    public void shouldNotBeEqualIfOtherIsNotAnOrPredicate() throws Exception {
        // Given
        Predicate<Object> first = or(firstPredicate, secondPredicate);
        Predicate<Object> second = alwaysFalse();

        // When
        boolean equal = first.equals(second);

        // Then
        assertThat(equal, is(false));
    }
}
