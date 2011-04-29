package org.javafunk.functional;

import org.junit.Test;
import org.javafunk.functional.functors.DoFunction;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.javafunk.Literals.listWith;

public class EagerTimesEachTest {
    @Test
    public void shouldExecuteSuppliedFunctionOnEachElement() {
        // Given
        Iterable<Target> targets = listWith(mock(Target.class), mock(Target.class), mock(Target.class));

        // When
        Eager.each(targets, new DoFunction<Target>() {
            @Override
            public void actOn(Target input) {
                input.doSomething();
            }
        });

        // Then
        for(Target target : targets) {
            verify(target).doSomething();
        }
    }

    @Test
    public void shouldExecuteTheDoFunctionPassingInEachNaturalNumberUpToTheSpecifiedNumber() {
        // Given
        final Target<Integer> target = mock(Target.class);

        // When
        Eager.times(5, new DoFunction<Integer>(){
            public void actOn(Integer input) {
                target.doSomethingWith(input);
            }
        });

        // Then
        for (int i = 0; i < 5; i++) {
            verify(target).doSomethingWith(i);
        }
    }

    @Test
    public void shouldNotExecuteTheDoFunctionAtAllIfANumberOfTimesToExecuteOfZeroIsSupplied() throws Exception {
        // Given
        final Target<Integer> target = mock(Target.class);

        // When
        Eager.times(0, new DoFunction<Integer>(){
            public void actOn(Integer input) {
                target.doSomethingWith(input);
            }
        });

        verify(target, never()).doSomethingWith(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedNumberOfTimesIsLessThanZero() throws Exception {
        // Given
        final Target<Integer> target = mock(Target.class);
        Integer numberOfTimes = -3;

        // When
        Eager.times(numberOfTimes, new DoFunction<Integer>(){
            public void actOn(Integer input) {
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
