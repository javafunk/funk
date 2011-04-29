package org.javafunk.matchers;

import org.javafunk.functional.functors.PredicateFunction;

public interface SelfDescribingPredicateFunction<T> extends PredicateFunction {
    String describe();
}
