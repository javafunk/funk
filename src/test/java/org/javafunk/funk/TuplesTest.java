package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.Mapper;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.tuple;

public class TuplesTest {
    @Test
    public void shouldConvertPairToMapEntryWithKeyAsFirstValueAsSecond() throws Exception {
        // Given
        Pair<String, Integer> pair = tuple("string", 100);
        Map.Entry<String, Integer> actual = new AbstractMap.SimpleImmutableEntry<String, Integer>("string", 100);
        Mapper<Pair<String, Integer>, Map.Entry<String, Integer>> toMapEntry = Tuples.toMapEntry();

        // When
        Map.Entry<String, Integer> expected = toMapEntry.map(pair);

        // Then
        assertThat(actual, is(expected));
    }
}
