package org.javafunk.funk.matchers;

import org.hamcrest.Matcher;
import org.javafunk.funk.matchers.implementations.EitherAnyLeftMatcher;
import org.javafunk.funk.matchers.implementations.EitherAnyRightMatcher;
import org.javafunk.funk.matchers.implementations.EitherSpecificLeftMatcher;
import org.javafunk.funk.matchers.implementations.EitherSpecificRightMatcher;
import org.javafunk.funk.monads.Either;

public class EitherMatchers {
    public static <S, T> Matcher<? super Either<S, T>> left() {
        return new EitherAnyLeftMatcher<S, T>();
    }

    public static <S, T> Matcher<? super Either<S, T>> left(S leftValue) {
        return new EitherSpecificLeftMatcher<S, T>(leftValue);
    }

    public static <S, T> Matcher<? super Either<S, T>> leftOver(Class<S> leftClass, Class<T> rightClass) {
        return left();
    }

    public static <S, T> Matcher<? super Either<S, T>> leftOverValueAndType(S leftValue, Class<T> rightClass) {
        return left(leftValue);
    }

    public static <S, T> Matcher<? super Either<S, T>> right() {
        return new EitherAnyRightMatcher<S, T>();
    }

    public static <S, T> Matcher<? super Either<S, T>> right(T rightValue) {
        return new EitherSpecificRightMatcher<S, T>(rightValue);
    }

    public static <S, T> Matcher<? super Either<S, T>> rightOver(Class<S> leftClass, Class<T> rightClass) {
        return right();
    }

    public static <S, T> Matcher<? super Either<S, T>> rightOverTypeAndValue(Class<S> leftClass, T rightValue) {
        return right(rightValue);
    }

}
