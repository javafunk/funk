package org.javafunk.functional;

import org.javafunk.datastructures.TwoTuple;
import org.javafunk.functional.functors.*;
import org.javafunk.functional.predicates.NotPredicate;
import org.javafunk.functional.functors.Predicate;
import org.javafunk.functional.iterators.*;

import java.util.Iterator;

import static org.javafunk.Literals.listWith;
import static org.javafunk.Literals.twoTuple;
import static org.javafunk.functional.Sequences.increasingIntegers;

public class Lazy {
    public static <T> Iterable<Iterable<T>> batch(final Iterable<? extends T> iterable, final int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("Batch size must be greater than zero.");
        }
        return new Iterable<Iterable<T>>(){
            public Iterator<Iterable<T>> iterator() {
                return new BatchedIterator<T>(iterable.iterator(), batchSize);
            }
        };
    }

    public static <T> Iterable<T> cycle(final Iterable<? extends T> iterable) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new CyclicIterator<T>(iterable.iterator());
            }
        };
    }

    public static <T> Iterable<T> drop(final Iterable<? extends T> iterable, final int numberToTake) {
        if (numberToTake < 0) {
            throw new IllegalArgumentException("Cannot drop a negative number of elements.");
        }
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new SubSequenceIterator<T>(iterable.iterator(), numberToTake, null);
            }
        };
    }

    public static <T> Iterable<T> dropUntil(final Iterable<? extends T> iterable, final Predicate<? super T> predicate) {
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

    public static <T> Iterable<T> dropWhile(final Iterable<? extends T> iterable, final Predicate<? super T> predicate) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                Iterator<? extends T> iterator = iterable.iterator();
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

    public static <T> Iterable<T> each(final Iterable<? extends T> iterable, final Procedure<? super T> procedure) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new EachIterator<T>(iterable.iterator(), procedure);
            }
        };

    }

    public static <T> Iterable<TwoTuple<Integer, T>> enumerate(final Iterable<? extends T> iterable) {
        return zip(increasingIntegers(), iterable);
    }

    public static <T> Iterable<T> filter(final Iterable<? extends T> iterable, final Predicate<? super T> predicate) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new FilteredIterator<T>(iterable.iterator(), predicate);
            }
        };
    }

    public static <S, T> Iterable<T> map(final Iterable<? extends S> iterable, final Mapper<? super S, ? extends T> function) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new MappedIterator<S, T>(iterable.iterator(), function);
            }
        };
    }

    public static <T> TwoTuple<Iterable<T>,Iterable<T>> partition(Iterable<? extends T> iterable, Predicate<? super T> predicate) {
        return twoTuple(filter(iterable, predicate), reject(iterable, predicate));
    }

    public static <T> Iterable<T> reject(final Iterable<? extends T> iterable, final Predicate<? super T> predicate) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new FilteredIterator<T>(iterable.iterator(), new NotPredicate<T>(predicate));
            }
        };
    }

    public static <T> Iterable<T> slice(final Iterable<? extends T> iterable, final Integer start, final Integer stop, final Integer step) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new SubSequenceIterator<T>(iterable.iterator(), start, stop, step);
            }
        };
    }

    public static <T> Iterable<T> take(final Iterable<? extends T> iterable, final int numberToTake) {
        if (numberToTake < 0) {
            throw new IllegalArgumentException("Cannot take a negative number of elements.");
        }
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new SubSequenceIterator<T>(iterable.iterator(), null, numberToTake);
            }
        };
    }

    public static <T> Iterable<T> takeUntil(final Iterable<? extends T> iterable, final Predicate<? super T> predicate) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new PredicatedIterator<T>(iterable.iterator(), new NotPredicate<T>(predicate));
            }
        };
    }

    public static <T> Iterable<T> takeWhile(final Iterable<? extends T> iterable, final Predicate<? super T> predicate) {
        return new Iterable<T>(){
            public Iterator<T> iterator() {
                return new PredicatedIterator<T>(iterable.iterator(), predicate);
            }
        };
    }

    public static <S, T> Iterable<TwoTuple<S, T>> zip(final Iterable<? extends S> firstIterable, final Iterable<? extends T> secondIterable) {
        return new Iterable<TwoTuple<S, T>>(){
            public Iterator<TwoTuple<S, T>> iterator() {
                return new ZippedIterator<S, T>(firstIterable.iterator(), secondIterable.iterator());
            }
        };
    }

    private static class EachIterator<T> implements Iterator<T> {
        private Iterator<? extends T> iterator;
        private Procedure<? super T> procedure;

        private EachIterator(Iterator<? extends T> iterator, Procedure<? super T> procedure) {
            this.iterator = iterator;
            this.procedure = procedure;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            T next = iterator.next();
            procedure.execute(next);
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
