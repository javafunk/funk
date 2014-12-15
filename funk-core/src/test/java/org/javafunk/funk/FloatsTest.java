package org.javafunk.funk;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Floats.*;
import static org.junit.Assert.assertThat;

public class FloatsTest {
    @Test
    public void returnsMapperFromStringToFloat() {
        assertThat(fromStringToFloat().map("12345678.910"), is(12345678.910F));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromStringToFloatPassedNull() {
        fromStringToFloat().map(null);
    }

    @Test
    public void returnsMapperFromIntegerToFloat() {
        assertThat(fromIntegerToFloat().map(1234), is(1234F));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromIntegerToFloatPassedNull() {
        fromIntegerToFloat().map(null);
    }

    @Test
    public void returnsMapperFromLongToFloat() {
        assertThat(fromLongToFloat().map(1234L), is(1234F));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromLongToFloatPassedNull() {
        fromLongToFloat().map(null);
    }

    @Test
    public void returnsMapperFromBigIntegerToFloat() {
        assertThat(fromBigIntegerToFloat().map(new BigInteger("1234")), is(1234F));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromBigIntegerToFloatPassedNull() {
        fromBigIntegerToFloat().map(null);
    }

    @Test
    public void returnsMapperFromDoubleToFloat() {
        assertThat(fromDoubleToFloat().map(1234D), is(1234F));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromDoubleToFloatPassedNull() {
        fromDoubleToFloat().map(null);
    }

    @Test
    public void returnsMapperFromBigDecimalToFloat() {
        assertThat(fromBigDecimalToFloat().map(new BigDecimal("1234.56")), is(1234.56F));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromBigDecimalToFloatPassedNull() {
        fromBigDecimalToFloat().map(null);
    }
}