package org.javafunk.functional.iterators;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IteratorCacheTest {
    @Test
    public void shouldStoreTheSuppliedObject() throws Exception {
        // Given
        IteratorCache<Integer> cache = new IteratorCache<Integer>();

        // When
        cache.store(123);

        // Then
        assertThat(cache.fetch(), is(123));
    }

    @Test
    public void shouldBePopulatedIfAnObjectHasBeenPushed() throws Exception {
        // Given
        IteratorCache<Integer> cache = new IteratorCache<Integer>();

        // When
        cache.store(123);

        // Then
        assertThat(cache.isPopulated(), is(true));
    }

    @Test
    public void shouldNotBePopulatedAfterAPushedObjectHasBeenPopped() throws Exception {
        // Given
        IteratorCache<Integer> cache = new IteratorCache<Integer>();

        // When
        cache.store(123);
        cache.fetch();

        // Then
        assertThat(cache.isPopulated(), is(false));
    }
}
