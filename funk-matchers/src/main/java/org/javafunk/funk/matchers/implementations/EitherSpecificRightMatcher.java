package org.javafunk.funk.matchers.implementations;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.javafunk.funk.monads.Either;

import static org.hamcrest.Matchers.is;

public class EitherSpecificRightMatcher<S, T> extends TypeSafeDiagnosingMatcher<Either<S, T>> {
    private T expectedRightValue;

    public EitherSpecificRightMatcher(T expectedRightValue) {
        this.expectedRightValue = expectedRightValue;
    }

    @Override protected boolean matchesSafely(Either<S, T> item, Description mismatchDescription) {
        if (item.isLeft()) {
            mismatchDescription.appendText("got Left with value: ").appendValue(item.getLeft());
            return false;
        }

        mismatchDescription.appendText("got Right with value: ").appendValue(item.getRight());
        return is(expectedRightValue).matches(item.getRight());
    }

    @Override public void describeTo(Description description) {
        description.appendText("Either to be Right with value: ").appendValue(expectedRightValue);
    }

    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
