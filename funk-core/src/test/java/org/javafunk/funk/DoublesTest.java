package org.javafunk.funk;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Doubles.fromIntegerToDouble;
import static org.junit.Assert.assertThat;

public class DoublesTest {
    @Test
    public void returnsMapperFromIntegerToDouble() {
        assertThat(fromIntegerToDouble().map(1234), is(1234D));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromIntegerToDoublePassedNull() {
        fromIntegerToDouble().map(null);
    }
}