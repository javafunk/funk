package org.javafunk.funk.behaviours.ordinals.mappables;

import org.javafunk.funk.functors.functions.UnaryFunction;

public interface MappableSeventh<I, O extends MappableSeventh<?, O>> {
    <A> O mapSeventh(UnaryFunction<I, A> function);
}
