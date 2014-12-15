package org.javafunk.funk;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Doubles.*;
import static org.junit.Assert.assertThat;

public class DoublesTest {
    @Test
    public void returnsMapperFromStringToDouble() {
        assertThat(fromStringToDouble().map("12345678"), is(12345678D));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromStringToDoublePassedNull() {
        fromStringToDouble().map(null);
    }

    @Test
    public void returnsMapperFromIntegerToDouble() {
        assertThat(fromIntegerToDouble().map(1234), is(1234D));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromIntegerToDoublePassedNull() {
        fromIntegerToDouble().map(null);
    }

    @Test
    public void returnsMapperFromLongToDouble() {
        assertThat(fromLongToDouble().map(1234L), is(1234D));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromLongToDoublePassedNull() {
        fromLongToDouble().map(null);
    }

    @Test
    public void returnsMapperFromBigIntegerToDouble() {
        assertThat(fromBigIntegerToDouble().map(new BigInteger("1234")), is(1234D));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromBigIntegerToDoublePassedNull() {
        fromBigIntegerToDouble().map(null);
    }

    @Test
    public void returnsMapperFromFloatToDouble() {
        assertThat(fromFloatToDouble().map(1234.56F), is(closeTo(1234.56D, 0.001)));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromFloatToDoublePassedNull() {
        fromFloatToDouble().map(null);
    }

    @Test
    public void returnsMapperFromBigDecimalToDouble() {
        assertThat(fromBigDecimalToDouble().map(new BigDecimal("1234.56")), is(closeTo(1234.56D, 0.001)));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromBigDecimalToDoublePassedNull() {
        fromBigDecimalToDouble().map(null);
    }

    @Test
    public void returnsMapperFromDoubleToStringValue() {
        assertThat(toStringValue().map(1234D), is("1234"));
    }
}