package org.javafunk.funk;

import com.google.common.collect.Multiset;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.multisetWith;

public class MultisetsTest {
    @Test
    public void shouldReturnAMultisetContainingThoseElementsInTheFirstMultisetThatAreNotInTheSecond() {
        // Given
        Multiset<String> firstMultiset = multisetWith("a", "b", "b", "c", "c", "c");
        Multiset<String> secondMultiset = multisetWith("a", "a", "a", "b", "b", "c");
        Multiset<String> expectedDifference = multisetWith("c", "c");

        // When
        Multiset<String> actualDifference = Multisets.difference(firstMultiset, secondMultiset);

        // Then
        assertThat(actualDifference, is(expectedDifference));
    }
}
