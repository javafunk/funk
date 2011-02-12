package org.smallvaluesofcool.misc.matchers;

import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;

public interface SelfDescribingPredicateFunction<T> extends PredicateFunction {
    String describe();
}
