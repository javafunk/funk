package org.javafunk.functional;

import org.javafunk.functional.functors.Procedure;
import org.junit.Test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.javafunk.Literals.listWith;

public class EagerTimesEachTest {
    @Test
    public void shouldExecuteSuppliedFunctionOnEachElement() {
        // Given
        Iterable<Target> targets = listWith(mock(Target.class), mock(Target.class), mock(Target.class));

        // When
        Eager.each(targets, new Procedure<Target>() {
            @Override
            public void execute(Target input) {
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
        Eager.times(5, new Procedure<Integer>(){
            public void execute(Integer input) {
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
        Eager.times(0, new Procedure<Integer>(){
            public void execute(Integer input) {
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
        Eager.times(numberOfTimes, new Procedure<Integer>(){
            public void execute(Integer input) {
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
