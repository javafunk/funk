package org.javafunk;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.listWith;

public class ArrayUtilsTest {
    @Test
    public void shouldConvertTheSuppliedArrayToAList() throws Exception {
        // Given
        String[] array = new String[]{"a", "b", "c"};
        List<String> expectedList = listWith("a", "b", "c");

        // When
        List<String> actualList = ArrayUtils.toList(array);

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldConvertTheSuppliedArrayToAnIterable() throws Exception {
        // Given
        String[] array = new String[]{"a", "b", "c"};

        // When
        Iterable<String> iterable = ArrayUtils.toIterable(array);
        Iterator<String> iterator = iterable.iterator();

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("a"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("b"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("c"));
        assertThat(iterator.hasNext(), is(false));
    }
}
