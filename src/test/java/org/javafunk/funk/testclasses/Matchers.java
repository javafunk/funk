package org.javafunk.funk.testclasses;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.javafunk.matchbox.SelfDescribingPredicate;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class Matchers {
    public static Matcher<Iterable<Boolean>> hasAllElementsEqualTo(final Boolean booleanValue) {
        return org.javafunk.matchbox.Matchers.hasAllElementsSatisfying(new SelfDescribingPredicate<Boolean>() {
            @Override public String describe() {
                return "equal to " + booleanValue.toString();
            }

            @Override public boolean evaluate(Boolean item) {
                return item.equals(booleanValue);
            }
        });
    }

    public static <T> Matcher<T> equalToIncludingConcreteType(final T expected) {
        return new TypeSafeDiagnosingMatcher<T>() {
            @Override protected boolean matchesSafely(T item, Description mismatchDescription) {
                mismatchDescription.appendText("got an object of type: ")
                        .appendValue(item.getClass().getSimpleName())
                        .appendText(" : ")
                        .appendValue(item);
                return instanceOf(expected.getClass()).matches(item) && is(expected).matches(item);
            }

            @Override public void describeTo(Description description) {
                description.appendText("an object of type: ")
                        .appendValue(expected.getClass().getSimpleName())
                        .appendText(" equal to: ")
                        .appendValue(expected);
            }
        };
    }
}
