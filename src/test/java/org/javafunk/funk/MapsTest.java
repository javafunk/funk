/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Factory;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.NullaryFunction;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.functors.procedures.UnaryProcedure;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.mapBuilderWith;

public class MapsTest {
    @Test
    public void shouldReturnTheValueFromTheMapIfTheKeyAlreadyExists() {
        // Given
        Map<Integer, String> input = mapBuilderWith(1, "one")
                .andKeyValuePair(2, "two")
                .andKeyValuePair(3, "three")
                .build();

        // When
        String value = Maps.getOrAdd(input, 2, new Mapper<Integer, String>() {
            @Override public String map(Integer integer) {
                return integer < 10 ? "lower value" : "higher value";
            }
        });

        // Then
        assertThat(value, is("two"));
    }

    @Test
    public void shouldReturnTheValueReturnedByTheMapperIfTheKeyDoesNotExistInTheMapInGetOrAddUsingUnaryFunction() {
        // Given
        Map<Integer, String> input = mapBuilderWith(1, "one")
                .andKeyValuePair(2, "two")
                .andKeyValuePair(3, "three")
                .build(HashMap.class);

        // When
        String value = Maps.getOrAdd(input, 5, new Mapper<Integer, String>() {
            @Override public String map(Integer integer) {
                return integer < 10 ? "lower value" : "higher value";
            }
        });

        // Then
        assertThat(value, is("lower value"));
    }

    @Test
    public void shouldStoreTheValueReturnedByTheMapperInTheMapIfTheKeyDoesntExist() {
        // Given
        Map<Integer, String> input = mapBuilderWith(1, "one")
                .andKeyValuePair(2, "two")
                .andKeyValuePair(3, "three")
                .build(HashMap.class);

        // When
        Maps.getOrAdd(input, 12, new Mapper<Integer, String>() {
            @Override public String map(Integer integer) {
                return integer < 10 ? "lower value" : "higher value";
            }
        });

        // Then
        assertThat(input.get(12), is("higher value"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfMapperSuppliedToGetOrAddIsNull() throws Exception {
        // Given
        Map<Integer, String> input = mapBuilderWith(1, "one")
                .andKeyValuePair(2, "two")
                .andKeyValuePair(3, "three")
                .build(HashMap.class);
        Mapper<? super Integer, ? extends String> mapper = null;

        // When
        Maps.getOrAdd(input, 5, mapper);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfUnaryFunctionSuppliedToGetOrAddIsNull() throws Exception {
        // Given
        Map<Integer, String> input = mapBuilderWith(1, "one")
                .andKeyValuePair(2, "two")
                .andKeyValuePair(3, "three")
                .build(HashMap.class);
        UnaryFunction<? super Integer, ? extends String> mapper = null;

        // When
        Maps.getOrAdd(input, 5, mapper);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnTheValueReturnedByTheFactoryIfTheKeyDoesNotExistInTheMapInGetOrAddUsingUnaryProcedure() throws Exception {
        // Given
        Map<Integer, String> input = mapBuilderWith(1, "one")
                .andKeyValuePair(2, "two")
                .andKeyValuePair(3, "three")
                .build(HashMap.class);

        // When
        String value = Maps.getOrAdd(input, 2, new Factory<String>() {
            @Override public String create() {
                return "default";
            }
        });

        // Then
        assertThat(value, is("two"));
    }

    @Test
    public void shouldReturnTheValueReturnedByTheFactoryIfTheKeyDoesNotExistInTheMapInGetOrAddUsingUnaryFunction() {
        // Given
        Map<Integer, String> input = mapBuilderWith(1, "one")
                .andKeyValuePair(2, "two")
                .andKeyValuePair(3, "three")
                .build(HashMap.class);

        // When
        String value = Maps.getOrAdd(input, 5, new Factory<String>() {
            @Override public String create() {
                return "default";
            }
        });

        // Then
        assertThat(value, is("default"));
    }

    @Test
    public void shouldStoreTheValueReturnedByTheFactoryInTheMapIfTheKeyDoesntExist() {
        // Given
        Map<Integer, String> input = mapBuilderWith(1, "one")
                .andKeyValuePair(2, "two")
                .andKeyValuePair(3, "three")
                .build(HashMap.class);

        // When
        Maps.getOrAdd(input, 12, new Factory<String>() {
            @Override public String create() {
                return "default";
            }
        });

        // Then
        assertThat(input.get(12), is("default"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfFactorySuppliedToGetOrAddIsNull() throws Exception {
        // Given
        Map<Integer, String> input = mapBuilderWith(1, "one")
                .andKeyValuePair(2, "two")
                .andKeyValuePair(3, "three")
                .build(HashMap.class);
        Factory<? extends String> factory = null;

        // When
        Maps.getOrAdd(input, 5, factory);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfUnaryProcedureSuppliedToGetOrAddIsNull() throws Exception {
        // Given
        Map<Integer, String> input = mapBuilderWith(1, "one")
                .andKeyValuePair(2, "two")
                .andKeyValuePair(3, "three")
                .build(HashMap.class);
        NullaryFunction<? extends String> factory = null;

        // When
        Maps.getOrAdd(input, 5, factory);

        // Then a NullPointerException is thrown.
    }
}
