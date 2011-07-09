package org.javafunk;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.mapWith;
import static org.javafunk.Maps.getOrAddDefault;

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
