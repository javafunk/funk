/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Action;
import org.junit.Test;

import static org.javafunk.funk.Literals.listWith;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class EagerTimesEachTest {
    @Test
    @SuppressWarnings("unchecked")
    public void shouldExecuteSuppliedFunctionOnEachElement() {
        // Given
        Iterable<Target<Object>> targets = listWith(
                (Target<Object>) mock(Target.class),
                (Target<Object>) mock(Target.class),
                (Target<Object>) mock(Target.class));

        // When
        Eager.each(targets, new Action<Target<Object>>() {
            @Override
            public void on(Target<Object> input) {
                input.doSomething();
            }
        });

        // Then
        for(Target<Object> target : targets) {
            verify(target).doSomething();
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldExecuteTheDoFunctionPassingInEachNaturalNumberUpToTheSpecifiedNumber() {
        // Given
        final Target<Integer> target = (Target<Integer>) mock(Target.class);

        // When
        Eager.times(5, new Action<Integer>(){
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
        Eager.times(0, new Action<Integer>(){
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
        Eager.times(numberOfTimes, new Action<Integer>() {
            public void on(Integer input) {
                target.doSomethingWith(input);
            }
        });

        // Then an IllegalArgumentException is thrown.
    }

    private interface Target<T> {
        void doSomething();
        void doSomethingWith(T input);
    }
}
