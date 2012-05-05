package org.javafunk.funk.matchers.implementations;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Iterator;

public class IsIteratorWithSameElementsAsMatcher<E> extends TypeSafeMatcher<Iterator<E>> {
    private final Iterator<? extends E> expected;

    public IsIteratorWithSameElementsAsMatcher(Iterator<? extends E> expected) {
        this.expected = expected;
    }

    @Override protected boolean matchesSafely(Iterator<E> actual) {
        Boolean identical = true;
        while (actual.hasNext() && expected.hasNext()) {
            E actualNext = actual.next();
            E expectedNext = expected.next();
            if (!(actualNext == null && expectedNext == null ||
                    (actualNext != null && actualNext.equals(expectedNext)))) {
                identical = false;
            }
        }
        if (actual.hasNext()) {
            identical = false;
        }
        if (expected.hasNext()) {
            identical = false;
        }
        return identical;
    }

    @Override public void describeTo(Description description) {
        description.appendText("iterators to have identical contents");
    }
}
