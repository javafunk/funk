package org.javafunk.funk.matchers;

import org.javafunk.funk.functors.Predicate;

public interface SelfDescribingPredicate<T> extends Predicate<T> {
    String describe();
}
