package org.javafunk.funk.behaviours.ordinals.mappables;

import org.javafunk.funk.functors.functions.UnaryFunction;

public interface MappableNinth<I, O extends MappableNinth<?, O>> {
    <A> O mapNinth(UnaryFunction<I, A> function);
}
