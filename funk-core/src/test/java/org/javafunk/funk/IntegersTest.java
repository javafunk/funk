package org.javafunk.funk;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Integers.*;
import static org.junit.Assert.assertThat;

public class IntegersTest {
    @Test
    public void returnsMapperFromStringToInteger() {
        assertThat(fromStringToInteger().map("1234"), is(1234));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromStringToIntegerPassedNull() {
        fromStringToInteger().map(null);
    }

    @Test
    public void returnsMapperFromLongToInteger() {
        assertThat(fromLongToInteger().map(1234L), is(1234));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromLongToIntegerPassedNull() {
        fromLongToInteger().map(null);
    }

    @Test
    public void returnsMapperFromBigIntegerToInteger() {
        assertThat(fromBigIntegerToInteger().map(new BigInteger("1234")), is(1234));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromBigIntegerToIntegerPassedNull() {
        fromBigIntegerToInteger().map(null);
    }

    @Test
    public void returnsMapperFromFloatToInteger() {
        assertThat(fromFloatToInteger().map(1234.56F), is(1234));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromFloatToIntegerPassedNull() {
        fromFloatToInteger().map(null);
    }

    @Test
    public void returnsMapperFromDoubleToInteger() {
        assertThat(fromDoubleToInteger().map(1234.56D), is(1234));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromDoubleToIntegerPassedNull() {
        fromDoubleToInteger().map(null);
    }

    @Test
    public void returnsMapperFromBigDecimalToInteger() {
        assertThat(fromBigDecimalToInteger().map(new BigDecimal("1234")), is(1234));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromBigDecimalToIntegerPassedNull() {
        fromBigDecimalToInteger().map(null);
    }
}