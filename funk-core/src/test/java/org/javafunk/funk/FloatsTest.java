package org.javafunk.funk;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Floats.fromDoubleToFloat;
import static org.junit.Assert.assertThat;

public class FloatsTest {
    @Test
    public void returnsMapperFromDoubleToFloat() {
        assertThat(fromDoubleToFloat().map(1234D), is(1234F));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromDoubleToFloatPassedNull() {
        fromDoubleToFloat().map(null);
    }
}