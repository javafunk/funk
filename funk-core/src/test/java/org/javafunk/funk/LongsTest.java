package org.javafunk.funk;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Longs.*;
import static org.junit.Assert.*;

public class LongsTest {
    @Test
    public void returnsMapperFromStringToLong() {
        assertThat(fromStringToLong().map("1234"), is(1234L));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromStringToLongPassedNull() {
        fromStringToLong().map(null);
    }

    @Test
    public void returnsMapperFromIntegerToLong() {
        assertThat(fromIntegerToLong().map(1234), is(1234L));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromIntegerToLongPassedNull() {
        fromIntegerToLong().map(null);
    }

    @Test
    public void returnsMapperFromBigIntegerToLong() {
        assertThat(fromBigIntegerToLong().map(new BigInteger("1234")), is(1234L));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromBigIntegerToLongPassedNull() {
        fromBigIntegerToLong().map(null);
    }

    @Test
    public void returnsMapperFromFloatToLong() {
        assertThat(fromFloatToLong().map(1234.56F), is(1234L));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromFloatToLongPassedNull() {
        fromFloatToLong().map(null);
    }

    @Test
    public void returnsMapperFromDoubleToLong() {
        assertThat(fromDoubleToLong().map(1234.56D), is(1234L));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromDoubleToLongPassedNull() {
        fromDoubleToLong().map(null);
    }

    @Test
    public void returnsMapperFromBigDecimalToLong() {
        assertThat(fromBigDecimalToLong().map(new BigDecimal("1234")), is(1234L));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromBigDecimalToLongPassedNull() {
        fromBigDecimalToLong().map(null);
    }
}