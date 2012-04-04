/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.datastructures.tuples.Triple;
import org.javafunk.funk.functors.*;
import org.javafunk.funk.iterators.*;
import org.javafunk.funk.predicates.NotPredicate;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.javafunk.funk.Eager.first;
import static org.javafunk.funk.Iterables.concat;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.Sequences.increasing;
import static org.javafunk.funk.Sequences.integers;

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

    public static <T> Iterable<Pair<Integer, T>> enumerate(final Iterable<T> iterable) {
        return zip(integers(increasing()), iterable);
    }

    public static <S, T> Iterable<Pair<T, S>> index(Iterable<S> iterable, final Indexer<? super S, T> indexer) {
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

    public static <T> Iterable<Boolean> equate(Iterable<T> first, Iterable<T> second, final Equivalence<? super T> equivalence) {
        return map(zip(first, second), new Mapper<Pair<T, T>, Boolean>() {
            public Boolean map(Pair<T, T> input) {
                return equivalence.equal(input.first(), input.second());
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

    public static <T> Pair<Iterable<T>,Iterable<T>> partition(Iterable<T> iterable, Predicate<? super T> predicate) {
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

    public static <S, T> Iterable<Pair<S, T>> cartesianProduct(final Iterable<S> firstIterable, final Iterable<T> secondIterable) {
        return concat(map(firstIterable, new Mapper<S, Iterable<Pair<S, T>>>() {
            public Iterable<Pair<S, T>> map(S input) {
                return zip(cycle(listWith(input)), secondIterable);
            }
        }));
    }

    public static <R, S> Iterable<Pair<R, S>> zip(final Iterable<R> firstIterable, final Iterable<S> secondIterable) {
        return new Iterable<Pair<R, S>>(){
            public Iterator<Pair<R, S>> iterator() {
                return new TwoZippedIterator<R, S>(firstIterable.iterator(), secondIterable.iterator());
            }
        };
    }

    public static <R, S, T> Iterable<Triple<R, S, T>> zip(Iterable<R> firstIterable, Iterable<S> secondIterable, Iterable<T> thirdIterable) {
        return map(zip(listWith(firstIterable, secondIterable, thirdIterable)), Lazy.<R, S, T>toTriple());
    }

    @SuppressWarnings("unchecked")
    private static <R, S, T> Mapper<Iterable<?>, Triple<R, S, T>> toTriple() {
        return new Mapper<Iterable<?>, Triple<R, S, T>>() {
            @Override public Triple<R, S, T> map(Iterable<?> iterable) {
                return tuple((R)first(iterable), (S)first(rest(iterable)), (T)first(rest(rest(iterable))));
            }
        };
    }

    public static Iterable<? extends Iterable<?>> zip(final Iterable<? extends Iterable<?>> iterables) {
        return new Iterable<Iterable<?>>() {
            public Iterator<Iterable<?>> iterator() {
                final Iterable<Iterator<?>> iterators = Eager.map(iterables, toIterators());
                return new Iterator<Iterable<?>>() {
                    public boolean hasNext() {
                        return Eager.all(iterators, new Predicate<Iterator<?>>(){
                            public boolean evaluate(Iterator<?> iterator) {
                                return iterator.hasNext();
                            }
                        });
                    }

                    public Iterable<?> next() {
                        if (hasNext()) {
                            return Eager.map(iterators, new Mapper<Iterator<?>, Object>() {
                                public Object map(Iterator<?> iterator) {
                                    return iterator.next();
                                }
                            });
                        } else {
                            throw new NoSuchElementException();
                        }
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };


    }

    private static <T> Mapper<Iterable<? extends T>, Iterator<? extends T>> toIterators() {
        return new Mapper<Iterable<? extends T>, Iterator<? extends T>>() {
            @Override public Iterator<? extends T> map(Iterable<? extends T> iterable) {
                return iterable.iterator();
            }
        };
    }

    public static <T> Iterable<T> rest(final Iterable<T> iterable) {
        return slice(iterable, 1, null, 1);
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
