package org.smallvaluesofcool.misc.datastructures;

public interface Range<T> extends Iterable<T> {
    boolean contains(Object other);
    T getStart();
    T getEnd();
    T getStep();
}
