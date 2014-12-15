package org.javafunk.funk;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.BigIntegers.*;
import static org.junit.Assert.assertThat;

public class BigIntegersTest {
    @Test
    public void returnsMapperFromStringToBigInteger() {
        assertThat(fromStringToBigInteger().map("12345678"), is(new BigInteger("12345678")));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromStringToBigIntegerPassedNull() {
        fromStringToBigInteger().map(null);
    }

    @Test
    public void returnsMapperFromIntegerToBigInteger() {
        assertThat(fromIntegerToBigInteger().map(1234), is(new BigInteger("1234")));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromIntegerToBigIntegerPassedNull() {
        fromIntegerToBigInteger().map(null);
    }

    @Test
    public void returnsMapperFromLongToBigInteger() {
        assertThat(fromLongToBigInteger().map(1234L), is(new BigInteger("1234")));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromLongToBigIntegerPassedNull() {
        fromLongToBigInteger().map(null);
    }

    @Test
    public void returnsMapperFromFloatToBigInteger() {
        assertThat(fromFloatToBigInteger().map(1234.56F), is(new BigInteger("1234")));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromFloatToBigIntegerPassedNull() {
        fromFloatToBigInteger().map(null);
    }

    @Test
    public void returnsMapperFromDoubleToBigInteger() {
        assertThat(fromDoubleToBigInteger().map(1234.56D), is(new BigInteger("1234")));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromDoubleToBigIntegerPassedNull() {
        fromDoubleToBigInteger().map(null);
    }

    @Test
    public void returnsMapperFromBigDecimalToBigInteger() {
        assertThat(fromBigDecimalToBigInteger().map(new BigDecimal("1234.56")), is(new BigInteger("1234")));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromBigDecimalToBigIntegerPassedNull() {
        fromBigDecimalToBigInteger().map(null);
    }
}