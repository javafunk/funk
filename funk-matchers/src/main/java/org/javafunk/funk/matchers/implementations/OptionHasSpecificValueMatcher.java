package org.javafunk.funk.matchers.implementations;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.javafunk.funk.monads.Option;

import static org.hamcrest.Matchers.is;

public class OptionHasSpecificValueMatcher<T> extends TypeSafeDiagnosingMatcher<Option<T>> {
    private final T value;

    public OptionHasSpecificValueMatcher(T value) {
        this.value = value;
    }

    @Override protected boolean matchesSafely(Option<T> item, Description mismatchDescription) {
        if (item.hasNoValue()) {
            mismatchDescription.appendText("Option with no value.");
            return false;
        }

        mismatchDescription.appendText("Option with value: ").appendValue(item.get());
        return is(value).matches(item.get());
    }

    @Override public void describeTo(Description description) {
        description.appendText("Option with value: ").appendValue(value);
    }

    @Override public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
