package org.smallvaluesofcool.misc;

import org.junit.Test;
import org.smallvaluesofcool.misc.collections.Bag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.smallvaluesofcool.misc.Literals.bagWith;

public class BagUtilsTest {
    @Test
    public void shouldReturnABagContainingThoseElementsInTheFirstSetThatAreNotInTheSecond() {
        // Given
        Bag<String> bag1 = bagWith("a", "b", "b", "c", "c", "c");
        Bag<String> bag2 = bagWith("a", "a", "a", "b", "b", "c");
        Bag<String> expectedDifference = bagWith("c", "c");

        // When
        Bag<String> actualDifference = BagUtils.difference(bag1, bag2);
        
        // Then
        assertThat(actualDifference, is(expectedDifference));
    }
}
