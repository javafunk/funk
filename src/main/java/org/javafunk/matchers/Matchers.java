package org.javafunk.matchers;

import org.apache.commons.beanutils.BeanMap;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.TypeSafeMatcher;
import org.javafunk.BagUtils;
import org.javafunk.collections.Bag;
import org.javafunk.datastructures.TwoTuple;

import java.util.*;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.join;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.javafunk.IteratorUtils.toBag;
import static org.javafunk.functional.Eager.all;
import static org.javafunk.functional.Eager.any;
import static org.javafunk.functional.Lazy.enumerate;

public class Matchers {
    public static <T> Matcher<Collection<T>> hasOnlyItemsInAnyOrder(T... items) {
        return hasOnlyItemsInAnyOrder(asList(items));
    }

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

    public static <T> Matcher<Collection<T>> hasOnlyItemsInOrder(T... items) {
        return hasOnlyItemsInOrder(asList(items));
    }

    public static <T> Matcher<Collection<T>> hasOnlyItemsInOrder(final Collection<T> expectedItems) {
        return new TypeSafeDiagnosingMatcher<Collection<T>>() {
            @Override
            protected boolean matchesSafely(Collection<T> actualItems, Description description) {
                Matcher<Collection<T>> orderAgnosticMatcher = hasOnlyItemsInAnyOrder(expectedItems);
                if (!orderAgnosticMatcher.matches(actualItems)) {
                    orderAgnosticMatcher.describeMismatch(actualItems, description);
                    return false;
                }

                Iterator<T> actualItemIterator = actualItems.iterator();
                for (TwoTuple<Integer, T> indexAndExpectedItem : enumerate(expectedItems)) {
                    T actualItem = actualItemIterator.next();
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
        };
    }

    public static <T> Matcher<T> isBeanWithSameAttributesAs(final T expectedObject) {
        return isBeanWithSameAttributesAs(expectedObject, Collections.<String>emptySet());
    }

    public static <T> Matcher<T> isBeanWithSameAttributesAs(final T expectedObject, final Set<String> ignoreProperties) {
        return new TypeSafeDiagnosingMatcher<T>() {
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
        };
    }

    public static Set<String> ignoring(String... ignoreProperties) {
        return new HashSet<String>(asList(ignoreProperties));
    }

    public static <T> Matcher<Iterable<T>> trueForAll(final SelfDescribingPredicateFunction<T> predicate) {
        return new TypeSafeMatcher<Iterable<T>>() {

            @Override
            protected boolean matchesSafely(Iterable<T> items) {
                return all(items, predicate);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Collection with all items matching predicate ").appendValue(predicate.describe());
            }
        };
    }

    public static <T> Matcher<Iterable<T>> trueForAny(final SelfDescribingPredicateFunction<T> predicate) {
        return new TypeSafeMatcher<Iterable<T>>() {

            @Override
            protected boolean matchesSafely(Iterable<T> items) {
                return any(items, predicate);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Collection with any items matching predicate ").appendValue(predicate.describe());
            }
        };
    }

    public static <T extends Comparable<T>> Matcher<? super T> between(final T low, final T high) {
        return new TypeSafeMatcher<T>() {
            @Override
            protected boolean matchesSafely(T number) {
                return greaterThanOrEqualTo(low).matches(number) && lessThanOrEqualTo(high).matches(number);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Value between ").appendValue(low).appendText(" and ").appendValue(high).appendText(" inclusive.");
            }
        };
    }
}
