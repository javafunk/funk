package org.javafunk.funk;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.javafunk.funk.Longs.*;
import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.option;
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

    @Test
    public void returnsMapperFromStringToPossibleLong() {
        assertThat(fromStringToPossibleLong().map("1234"), is(option(1234L)));
    }

    @Test
    public void returnsNoneWhenMapperFromStringToPossibleLongPassedNull() {
        assertThat(fromStringToPossibleLong().map(null), is(none(Long.class)));
    }

    @Test
    public void returnsNoneWhenMapperFromStringToPossibleLongPassedNonNumericString() {
        assertThat(fromStringToPossibleLong().map("abcd"), is(none(Long.class)));
    }

    @Test
    public void returnsLongIfStringCanBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull("1234"), is(1234L));
    }

    @Test
    public void returnsNoneIfStringCannotBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull((String) null), is(nullValue()));
    }

    @Test
    public void returnsLongIfIntegerCanBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull(1234), is(1234L));
    }

    @Test
    public void returnsNoneIfIntegerCannotBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull((Integer) null), is(nullValue()));
    }

    @Test
    public void returnsLongIfBigIntegerCanBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull(new BigInteger("1234")), is(1234L));
    }

    @Test
    public void returnsNoneIfBigIntegerCannotBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull((BigInteger) null), is(nullValue()));
    }

    @Test
    public void returnsLongIfFloatCanBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull(1234.56F), is(1234L));
    }

    @Test
    public void returnsNoneIfFloatCannotBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull((Float) null), is(nullValue()));
    }

    @Test
    public void returnsLongIfDoubleCanBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull(1234.56D), is(1234L));
    }

    @Test
    public void returnsNoneIfDoubleCannotBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull((Double) null), is(nullValue()));
    }

    @Test
    public void returnsLongIfBigDecimalCanBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull(new BigDecimal("1234.56")), is(1234L));
    }

    @Test
    public void returnsNoneIfBigDecimalCannotBeConvertedUsingToLongOrNull() {
        assertThat(toLongOrNull((BigDecimal) null), is(nullValue()));
    }
}