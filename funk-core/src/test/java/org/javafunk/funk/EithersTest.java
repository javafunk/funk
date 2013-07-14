/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.monads.Either;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;

public class EithersTest {
    @Test
    public void shouldCollapseIterableOfEithersOntoTheirRights() throws Exception {
        // Given
        Either<String, Integer> firstEither = Either.left("first");
        Either<String, Integer> secondEither = Either.left("second");
        Either<String, Integer> thirdEither = Either.right(3);
        Either<String, Integer> fourthEither = Either.right(4);
        Iterable<Either<String, Integer>> eithers = iterableWith(firstEither, secondEither, thirdEither, fourthEither);

        // When
        Iterable<Integer> rights = Eithers.rights(eithers);

        // Then
        assertThat(materialize(rights), hasOnlyItemsInAnyOrder(3, 4));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfIterableSuppliedToRightsIsNull() throws Exception {
        // Given
        Iterable<Either<String, Integer>> input = null;

        // When
        Eithers.rights(input);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldCollapseIterableOfEithersOntoTheirLefts() throws Exception {
        // Given
        Either<String, Integer> firstEither = Either.left("first");
        Either<String, Integer> secondEither = Either.left("second");
        Either<String, Integer> thirdEither = Either.right(3);
        Either<String, Integer> fourthEither = Either.right(4);
        Iterable<Either<String, Integer>> eithers = iterableWith(firstEither, secondEither, thirdEither, fourthEither);

        // When
        Iterable<String> lefts = Eithers.lefts(eithers);

        // Then
        assertThat(materialize(lefts), hasOnlyItemsInAnyOrder("first", "second"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfIterableSuppliedToLeftsIsNull() throws Exception {
        // Given
        Iterable<Either<String, Integer>> input = null;

        // When
        Eithers.lefts(input);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnAPredicateThatReturnsTrueIfEitherIsLeft() throws Exception {
        // Given
        Either<String, Integer> either = Either.left("left");
        Predicate<? super Either<String, Integer>> predicate = Eithers.isLeft();

        // When
        Boolean isLeft = predicate.evaluate(either);

        // Then
        assertThat(isLeft, is(true));
    }

    @Test
    public void shouldReturnAPredicateThatReturnsFalseIfEitherIsRight() throws Exception {
        // Given
        Either<String, Integer> either = Either.right(100);
        Predicate<? super Either<String, Integer>> predicate = Eithers.isLeft();

        // When
        Boolean isLeft = predicate.evaluate(either);

        // Then
        assertThat(isLeft, is(false));
    }

    @Test
    public void shouldReturnAPredicateThatReturnsTrueIfEitherIsRight() throws Exception {
        // Given
        Either<String, Integer> either = Either.right(100);
        Predicate<? super Either<String, Integer>> predicate = Eithers.isRight();

        // When
        Boolean isLeft = predicate.evaluate(either);

        // Then
        assertThat(isLeft, is(true));
    }

    @Test
    public void shouldReturnAPredicateThatReturnsFalseIfEitherIsLeft() throws Exception {
        // Given
        Either<String, Integer> either = Either.left("left");
        Predicate<? super Either<String, Integer>> predicate = Eithers.isRight();

        // When
        Boolean isLeft = predicate.evaluate(either);

        // Then
        assertThat(isLeft, is(false));
    }

    @Test
    public void shouldReturnAMapperThatMapsAnEitherToItsLeft() throws Exception {
        // Given
        String expected = "left";
        Either<String, Integer> either = Either.left("left");
        Mapper<? super Either<String, Integer>, String> mapper = Eithers.toLeft();

        // When
        String actual = mapper.map(either);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAMapperThatMapsAnEitherToItsRight() throws Exception {
        // Given
        Integer expected = 100;
        Either<String, Integer> either = Either.right(100);
        Mapper<? super Either<String, Integer>, Integer> mapper = Eithers.toRight();

        // When
        Integer actual = mapper.map(either);

        // Then
        assertThat(actual, is(expected));
    }
}
