package org.javafunk.funk.behaviours.ordinals.mappables;

import org.javafunk.funk.functors.functions.UnaryFunction;

public interface MappableFourth<I, O extends MappableFourth<?, O>> {
    <A> O mapFourth(UnaryFunction<I, A> function);
}
