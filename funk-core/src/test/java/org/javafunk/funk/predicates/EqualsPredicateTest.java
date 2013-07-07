package org.javafunk.funk.predicates;

import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.testclasses.Name.name;
import static org.junit.Assert.fail;

public class EqualsPredicateTest {
    @Test
    public void shouldDelegateThroughToEqualsOnInitialisedObjectWithSuppliedObjectAndReturnTheResult() throws Exception {
        // Given
        Name first = name("James");
        Name second = name("James");
        EqualsPredicate<Name> predicate = new EqualsPredicate<Name>(first);

        // When
        Boolean result = predicate.evaluate(second);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfEvaluatedObjectIsNotEqualToObjectUsedAtInitialisationViaEqualsMethod() throws Exception {
        // Given
        Name first = name("John");
        Name second = name("Anne");
        EqualsPredicate<Name> predicate = new EqualsPredicate<Name>(first);

        // When
        Boolean result = predicate.evaluate(second);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldPassNullValuesThroughToObjectsEquals() throws Exception {
        // Given
        Object first = new Object();
        ObjectWithEqualsCapture second = new ObjectWithEqualsCapture();
        EqualsPredicate<Object> predicate = new EqualsPredicate<Object>(first);

        // When
        predicate.evaluate(second);

        // Then
        assertThat(second.getObjectEqualsWasCalledWith(), is((Object) null));
    }

    @Test
    public void shouldNotThrowNullPointerExceptionIfControlObjectIsNull() throws Exception {
        // Given
        Object first = null;
        Object second = new Object();
        EqualsPredicate<Object> predicate = new EqualsPredicate<Object>(first);

        try {
            // When
            predicate.evaluate(second);
        } catch(NullPointerException exception) {
            // Then
            fail("Expected no NullPointerException to be thrown but one was.");
        }
    }

    @Test
    public void shouldNotThrowNullPointerExceptionIfTestObjectIsNull() throws Exception {
        // Given
        Object first = new Object();
        Object second = null;
        EqualsPredicate<Object> predicate = new EqualsPredicate<Object>(first);

        try {
            // When
            predicate.evaluate(second);
        } catch(NullPointerException exception) {
            // Then
            fail("Expected no NullPointerException to be thrown but one was.");
        }
    }

    private static class ObjectWithEqualsCapture {
        private Object objectEqualsWasCalledWith;

        @Override public boolean equals(Object object) {
            objectEqualsWasCalledWith = object;
            return true;
        }

        public Object getObjectEqualsWasCalledWith() {
            return objectEqualsWasCalledWith;
        }
    }
}
