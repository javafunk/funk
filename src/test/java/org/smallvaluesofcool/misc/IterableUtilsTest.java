package org.smallvaluesofcool.misc;

import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.smallvaluesofcool.misc.IterableUtils.materialize;
import static org.smallvaluesofcool.misc.IterableUtils.toList;
import static org.smallvaluesofcool.misc.IteratorUtils.toIterable;
import static org.smallvaluesofcool.misc.Literals.listWith;

public class IterableUtilsTest {
    @Test
    public void shouldFetchAllValuesFromTheAssociatedIteratorAndReturnThemInAList() {
        // Given
        final List<Integer> expectedList = listWith(1, 2, 3);
        Iterable<Integer> input = toIterable(new Iterator<Integer>() {
            private Integer cursor = 0;
            @Override
            public boolean hasNext() {
                return cursor < (expectedList.size());
            }

            @Override
            public Integer next() {
                Integer next = expectedList.get(cursor);
                cursor++;
                return next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        });

        // When
        List<Integer> actualList = toList(input);

        // Then
        assertThat(actualList, is(expectedList));
    }
}
