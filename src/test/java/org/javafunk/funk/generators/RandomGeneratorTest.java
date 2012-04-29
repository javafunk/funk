package org.javafunk.funk.generators;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RandomGeneratorTest {
    @Test
    public void shouldGenerateAnIndexAtRandomAndReturnTheElementAtThatIndex() throws Exception {
        // Given
        Random random = mock(Random.class);
        RandomGenerator<String> generator = new RandomGenerator<String>(
                iterableWith("zeroth", "first", "second"),
                random);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);

        when(random.nextInt(argumentCaptor.capture())).thenReturn(1);

        // When
        String value = generator.next();

        // Then
        assertThat(argumentCaptor.getValue(), is(3));
        assertThat(value, is("first"));
    }

    @Test
    public void shouldBeEqualIfSuppliedIterablesAreTheSame() throws Exception {
        // Given
        RandomGenerator<Integer> firstGenerator = new RandomGenerator<Integer>(iterableWith(1, 2, 3, 4, 5, 6));
        RandomGenerator<Integer> secondGenerator = new RandomGenerator<Integer>(iterableWith(1, 2, 3, 4, 5, 6));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfSuppliedIterablesAreDifferent() throws Exception {
        // Given
        RandomGenerator<Integer> firstGenerator = new RandomGenerator<Integer>(iterableWith(1, 2, 3, 4, 5, 6));
        RandomGenerator<Integer> secondGenerator = new RandomGenerator<Integer>(iterableWith(7, 8, 9, 10, 11, 12));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }
}
