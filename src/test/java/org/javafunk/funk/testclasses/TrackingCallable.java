package org.javafunk.funk.testclasses;

import java.util.concurrent.Callable;

public class TrackingCallable<R> implements Callable<R> {
    private final R callResult;

    private Boolean wasCalled = false;

    public static <R> TrackingCallable<R> trackingCallable(R result) {
        return new TrackingCallable<R>(result);
    }

    public TrackingCallable(R callResult) {
        this.callResult = callResult;
    }

    @Override public R call() throws Exception {
        wasCalled = true;
        return callResult;
    }

    public boolean wasCalled() {
        return wasCalled;
    }
}
