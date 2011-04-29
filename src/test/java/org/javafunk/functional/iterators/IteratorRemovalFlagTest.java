package org.javafunk.functional.iterators;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IteratorRemovalFlagTest {
    @Test
    public void shouldBeDisabledByDefault() throws Exception {
        assertThat(new IteratorRemovalFlag().isEnabled(), is(false));
    }

    @Test
    public void shouldBeEnabledIfEnableHasBeenCalled() throws Exception {
        // Given
        IteratorRemovalFlag removalFlag = new IteratorRemovalFlag();

        // When
        removalFlag.enable();

        // Then
        assertThat(removalFlag.isEnabled(), is(true));
    }

    @Test
    public void shouldNotBeEnabledIfDisableHasBeenCalled() throws Exception {
        // Given
        IteratorRemovalFlag removalFlag = new IteratorRemovalFlag();

        // When
        removalFlag.disable();

        // Then
        assertThat(removalFlag.isEnabled(), is(false));
    }
}
