package org.smallvaluesofcool.misc.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MatchersTest {

    @Test
    public void shouldReportOnMismatchedItemsInHasOnlyItemsInAnyOrder() {
        // Given
        Collection<Integer> actual = asList(1, 2, 5, 6);
        Collection<Integer> expected = asList(1, 2, 3, 4);

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(actual, "got <1>, <2>, <5>, <6>\n" +
                "expected but didn't get <3>, <4>\n" +
                "got but didn't expect <5>, <6>"));
    }

    @Test
    public void shouldReportOnTooManyItemsInHasOnlyItemsInAnyOrder() {
        // Given
        Collection<Integer> actual = asList(1, 2, 5, 6);
        Collection<Integer> expected = asList(1, 2);

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(actual, "got <1>, <2>, <5>, <6>\n" +
                "got collection with size <4> rather than <2>\n" +
                "got but didn't expect <5>, <6>"));
    }

    @Test
    public void shouldReportOnTooFewItemsInHasOnlyItemsInAnyOrder() {
        // Given
        Collection<Integer> actual = asList(1, 2);
        Collection<Integer> expected = asList(1, 2, 4, 5);

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(actual, "got <1>, <2>\n" +
                "got collection with size <2> rather than <4>\n" +
                "expected but didn't get <4>, <5>"));
    }

    @Test
    public void shouldMatchIdenticalCollectionsInHasOnlyItemsInAnyOrder() {
        // Given
        Collection<Integer> actual = asList(1, 2, 3);
        Collection<Integer> expected = asList(1, 2, 3);

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher, matches(actual));
    }

    @Test
    public void shouldMatchCollectionsDifferingOnlyInOrderInHasOnlyItemsInAnyOrder() {
        // Given
        Collection<Integer> actual = asList(1, 2, 3);
        Collection<Integer> expected = asList(1, 3, 2);

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher, matches(actual));
    }

    @Test
    public void shouldMismatchCollectionsDifferingOnlyInItemCountInHasOnlyItemsInAnyOrder() {
        // Given
        Collection<Integer> actual = asList(1, 2, 2, 3);
        Collection<Integer> expected = asList(1, 3, 3, 2);

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(actual, "got <1>, <2>, <2>, <3>\n" +
                "expected but didn't get <3>\n" +
                "got but didn't expect <2>"));
    }


    @Test
    public void shouldReportWhenActualItemsIsEmpty() {
        // Given
        Collection<Integer> actual = Collections.emptyList();
        Collection<Integer> expected = asList(1, 2, 3, 4);

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(actual, "got empty collection"));
    }

    @Test
    public void shouldReportWhenActualItemsIsNull() {
        // Given
        Collection<Integer> actual = null;
        Collection<Integer> expected = asList(1, 2, 3, 4);

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher, mismatchesSampleWithMessage(actual, "was null"));
    }

    @Test
    public void shouldReportWhenExpectedItemsIsEmpty() {
        // Given
        Collection<Integer> expected = Collections.emptyList();

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher.toString(), is("Empty collection"));
    }

    @Test
    public void shouldMatchWhenExpectedAndActualItemsAreEmpty() {
        // Given
        Collection<Integer> actual = Collections.emptyList();
        Collection<Integer> expected = Collections.emptyList();

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher, matches(actual));
    }

    @Test
    public void shouldReportItemsFoundInExpectedCollection() {
        // Given
        Collection<Integer> expected = asList(1, 2, 3, 4);

        // When
        Matcher<Collection<Integer>> matcher = Matchers.hasOnlyItemsInAnyOrder(expected);

        // Then
        assertThat(matcher.toString(), is("Collection with size <4> containing exactly items <1>, <2>, <3>, <4> in any order."));
    }


    private static <T> Matcher<Matcher<T>> mismatchesSampleWithMessage(final T sample, final String descriptionContains) {
        return new TypeSafeDiagnosingMatcher<Matcher<T>>() {
            @Override
            protected boolean matchesSafely(Matcher<T> matcher, Description description) {
                if (matcher.matches(sample)) {
                    description.appendText("matcher matched");
                    return false;
                }

                Description actualDescription = new StringDescription();
                matcher.describeMismatch(sample, actualDescription);
                description.appendText("got description ").appendValue(actualDescription);

                return actualDescription.toString().equals(descriptionContains);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Matcher to mismatch ").appendValue(sample).appendText(" and give description containing ").appendValue(descriptionContains);
            }
        };
    }

    private static <T> Matcher<Matcher<T>> matches(final T sample) {
        return new TypeSafeDiagnosingMatcher<Matcher<T>>() {
            @Override
            protected boolean matchesSafely(Matcher<T> matcher, Description description) {
                if (!matcher.matches(sample)) {
                    Description actualDescription = new StringDescription();
                    matcher.describeMismatch(sample, actualDescription);
                    description.appendText("got mismatch, description ").appendValue(actualDescription);
                    return false;
                }
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Matcher to match ").appendValue(sample);
            }
        };
    }

    public static class Bean {

        private String attribute1;
        private String attribute2;
        private String attribute3;
        private String attribute4;

        private Bean(String attribute1, String attribute2, String attribute3, String attribute4) {
            this.attribute1 = attribute1;
            this.attribute2 = attribute2;
            this.attribute3 = attribute3;
            this.attribute4 = attribute4;
        }

        public static Bean bean(String attribute1, String attribute2, String attribute3, String attribute4) {
            return new Bean(attribute1, attribute2, attribute3, attribute4);
        }

        public String getAttribute1() {
            return attribute1;
        }

        public String getAttribute2() {
            return attribute2;
        }

        public String getAttribute3() {
            return attribute3;
        }

        public String getAttribute4() {
            return attribute4;
        }

        @Override
        public String toString() {
            return format("Bean<attribute1=<%s>, attribute2=<%s>, attribute3=<%s>, attribute4=<%s>, >", attribute1, attribute2, attribute3, attribute4);
        }
    }
}
