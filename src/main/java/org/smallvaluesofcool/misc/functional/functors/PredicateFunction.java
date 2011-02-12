package org.smallvaluesofcool.misc.functional.functors;

public interface PredicateFunction<T> {
    boolean matches(T item);
}

