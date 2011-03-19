package org.smallvaluesofcool.misc.functional;

import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.smallvaluesofcool.misc.IterableUtils.materialize;
import static org.smallvaluesofcool.misc.Literals.listWith;

public class LazySliceTest {
    @Test
    public void shouldTakeElementsFromTheIterableBetweenTheSpecifiedStartAndStopInStepsOfTheSpecifiedSize() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> expectedOutput = listWith(3, 5, 7);

        // When
        Collection<Integer> actualOutput = materialize(Lazy.slice(input, 2, 7, 2));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
