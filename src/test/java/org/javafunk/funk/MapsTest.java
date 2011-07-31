/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.mapWith;
import static org.javafunk.funk.Maps.getOrAddDefault;

public class MapsTest {
    @Test
    public void shouldReturnTheValueFromTheMapIfTheKeyAlreadyExists() {
        // Given
        Map<Integer, String> inputMap = mapWith(1, "one")
                .and(2, "two")
                .and(3, "three");

        // When
        String value = getOrAddDefault(inputMap, 2, defaultValueFactory());

        // Then
        assertThat(value, is("two"));
    }

    @Test
    public void shouldReturnTheValueCreatedByTheDefaultFactoryIfTheKeyDoesNotExistInTheMap() {
        // Given
        Map<Integer, String> inputMap = mapWith(1, "one")
                .and(2, "two")
                .and(3, "three");

        // When
        String value = getOrAddDefault(inputMap, 5, defaultValueFactory());

        // Then
        assertThat(value, is("default value"));
    }

    @Test
    public void shouldStoreTheDefaultValueInTheMapIfTheKeyDoesntExist() {
        // Given
        Map<Integer, String> inputMap = mapWith(1, "one")
                .and(2, "two")
                .and(3, "three");

        // When
        getOrAddDefault(inputMap, 5, defaultValueFactory());

        // Then
        assertThat(inputMap.get(5), is("default value"));
    }

    private Maps.DefaultValueFactory<String> defaultValueFactory() {
        return new Maps.DefaultValueFactory<String>() {
            public String create() {
                return "default value";
            }
        };
    }
}
