/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.matchers.implementations;

import org.apache.commons.beanutils.BeanMap;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.join;

public class IsBeanWithSameAttributesAsMatcher<T> extends TypeSafeDiagnosingMatcher<T> {
    private final T expectedObject;
    private final Set<String> ignoreProperties;

    public IsBeanWithSameAttributesAsMatcher(T expectedObject, Set<String> ignoreProperties) {
        this.expectedObject = expectedObject;
        this.ignoreProperties = ignoreProperties;
    }

    @Override
    protected boolean matchesSafely(T actualObject, Description description) {
        @SuppressWarnings("unchecked")
        Map<String, Object> actualProperties = new BeanMap(actualObject);
        @SuppressWarnings("unchecked")
        Map<String, Object> expectedProperties = new BeanMap(expectedObject);
        for (String propertyName : actualProperties.keySet()) {
            if (ignoreProperties.contains(propertyName)) {
                continue;
            }
            Object actualValue = actualProperties.get(propertyName);
            Object expectedValue = expectedProperties.get(propertyName);
            if ((actualValue == null && expectedValue != null) || (actualValue != null && !actualValue.equals(expectedValue))) {
                description.appendText(format("got      %s ", actualObject.getClass().getSimpleName()))
                        .appendValue(expectedObject)
                        .appendText(format("\nMismatch: expected property \"%s\" = ", propertyName))
                        .appendValue(expectedValue)
                        .appendText(format("\n            actual property \"%s\" = ", propertyName))
                        .appendValue(actualValue);
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(format("%s matching ", expectedObject.getClass().getSimpleName()))
                .appendValue(expectedObject);
        if (ignoreProperties.size() > 0) {
            description
                    .appendText(" ignoring properties ")
                    .appendText(join(ignoreProperties, ", "));
        }
    }
}
