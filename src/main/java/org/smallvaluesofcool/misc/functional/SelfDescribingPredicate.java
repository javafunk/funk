package org.smallvaluesofcool.misc.functional;

public interface SelfDescribingPredicate<T> extends Predicate<T> {
    String describe();
}
