package org.javafunk.funk;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.javafunk.funk.Literals.listFrom;
import static org.javafunk.funk.Literals.listWith;
import static org.junit.Assert.assertThat;

public class MappersTest {
    @Test
    public void identityShouldReturnItself() {
        // Given
        List<String> eastwood = listWith("the", "good", "the", "bad", "and", "the", "ugly");

        // When
        List<String> mappedEastwood = listFrom(Eagerly.map(eastwood, Mappers.<String>identity()));

        // Then
        assertThat(mappedEastwood, is(eastwood));
    }

    @Test
    public void identityShouldReturnItselfEvenWhenNull() {
        // Given
        List<String> eastwood = listWith(null, "good", null, "bad", "and", null, "ugly");

        // When
        List<String> mappedEastwood = listFrom(Eagerly.map(eastwood, Mappers.<String>identity()));

        // Then
        assertThat(mappedEastwood, is(eastwood));
    }
}
