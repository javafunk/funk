package org.javafunk.funk.matchers.implementations;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.javafunk.funk.matchers.SelfDescribingPredicate;

import static org.javafunk.funk.Eagerly.all;

public class HasAllElementsSatisfyingMatcher<T> extends TypeSafeMatcher<Iterable<T>> {
    private final SelfDescribingPredicate<T> predicate;

    public HasAllElementsSatisfyingMatcher(SelfDescribingPredicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    protected boolean matchesSafely(Iterable<T> items) {
        return all(items, predicate);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Collection with all items matching predicate ").appendValue(predicate.describe());
    }
}
