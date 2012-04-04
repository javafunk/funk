package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Quadruple;
import org.javafunk.funk.datastructures.tuples.Triple;
import org.javafunk.funk.functors.Mapper;

import static org.javafunk.funk.Eager.first;
import static org.javafunk.funk.Literals.tuple;

public class Mappers {
    @SuppressWarnings("unchecked")
    static <S, T, V> Mapper<? super Iterable<?>, ? extends Triple<S, T, V>> toTriple() {
        return new Mapper<Iterable<?>, Triple<S, T, V>>() {
            public Triple<S, T, V> map(Iterable<?> input) {
                return tuple((S)first(input), (T)first(Lazy.rest(input)), (V)first(Lazy.rest(Lazy.rest(input))));
            }
        };
    }

    @SuppressWarnings("unchecked")
    static <S, T, U, V> Mapper<? super Iterable<?>, ? extends Quadruple<S, T, U, V>> toQuadruple() {
        return new Mapper<Iterable<?>, Quadruple<S, T, U, V>>() {
            public Quadruple<S, T, U, V> map(Iterable<?> iterable) {
                return tuple((S)first(iterable), (T)first(Lazy.rest(iterable)), (U)first(Lazy.rest(Lazy.rest(iterable))), (V)first(Lazy.rest(Lazy.rest(Lazy.rest(iterable)))));
            }
        };
    }
}
