package org.javafunk.funk.monads.eithers;

import org.javafunk.funk.monads.Either;

public class Left<L, R> extends Either<L, R> {
    private L value;

    public static <L, R> Left<L, R> left(L value) {
        return new Left<L, R>(value);
    }

    private Left() {}

    private Left(L value){
        this.value = value;
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public L getLeft(){
        return value;
    }
}
