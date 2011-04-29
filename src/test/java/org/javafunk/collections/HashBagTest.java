package org.javafunk.collections;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.javafunk.IteratorUtils;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.javafunk.Literals.bagWith;
import static org.javafunk.Literals.listWith;

public class HashBagTest {
    @Test
    public void shouldReturnEmpty() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();

        // When
        boolean empty = bag.isEmpty();

        // Then
        assertThat(empty, is(true));
    }

    @Test
    public void shouldNotReturnEmptyOncePopulated() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();
        bag.add(1);

        // When
        boolean empty = bag.isEmpty();

        // Then
        assertThat(empty, is(false));
    }

    @Test
    public void shouldContainAddedItems() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();
        bag.add(1);

        // When
        boolean contains = bag.contains(1);

        // Then
        assertThat(contains, is(true));
    }

    @Test
    public void shouldNotContainOtherItems() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();
        bag.add(1);

        // When
        boolean contains = bag.contains(2);

        // Then
        assertThat(contains, is(false));
    }

    @Test
    public void shouldContainAllAddedItems() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();

        // When
        bag.addAll(listWith(1, 2));

        // Then
        assertThat(bag.contains(1), is(true));
        assertThat(bag.contains(2), is(true));
        assertThat(bag.contains(3), is(false));
    }

    @Test
    public void shouldHaveZeroSize() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();

        // When

        // Then
        assertThat(bag.size(), is(0));
    }

    @Test
    public void shouldHaveSize() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();

        // When
        bag.addAll(listWith(1, 2, 3));

        // Then
        assertThat(bag.size(), is(3));
    }

    @Test
    public void shouldIterate() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();
        bag.addAll(listWith(1, 2, 2, 3));

        // When
        List<Integer> actual = IteratorUtils.toList(bag.iterator());

        // Then
        assertThat(actual, Matchers.containsInAnyOrder(1, 2, 2, 3));
    }

    @Test
    public void shouldRemoveItems() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();
        bag.addAll(listWith(1, 2, 3));

        // When
        bag.remove(2);

        // Then
        assertThat(bag, Matchers.containsInAnyOrder(1, 3));
    }

    @Test
    public void shouldRemoveAllItems() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();
        bag.addAll(listWith(1, 2, 3, 2, 3, 4, 4));

        // When
        bag.removeAll(listWith(2, 2, 3));

        // Then
        assertThat(bag, Matchers.containsInAnyOrder(1, 3, 4, 4));
    }

    @Test
    public void shouldClearBag() {
        // Given
        Bag<Integer> bag = new HashBag<Integer>();
        bag.addAll(listWith(1, 2, 3, 2, 3, 4, 4));

        // When
        bag.clear();

        // Then
        assertThat(bag.size(), is(0));
    }

    @Test
    public void shouldBeEqualIfHasSameElements() {
        // Given
        Bag<Integer> bag1 = bagWith(1, 1, 4);
        Bag<Integer> bag2 = bagWith(1, 4, 1);

        // When
        Boolean isEqual = bag1.equals(bag2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentElements() {
        // Given
        Bag<Integer> bag1 = bagWith(1, 1, 2);
        Bag<Integer> bag2 = bagWith(1, 4, 1);

        // When
        Boolean isEqual = bag1.equals(bag2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeReflexivelyEqual() {
        // Given
        Bag<Integer> bag = bagWith(1, 2, 2);

        // When

        // Then
        assertThat(bag.equals(bag), is(true));
    }

    @Test
    public void shouldBeSymmetricallyEqual() {
        // Given
        Bag<Integer> bag1 = bagWith(1, 2, 2);
        Bag<Integer> bag2 = bagWith(1, 2, 2);
        Bag<Integer> bag3 = bagWith(1, 1, 2);

        // When

        // Then
        assertThat(bag1.equals(bag2), is(bag2.equals(bag1)));
        assertThat(bag2.equals(bag3), is(bag3.equals(bag2)));
    }

    @Test
    public void shouldBeTransitivelyEqual() {
        // Given
        Bag<Integer> bag1 = bagWith(1, 2, 2);
        Bag<Integer> bag2 = bagWith(1, 2, 2);
        Bag<Integer> bag3 = bagWith(1, 2, 2);

        // When

        // Then
        assertThat(bag1.equals(bag2), is(true));
        assertThat(bag2.equals(bag3), is(true));
        assertThat(bag1.equals(bag3), is(true));
    }

    @Test
    public void shouldReturnFalseIfTestingEqualityWithNull() {
        // Given
        Bag<Integer> bag = bagWith(1, 2, 3);

        // When

        // Then
        assertThat(bag.equals(null), is(false));
    }

    @Test
    public void shouldHaveSameHashCodeIfEqual() {
        // Given
        Bag<Integer> bag1 = bagWith(1, 2, 2);
        Bag<Integer> bag2 = bagWith(1, 2, 2);

        // When
        Integer hashCode1 = bag1.hashCode();
        Integer hashCode2 = bag2.hashCode();

        // Then
        assertThat(hashCode1, is(hashCode2));
    }

    @Test
    public void shouldHaveDifferentHashCodesIfNotEqual() {
        // Given
        Bag<Integer> bag1 = bagWith(1, 2, 2);
        Bag<Integer> bag2 = bagWith(3, 2, 1);

        // When
        Integer hashCode1 = bag1.hashCode();
        Integer hashCode2 = bag2.hashCode();

        // Then
        assertThat(hashCode1, is(not(hashCode2)));
    }
}
