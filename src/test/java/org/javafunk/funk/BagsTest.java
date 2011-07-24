package org.javafunk.funk;

import org.javafunk.funk.collections.Bag;
import org.javafunk.funk.Bags;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.bagWith;

public class BagsTest {
    @Test
    public void shouldReturnABagContainingThoseElementsInTheFirstSetThatAreNotInTheSecond() {
        // Given
        Bag<String> bag1 = bagWith("a", "b", "b", "c", "c", "c");
        Bag<String> bag2 = bagWith("a", "a", "a", "b", "b", "c");
        Bag<String> expectedDifference = bagWith("c", "c");

        // When
        Bag<String> actualDifference = Bags.difference(bag1, bag2);

        // Then
        assertThat(actualDifference, is(expectedDifference));
    }
}
