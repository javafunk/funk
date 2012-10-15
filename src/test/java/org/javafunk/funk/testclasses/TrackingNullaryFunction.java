package org.javafunk.funk.testclasses;

import org.javafunk.funk.functors.functions.NullaryFunction;

public class TrackingNullaryFunction<R> implements NullaryFunction<R> {
    private final R result;

    private Boolean wasCalled = false;

    public static <R> TrackingNullaryFunction<R> trackingNullaryFunction(R result) {
        return new TrackingNullaryFunction<R>(result);
    }

    public TrackingNullaryFunction(R result) {
        this.result = result;
    }

    @Override public R call() {
        this.wasCalled = true;
        return result;
    }

    public Boolean wasCalled() {
        return wasCalled;
    }
}
