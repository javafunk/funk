package org.javafunk.functional;

import org.javafunk.functional.functors.Action;
import org.junit.Test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.javafunk.Literals.listWith;

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
        Eager.times(numberOfTimes, new Action<Integer>(){
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
