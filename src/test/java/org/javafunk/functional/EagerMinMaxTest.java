package org.javafunk.functional;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.listWith;
import static org.javafunk.functional.EagerMinMaxTest.NonComparableObject.nonComparableObject;

public class EagerMinMaxTest {
    @Test
    public void shouldReturnTheMinimumValue() throws Exception {
        // Given
        List<String> list = listWith("b", "a", "c");

        // When
        String actual = Eager.min(list);

        // Then
        assertThat(actual, is("a"));
    }

    @Test
    public void shouldReturnTheMinimumValueAccordingToTheSuppliedComparator() throws Exception {
        // Given

        List<NonComparableObject> list = listWith(
                nonComparableObject("aaaa"),
                nonComparableObject("aa"),
                nonComparableObject("aaa"),
                nonComparableObject("aaaaa"));

        // When
        NonComparableObject actual = Eager.min(list, new Comparator<NonComparableObject>(){
            public int compare(NonComparableObject first, NonComparableObject second) {
                return first.length() - second.length();
            }
        });

        // Then
        assertThat(actual, is(nonComparableObject("aa")));
    }

    @Test
    public void shouldReturnTheMaximumValue() throws Exception {
        // Given
        List<Integer> list = listWith(3, 2, 6);

        // When
        Integer actual = Eager.max(list);

        // Then
        assertThat(actual, is(6));
    }

    @Test
    public void shouldReturnTheMaximumValueAccordingToTheSuppliedComparator() throws Exception {
        // Given
        List<NonComparableObject> list = listWith(
                nonComparableObject("aaaa"),
                nonComparableObject("aa"),
                nonComparableObject("aaa"),
                nonComparableObject("aaaaa"));

        // When
        NonComparableObject actual = Eager.max(list, new Comparator<NonComparableObject>() {
            public int compare(NonComparableObject first, NonComparableObject second) {
                return first.length() - second.length();
            }
        });

        // Then
        assertThat(actual, is(nonComparableObject("aaaaa")));
    }

    static class NonComparableObject {
        private String field;

        public static NonComparableObject nonComparableObject(String field) {
            return new NonComparableObject(field);
        }

        private NonComparableObject(String field) {
            this.field = field;
        }

        public int length() {
            return field.length();
        }

        @Override
        public boolean equals(Object other) {
            return EqualsBuilder.reflectionEquals(this, other);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
