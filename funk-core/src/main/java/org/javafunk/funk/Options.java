package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.functors.predicates.UnaryPredicate;
import org.javafunk.funk.monads.Option;

public class Options {
    public static <T> Iterable<Option<T>> somes(Iterable<Option<T>> options) {
        return Lazily.filter(options, Options.<T>isSome());
    }

    public static <T> Iterable<Option<T>> nones(Iterable<Option<T>> options) {
        return Lazily.filter(options, Options.<T>isNone());
    }

    public static <T> Predicate<Option<T>> isSome() {
        return new Predicate<Option<T>>() {
            @Override public boolean evaluate(Option<T> option) {
                return option.hasValue();
            }
        };
    }

    public static <T> Predicate<Option<T>> isNone() {
        return new Predicate<Option<T>>() {
            @Override public boolean evaluate(Option<T> option) {
                return option.hasNoValue();
            }
        };
    }

    public static <T> Predicate<Option<T>> hasValue() {
        return isSome();
    }

    public static <T> Predicate<Option<T>> hasNoValue() {
        return isNone();
    }

    public static <T> Mapper<Option<T>, T> toValue() {
        return new Mapper<Option<T>, T>() {
            @Override public T map(Option<T> input) {
                return input.getValue();
            }
        };
    }

    public static <T> Mapper<Option<T>, T> toValue(
            @SuppressWarnings("unused") Class<T> valueClass) {
        return toValue();
    }

    public static <T> Iterable<T> flatten(Iterable<Option<T>> options) {
        return Lazily.map(somes(options), Options.<T>toValue());
    }
}
