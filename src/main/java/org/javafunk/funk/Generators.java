package org.javafunk.funk;

import org.javafunk.funk.behaviours.Generatable;
import org.javafunk.funk.behaviours.Generator;
import org.javafunk.funk.generators.ConstantGenerator;
import org.javafunk.funk.generators.FiniteGenerator;

import java.util.Iterator;

public class Generators {
    public static <T> Generator<T> constantGenerator(T value) {
        return new ConstantGenerator<T>(value);
    }

    public static <T> Generator<T> finiteGenerator(Iterable<T> iterable) {
        return new FiniteGenerator<T>(iterable);
    }

    // TODO: Toby (2012-03-25) This needs to tee rather than using the
    // same instance in the same way that toIterable would
    public static <T> Generatable<T> toGeneratable(final Generator<T> generator) {
        return new Generatable<T>() {
            @Override public Iterator<T> iterator() {
                return generator;
            }
        };
    }
}
