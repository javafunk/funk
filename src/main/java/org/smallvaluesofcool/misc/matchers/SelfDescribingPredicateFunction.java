package org.smallvaluesofcool.misc.matchers;

import org.smallvaluesofcool.misc.functional.PredicateFunction;

public interface SelfDescribingPredicateFunction<T> extends PredicateFunction {
    String describe();
}
