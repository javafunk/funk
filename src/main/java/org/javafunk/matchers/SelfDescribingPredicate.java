package org.javafunk.matchers;

import org.javafunk.functors.Predicate;

public interface SelfDescribingPredicate<T> extends Predicate<T> {
    String describe();
}
