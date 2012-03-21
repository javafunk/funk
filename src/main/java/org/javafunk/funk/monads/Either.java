package org.javafunk.funk.monads;

import java.util.NoSuchElementException;

public abstract class Either<L, R>{
    public abstract boolean isLeft();
    public abstract boolean isRight();

    public static <L, R> Either<L, R> left(L left){
        return new Left<L, R>(left);
    }

    public static <L, R> Either<L, R> right(R right) {
        return new Right<L, R>(right);
    }

    public R getRight(){
        throw new NoSuchElementException();
    }

    public L getLeft(){
        throw new NoSuchElementException();
    }

    private static class Left<L, R> extends Either<L, R> {
        private L value;

        public Left(L value){
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

    private static class Right<L, R> extends Either<L, R> {
        private R value;

        public Right(R value){
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
}
