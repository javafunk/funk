package org.javafunk.funk;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Integers.fromLongToInteger;
import static org.junit.Assert.*;

public class IntegersTest {
    @Test
    public void returnsMapperFromLongToInteger() {
        assertThat(fromLongToInteger().map(1234L), is(1234));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromLongToIntegerPassedNull() {
        fromLongToInteger().map(null);
    }
}