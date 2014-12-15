/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.BigDecimals.*;
import static org.junit.Assert.*;

public class BigDecimalsTest {
    @Test
    public void returnsMapperFromLongToBigDecimal() {
        assertThat(fromLongToBigDecimal().map(123L), is(new BigDecimal("123")));
    }

    @Test
    public void returnsMapperFromDoubleToBigDecimal() {
        assertThat(fromDoubleToBigDecimal().map(123.45D).setScale(2, RoundingMode.DOWN), is(new BigDecimal("123.45")));
    }

    @Test
    public void returnsMapperFromIntegerToBigDecimal() {
        assertThat(fromIntegerToBigDecimal().map(123), is(new BigDecimal("123")));
    }

    @Test
    public void returnsMapperFromStringToBigDecimal() {
        assertThat(fromStringToBigDecimal().map("123.45"), is(new BigDecimal("123.45")));
    }

    @Test
    public void returnsMapperFromBigDecimalToPlainString() {
        assertThat(toPlainString().map(new BigDecimal("123.456")), is("123.456"));
    }

    @Test
    public void returnsMapperWhichScalesBigDecimalAccordingToSuppliedArguments() {
        assertThat(BigDecimals.toScaled(2, RoundingMode.HALF_UP).map(new BigDecimal("123.455")),
                is(new BigDecimal("123.46")));
        assertThat(BigDecimals.toScaled(1, RoundingMode.HALF_DOWN).map(new BigDecimal("123.45")),
                is(new BigDecimal("123.4")));
    }

    @Test
    public void providesLiteralsForBigDecimals() {
        assertThat(bigDecimal(123), is(new BigDecimal("123")));
        assertThat(bigDecimal(123L), is(new BigDecimal("123")));
        assertThat(bigDecimal(123.45D).setScale(2, RoundingMode.DOWN), is(new BigDecimal("123.45")));
        assertThat(bigDecimal("123.45"), is(new BigDecimal("123.45")));
    }
}