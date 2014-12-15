package org.javafunk.funk;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.BigIntegers.fromFloatToBigInteger;
import static org.javafunk.funk.BigIntegers.toStringValue;
import static org.junit.Assert.assertThat;

public class BigIntegersTest {
    @Test
    public void returnsMapperFromFloatToBigInteger() {
        assertThat(fromFloatToBigInteger().map(1234.56F), is(new BigInteger("1234")));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenMapperFromFloatToBigIntegerPassedNull() {
        fromFloatToBigInteger().map(null);
    }

    @Test
    public void returnsMapperFromBigIntegerToStringValue() {
        assertThat(toStringValue().map(new BigInteger("1234")), is("1234"));
    }
}