package org.javafunk.funk;

import org.javafunk.funk.functors.Generatable;
import org.javafunk.funk.functors.Generator;
import org.javafunk.funk.generators.ConstantGenerator;

import java.util.Iterator;

public class Generators {
    public static <T> Generator<T> constantGenerator(T value) {
        return new ConstantGenerator<T>(value);
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
