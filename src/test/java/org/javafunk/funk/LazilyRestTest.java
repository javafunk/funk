package org.javafunk.funk;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.iterableWith;
import static org.junit.Assert.assertThat;

public class LazilyRestTest {
    @Test
    public void shouldReturnTheRestOfTheIterable(){
        //given
        Iterable<String> iterable = iterableWith("a", "b", "c", "d");
        Iterable<String> expectedRest = iterableWith("b", "c", "d");

        //when
        Iterable<String> rest = Lazily.rest(iterable);
        
        //then
        assertThat(materialize(rest), equalTo(expectedRest));
    }

    @Test
    public void shouldReturnEmptyIterableForAnIterableWithOneElement(){
        //given
        Iterable<String> iterable = iterableWith("a");
        Iterable<String> expectedRest = Iterables.empty();

        //when
        Iterable<String> rest = Lazily.rest(iterable);

        //then
        assertThat(materialize(rest), equalTo(expectedRest));
    }

    @Test
    public void shouldReturnEmptyIterableForAnEmptyIterable(){
        //given
        Iterable<String> iterable = Iterables.empty();
        Iterable<String> expectedRest = Iterables.empty();

        //when
        Iterable<String> rest = Lazily.rest(iterable);

        //then
        assertThat(materialize(rest), equalTo(expectedRest));
    }
}
