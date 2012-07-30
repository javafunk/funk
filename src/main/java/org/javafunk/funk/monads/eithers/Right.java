package org.javafunk.funk.monads.eithers;

import org.javafunk.funk.monads.Either;

public class Right<L, R> extends Either<L, R> {
    private R value;

    public static <L, R> Right<L, R> right(R value) {
        return new Right<L, R>(value);
    }

    private Right() {}

    private Right(R value){
        this.value = value;
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public R getRight(){
        return value;
    }
}
