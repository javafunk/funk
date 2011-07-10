package org.javafunk;

import org.javafunk.Lazy;
import org.javafunk.functors.Action;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.javafunk.Literals.listWith;

public class LazyEachTest {
    @Test
    public void shouldLazilyExecuteSuppliedFunctionOnEachElement() {
        // Given
        Iterable<Target> targets = listWith(mock(Target.class), mock(Target.class), mock(Target.class));

        // When
        Iterable<Target> preparedTargets = Lazy.each(targets, new Action<Target>() {
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

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        List<Target> targets = listWith(mock(Target.class), mock(Target.class), mock(Target.class));

        // When
        Iterable<Target> iterable = Lazy.each(targets, new Action<Target>() {
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
