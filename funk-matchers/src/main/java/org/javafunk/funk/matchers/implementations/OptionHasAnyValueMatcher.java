package org.javafunk.funk.matchers.implementations;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.javafunk.funk.monads.Option;

public class OptionHasAnyValueMatcher extends TypeSafeDiagnosingMatcher<Option<?>> {
    @Override protected boolean matchesSafely(Option<?> option, Description mismatchDescription) {
        mismatchDescription.appendText("option with no value.");
        return option.hasValue();
    }

    @Override public void describeTo(Description description) {
        description.appendText("option with value.");
    }

    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
