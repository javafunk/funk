/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Action;
import org.javafunk.funk.functors.procedures.UnaryProcedure;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.listWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LazilyEachTest {
    @Test
    public void shouldLazilyExecuteSuppliedFunctionOnEachElement() {
        // Given
        Iterable<Target> targets = iterableWith(mock(Target.class), mock(Target.class), mock(Target.class));

        // When
        Iterable<Target> preparedTargets = Lazily.each(targets, new Action<Target>() {
            @Override
            public void on(Target input) {
                input.doSomething();
            }
        });

        // Then
        Iterator<Target> preparedTargetsIterator = preparedTargets.iterator();
        for (Target target : targets) {
            verify(target, never()).doSomething();
            preparedTargetsIterator.next();
            verify(target, times(1)).doSomething();
        }
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenNullActionSuppliedToEach() throws Exception {
        // Given
        Iterable<Target> targets = iterableWith(mock(Target.class), mock(Target.class), mock(Target.class));
        Action<? super Target> action = null;

        // When
        Lazily.each(targets, action);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenNullUnaryProcedureSuppliedToEach() throws Exception {
        // Given
        Iterable<Target> targets = iterableWith(mock(Target.class), mock(Target.class), mock(Target.class));
        UnaryProcedure<? super Target> action = null;

        // When
        Lazily.each(targets, action);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        List<Target> targets = listWith(mock(Target.class), mock(Target.class), mock(Target.class));

        // When
        Iterable<Target> iterable = Lazily.each(targets, new Action<Target>() {
            @Override
            public void on(Target input) {
                input.doSomething();
            }
        });
        Iterator<Target> iterator1 = iterable.iterator();
        Iterator<Target> iterator2 = iterable.iterator();

        // Then
        verify(targets.get(0), never()).doSomething();
        iterator1.next();
        verify(targets.get(0), times(1)).doSomething();
        verify(targets.get(1), never()).doSomething();
        iterator1.next();
        verify(targets.get(1), times(1)).doSomething();
        iterator2.next();
        verify(targets.get(0), times(2)).doSomething();
        verify(targets.get(2), never()).doSomething();
        iterator1.next();
        verify(targets.get(2), times(1)).doSomething();
        iterator2.next();
        verify(targets.get(1), times(2)).doSomething();
        iterator2.next();
        verify(targets.get(2), times(2)).doSomething();
    }

    private interface Target {
        void doSomething();
    }
}
