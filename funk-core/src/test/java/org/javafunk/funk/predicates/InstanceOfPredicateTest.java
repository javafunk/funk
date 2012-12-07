package org.javafunk.funk.predicates;

import org.javafunk.funk.functors.Predicate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Predicates.alwaysFalse;

public class InstanceOfPredicateTest {
    @Test
    public void shouldReturnTrueIfTheInstanceBeingEvaluatedIsOfTheClassSpecifiedAtConstruction() throws Exception {
        // Given
        Predicate<Number> instanceOfPredicate = new InstanceOfPredicate<Number>(Integer.class);

        // When
        boolean result = instanceOfPredicate.evaluate(180);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfTheInstanceBeingEvaluatedIsNotOfTheClassSpecifiedAtConstruction() throws Exception {
        // Given
        Predicate<Number> instanceOfPredicate = new InstanceOfPredicate<Number>(Integer.class);

        // When
        boolean result = instanceOfPredicate.evaluate(180L);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldBeEqualIfOtherIsAnInstanceOfPredicateOverTheSameClass() throws Exception {
        // Given
        Predicate<Number> firstInstanceOfPredicate = new InstanceOfPredicate<Number>(Integer.class);
        Predicate<Number> secondInstanceOfPredicate = new InstanceOfPredicate<Number>(Integer.class);

        // When
        boolean equal = firstInstanceOfPredicate.equals(secondInstanceOfPredicate);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfOtherIsAnInstanceOfPredicateOverADifferentClass() throws Exception {
        // Given
        Predicate<Number> firstInstanceOfPredicate = new InstanceOfPredicate<Number>(Integer.class);
        Predicate<Number> secondInstanceOfPredicate = new InstanceOfPredicate<Number>(Long.class);

        // When
        boolean equal = firstInstanceOfPredicate.equals(secondInstanceOfPredicate);

        // Then
        assertThat(equal, is(false));
    }

    @Test
    public void shouldNotBeEqualIfOtherIsNotAnInstanceOfPredicate() throws Exception {
        // Given
        Predicate<Number> firstInstanceOfPredicate = new InstanceOfPredicate<Number>(Integer.class);
        Predicate<Object> secondInstanceOfPredicate = alwaysFalse();

        // When
        boolean equal = firstInstanceOfPredicate.equals(secondInstanceOfPredicate);

        // Then
        assertThat(equal, is(false));
    }
}
