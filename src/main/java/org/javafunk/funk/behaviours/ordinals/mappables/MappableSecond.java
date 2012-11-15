package org.javafunk.funk.behaviours.ordinals.mappables;

import org.javafunk.funk.functors.functions.UnaryFunction;

public interface MappableSecond<I, O extends MappableSecond<?, O>> {
    <A> O mapSecond(UnaryFunction<I, A> function);
}
