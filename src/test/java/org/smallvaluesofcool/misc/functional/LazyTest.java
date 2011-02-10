package org.smallvaluesofcool.misc.functional;

import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.smallvalues.misc.collections.IterableUtils.toList;

public class LazyTest {

    @Test
    public void shouldMapCollectionUsingCustomFunction() throws Exception {
        // Given
        Collection<Integer> input = asList(1, 2, 3);

        // When
        Collection<String> actual = toList(Lazy.map(input, new MapFunction<Integer, String>() {
            public String map(Integer input) {
                return String.valueOf(input);
            }
        }));

        // Then
        assertThat(actual, hasItems("1", "2", "3"));
    }
}
