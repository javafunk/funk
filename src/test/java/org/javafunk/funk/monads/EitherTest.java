/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.monads;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EitherTest {
    @Test
    public void shouldBeLeftForLeft(){
        Either<String, Integer> either = Either.left("LEFT");

        assertThat(either.isLeft(), is(true));
    }

    @Test
    public void shouldNotBeRightForLeft(){
        Either<String, Integer> either = Either.left("LEFT");

        assertThat(either.isRight(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionOnGetRightForLeft(){
        Either<String, Integer> either = Either.left("LEFT");

        either.getRight();
    }

    @Test
    public void shouldReturnLeftValueForGetLeft(){
        Either<String, Integer> either = Either.right(1);

        assertThat(either.getRight(), is(1));
    }

    @Test
    public void shouldNotBeLeftForRight(){
        Either<String, Integer> either = Either.right(1);

        assertThat(either.isLeft(), is(false));
    }

    @Test
    public void shouldBeRightForRight(){
        Either<String, Integer> either = Either.right(1);

        assertThat(either.isRight(), is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionOnGetLeftForRight(){
        Either<String, Integer> either = Either.right(1);

        either.getLeft();
    }

    @Test
    public void shouldReturnRightValueForGetRight(){
        Either<String, Integer> either = Either.right(1);

        assertThat(either.getRight(), is(1));
    }
}
