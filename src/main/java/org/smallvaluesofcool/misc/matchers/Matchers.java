package org.smallvaluesofcool.misc.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.smallvaluesofcool.misc.collections.Bag;
import org.smallvaluesofcool.misc.collections.BagUtils;

import java.util.Collection;

import static org.smallvaluesofcool.misc.collections.IteratorUtils.toBag;

public class Matchers {

    public static <T> Matcher<Collection<T>> hasOnlyItemsInAnyOrder(final Collection<T> expectedItems) {
        return new TypeSafeDiagnosingMatcher<Collection<T>>() {
            @Override
            protected boolean matchesSafely(final Collection<T> actualItems, Description description) {
                boolean matches = true;

                final Bag<T> expectedSet = toBag(expectedItems.iterator());
                final Bag<T> actualSet = toBag(actualItems.iterator());

                description.appendText("got ");

                if (actualSet.size() > 0) {
                    description.appendValueList("", ", ", "", actualItems);
                } else {
                    description.appendText("empty collection");
                    if (expectedItems.size() != 0) {
                        return false;
                    }
                }

                if (actualSet.size() != expectedSet.size()) {
                    description.appendText("\n")
                            .appendText("got collection with size ")
                            .appendValue(actualSet.size())
                            .appendText(" rather than ")
                            .appendValue(expectedSet.size());
                    matches = false;
                }

                if (checkForDifferences(expectedSet, actualSet, description, "expected but didn't get ")) {
                    matches = false;
                }
                if (checkForDifferences(actualSet, expectedSet, description, "got but didn't expect ")) {
                    matches = false;
                }

                return matches;
            }

            private boolean checkForDifferences(Bag<T> expectedSet, Bag<T> actualSet, Description description, String message) {
                Bag<T> differences = BagUtils.difference(expectedSet, actualSet);
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
                if (expectedItems.size() > 0) {
                    description.appendText("Collection with size ")
                            .appendValue(expectedItems.size())
                            .appendText(" containing exactly items ")
                            .appendValueList("", ", ", "", expectedItems)
                            .appendText(" in any order.");
                } else {
                    description.appendText("Empty collection");
                }
            }
        };
    }
}
