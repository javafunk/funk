package org.javafunk.funk.testclasses;

import org.hamcrest.Matcher;
import org.javafunk.matchbox.SelfDescribingPredicate;

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
}
