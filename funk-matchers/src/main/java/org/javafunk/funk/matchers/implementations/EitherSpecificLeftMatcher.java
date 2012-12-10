package org.javafunk.funk.matchers.implementations;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.javafunk.funk.monads.Either;

public class EitherSpecificLeftMatcher<S, T> extends TypeSafeDiagnosingMatcher<Either<S, T>> {
    private S expectedLeftValue;

    public EitherSpecificLeftMatcher(S expectedLeftValue) {
        this.expectedLeftValue = expectedLeftValue;
    }

    @Override protected boolean matchesSafely(Either<S, T> item, Description mismatchDescription) {
        if (item.isRight()) {
            mismatchDescription.appendText("got Right with value: ").appendValue(item.getRight());
            return false;
        }
        mismatchDescription.appendText("got Left with value: ").appendValue(item.getLeft());

        return (expectedLeftValue == null && item.getLeft() == null) ||
                (item.getLeft() != null && item.getLeft().equals(expectedLeftValue));
    }

    @Override public void describeTo(Description description) {
        description.appendText("Either to be Left with value: ").appendValue(expectedLeftValue);
    }

    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
