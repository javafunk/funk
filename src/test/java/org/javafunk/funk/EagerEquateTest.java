package org.javafunk.funk;

import org.javafunk.funk.Eager;
import org.javafunk.funk.Eager;
import org.javafunk.funk.functors.Equator;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.listWith;

public class EagerEquateTest {
    @Test
    public void shouldReturnACollectionContainingTheResultOfEquatingEachElementUsingTheSuppliedEquator() throws Exception {
        // Given
        Iterable<String> first = listWith("Dog", "Cat", "Goldfish");
        Iterable<String> second = listWith("DOG", "BAT", "GOLDFISH");
        Collection<Boolean> expectedEqualityResult = listWith(true, false, true);

        // When
        Collection<Boolean> actualEqualityResult = Eager.equate(first, second, new Equator<String>() {
            public boolean equate(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });

        // Then
        assertThat(actualEqualityResult, is(expectedEqualityResult));
    }

    @Test
    public void shouldOnlyEquateAsManyElementsAsPossibleIfTheFirstIterableIsShorterThanTheSecond() throws Exception {
        // Given
        Iterable<String> first = listWith("Dog", "Cat", "Goldfish");
        Iterable<String> second = listWith("DOG", "BAT", "GOLDFISH", "HORSE", "PIG");
        Collection<Boolean> expectedEqualityResult = listWith(true, false, true);

        // When
        Collection<Boolean> actualEqualityResult = Eager.equate(first, second, new Equator<String>() {
            public boolean equate(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });

        // Then
        assertThat(actualEqualityResult, is(expectedEqualityResult));
    }

    @Test
    public void shouldOnlyEquateAsManyElementsAsPossibleIfTheSecondIterableIsShorterThanTheFirst() throws Exception {
        // Given
        Iterable<String> first = listWith("Dog", "Cat", "Goldfish", "Horse", "Pig");
        Iterable<String> second = listWith("DOG", "BAT", "GOLDFISH");
        Collection<Boolean> expectedEqualityResult = listWith(true, false, true);

        // When
        Collection<Boolean> actualEqualityResult = Eager.equate(first, second, new Equator<String>() {
            public boolean equate(String first, String second) {
                return first.compareToIgnoreCase(second) == 0;
            }
        });

        // Then
        assertThat(actualEqualityResult, is(expectedEqualityResult));
    }
}
