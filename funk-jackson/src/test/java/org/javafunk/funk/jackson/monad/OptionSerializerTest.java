/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.javafunk.funk.jackson.monad;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javafunk.funk.jackson.FunkModule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.option;

public class OptionSerializerTest {

    private ObjectMapper objectMapper;

    @Before
    public void createObjectMapper() {
        objectMapper = new ObjectMapper()
                .registerModule(new FunkModule());
    }

    @Test
    public void shouldSerializeNone() throws IOException {
        // When
        String value = objectMapper.writeValueAsString(none());

        // Then
        assertThat(value, is("null"));
    }

    @Test
    public void shouldSerializeSimpleString() throws Exception {
        // When
        String value = objectMapper.writeValueAsString(option("simpleString"));

        // Then
        assertThat(value, is("\"simpleString\""));
    }

    @Test
    public void shouldSerializeInsideAnObject() throws Exception {
        // Given
        OptionData data = new OptionData();
        data.myString = option("simpleString");

        // When
        String value = objectMapper.writeValueAsString(data);

        // Then
        assertThat(value, is("{\"myString\":\"simpleString\"}"));
    }

    @Test
    public void shouldSerializeComplexObject() throws Exception {
        // Given
        OptionData data = new OptionData();
        data.myString = option("simpleString");

        // When
        String value = objectMapper.writeValueAsString(option(data));

        // Then
        assertThat(value, is("{\"myString\":\"simpleString\"}"));
    }

    @Test
    public void shouldSerializeGenerics() throws Exception {
        // Given
        GenericData<String> data = new GenericData<String>();
        data.myData = option("simpleString");

        // When
        String value = objectMapper.writeValueAsString(option(data));

        // Then
        assertThat(value, is("{\"myData\":\"simpleString\"}"));
    }

    @Test
    @Ignore("This is currently failing, need to do more investigation to find out why")
    public void shouldTreatNoneAsNullAndExcludeWhenIncludeNonNullIsSet() throws Exception {
        // Given
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new FunkModule())
                .setSerializationInclusion(NON_NULL);

        OptionData data = new OptionData();
        data.myString = none();

        // When
        String value = objectMapper.writeValueAsString(data);

        // Then
        assertThat(value, is("{}"));
    }

    @Test
    public void shouldSerializeANullOptionEvenThoughThePointOfOptionIsToAvoidNulls() throws Exception {
        // Given
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new FunkModule())
                .setSerializationInclusion(NON_NULL);

        OptionData data = new OptionData();
        data.myString = null;

        // When
        String value = objectMapper.writeValueAsString(data);

        // Then
        assertThat(value, is("{}"));
    }

}
