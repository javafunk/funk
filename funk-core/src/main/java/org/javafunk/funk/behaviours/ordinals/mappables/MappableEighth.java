package org.javafunk.funk.behaviours.ordinals.mappables;

import org.javafunk.funk.functors.functions.UnaryFunction;

public interface MappableEighth<I, O extends MappableEighth<?, O>> {
    <A> O mapEighth(UnaryFunction<I, A> function);
}
