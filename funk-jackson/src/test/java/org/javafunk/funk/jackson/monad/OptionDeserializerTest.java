/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.javafunk.funk.jackson.monad;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javafunk.funk.jackson.FunkModule;
import org.javafunk.funk.matchers.OptionMatchers;
import org.javafunk.funk.monads.Option;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.matchers.OptionMatchers.hasValue;

public class OptionDeserializerTest {

    private static final TypeReference<?> OptionTypeRef = new TypeReference<Option<String>>() {};

    private ObjectMapper objectMapper;

    @Before
    public void createObjectMapper() {
        objectMapper = new ObjectMapper()
                .registerModule(new FunkModule());
    }

    @Test
    public void shouldDeserializeNone() throws Exception {
        // When
        Option<String> value = objectMapper.readValue("null", OptionTypeRef);

        // Then
        assertThat(value, OptionMatchers.<String>hasNoValue());
    }

    @Test
    public void shouldDeserializeSimpleString() throws Exception {
        // When
        Option<String> value = objectMapper.readValue("\"simpleString\"", OptionTypeRef);

        // Then
        assertThat(value, hasValue("simpleString"));
    }

    @Test
    public void shouldDeserializeInsideAnObject() throws Exception {
        // When
        OptionData data = objectMapper.readValue("{\"myString\":\"simpleString\"}", OptionData.class);

        // Then
        assertThat(data.myString, hasValue("simpleString"));
    }

    @Test
    public void shouldDeserializeComplexObject() throws Exception {
        // Given
        TypeReference<Option<OptionData>> type = new TypeReference<Option<OptionData>>() {};

        // When
        Option<OptionData> data = objectMapper.readValue("{\"myString\":\"simpleString\"}", type);

        // Then
        assertThat(data, OptionMatchers.<OptionData>hasValue());
        assertThat(data.get().myString, hasValue("simpleString"));
    }

    @Test
    public void shouldDeserializeGenerics() throws Exception {
        // Given
        TypeReference<Option<GenericData<String>>> type = new TypeReference<Option<GenericData<String>>>() {};

        // When
        Option<GenericData<String>> data = objectMapper.readValue("{\"myData\":\"simpleString\"}", type);

        // Then
        assertThat(data, OptionMatchers.<GenericData<String>>hasValue());
        assertThat(data.get().myData, hasValue("simpleString"));
    }

}
