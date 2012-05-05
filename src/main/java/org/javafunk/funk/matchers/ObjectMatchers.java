package org.javafunk.funk.matchers;

import org.hamcrest.Matcher;
import org.javafunk.funk.matchers.implementations.IsBeanWithSameAttributesAsMatcher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class ObjectMatchers {
    public static <T> Matcher<T> isBeanWithSameAttributesAs(final T expectedObject) {
        return isBeanWithSameAttributesAs(expectedObject, Collections.<String>emptySet());
    }

    public static <T> Matcher<T> isBeanWithSameAttributesAs(final T expectedObject, final Set<String> ignoreProperties) {
        return new IsBeanWithSameAttributesAsMatcher<T>(expectedObject, ignoreProperties);
    }

    public static Set<String> ignoring(String... ignoreProperties) {
        return new HashSet<String>(asList(ignoreProperties));
    }
}
