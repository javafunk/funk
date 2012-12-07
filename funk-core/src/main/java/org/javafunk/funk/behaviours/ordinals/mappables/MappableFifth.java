package org.javafunk.funk.behaviours.ordinals.mappables;

import org.javafunk.funk.functors.functions.UnaryFunction;

public interface MappableFifth<I, O extends MappableFifth<?, O>> {
    <A> O mapFifth(UnaryFunction<I, A> function);
}
