package org.javafunk.funk.matchers;

import org.hamcrest.Matcher;
import org.javafunk.funk.matchers.implementations.BetweenMatcher;

public class ComparableMatchers {
    public static <T extends Comparable<T>> Matcher<? super T> between(final T low, final T high) {
        return new BetweenMatcher<T>(low, high);
    }
}
