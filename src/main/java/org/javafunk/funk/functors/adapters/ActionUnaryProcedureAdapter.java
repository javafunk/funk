/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Action;
import org.javafunk.funk.functors.procedures.UnaryProcedure;

public class ActionUnaryProcedureAdapter<T> implements UnaryProcedure<T> {
    public static <T> ActionUnaryProcedureAdapter<T> actionUnaryProcedure(Action<? super T> action) {
        return new ActionUnaryProcedureAdapter<T>(action);
    }

    private final Action<? super T> action;

    public ActionUnaryProcedureAdapter(Action<? super T> action) {
        this.action = action;
    }

    @Override public void execute(T target) {
        action.on(target);
    }
}
