package org.javafunk.funk.behaviours.ordinals.mappables;

import org.javafunk.funk.functors.functions.UnaryFunction;

public interface MappableThird<I, O extends MappableThird<?, O>> {
    <A> O mapThird(UnaryFunction<I, A> function);
}
