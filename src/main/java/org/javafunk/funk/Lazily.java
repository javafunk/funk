/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Nonuple;
import org.javafunk.funk.datastructures.tuples.Octuple;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.datastructures.tuples.Quadruple;
import org.javafunk.funk.datastructures.tuples.Quintuple;
import org.javafunk.funk.datastructures.tuples.Septuple;
import org.javafunk.funk.datastructures.tuples.Sextuple;
import org.javafunk.funk.datastructures.tuples.Triple;
import org.javafunk.funk.functors.Action;
import org.javafunk.funk.functors.Equivalence;
import org.javafunk.funk.functors.Indexer;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.functors.predicates.BinaryPredicate;
import org.javafunk.funk.functors.predicates.UnaryPredicate;
import org.javafunk.funk.functors.procedures.UnaryProcedure;
import org.javafunk.funk.iterators.BatchedIterator;
import org.javafunk.funk.iterators.ChainedIterator;
import org.javafunk.funk.iterators.CyclicIterator;
import org.javafunk.funk.iterators.EachIterator;
import org.javafunk.funk.iterators.FilteredIterator;
import org.javafunk.funk.iterators.MappedIterator;
import org.javafunk.funk.iterators.PredicatedIterator;
import org.javafunk.funk.iterators.SubSequenceIterator;
import org.javafunk.funk.iterators.ZippedIterator;
import org.javafunk.funk.predicates.NotPredicate;

import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.javafunk.funk.Eagerly.first;
import static org.javafunk.funk.Iterables.concat;
import static org.javafunk.funk.Literals.iterableBuilderWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.listFrom;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.Mappers.toIterators;
import static org.javafunk.funk.Sequences.increasing;
import static org.javafunk.funk.Sequences.integers;
import static org.javafunk.funk.functors.adapters.ActionUnaryProcedureAdapter.actionUnaryProcedure;
import static org.javafunk.funk.functors.adapters.EquivalenceBinaryPredicateAdapter.equivalenceBinaryPredicate;
import static org.javafunk.funk.functors.adapters.IndexerUnaryFunctionAdapter.indexerUnaryFunction;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

/**
 * A suite of lazy functions, often higher order, across {@code Iterable} instances.
 *
 * <p>Each function defined in this class is lazy, i.e., it does not iterate over the
 * supplied {@code Iterable} instance(s) itself but instead prepares a return value
 * that will yield semantically correct results with respect to the function's definition
 * when the return value or one of its components is iterated. This has a number of benefits:
 * <ul>
 * <li>Iteration/realisation of the underlying {@code Iterable} instance(s) are only
 * performed when required and only as far through the {@code Iterable} as required.</li>
 * <li>Infinite {@code Iterable} instances can be manipulated while consuming a
 * finite amount of memory.</li>
 * <li>Lazy function evaluations can easily be composed into complex calculations
 * whilst keeping the number of required iterations of the underlying {@code Iterable}
 * instance(s) to a minimum.</li>
 * </ul>
 * Because of this it is generally recommended to use functions from {@code Lazily} over
 * those in {@code Eagerly} and then to materialize the resulting {@code Iterable}
 * instance(s) into whatever concrete {@code Iterable} type is required as the last step
 * in evaluation.
 * </p>
 *
 * <p>As an example consider the following:
 * <pre>
 *  <code>
 *      Iterable&lt;BigInteger&gt; naturalNumbers = naturalNumbers.getAll();
 *      Iterable&lt;BigDecimal&gt; doubledNaturals = Lazily.map(naturalNumbers, new Mapper&lt;BigInteger, BigDecimal&gt;() {
 *        &#64;Override public BigDecimal map(BigInteger number) {
 *          return new BigDecimal(number.multiply(new BigInteger("2")));
 *        }
 *      });
 *      Iterable&lt;Pair&lt;Integer, BigDecimal&gt;&gt; enumeratedDoubledNaturals = Lazily.enumerate(doubledNaturals);
 *      Iterable&lt;Pair&lt;Integer, BigDecimal&gt;&gt; firstHundredEnumeratedDoubledNaturals = Lazily.take(enumeratedDoubledNaturals, 100);
 *      Map&lt;Integer, BigDecimal&gt; firstHundredDoubledNaturals = Literals.mapFromPairs(firstHundredEnumeratedDoubledNaturals);
 *  </code>
 * </pre>
 * Here a number of lazy operations are performed and then the final {@code Iterable} is
 * materialised into a {@code Map} instance. The original {@code Iterable} is an
 * infinite sequence of elements however this series of operations will only iterate and
 * map over the first hundred elements.
 * </p>
 *
 * <p>This example has been written in full but of course, via static imports and method extraction, it can
 * be made more concise as shown in the following:
 * <pre>
 *  <code>
 *      Map&lt;Integer, BigDecimal&gt; firstHundredDoubledNaturals = mapFromPairs(take(enumerate(map(naturalNumbers.getAll(), toDoubledBigDecimals())), 100);
 *  </code>
 * </pre>
 *  </p>
 *
 *  <p>Note that none of the values returned by these functions memoise their contents
 *  upon iteration. Thus, if the input {@code Iterable} instance(s) have expensive side
 *  effects on iteration such as loading from the file system or a database or performing
 *  expensive computations, the laziness of these functions will mean this cost will
 *  be incurred on every iteration. Instead consider materialising either the input
 *  or output values prior to repeated iteration.</p>
 *
 * @see Eagerly
 * @since 1.0
 */
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
