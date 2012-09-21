package org.javafunk.funk;

import org.javafunk.funk.functors.predicates.UnaryPredicate;
import org.javafunk.funk.monads.Option;

public class Options {
    public static <T> Iterable<Option<T>> somes(Iterable<Option<T>> options) {
        return Lazily.filter(options, Options.<T>isSome());
    }

    public static <T> Iterable<Option<T>> nones(Iterable<Option<T>> options) {
        return Lazily.filter(options, Options.<T>isNone());
    }

    public static <T> UnaryPredicate<Option<T>> isSome() {
        return new UnaryPredicate<Option<T>>() {
            @Override public boolean evaluate(Option<T> option) {
                return option.hasValue();
            }
        };
    }

    public static <T> UnaryPredicate<Option<T>> isNone() {
        return new UnaryPredicate<Option<T>>() {
            @Override public boolean evaluate(Option<T> option) {
                return option.hasNoValue();
            }
        };
    }
}
