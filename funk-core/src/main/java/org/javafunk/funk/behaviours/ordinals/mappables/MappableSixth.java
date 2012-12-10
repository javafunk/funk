package org.javafunk.funk.behaviours.ordinals.mappables;

import org.javafunk.funk.functors.functions.UnaryFunction;

public interface MappableSixth<I, O extends MappableSixth<?, O>> {
    <A> O mapSixth(UnaryFunction<I, A> function);
}
