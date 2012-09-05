package org.javafunk.funk.predicates;

import org.javafunk.funk.functors.Predicate;

public class InstanceOfPredicate<T> implements Predicate<T> {
    private final Class<?> testClass;

    public InstanceOfPredicate(Class<?> testClass) {
        this.testClass = testClass;
    }

    @Override public boolean evaluate(T instance) {
        return testClass.isInstance(instance);
    }
}
