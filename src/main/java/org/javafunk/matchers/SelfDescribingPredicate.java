package org.javafunk.matchers;

import org.javafunk.functional.functors.Predicate;

public interface SelfDescribingPredicate<T> extends Predicate {
    String describe();
}
