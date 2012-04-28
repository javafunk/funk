package org.javafunk.funk.behaviours;

import org.javafunk.funk.functors.Mapper;

public interface Mappable<I, O extends Mappable<?, O>> {
    <T> O map(Mapper<? super I, ? extends T> mapper);
}
