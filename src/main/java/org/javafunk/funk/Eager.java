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
import org.javafunk.funk.datastructures.TwoTuple;
import org.javafunk.funk.functors.*;

import java.util.*;

import static org.javafunk.funk.Iterators.asIterable;
import static org.javafunk.funk.Literals.tuple;

public class Eager {
    private Eager() {}

    public static <S, T> T reduce(Iterable<? extends S> iterable, T initialValue, org.javafunk.funk.functors.Reducer<? super S, T> function) {
        T accumulator = initialValue;
        for (S element : iterable) {
            accumulator = function.accumulate(accumulator, element);
        }
        return accumulator;
    }

    public static <T> T reduce(Iterable<T> iterable, org.javafunk.funk.functors.Reducer<? super T, T> function) {
        final Iterator<T> iterator = iterable.iterator();
        final T firstElement = iterator.next();
        final Iterable<T> restOfElements = asIterable(iterator);
        return reduce(restOfElements, firstElement, function);
    }

    public static Integer sum(Iterable<Integer> iterable) {
        return reduce(iterable, Accumulators.integerAdditionAccumulator());
    }

    public static Long sum(Iterable<Long> iterable) {
        return reduce(iterable, Accumulators.longAdditionAccumulator());
    }

    public static Double sum(Iterable<Double> iterable) {
        return reduce(iterable, Accumulators.doubleAdditionAccumulator());
    }

    public static Float sum(Iterable<Float> iterable) {
        return reduce(iterable, Accumulators.floatAdditionAccumulator());
    }

    public static Integer product(Iterable<Integer> iterable) {
        return reduce(iterable, Accumulators.integerMultiplicationAccumulator());
    }

    public static Long product(Iterable<Long> iterable) {
        return reduce(iterable, Accumulators.longMultiplicationAccumulator());
    }

    public static Float product(Iterable<Float> iterable) {
        return reduce(iterable, Accumulators.floatMultiplicationAccumulator());
    }

    public static Double product(Iterable<Double> iterable) {
        return reduce(iterable, Accumulators.doubleMultiplicationAccumulator());
    }

    public static <T> Boolean any(Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        for (T item : iterable) {
            if (predicate.evaluate(item)) {
                return true;
            }
        }
        return false;
    }

    public static <T> Boolean all(Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        for (T item : iterable) {
            if (!predicate.evaluate(item)) {
                return false;
            }
        }
        return true;
    }

    public static <T> Boolean none(Iterable<T> items, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return !any(items, predicate);
    }

    public static <T> T max(Iterable<T> iterable, final Comparator<? super T> comparator) {
        return reduce(iterable, new org.javafunk.funk.functors.Reducer<T, T>() {
            public T accumulate(T currentMax, T element) {
                return (element != null && comparator.compare(element, currentMax) > 0) ? element : currentMax;
            }
        });
    }

    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        return reduce(iterable, new org.javafunk.funk.functors.Reducer<T, T>() {
            public T accumulate(T currentMax, T element) {
                return (element != null && element.compareTo(currentMax) > 0) ? element : currentMax;
            }
        });
    }

    public static <T> T min(Iterable<T> iterable, final Comparator<? super T> comparator) {
        return reduce(iterable, new org.javafunk.funk.functors.Reducer<T, T>() {
            public T accumulate(T currentMin, T element) {
                return (element != null && comparator.compare(element, currentMin) < 0) ? element : currentMin;
            }
        });
    }

    public static <T extends Comparable<T>> T min(Iterable<T> iterable) {
        return reduce(iterable, new org.javafunk.funk.functors.Reducer<T, T>() {
            public T accumulate(T currentMin, T element) {
                return (element != null && element.compareTo(currentMin) < 0) ? element : currentMin;
            }
        });
    }

    public static <S, T> Collection<T> map(Iterable<S> iterable, org.javafunk.funk.functors.Mapper<? super S, T> function) {
        return Iterables.materialize(Lazy.map(iterable, function));
    }

    public static <S, T> Collection<TwoTuple<S, T>> zip(Iterable<S> iterable1, Iterable<T> iterable2) {
        return Iterables.materialize(Lazy.zip(iterable1, iterable2));
    }

    public static <T> Collection<TwoTuple<Integer, T>> enumerate(Iterable<T> iterable) {
        return Iterables.materialize(Lazy.enumerate(iterable));
    }

    public static <T> Collection<Boolean> equate(Iterable<T> first, Iterable<T> second, final Equivalence<? super T> equivalence) {
        return Iterables.materialize(Lazy.equate(first, second, equivalence));
    }

    public static <S, T> Collection<TwoTuple<T, S>> index(Iterable<S> iterable, final org.javafunk.funk.functors.Indexer<? super S, T> indexer) {
        return Iterables.materialize(Lazy.index(iterable, indexer));
    }

    public static <S, T> Map<T, Collection<S>> group(Iterable<S> iterable, org.javafunk.funk.functors.Indexer<? super S, T> indexer) {
        Map<T, Collection<S>> groupedElements = new HashMap<T, Collection<S>>();
        for (S element : iterable) {
            T index = indexer.index(element);
            if (!groupedElements.containsKey(index)) {
                groupedElements.put(index, new ArrayList<S>());
            }
            groupedElements.get(index).add(element);
        }
        return groupedElements;
    }

    public static <T> void each(Iterable<T> targets, org.javafunk.funk.functors.Action<? super T> function) {
        Iterables.materialize(Lazy.each(targets, function));
    }

    public static <T> Collection<T> filter(Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return Iterables.materialize(Lazy.filter(iterable, predicate));
    }

    public static <T> Collection<T> reject(Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return Iterables.materialize(Lazy.reject(iterable, predicate));
    }

    public static <T> T first(Iterable<? extends T> iterable) {
        return iterable.iterator().next();
    }

    public static <T> T first(Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return first(filter(iterable, predicate));
    }

    public static <T> Collection<T> first(Iterable<T> iterable, int numberOfElementsRequired) {
        return take(iterable, numberOfElementsRequired);
    }

    public static <T> Collection<T> first(Iterable<T> iterable, int numberOfElementsRequired, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return first(filter(iterable, predicate), numberOfElementsRequired);
    }

    public static <T> T last(Iterable<T> iterable) {
        return slice(iterable, -1, null).iterator().next();
    }

    public static <T> T last(Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return last(filter(iterable, predicate));
    }

    public static <T> Collection<T> last(Iterable<T> iterable, int numberOfElementsRequired) {
        if (numberOfElementsRequired < 0) {
            throw new IllegalArgumentException("Number of elements required cannot be negative");
        }
        if (numberOfElementsRequired == 0) {
            return Collections.emptyList();
        }
        return slice(iterable, -numberOfElementsRequired, null);
    }

    public static <T> Collection<T> last(Iterable<T> iterable, int numberOfElementsRequired, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return last(filter(iterable, predicate), numberOfElementsRequired);
    }

    // TODO: Toby (10/07/11): Test this
    public static <T> Collection<T> rest(Iterable<T> iterable) {
        return slice(iterable, 1, null);
    }

    public static <T> Collection<T> take(Iterable<T> iterable, int numberToTake) {
        return Iterables.materialize(Lazy.take(iterable, numberToTake));
    }

    public static <T> Collection<T> drop(Iterable<T> iterable, int numberToDrop) {
        return Iterables.materialize(Lazy.drop(iterable, numberToDrop));
    }

    public static <T> TwoTuple<Collection<T>, Collection<T>> partition(
            Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        TwoTuple<Iterable<T>, Iterable<T>> partition = Lazy.partition(iterable, predicate);
        return tuple(Iterables.materialize(partition.first()), Iterables.materialize(partition.second()));
    }

    public static <T> Collection<Collection<T>> batch(Iterable<T> iterable, int batchSize) {
        Collection<Collection<T>> result = new ArrayList<Collection<T>>();
        Iterable<Iterable<T>> batches = Lazy.batch(iterable, batchSize);
        for (Iterable<T> batch : batches) {
            result.add(Iterables.materialize(batch));
        }
        return result;
    }

    public static void times(int numberOfTimes, org.javafunk.funk.functors.Action<? super Integer> function) {
        if (numberOfTimes < 0) {
            throw new IllegalArgumentException("The number of times to execute the function cannot be less than zero.");
        }
        for (int i = 0; i < numberOfTimes; i++) {
            function.on(i);
        }
    }

    public static <T> Collection<T> takeWhile(Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return Iterables.materialize(Lazy.takeWhile(iterable, predicate));
    }

    public static <T> Collection<T> takeUntil(Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return Iterables.materialize(Lazy.takeUntil(iterable, predicate));
    }

    public static <T> Collection<T> dropWhile(Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return Iterables.materialize(Lazy.dropWhile(iterable, predicate));
    }

    public static <T> Collection<T> dropUntil(Iterable<T> iterable, org.javafunk.funk.functors.Predicate<? super T> predicate) {
        return Iterables.materialize(Lazy.dropUntil(iterable, predicate));
    }

    public static <T> Collection<T> slice(Iterable<T> iterable, Integer start, Integer stop, Integer step) {
        List<? extends T> inputCollection = Iterables.asList(iterable);

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
        return Iterables.materialize(Lazy.repeat(iterable, numberOfTimesToRepeat));
    }

    private static class SliceHelper {
        private static int resolveStartIndex(Integer start, Integer numberOfElements) {
            return resolveIndex(start, numberOfElements, 0, 0);
        }

        private static int resolveStopIndex(Integer stop, Integer numberOfElements) {
            return resolveIndex(stop, numberOfElements, numberOfElements, 1);
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

        private static int resolveIndex(Integer index, Integer numberOfElements, Integer defaultIndex, Integer spread) {
            if (index == null) {
                return defaultIndex;
            } else if (index + numberOfElements < 0) {
                return 0 - spread;
            } else if (index < 0) {
                return index + numberOfElements;
            } else if (index >= numberOfElements) {
                return numberOfElements - 1 + spread;
            } else {
                return index;
            }
        }
    }
}
