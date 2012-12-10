package org.javafunk.funk.matchers;

import org.hamcrest.Matcher;
import org.javafunk.funk.matchers.implementations.OptionHasAnyValueMatcher;
import org.javafunk.funk.matchers.implementations.OptionHasNoValueMatcher;
import org.javafunk.funk.matchers.implementations.OptionHasSpecificValueMatcher;
import org.javafunk.funk.monads.Option;

public class OptionMatchers {
    public static <T> Matcher<? super Option<T>> hasValue() {
        return new OptionHasAnyValueMatcher();
    }

    public static <T> Matcher<? super Option<T>> hasValue(final T value) {
        return new OptionHasSpecificValueMatcher<T>(value);
    }

    public static <T> Matcher<? super Option<T>> hasValueOf(Class<T> valueClass) {
        return hasValue();
    }

    public static <T> Matcher<? super Option<T>> hasNoValue() {
        return new OptionHasNoValueMatcher();
    }

    public static <T> Matcher<? super Option<T>> hasNoValueOf(Class<T> valueClass) {
        return hasNoValue();
    }
}
