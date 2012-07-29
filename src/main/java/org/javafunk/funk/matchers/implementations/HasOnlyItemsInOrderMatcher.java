/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.matchers.implementations;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.matchers.IterableMatchers;

import java.util.Iterator;

import static org.javafunk.funk.Lazily.enumerate;

public class HasOnlyItemsInOrderMatcher<E> extends TypeSafeDiagnosingMatcher<Iterable<E>> {
    private final Iterable<E> expectedItems;

    public HasOnlyItemsInOrderMatcher(Iterable<E> expectedItems) {
        this.expectedItems = expectedItems;
    }

    @Override
        protected boolean matchesSafely(Iterable<E> actualItems, Description description) {
            Matcher<Iterable<E>> orderAgnosticMatcher = IterableMatchers.hasOnlyItemsInAnyOrder(expectedItems);
            if (!orderAgnosticMatcher.matches(actualItems)) {
                orderAgnosticMatcher.describeMismatch(actualItems, description);
                return false;
            }

            Iterator<E> actualItemIterator = actualItems.iterator();
            for (Pair<Integer, E> indexAndExpectedItem : enumerate(expectedItems)) {
                E actualItem = actualItemIterator.next();
                if (!indexAndExpectedItem.second().equals(actualItem)) {
                    description
                            .appendText("got ")
                            .appendValueList("", ", ", "", actualItems)
                            .appendText("\n")
                            .appendText("first item out of order ")
                            .appendValue(actualItem)
                            .appendText(" at index ")
                            .appendText(String.valueOf(indexAndExpectedItem.first()));
                    return false;
                }
            }
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Collection containing exactly ")
                    .appendValueList("", ", ", "", expectedItems)
                    .appendText(" in order.");
        }
    }
