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
import org.junit.Test;

import static org.javafunk.funk.Literals.iterableWith;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class EagerlyTimesEachTest {
    @Test
    @SuppressWarnings("unchecked")
    public void shouldExecuteSuppliedFunctionOnEachElement() {
        // Given
        Iterable<Target<Object>> targets = iterableWith(
                (Target<Object>) mock(Target.class),
                (Target<Object>) mock(Target.class),
                (Target<Object>) mock(Target.class));

        // When
        Eagerly.each(targets, new Action<Target<Object>>() {
            @Override
            public void on(Target<Object> input) {
                input.doSomething();
            }
        });

        // Then
        for (Target<Object> target : targets) {
            verify(target).doSomething();
        }
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void shouldThrowNullPointerExceptionIfTheActionSuppliedToEachIsNull() throws Exception {
        // Given
        Iterable<Target<Object>> targets = iterableWith(
                (Target<Object>) mock(Target.class),
                (Target<Object>) mock(Target.class),
                (Target<Object>) mock(Target.class));
        Action<Target> action = null;

        // When
        Eagerly.each(targets, action);

        // Then a NullPointerException is thrown
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldExecuteTheDoFunctionPassingInEachNaturalNumberUpToTheSpecifiedNumber() {
        // Given
        final Target<Integer> target = (Target<Integer>) mock(Target.class);

        // When
        Eagerly.times(5, new Action<Integer>() {
            public void on(Integer input) {
                target.doSomethingWith(input);
            }
        });

        // Then
        for (int i = 0; i < 5; i++) {
            verify(target).doSomethingWith(i);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldNotExecuteTheDoFunctionAtAllIfANumberOfTimesToExecuteOfZeroIsSupplied() throws Exception {
        // Given
        final Target<Integer> target = (Target<Integer>) mock(Target.class);

        // When
        Eagerly.times(0, new Action<Integer>() {
            public void on(Integer input) {
                target.doSomethingWith(input);
            }
        });

        verify(target, never()).doSomethingWith(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("unchecked")
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedNumberOfTimesIsLessThanZero() throws Exception {
        // Given
        final Target<Integer> target = (Target<Integer>) mock(Target.class);
        Integer numberOfTimes = -3;

        // When
        Eagerly.times(numberOfTimes, new Action<Integer>() {
            public void on(Integer input) {
                target.doSomethingWith(input);
            }
        });

        // Then an IllegalArgumentException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfTheActionSuppliedToTimesIsNull() throws Exception {
        // Given
        Action<Integer> action = null;
        Integer numberOfTimes = 10;


        // When
        Eagerly.times(numberOfTimes, action);

        // Then a NullPointerException is thrown
    }

    private interface Target<T> {
        void doSomething();

        void doSomethingWith(T input);
    }
}
