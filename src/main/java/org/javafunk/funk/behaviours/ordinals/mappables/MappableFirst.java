package org.javafunk.funk.behaviours.ordinals.mappables;

import org.javafunk.funk.functors.functions.UnaryFunction;

public interface MappableFirst<I, O extends MappableFirst<?, O>> {
    <A> O mapFirst(UnaryFunction<I, A> function);
}
