package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.monads.Either;

import static org.javafunk.funk.Lazily.filter;
import static org.javafunk.funk.Lazily.map;

public class Eithers {
    public static <S, T> Iterable<T> rights(Iterable<Either<S, T>> eithers) {
        return map(filter(eithers, Eithers.<S, T>isRight()), Eithers.<S, T>toRight());
    }

    public static <S, T> Iterable<S> lefts(Iterable<Either<S, T>> eithers) {
        return map(filter(eithers, Eithers.<S, T>isLeft()), Eithers.<S, T>toLeft());
    }

    public static <S, T> Mapper<? super Either<S, T>, T> toRight() {
        return new Mapper<Either<S, T>, T>(){
            @Override public T map(Either<S, T> either) {
                return either.getRight();
            }
        };
    }

    public static <S, T> Mapper<? super Either<S, T>, S> toLeft() {
        return new Mapper<Either<S, T>, S>() {
            @Override public S map(Either<S, T> either) {
                return either.getLeft();
            }
        };
    }

    public static <S, T> Predicate<? super Either<S, T>> isRight() {
        return new Predicate<Either<S, T>>() {
            @Override public boolean evaluate(Either<S, T> either) {
                return either.isRight();
            }
        };
    }

    public static <S, T> Predicate<? super Either<S, T>> isLeft() {
        return new Predicate<Either<S, T>>() {
            @Override public boolean evaluate(Either<S, T> either) {
                return either.isLeft();
            }
        };
    }
}
