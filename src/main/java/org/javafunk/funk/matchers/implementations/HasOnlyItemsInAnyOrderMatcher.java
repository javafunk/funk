/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.matchers.implementations;

import com.google.common.collect.Multiset;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.javafunk.funk.Multisets;

import static org.javafunk.funk.Literals.multisetFrom;

public class HasOnlyItemsInAnyOrderMatcher<E> extends TypeSafeDiagnosingMatcher<Iterable<E>> {
    private final Multiset<E> expectedMultiset;

    public HasOnlyItemsInAnyOrderMatcher(Iterable<E> expectedMultiset) {
        this.expectedMultiset = multisetFrom(expectedMultiset);
    }

    @Override
    protected boolean matchesSafely(final Iterable<E> actualItems, Description description) {
        boolean matches = true;
        final Multiset<E> actualMultiset = multisetFrom(actualItems);

        description.appendText("got ");

        if (actualMultiset.size() > 0) {
            description.appendValueList("", ", ", "", actualItems);
        } else {
            description.appendText("empty collection");
            if (expectedMultiset.size() != 0) {
                return false;
            }
        }

        if (actualMultiset.size() != expectedMultiset.size()) {
            description.appendText("\n")
                    .appendText("got collection with size ")
                    .appendValue(actualMultiset.size())
                    .appendText(" rather than ")
                    .appendValue(expectedMultiset.size());
            matches = false;
        }

        if (checkForDifferences(expectedMultiset, actualMultiset, description, "expected but didn't get ")) {
            matches = false;
        }
        if (checkForDifferences(actualMultiset, expectedMultiset, description, "got but didn't expect ")) {
            matches = false;
        }

        return matches;
    }

    private boolean checkForDifferences(Multiset<E> expectedSet, Multiset<E> actualSet, Description description, String message) {
        Multiset<E> differences = Multisets.difference(expectedSet, actualSet);
        if (differences.size() > 0) {
            description.appendText("\n")
                    .appendText(message)
                    .appendValueList("", ", ", "", differences);
            return true;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        if (expectedMultiset.size() > 0) {
            description.appendText("Collection with size ")
                    .appendValue(expectedMultiset.size())
                    .appendText(" containing exactly items ")
                    .appendValueList("", ", ", "", expectedMultiset)
                    .appendText(" in any order.");
        } else {
            description.appendText("Empty collection");
        }
    }
}
