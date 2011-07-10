package org.javafunk.functional;

import org.javafunk.datastructures.TwoTuple;
import org.javafunk.functional.functors.*;
import org.javafunk.functional.iterators.*;
import org.javafunk.functional.predicates.NotPredicate;

import java.util.Iterator;

import static org.javafunk.Literals.listWith;
import static org.javafunk.Literals.tuple;
import static org.javafunk.functional.Sequences.increasing;
import static org.javafunk.functional.Sequences.integers;

public class Lazy {
    private Lazy() {}

    public static <T> Iterable<Iterable<T>> batch(final Iterable<T> iterable, final int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("Batch size must be greater than zero.");
        }
        return new Iterable<Iterable<T>>(){
            public Iterator<Iterable<T>> iterator() {
                return new BatchedIterator<T>(iterable.iterator(), batchSize);
            }
        };
    }

    public static <T> Iterable<T> cycle(final Iterable<T> iterable) {
        return new Iterable<T>(){
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
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new SubSequenceIterator<T>(iterable.iterator(), numberToTake, null);
            }
        };
    }

    public static <T> Iterable<T> dropUntil(final Iterable<T> iterable, final Predicate<? super T> predicate) {
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
                return new ChainedIterator<T>(listWith(next).iterator(), iterator);
            }
        };
    }

    public static <T> Iterable<T> dropWhile(final Iterable<T> iterable, final Predicate<? super T> predicate) {
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
                return new ChainedIterator<T>(listWith(next).iterator(), iterator);
            }
        };
    }

    public static <T> Iterable<T> each(final Iterable<T> iterable, final Action<? super T> action) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new EachIterator<T>(iterable.iterator(), action);
            }
        };

    }

    public static <T> Iterable<TwoTuple<Integer, T>> enumerate(final Iterable<T> iterable) {
        return zip(integers(increasing()), iterable);
    }

    public static <S, T> Iterable<TwoTuple<T, S>> index(Iterable<S> iterable, final Indexer<? super S, T> indexer) {
        return zip(map(iterable, new Mapper<S, T>() {
            public T map(S input) {
                return indexer.index(input);
            }
        }), iterable);
    }

    public static <S, T> Iterable<T> map(final Iterable<S> iterable, final Mapper<? super S, T> function) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new MappedIterator<S, T>(iterable.iterator(), function);
            }
        };
    }

    public static <T> Iterable<Boolean> equate(Iterable<T> first, Iterable<T> second, final Equator<? super T> equator) {
        return map(zip(first, second), new Mapper<TwoTuple<T, T>, Boolean>() {
            public Boolean map(TwoTuple<T, T> input) {
                return equator.equate(input.first(), input.second());
            }
        });
    }

    public static <T> Iterable<T> filter(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new FilteredIterator<T>(iterable.iterator(), predicate);
            }
        };
    }

    public static <T> Iterable<T> reject(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new FilteredIterator<T>(iterable.iterator(), new NotPredicate<T>(predicate));
            }
        };
    }

    public static <T> TwoTuple<Iterable<T>,Iterable<T>> partition(Iterable<T> iterable, Predicate<? super T> predicate) {
        return tuple(filter(iterable, predicate), reject(iterable, predicate));
    }

    public static <T> Iterable<T> slice(final Iterable<T> iterable, final Integer start, final Integer stop, final Integer step) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new SubSequenceIterator<T>(iterable.iterator(), start, stop, step);
            }
        };
    }

    public static <T> Iterable<T> take(final Iterable<T> iterable, final int numberToTake) {
        if (numberToTake < 0) {
            throw new IllegalArgumentException("Cannot take a negative number of elements.");
        }
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new SubSequenceIterator<T>(iterable.iterator(), null, numberToTake);
            }
        };
    }

    public static <T> Iterable<T> takeUntil(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new PredicatedIterator<T>(iterable.iterator(), new NotPredicate<T>(predicate));
            }
        };
    }

    public static <T> Iterable<T> takeWhile(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new PredicatedIterator<T>(iterable.iterator(), predicate);
            }
        };
    }

    public static <S, T> Iterable<TwoTuple<S, T>> zip(final Iterable<S> firstIterable, final Iterable<T> secondIterable) {
        return new Iterable<TwoTuple<S, T>>(){
            public Iterator<TwoTuple<S, T>> iterator() {
                return new ZippedIterator<S, T>(firstIterable.iterator(), secondIterable.iterator());
            }
        };
    }

    private static class EachIterator<T> implements Iterator<T> {
        private Iterator<T> iterator;
        private Action<? super T> action;

        private EachIterator(Iterator<T> iterator, Action<? super T> action) {
            this.iterator = iterator;
            this.action = action;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            T next = iterator.next();
            action.on(next);
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
