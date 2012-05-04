package org.javafunk.funk.matchers;

import org.hamcrest.Matcher;
import org.javafunk.funk.annotations.ToDo;

import java.util.Iterator;

public class IteratorMatchers {
    public static <E> Matcher<? super Iterator<E>> isIteratorWithSameElementsAs(final Iterator<E> expected) {
        return new IsValidIteratorWithSameElementsAs<E>(expected);
    }
}
