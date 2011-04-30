package org.javafunk.functional;

import org.javafunk.datastructures.IntegerRange;
import org.javafunk.datastructures.TwoTuple;
import org.javafunk.functional.functors.DoFunction;
import org.javafunk.functional.functors.MapFunction;
import org.javafunk.functional.functors.PredicateFunction;
import org.javafunk.functional.functors.ReduceFunction;

import java.util.*;

import static org.javafunk.IterableUtils.materialize;
import static org.javafunk.IterableUtils.toList;
import static org.javafunk.IteratorUtils.toIterable;
import static org.javafunk.Literals.twoTuple;

public class Eager {
    public static <S, T> T reduce(Iterable<? extends S> iterable, T initialValue, ReduceFunction<S, T> function) {
        T accumulator = initialValue;
        for (S element : iterable) {
            accumulator = function.accumulate(accumulator, element);
        }
        return accumulator;
    }

    public static <T> T reduce(Iterable<? extends T> iterable, ReduceFunction<T, T> function) {
        final Iterator<? extends T> iterator = iterable.iterator();
        final T firstElement = iterator.next();
        final Iterable<? extends T> restOfElements = toIterable(iterator);
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

    public static <T> Boolean any(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        for (T item : iterable) {
            if (predicate.matches(item)) {
                return true;
            }
        }
        return false;
    }

    public static <T> Boolean all(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        for (T item : iterable) {
            if (!predicate.matches(item)) {
                return false;
            }
        }
        return true;
    }

    public static <T> Boolean none(Iterable<? extends T> items, PredicateFunction<T> predicate) {
        return !any(items, predicate);
    }

    public static <T extends Comparable<T>> T max(Iterable<? extends T> iterable) {
        return reduce(iterable, new ReduceFunction<T, T>() {
            public T accumulate(T currentMax, T element) {
                return (element != null && element.compareTo(currentMax) > 0) ? element : currentMax;
            }
        });
    }

    public static <T extends Comparable<T>> T min(Iterable<? extends T> iterable) {
        return reduce(iterable, new ReduceFunction<T, T>() {
            public T accumulate(T currentMin, T element) {
                return (element != null && element.compareTo(currentMin) < 0) ? element : currentMin;
            }
        });
    }

    public static <S, T> Collection<T> map(Iterable<? extends S> iterable, MapFunction<S, T> function) {
        return materialize(Lazy.map(iterable, function));
    }

    public static <S, T> Collection<TwoTuple<S, T>> zip(Iterable<? extends S> iterable1, Iterable<? extends T> iterable2) {
        return materialize(Lazy.zip(iterable1, iterable2));
    }

    public static <T> Collection<TwoTuple<Integer, T>> enumerate(Iterable<? extends T> iterable) {
        return materialize(Lazy.enumerate(iterable));
    }

    public static <T> void each(Iterable<? extends T> targets, DoFunction<T> function) {
        materialize(Lazy.each(targets, function));
    }

    public static <T> Collection<T> filter(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return materialize(Lazy.filter(iterable, predicate));
    }

    public static <T> Collection<T> reject(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return materialize(Lazy.reject(iterable, predicate));
    }

    public static <T> T first(Iterable<? extends T> iterable) {
        return iterable.iterator().next();
    }

    public static <T> T first(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return first(filter(iterable, predicate));
    }

    public static <T> Collection<T> first(Iterable<? extends T> iterable, int numberOfElementsRequired) {
        return take(iterable, numberOfElementsRequired);
    }

    public static <T> Collection<T> first(Iterable<? extends T> iterable, int numberOfElementsRequired, PredicateFunction<T> predicate) {
        return first(filter(iterable, predicate), numberOfElementsRequired);
    }

    public static <T> T last(Iterable<? extends T> iterable) {
        return slice(iterable, -1, null).iterator().next();
    }

    public static <T> T last(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return last(filter(iterable, predicate));
    }

    public static <T> Collection<T> last(Iterable<? extends T> iterable, int numberOfElementsRequired) {
        if (numberOfElementsRequired < 0) {
            throw new IllegalArgumentException("Number of elements required cannot be negative");
        }
        if (numberOfElementsRequired == 0) {
            return Collections.emptyList();
        }
        return slice(iterable, -numberOfElementsRequired, null);
    }

    public static <T> Collection<T> last(Iterable<? extends T> iterable, int numberOfElementsRequired, PredicateFunction<T> predicate) {
        return last(filter(iterable, predicate), numberOfElementsRequired);
    }

    public static <T> Collection<T> take(Iterable<? extends T> iterable, int numberToTake) {
        return materialize(Lazy.take(iterable, numberToTake));
    }

    public static <T> Collection<T> drop(Iterable<? extends T> iterable, int numberToDrop) {
        return materialize(Lazy.drop(iterable, numberToDrop));
    }

    public static <T> TwoTuple<Collection<T>, Collection<T>> partition(
            Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        TwoTuple<Iterable<T>, Iterable<T>> partition = Lazy.partition(iterable, predicate);
        return twoTuple(materialize(partition.first()), materialize(partition.second()));
    }

    public static <T> Collection<Collection<T>> batch(Iterable<T> iterable, int batchSize) {
        Collection<Collection<T>> result = new ArrayList<Collection<T>>();
        Iterable<Iterable<T>> batches = Lazy.batch(iterable, batchSize);
        for (Iterable<T> batch : batches) {
            result.add(materialize(batch));
        }
        return result;
    }

    public static void times(int numberOfTimes, DoFunction<Integer> function) {
        if (numberOfTimes < 0) {
            throw new IllegalArgumentException("The number of times to execute the function cannot be less than zero.");
        }
        for (int i = 0; i < numberOfTimes; i++) {
            function.actOn(i);
        }
    }

    public static <T> Collection<T> takeWhile(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return materialize(Lazy.takeWhile(iterable, predicate));
    }

    public static <T> Collection<T> takeUntil(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return materialize(Lazy.takeUntil(iterable, predicate));
    }

    public static <T> Collection<T> dropWhile(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return materialize(Lazy.dropWhile(iterable, predicate));
    }

    public static <T> Collection<T> dropUntil(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return materialize(Lazy.dropUntil(iterable, predicate));
    }

    public static <T> Collection<T> slice(Iterable<? extends T> iterable, Integer start, Integer stop, Integer step) {
        List<? extends T> inputCollection = toList(iterable);

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

    public static <T> Collection<T> slice(Iterable<? extends T> iterable, Integer start, Integer stop) {
        return slice(iterable, start, stop, 1);
    }

    private static class SliceHelper {
        private static int resolveStartIndex(Integer start, Integer numberOfElements) {
            return resolveIndex(start, numberOfElements, 0, 0);
        }

        private static int resolveStopIndex(Integer stop, Integer numberOfElements) {
            return resolveIndex(stop, numberOfElements, numberOfElements, 1);
        }

        private static int resolveStepSize(Integer step) {
            if (step == null)   { return 1; }
            else if (step == 0) { throw new IllegalArgumentException("Step size cannot be zero"); }
            else                { return step; }
        }

        private static int resolveIndex(Integer index, Integer numberOfElements, Integer defaultIndex, Integer spread) {
            if      (index == null)                { return defaultIndex; }
            else if (index + numberOfElements < 0) { return 0 - spread; }
            else if (index < 0)                    { return index + numberOfElements; }
            else if (index >= numberOfElements)    { return numberOfElements - 1 + spread; }
            else                                   { return index; }
        }
    }
}
