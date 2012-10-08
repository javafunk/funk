/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.*;
import org.javafunk.funk.functors.Action;
import org.javafunk.funk.functors.Equivalence;
import org.javafunk.funk.functors.Indexer;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.functors.predicates.BinaryPredicate;
import org.javafunk.funk.functors.predicates.UnaryPredicate;
import org.javafunk.funk.functors.procedures.UnaryProcedure;
import org.javafunk.funk.iterators.*;
import org.javafunk.funk.predicates.NotPredicate;

import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.javafunk.funk.Eagerly.first;
import static org.javafunk.funk.Iterables.concat;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.Mappers.toIterators;
import static org.javafunk.funk.Sequences.increasing;
import static org.javafunk.funk.Sequences.integers;
import static org.javafunk.funk.functors.adapters.ActionUnaryProcedureAdapter.actionUnaryProcedure;
import static org.javafunk.funk.functors.adapters.EquivalenceBinaryPredicateAdapter.equivalenceBinaryPredicate;
import static org.javafunk.funk.functors.adapters.IndexerUnaryFunctionAdapter.indexerUnaryFunction;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

public class Lazily {
    private Lazily() {}

    public static <T> Iterable<Iterable<T>> batch(final Iterable<T> iterable, final int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("Batch size must be greater than zero.");
        }
        return new Iterable<Iterable<T>>() {
            public Iterator<Iterable<T>> iterator() {
                return new BatchedIterator<T>(iterable.iterator(), batchSize);
            }
        };
    }

    public static <T> Iterable<T> cycle(final Iterable<T> iterable) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new CyclicIterator<T>(iterable.iterator());
            }
        };
    }

    public static <T> Iterable<T> repeat(final Iterable<T> iterable, final int numberOfTimesToRepeat) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new CyclicIterator<T>(iterable.iterator(), numberOfTimesToRepeat);
            }
        };
    }

    public static <T> Iterable<T> drop(final Iterable<T> iterable, final int numberToTake) {
        if (numberToTake < 0) {
            throw new IllegalArgumentException("Cannot drop a negative number of elements.");
        }
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new SubSequenceIterator<T>(iterable.iterator(), numberToTake, null);
            }
        };
    }

    public static <T> Iterable<T> dropUntil(final Iterable<T> iterable, final UnaryPredicate<? super T> predicate) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                Iterator<? extends T> iterator = iterable.iterator();
                T next = null;
                while (iterator.hasNext()) {
                    next = iterator.next();
                    if (predicate.evaluate(next)) {
                        break;
                    }
                }
                return new ChainedIterator<T>(iterableWith(next).iterator(), iterator);
            }
        };
    }

    public static <T> Iterable<T> dropWhile(final Iterable<T> iterable, final UnaryPredicate<? super T> predicate) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                Iterator<T> iterator = iterable.iterator();
                T next = null;
                while (iterator.hasNext()) {
                    next = iterator.next();
                    if (!predicate.evaluate(next)) {
                        break;
                    }
                }
                return new ChainedIterator<T>(iterableWith(next).iterator(), iterator);
            }
        };
    }

    public static <T> Iterable<T> each(final Iterable<T> iterable, final UnaryProcedure<? super T> procedure) {
        checkNotNull(procedure);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new EachIterator<T>(iterable.iterator(), procedure);
            }
        };
    }

    public static <T> Iterable<T> each(final Iterable<T> iterable, final Action<? super T> action) {
        return each(iterable, actionUnaryProcedure(checkNotNull(action)));
    }

    public static <T> Iterable<Pair<Integer, T>> enumerate(final Iterable<T> iterable) {
        return zip(integers(increasing()), iterable);
    }

    public static <S, T> Iterable<Pair<T, S>> index(Iterable<S> iterable, final UnaryFunction<? super S, T> function) {
        checkNotNull(function);
        return zip(map(iterable, new Mapper<S, T>() {
            public T map(S input) {
                return function.call(input);
            }
        }), iterable);
    }

    public static <S, T> Iterable<Pair<T, S>> index(Iterable<S> iterable, final Indexer<? super S, T> indexer) {
        checkNotNull(indexer);
        return index(iterable, indexerUnaryFunction(indexer));
    }

    public static <S, T> Iterable<T> map(final Iterable<S> iterable, final UnaryFunction<? super S, T> mapper) {
        checkNotNull(mapper);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new MappedIterator<S, T>(iterable.iterator(), mapper);
            }
        };
    }

    public static <S, T> Iterable<T> map(final Iterable<S> iterable, final Mapper<? super S, T> mapper) {
        checkNotNull(mapper);
        return map(iterable, mapperUnaryFunction(mapper));
    }

    public static <T> Iterable<Boolean> equate(Iterable<T> first, Iterable<T> second, final BinaryPredicate<? super T, ? super T> predicate) {
        checkNotNull(predicate);
        return map(zip(first, second), new Mapper<Pair<T, T>, Boolean>() {
            public Boolean map(Pair<T, T> input) {
                return predicate.evaluate(input.getFirst(), input.getSecond());
            }
        });
    }

    public static <T> Iterable<Boolean> equate(Iterable<T> first, Iterable<T> second, final Equivalence<? super T> equivalence) {
        return equate(first, second, equivalenceBinaryPredicate(checkNotNull(equivalence)));
    }

    public static <T> Iterable<T> filter(final Iterable<T> iterable, final UnaryPredicate<? super T> predicate) {
        checkNotNull(predicate);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new FilteredIterator<T>(iterable.iterator(), predicate);
            }
        };
    }

    public static <T> Iterable<T> reject(final Iterable<T> iterable, final UnaryPredicate<? super T> predicate) {
        checkNotNull(predicate);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new FilteredIterator<T>(iterable.iterator(), new NotPredicate<T>(predicate));
            }
        };
    }

    public static <T> Pair<Iterable<T>, Iterable<T>> partition(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        checkNotNull(predicate);
        return tuple(filter(iterable, predicate), reject(iterable, predicate));
    }

    public static <T> Iterable<T> rest(final Iterable<T> iterable) {
        return slice(iterable, 1, null, 1);
    }

    public static <T> Iterable<T> slice(final Iterable<T> iterable, final Integer start, final Integer stop, final Integer step) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new SubSequenceIterator<T>(iterable.iterator(), start, stop, step);
            }
        };
    }

    public static <T> Iterable<T> take(final Iterable<T> iterable, final int numberToTake) {
        if (numberToTake < 0) {
            throw new IllegalArgumentException("Cannot take a negative number of elements.");
        }
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new SubSequenceIterator<T>(iterable.iterator(), null, numberToTake);
            }
        };
    }

    public static <T> Iterable<T> takeUntil(final Iterable<T> iterable, final UnaryPredicate<? super T> predicate) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new PredicatedIterator<T>(iterable.iterator(), new NotPredicate<T>(predicate));
            }
        };
    }

    public static <T> Iterable<T> takeWhile(final Iterable<T> iterable, final UnaryPredicate<? super T> predicate) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new PredicatedIterator<T>(iterable.iterator(), predicate);
            }
        };
    }

    public static <R, S> Iterable<Pair<R, S>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second) {
        return map(
                cartesianProduct(iterableWith(first, second)),
                Mappers.<R, S>toPair());
    }

    public static <R, S, T> Iterable<Triple<R, S, T>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third) {
        return map(
                cartesianProduct(iterableWith(first, second, third)),
                Mappers.<R, S, T>toTriple());
    }

    public static <R, S, T, U> Iterable<Quadruple<R, S, T, U>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth) {
        return map(
                cartesianProduct(iterableWith(first, second, third, fourth)),
                Mappers.<R, S, T, U>toQuadruple());
    }

    public static <R, S, T, U, V> Iterable<Quintuple<R, S, T, U, V>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth) {
        return map(
                cartesianProduct(iterableWith(first, second, third, fourth, fifth)),
                Mappers.<R, S, T, U, V>toQuintuple());
    }

    public static <R, S, T, U, V, W> Iterable<Sextuple<R, S, T, U, V, W>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth) {
        return map(
                cartesianProduct(iterableWith(first, second, third, fourth, fifth, sixth)),
                Mappers.<R, S, T, U, V, W>toSextuple());
    }

    public static <R, S, T, U, V, W, X> Iterable<Septuple<R, S, T, U, V, W, X>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth,
            Iterable<X> seventh) {
        return map(
                cartesianProduct(iterableWith(first, second, third, fourth, fifth, sixth, seventh)),
                Mappers.<R, S, T, U, V, W, X>toSeptuple());
    }

    public static <R, S, T, U, V, W, X, Y> Iterable<Octuple<R, S, T, U, V, W, X, Y>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth,
            Iterable<X> seventh,
            Iterable<Y> eighth) {
        return map(
                cartesianProduct(iterableWith(first, second, third, fourth, fifth, sixth, seventh, eighth)),
                Mappers.<R, S, T, U, V, W, X, Y>toOctuple());
    }

    public static <R, S, T, U, V, W, X, Y, Z> Iterable<Nonuple<R, S, T, U, V, W, X, Y, Z>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth,
            Iterable<X> seventh,
            Iterable<Y> eighth,
            Iterable<Z> ninth) {
        return map(
                cartesianProduct(iterableWith(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)),
                Mappers.<R, S, T, U, V, W, X, Y, Z>toNonuple());
    }

    public static Iterable<? extends Iterable<?>> cartesianProduct(final Iterable<? extends Iterable<?>> iterables) {
        return cartesianProduct(listFrom(iterables));
    }

    private static Iterable<? extends Iterable<?>> cartesianProduct(final List<? extends Iterable<?>> iterables) {
        if (iterables.size() == 2) {
            final Iterable<?> first = first(iterables).get();
            final Iterable<?> second = first(rest(iterables)).get();
            return concat(map(first, new Mapper<Object, Iterable<? extends Iterable<?>>>() {
                public Iterable<? extends Iterable<?>> map(Object input) {
                    return zip(cycle(iterableWith(input)), second);
                }
            }));
        }

        Iterable<? extends Pair<?, ? extends Iterable<?>>> pairs = cartesianProduct(
                first(iterables).get(),
                cartesianProduct(rest(iterables)));

        return map(pairs, new Mapper<Pair<?, ? extends Iterable<?>>, Iterable<?>>() {
            public Iterable<?> map(Pair<?, ? extends Iterable<?>> input) {
                return iterableBuilderWith(input.getFirst()).and(input.getSecond()).build();
            }
        });
    }

    public static <R, S> Iterable<Pair<R, S>> zip(
            Iterable<R> first,
            Iterable<S> second) {
        return map(zip(iterableWith(first, second)), Mappers.<R, S>toPair());
    }

    public static <R, S, T> Iterable<Triple<R, S, T>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third) {
        return map(zip(iterableWith(first, second, third)), Mappers.<R, S, T>toTriple());
    }

    public static <R, S, T, U> Iterable<Quadruple<R, S, T, U>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth) {
        return map(zip(iterableWith(first, second, third, fourth)), Mappers.<R, S, T, U>toQuadruple());
    }

    public static <R, S, T, U, V> Iterable<Quintuple<R, S, T, U, V>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth) {
        return map(zip(iterableWith(first, second, third, fourth, fifth)), Mappers.<R, S, T, U, V>toQuintuple());
    }

    public static <R, S, T, U, V, W> Iterable<Sextuple<R, S, T, U, V, W>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth) {
        return map(zip(iterableWith(first, second, third, fourth, fifth, sixth)), Mappers.<R, S, T, U, V, W>toSextuple());
    }

    public static <R, S, T, U, V, W, X> Iterable<Septuple<R, S, T, U, V, W, X>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth,
            Iterable<X> seventh) {
        return map(zip(iterableWith(first, second, third, fourth, fifth, sixth, seventh)), Mappers.<R, S, T, U, V, W, X>toSeptuple());
    }

    public static <R, S, T, U, V, W, X, Y> Iterable<Octuple<R, S, T, U, V, W, X, Y>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth,
            Iterable<X> seventh,
            Iterable<Y> eighth) {
        return map(zip(iterableWith(first, second, third, fourth, fifth, sixth, seventh, eighth)), Mappers.<R, S, T, U, V, W, X, Y>toOctuple());
    }

    public static <R, S, T, U, V, W, X, Y, Z> Iterable<Nonuple<R, S, T, U, V, W, X, Y, Z>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth,
            Iterable<X> seventh,
            Iterable<Y> eighth,
            Iterable<Z> ninth) {
        return map(zip(iterableWith(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)), Mappers.<R, S, T, U, V, W, X, Y, Z>toNonuple());
    }

    public static Iterable<? extends Iterable<?>> zip(final Iterable<? extends Iterable<?>> iterables) {
        return new Iterable<Iterable<?>>() {
            public Iterator<Iterable<?>> iterator() {
                final Iterable<? extends Iterator<?>> iterators = Eagerly.map(iterables, toIterators());
                return new ZippedIterator(iterators);
            }
        };
    }
}
