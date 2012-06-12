/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.IntegerRange;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.*;
import org.javafunk.funk.functors.functions.BinaryFunction;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.functors.predicates.BinaryPredicate;
import org.javafunk.funk.functors.predicates.UnaryPredicate;
import org.javafunk.funk.functors.procedures.UnaryProcedure;
import org.javafunk.funk.monads.Option;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.util.Collections.emptyList;
import static org.javafunk.funk.Accumulators.*;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Iterators.asIterable;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.functors.adapters.ActionUnaryProcedureAdapter.actionUnaryProcedure;
import static org.javafunk.funk.functors.adapters.EquivalenceBinaryPredicateAdapter.equivalenceBinaryPredicate;
import static org.javafunk.funk.functors.adapters.IndexerUnaryFunctionAdapter.indexerUnaryFunction;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;
import static org.javafunk.funk.functors.adapters.ReducerBinaryFunctionAdapter.reducerBinaryFunction;

public class Eagerly {
    private Eagerly() {}

    public static <S, T> T reduce(Iterable<? extends S> iterable, T initialValue, BinaryFunction<T, ? super S, T> function) {
        T accumulator = initialValue;
        for (S element : iterable) {
            accumulator = function.call(accumulator, element);
        }
        return accumulator;
    }

    public static <S, T> T reduce(Iterable<? extends S> iterable, T initialValue, Reducer<? super S, T> reducer) {
        return reduce(iterable, initialValue, reducerBinaryFunction(reducer));
    }

    public static <T> T reduce(Iterable<T> iterable, BinaryFunction<T, ? super T, T> function) {
        final Iterator<T> iterator = iterable.iterator();
        final T firstElement = iterator.next();
        final Iterable<T> restOfElements = asIterable(iterator);
        return reduce(restOfElements, firstElement, function);
    }

    public static <T> T reduce(Iterable<T> iterable, Reducer<T, T> reducer) {
        return reduce(iterable, reducerBinaryFunction(reducer));
    }

    public static Integer sum(Iterable<Integer> iterable) {
        return reduce(iterable, integerAdditionAccumulator());
    }

    public static Long sum(Iterable<Long> iterable) {
        return reduce(iterable, longAdditionAccumulator());
    }

    public static BigInteger sum(Iterable<BigInteger> iterable) {
        return reduce(iterable, bigIntegerAdditionAccumulator());
    }

    public static Double sum(Iterable<Double> iterable) {
        return reduce(iterable, doubleAdditionAccumulator());
    }

    public static Float sum(Iterable<Float> iterable) {
        return reduce(iterable, floatAdditionAccumulator());
    }

    public static BigDecimal sum(Iterable<BigDecimal> iterable) {
        return reduce(iterable, bigDecimalAdditionAccumulator());
    }

    public static Integer product(Iterable<Integer> iterable) {
        return reduce(iterable, integerMultiplicationAccumulator());
    }

    public static Long product(Iterable<Long> iterable) {
        return reduce(iterable, longMultiplicationAccumulator());
    }

    public static BigInteger product(Iterable<BigInteger> iterable) {
        return reduce(iterable, bigIntegerMultiplicationAccumulator());
    }

    public static Float product(Iterable<Float> iterable) {
        return reduce(iterable, floatMultiplicationAccumulator());
    }

    public static Double product(Iterable<Double> iterable) {
        return reduce(iterable, doubleMultiplicationAccumulator());
    }

    public static BigDecimal product(Iterable<BigDecimal> iterable) {
        return reduce(iterable, bigDecimalMultiplicationAccumulator());
    }

    public static <T> Boolean any(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        for (T item : iterable) {
            if (predicate.evaluate(item)) {
                return true;
            }
        }
        return false;
    }

    public static <T> Boolean all(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        for (T item : iterable) {
            if (!predicate.evaluate(item)) {
                return false;
            }
        }
        return true;
    }

    public static <T> Boolean none(Iterable<T> items, UnaryPredicate<? super T> predicate) {
        return !any(items, predicate);
    }

    public static <T> T max(Iterable<T> iterable, final Comparator<? super T> comparator) {
        return reduce(iterable, new Reducer<T, T>() {
            public T accumulate(T currentMax, T element) {
                return (element != null && comparator.compare(element, currentMax) > 0) ? element : currentMax;
            }
        });
    }

    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        return reduce(iterable, new Reducer<T, T>() {
            public T accumulate(T currentMax, T element) {
                return (element != null && element.compareTo(currentMax) > 0) ? element : currentMax;
            }
        });
    }

    public static <T> T min(Iterable<T> iterable, final Comparator<? super T> comparator) {
        return reduce(iterable, new Reducer<T, T>() {
            public T accumulate(T currentMin, T element) {
                return (element != null && comparator.compare(element, currentMin) < 0) ? element : currentMin;
            }
        });
    }

    public static <T extends Comparable<T>> T min(Iterable<T> iterable) {
        return reduce(iterable, new Reducer<T, T>() {
            public T accumulate(T currentMin, T element) {
                return (element != null && element.compareTo(currentMin) < 0) ? element : currentMin;
            }
        });
    }

    public static <S, T> Collection<T> map(Iterable<S> iterable, UnaryFunction<? super S, T> function) {
        return materialize(Lazily.map(iterable, function));
    }

    public static <S, T> Collection<T> map(Iterable<S> iterable, Mapper<? super S, T> mapper) {
        return map(iterable, mapperUnaryFunction(mapper));
    }

    public static <S, T> Collection<Pair<S, T>> zip(Iterable<S> iterable1, Iterable<T> iterable2) {
        return materialize(Lazily.zip(iterable1, iterable2));
    }

    public static <T> Collection<Pair<Integer, T>> enumerate(Iterable<T> iterable) {
        return materialize(Lazily.enumerate(iterable));
    }

    public static <T> Collection<Boolean> equate(Iterable<T> first, Iterable<T> second, final BinaryPredicate<? super T, ? super T> predicate) {
        return materialize(Lazily.equate(first, second, predicate));
    }

    public static <T> Collection<Boolean> equate(Iterable<T> first, Iterable<T> second, final Equivalence<? super T> equivalence) {
        return equate(first, second, equivalenceBinaryPredicate(equivalence));
    }

    public static <S, T> Collection<Pair<T, S>> index(Iterable<S> iterable, final UnaryFunction<? super S, T> function) {
        return materialize(Lazily.index(iterable, function));
    }

    public static <S, T> Collection<Pair<T, S>> index(Iterable<S> iterable, final Indexer<? super S, T> indexer) {
        return index(iterable, indexerUnaryFunction(indexer));
    }

    public static <S, T> Map<T, Collection<S>> group(Iterable<S> iterable, UnaryFunction<? super S, T> indexer) {
        Map<T, Collection<S>> groupedElements = new HashMap<T, Collection<S>>();
        for (S element : iterable) {
            T index = indexer.call(element);
            if (!groupedElements.containsKey(index)) {
                groupedElements.put(index, new ArrayList<S>());
            }
            groupedElements.get(index).add(element);
        }
        return groupedElements;
    }

    public static <S, T> Map<T, Collection<S>> group(Iterable<S> iterable, Indexer<? super S, T> indexer) {
        return group(iterable, indexerUnaryFunction(indexer));
    }

    public static <T> void each(Iterable<T> targets, UnaryProcedure<? super T> procedure) {
        materialize(Lazily.each(targets, procedure));
    }

    public static <T> void each(Iterable<T> targets, Action<? super T> action) {
        each(targets, actionUnaryProcedure(action));
    }

    public static <T> Collection<T> filter(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.filter(iterable, predicate));
    }

    public static <T> Collection<T> reject(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.reject(iterable, predicate));
    }

    public static <T> Option<T> first(Iterable<? extends T> iterable) {
        Iterator<? extends T> iterator = iterable.iterator();
        if(iterator.hasNext()){
            return Option.some(iterator.next());
        }else{
            return Option.none();
        }
    }

    public static <T> Option<T> first(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        return first(filter(iterable, predicate));
    }

    public static <T> Collection<T> first(Iterable<T> iterable, int numberOfElementsRequired) {
        return take(iterable, numberOfElementsRequired);
    }

    public static <T> Collection<T> first(Iterable<T> iterable, int numberOfElementsRequired, UnaryPredicate<? super T> predicate) {
        return first(filter(iterable, predicate), numberOfElementsRequired);
    }

    public static <T> Option<T> last(Iterable<T> iterable) {
        return first(slice(iterable, -1, null));
    }

    public static <T> T last(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        return last(filter(iterable, predicate)).get();
    }

    public static <T> Collection<T> last(Iterable<T> iterable, int numberOfElementsRequired) {
        if (numberOfElementsRequired < 0) {
            throw new IllegalArgumentException("Number of elements required cannot be negative");
        }
        if (numberOfElementsRequired == 0) {
            return emptyList();
        }
        return slice(iterable, -numberOfElementsRequired, null);
    }

    public static <T> Collection<T> last(Iterable<T> iterable, int numberOfElementsRequired, UnaryPredicate<? super T> predicate) {
        return last(filter(iterable, predicate), numberOfElementsRequired);
    }

    public static <T> Collection<T> rest(final Iterable<T> iterable) {
        return slice(iterable, 1, null);
    }

    public static <T> Collection<T> take(Iterable<T> iterable, int numberToTake) {
        return materialize(Lazily.take(iterable, numberToTake));
    }

    public static <T> Collection<T> drop(Iterable<T> iterable, int numberToDrop) {
        return materialize(Lazily.drop(iterable, numberToDrop));
    }

    public static <T> Pair<Collection<T>, Collection<T>> partition(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        Pair<Iterable<T>, Iterable<T>> partition = Lazily.partition(iterable, predicate);
        return tuple(materialize(partition.first()), materialize(partition.second()));
    }

    public static <T> Collection<Collection<T>> batch(Iterable<T> iterable, int batchSize) {
        Collection<Collection<T>> result = new ArrayList<Collection<T>>();
        Iterable<Iterable<T>> batches = Lazily.batch(iterable, batchSize);
        for (Iterable<T> batch : batches) {
            result.add(materialize(batch));
        }
        return result;
    }

    public static void times(int numberOfTimes, UnaryProcedure<? super Integer> procedure) {
        if (numberOfTimes < 0) {
            throw new IllegalArgumentException("The number of times to execute the function cannot be less than zero.");
        }
        for (int i = 0; i < numberOfTimes; i++) {
            procedure.execute(i);
        }
    }

    public static void times(int numberOfTimes, Action<? super Integer> action) {
        times(numberOfTimes, actionUnaryProcedure(action));
    }

    public static <T> Collection<T> takeWhile(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.takeWhile(iterable, predicate));
    }

    public static <T> Collection<T> takeUntil(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.takeUntil(iterable, predicate));
    }

    public static <T> Collection<T> dropWhile(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.dropWhile(iterable, predicate));
    }

    public static <T> Collection<T> dropUntil(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.dropUntil(iterable, predicate));
    }

    public static <T> Collection<T> slice(Iterable<T> iterable, Integer start, Integer stop, Integer step) {
        List<? extends T> inputCollection = Iterables.asList(iterable);

        if (inputCollection.size() == 0) {
            return Collections.emptyList();
        }

        int startIndex = SliceHelper.resolveStartIndex(start, inputCollection.size());
        int stopIndex = SliceHelper.resolveStopIndex(stop, inputCollection.size());
        int stepSize = SliceHelper.resolveStepSize(step);

        IntegerRange requiredElementIndices = new IntegerRange(startIndex, stopIndex, stepSize);
        List<T> outputCollection = new ArrayList<T>();

        for (Integer elementIndex : requiredElementIndices) {
            outputCollection.add(inputCollection.get(elementIndex));
        }

        return outputCollection;
    }

    public static <T> Collection<T> slice(Iterable<T> iterable, Integer start, Integer stop) {
        return slice(iterable, start, stop, 1);
    }

    public static <T> Collection<T> repeat(Iterable<T> iterable, int numberOfTimesToRepeat) {
        return materialize(Lazily.repeat(iterable, numberOfTimesToRepeat));
    }

    static <T> Option<T> second(Iterable<? extends T> iterable) {
        return first(Lazily.rest(iterable));
    }

    private static class SliceHelper {
        private static int resolveStartIndex(Integer start, Integer numberOfElements) {
            if (start == null || start + numberOfElements < 0) {
                return 0;
            } else if (start < 0) {
                return start + numberOfElements;
            } else if (start > numberOfElements) {
                return numberOfElements - 1;
            } else {
                return start;
            }
        }

        private static int resolveStopIndex(Integer stop, Integer numberOfElements) {
            if (stop == null || stop > numberOfElements) {
                return numberOfElements;
            } else if (stop + numberOfElements < 0) {
                return -1;
            } else if (stop < 0) {
                return stop + numberOfElements;
            } else {
                return stop;
            }
        }

        private static int resolveStepSize(Integer step) {
            if (step == null) {
                return 1;
            } else if (step == 0) {
                throw new IllegalArgumentException("Step size cannot be zero");
            } else {
                return step;
            }
        }
    }
}
