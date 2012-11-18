/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.IntegerRange;
import org.javafunk.funk.datastructures.tuples.*;
import org.javafunk.funk.functors.*;
import org.javafunk.funk.functors.functions.BinaryFunction;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.functors.predicates.BinaryPredicate;
import org.javafunk.funk.functors.predicates.UnaryPredicate;
import org.javafunk.funk.functors.procedures.UnaryProcedure;
import org.javafunk.funk.monads.Option;

import java.util.*;

import static java.util.Collections.emptyList;
import static org.javafunk.funk.Checks.returnOrThrowIfNull;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Iterators.asIterable;
import static org.javafunk.funk.Literals.collectionFrom;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.functors.adapters.ActionUnaryProcedureAdapter.actionUnaryProcedure;
import static org.javafunk.funk.functors.adapters.EquivalenceBinaryPredicateAdapter.equivalenceBinaryPredicate;
import static org.javafunk.funk.functors.adapters.IndexerUnaryFunctionAdapter.indexerUnaryFunction;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;
import static org.javafunk.funk.functors.adapters.ReducerBinaryFunctionAdapter.reducerBinaryFunction;

/**
 * @since 1.0
 */
public class Eagerly {
    private Eagerly() {}

    public static <S, T> T reduce(
            Iterable<? extends S> iterable,
            T initialValue,
            BinaryFunction<T, ? super S, T> function) {
        T accumulator = initialValue;
        for (S element : iterable) {
            accumulator = function.call(accumulator, element);
        }
        return accumulator;
    }

    public static <S, T> T reduce(
            Iterable<? extends S> iterable,
            T initialValue,
            Reducer<? super S, T> reducer) {
        return reduce(iterable, initialValue, reducerBinaryFunction(reducer));
    }

    public static <T> T reduce(
            Iterable<T> iterable,
            BinaryFunction<T, ? super T, T> function) {
        Iterator<T> iterator = iterable.iterator();
        T firstElement = iterator.next();
        Iterable<T> restOfElements = asIterable(iterator);
        return reduce(restOfElements, firstElement, function);
    }

    public static <T> T reduce(Iterable<T> iterable, Reducer<T, T> reducer) {
        return reduce(iterable, reducerBinaryFunction(reducer));
    }

    public static <T> Boolean any(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        for (T item : iterable) {
            if (predicate.evaluate(item)) {
                return true;
            }
        }
        return false;
    }

    public static <T> Boolean all(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        for (T item : iterable) {
            if (!predicate.evaluate(item)) {
                return false;
            }
        }
        return true;
    }

    public static <T> Boolean none(
            Iterable<T> items,
            UnaryPredicate<? super T> predicate) {
        return !any(items, predicate);
    }

    public static <T> T max(Iterable<T> iterable, final Comparator<? super T> comparator) {
        return reduce(iterable, new Reducer<T, T>() {
            public T accumulate(T currentMax, T element) {
                return comparator.compare(element, currentMax) > 0 ?
                        element :
                        currentMax;
            }
        });
    }

    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        return returnOrThrowIfNull(reduce(iterable, new Reducer<T, T>() {
            public T accumulate(T currentMax, T element) {
                return (element != null && element.compareTo(currentMax) > 0) ?
                        element :
                        currentMax;
            }
        }), new NoSuchElementException("Maximum value is undefined if all values in the supplied Iterable are null."));
    }

    public static <T> T min(Iterable<T> iterable, final Comparator<? super T> comparator) {
        return reduce(iterable, new Reducer<T, T>() {
            public T accumulate(T currentMin, T element) {
                return comparator.compare(element, currentMin) < 0 ?
                        element :
                        currentMin;
            }
        });
    }

    public static <T extends Comparable<T>> T min(Iterable<T> iterable) {
        return returnOrThrowIfNull(reduce(iterable, new Reducer<T, T>() {
            public T accumulate(T currentMin, T element) {
                return (element != null && element.compareTo(currentMin) < 0) ?
                        element :
                        currentMin;
            }
        }), new NoSuchElementException("Minimum value is undefined if all values in the supplied Iterable are null."));
    }

    /**
     * Maps an {@code Iterable} of elements of type {@code S} into a {@code Collection}
     * of elements of type {@code T} using the supplied {@code UnaryFunction}. The
     * {@code UnaryFunction} will be provided with each element in the input
     * {@code Iterable} and the value returned from the {@code UnaryFunction} will be
     * used in place of the input element in the returned {@code Collection}. For a
     * more mathematical description of the map higher order function, see the
     * <a href="http://en.wikipedia.org/wiki/Map_(higher-order_function)">
     * map article on Wikipedia</a>.
     *
     * <p>Since a {@code Collection} instance is returned, the mapping is performed
     * eagerly, i.e., the {@code UnaryFunction} is applied to each element in the input
     * {@code Iterable} immediately.</p>
     *
     * <p>{@code map} does not discriminate against {@code null} values in the input
     * {@code Iterable}, they are passed to the function in the same way as any other
     * value. Similarly, any {@code null} values returned are retained in the output
     * {@code Collection}. Thus, the input and output collections will always be of
     * the same size.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Consider a collection of {@code Person} objects where a {@code Person} is defined
     * by the following class:
     * <blockquote>
     * <pre>
     *   public class Person {
     *       private Name name;
     *
     *       public Person(Name name) {
     *           this.name = name;
     *       }
     *
     *       public Name getName() {
     *           return name;
     *       };
     *
     *       ...
     *   }
     * </pre>
     * </blockquote>
     * and a {@code Name} is defined by the following class:
     * <blockquote>
     * <pre>
     *   public class Name {
     *       private String firstName;
     *       private String lastName;
     *
     *       public Name(String firstName, String lastName) {
     *           this.firstName = firstName;
     *           this.lastName = lastName;
     *       }
     *
     *       public String getFirstName() {
     *           return firstName;
     *       }
     *
     *       public String getLastName() {
     *           return lastName;
     *       }
     *
     *       ...
     *   }
     * </pre>
     * </blockquote>
     * Say we have an in memory database of all employees at a company:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Person&gt; people = Literals.listWith(
     *           new Person(new Name("Julio", "Tilman")),
     *           new Person(new Name("Roslyn", "Snipe")),
     *           new Person(new Name("Tameka", "Brickhouse")));
     * </pre>
     * </blockquote>
     * and we need to generate a report of all names, last name first, first name second,
     * hyphen separated. In order to do this we need to convert, or <em>map</em>, each
     * {@code Person} instance to the required {@code String}. This can be achieved
     * as follow:
     * <blockquote>
     * <pre>
     *   Collection&lt;String&gt; names = Eagerly.map(people, new UnaryFunction&lt;Person, String&gt;() {
     *       &#64;Override public String call(Person person) {
     *           return person.getLastName() + "-" + person.getFirstName;
     *       }
     *   });
     * </pre>
     * </blockquote>
     * The resulting collection is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Collection&lt;String&gt; names = Literals.collectionWith(
     *           "Tilman-Julio",
     *           "Snipe-Roslyn",
     *           "Brickhouse-Tameka");
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} of elements to be mapped.
     * @param function A {@code UnaryFunction} which, given an element from the input iterable,
     *                 returns that element mapped to a new value potentially of a different type.
     * @param <S>      The type of the input elements, i.e., the elements to map.
     * @param <T>      The type of the output elements, i.e., the mapped elements.
     * @return A {@code Collection} containing each instance of {@code S} from the input
     *         {@code Iterable} mapped to an instance of {@code T}.
     */
    public static <S, T> Collection<T> map(
            Iterable<S> iterable,
            UnaryFunction<? super S, T> function) {
        return materialize(Lazily.map(iterable, function));
    }

    /**
     * Maps an {@code Iterable} of elements of type {@code S} into a {@code Collection}
     * of elements of type {@code T} using the supplied {@code Mapper}. The
     * {@code Mapper} will be provided with each element in the input {@code Iterable}
     * and the value returned from the {@code Mapper} will be used in place of the
     * input element in the returned {@code Collection}. For a more mathematical
     * description of the map higher order function, see the
     * <a href="http://en.wikipedia.org/wiki/Map_(higher-order_function)">
     * map article on Wikipedia</a>.
     *
     * <p>This override of
     * {@link #map(Iterable, org.javafunk.funk.functors.functions.UnaryFunction)} is
     * provided to allow a {@code Mapper} to be used in place of a {@code UnaryFunction}
     * to enhance readability and better express intent. The contract of the function
     * is identical to that of the {@code UnaryFunction} version of {@code map}.</p>
     *
     * <p>For example usage and further documentation, see
     * {@link #map(Iterable, org.javafunk.funk.functors.functions.UnaryFunction)}.</p>
     *
     * @param iterable The {@code Iterable} of elements to be mapped.
     * @param mapper   A {@code Mapper} which, given an element from the input iterable,
     *                 returns that element mapped to a new value potentially of a different type.
     * @param <S>      The type of the input elements, i.e., the elements to map.
     * @param <T>      The type of the output elements, i.e., the mapped elements.
     * @return A {@code Collection} containing each instance of {@code S} from the input
     *         {@code Iterable} mapped to an instance of {@code T}.
     * @see #map(Iterable, org.javafunk.funk.functors.functions.UnaryFunction)
     */
    public static <S, T> Collection<T> map(Iterable<S> iterable, Mapper<? super S, T> mapper) {
        return map(iterable, mapperUnaryFunction(mapper));
    }

    public static <S, T> Collection<Pair<S, T>> zip(
            Iterable<S> first,
            Iterable<T> second) {
        return materialize(Lazily.zip(first, second));
    }

    public static <R, S, T> Collection<Triple<R, S, T>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third) {
        return materialize(Lazily.zip(first, second, third));
    }

    public static <R, S, T, U> Collection<Quadruple<R, S, T, U>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth) {
        return materialize(Lazily.zip(first, second, third, fourth));
    }

    public static <R, S, T, U, V> Collection<Quintuple<R, S, T, U, V>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth) {
        return materialize(Lazily.zip(first, second, third, fourth, fifth));
    }

    public static <R, S, T, U, V, W> Collection<Sextuple<R, S, T, U, V, W>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth) {
        return materialize(Lazily.zip(first, second, third, fourth, fifth, sixth));
    }

    public static <R, S, T, U, V, W, X> Collection<Septuple<R, S, T, U, V, W, X>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth,
            Iterable<X> seventh) {
        return materialize(Lazily.zip(first, second, third, fourth, fifth, sixth, seventh));
    }

    public static <R, S, T, U, V, W, X, Y> Collection<Octuple<R, S, T, U, V, W, X, Y>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth,
            Iterable<X> seventh,
            Iterable<Y> eighth) {
        return materialize(Lazily.zip(first, second, third, fourth, fifth, sixth, seventh, eighth));
    }

    public static <R, S, T, U, V, W, X, Y, Z> Collection<Nonuple<R, S, T, U, V, W, X, Y, Z>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth,
            Iterable<X> seventh,
            Iterable<Y> eighth,
            Iterable<Z> ninth) {
        return materialize(Lazily.zip(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth));
    }

    public static Collection<Collection<?>> zip(Iterable<? extends Iterable<?>> iterables) {
        return Eagerly.map(Lazily.zip(iterables), new Mapper<Iterable<?>, Collection<?>>() {
            @Override public Collection<?> map(Iterable<?> iterable) {
                return collectionFrom(iterable);
            }
        });
    }

    public static <R, S> Collection<Pair<R, S>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second) {
        return materialize(Lazily.cartesianProduct(first, second));
    }

    public static <R, S, T> Collection<Triple<R, S, T>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third) {
        return materialize(Lazily.cartesianProduct(first, second, third));
    }

    public static <R, S, T, U> Collection<Quadruple<R, S, T, U>> cartesianProduct(
                Iterable<R> first,
                Iterable<S> second,
                Iterable<T> third,
                Iterable<U> fourth) {
        return materialize(Lazily.cartesianProduct(first, second, third, fourth));
    }

    public static <R, S, T, U, V> Collection<Quintuple<R, S, T, U, V>> cartesianProduct(
                    Iterable<R> first,
                    Iterable<S> second,
                    Iterable<T> third,
                    Iterable<U> fourth,
                    Iterable<V> fifth) {
        return materialize(Lazily.cartesianProduct(first, second, third, fourth, fifth));
    }

    public static <R, S, T, U, V, W> Collection<Sextuple<R, S, T, U, V, W>> cartesianProduct(
                        Iterable<R> first,
                        Iterable<S> second,
                        Iterable<T> third,
                        Iterable<U> fourth,
                        Iterable<V> fifth,
                        Iterable<W> sixth) {
        return materialize(Lazily.cartesianProduct(first, second, third, fourth, fifth, sixth));
    }

    public static <R, S, T, U, V, W, X> Collection<Septuple<R, S, T, U, V, W, X>> cartesianProduct(
                        Iterable<R> first,
                        Iterable<S> second,
                        Iterable<T> third,
                        Iterable<U> fourth,
                        Iterable<V> fifth,
                        Iterable<W> sixth,
                        Iterable<X> seventh) {
        return materialize(Lazily.cartesianProduct(first, second, third, fourth, fifth, sixth, seventh));
    }

    public static <R, S, T, U, V, W, X, Y> Collection<Octuple<R, S, T, U, V, W, X, Y>> cartesianProduct(
                            Iterable<R> first,
                            Iterable<S> second,
                            Iterable<T> third,
                            Iterable<U> fourth,
                            Iterable<V> fifth,
                            Iterable<W> sixth,
                            Iterable<X> seventh,
                            Iterable<Y> eighth) {
        return materialize(Lazily.cartesianProduct(first, second, third, fourth, fifth, sixth, seventh, eighth));
    }

    public static <R, S, T, U, V, W, X, Y, Z> Collection<Nonuple<R, S, T, U, V, W, X, Y, Z>> cartesianProduct(
                            Iterable<R> first,
                            Iterable<S> second,
                            Iterable<T> third,
                            Iterable<U> fourth,
                            Iterable<V> fifth,
                            Iterable<W> sixth,
                            Iterable<X> seventh,
                            Iterable<Y> eighth,
                            Iterable<Z> ninth) {
        return materialize(Lazily.cartesianProduct(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth));
    }

    public static Collection<Collection<?>> cartesianProduct(Iterable<? extends Iterable<?>> iterables) {
        return Eagerly.map(Lazily.cartesianProduct(iterables), new Mapper<Iterable<?>, Collection<?>>() {
            @Override public Collection<?> map(Iterable<?> iterable) {
                return collectionFrom(iterable);
            }
        });
    }

    public static <T> Collection<Pair<Integer, T>> enumerate(Iterable<T> iterable) {
        return materialize(Lazily.enumerate(iterable));
    }

    public static <T> Collection<Boolean> equate(
            Iterable<T> first,
            Iterable<T> second,
            BinaryPredicate<? super T, ? super T> predicate) {
        return materialize(Lazily.equate(first, second, predicate));
    }

    public static <T> Collection<Boolean> equate(
            Iterable<T> first,
            Iterable<T> second,
            Equivalence<? super T> equivalence) {
        return equate(first, second, equivalenceBinaryPredicate(equivalence));
    }

    public static <S, T> Collection<Pair<T, S>> index(
            Iterable<S> iterable,
            UnaryFunction<? super S, T> function) {
        return materialize(Lazily.index(iterable, function));
    }

    public static <S, T> Collection<Pair<T, S>> index(
            Iterable<S> iterable,
            Indexer<? super S, T> indexer) {
        return index(iterable, indexerUnaryFunction(indexer));
    }

    public static <S, T> Map<T, Collection<S>> group(
            Iterable<S> iterable,
            UnaryFunction<? super S, T> indexer) {
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

    public static <S, T> Map<T, Collection<S>> group(
            Iterable<S> iterable,
            Indexer<? super S, T> indexer) {
        return group(iterable, indexerUnaryFunction(indexer));
    }

    public static <T> void each(
            Iterable<T> targets,
            UnaryProcedure<? super T> procedure) {
        materialize(Lazily.each(targets, procedure));
    }

    public static <T> void each(
            Iterable<T> targets,
            Action<? super T> action) {
        each(targets, actionUnaryProcedure(action));
    }

    /**
     * Filters those elements from the input {@code Iterable} of type {@code T}
     * that satisfy the supplied {@code UnaryPredicate} into a {@code Collection}
     * of elements of type {@code T} . The {@code UnaryPredicate} will be provided
     * with each element in the input {@code Iterable} and the element will be
     * retained in the output {@code Collection} if and only if the
     * {@code UnaryPredicate} returns {@code true}. If it returns {@code false},
     * the element will be discarded.
     *
     * <p>For a more complete description of the filter higher order function,
     * see the <a href="http://en.wikipedia.org/wiki/Filter_(higher-order_function)">
     * filter article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} instance is returned, the filtering is performed
     * eagerly, i.e., the {@code UnaryPredicate} is applied to each element in the input
     * {@code Iterable} immediately.</p>
     *
     * <p>{@code filter} does not discriminate against {@code null} values in the input
     * {@code Iterable}, they are passed to the {@code Predicate} in the same way as
     * any other value. Similarly, if the {@code UnaryPredicate} returns {@code true}
     * when called with a {@code null} value, the {@code null} value will be retained in
     * the output {@code Collection}.</p>
     *
     * <h4>Example Usage</h4>
     *
     * Consider a collection of {@code Pet} objects where a {@code Pet} is defined
     * by the following interface:
     * <blockquote>
     * <pre>
     *   public interface Pet {
     *       String getName();
     *   }
     * </pre>
     * </blockquote>
     * Now, consider {@code Pet} has two implementations, {@code Cat} and
     * {@code Dog}, defined by the following classes:
     * <blockquote>
     * <pre>
     *   public class Dog implements Pet {
     *       private String name;
     *
     *       public Dog(String name) {
     *           this.name = name;
     *       }
     *
     *       &#64;Override
     *       public String getName() {
     *           return String.format("Bark, bark, %s, bark", name);
     *       }
     *   }
     *
     *   public class Cat implements Pet {
     *       private String name;
     *
     *       public Cat(String name) {
     *           this.name = name;
     *       }
     *
     *       &#64;Override
     *       public String getName() {
     *           return String.format("%s, miaow", name);
     *       }
     *   }
     * </pre>
     * </blockquote>
     * Say we have an in memory database of all pets in a neighbourhood:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Pet&gt; pets = Literals.listWith(
     *           new Dog("Fido"),
     *           new Dog("Bones"),
     *           new Cat("Fluff"),
     *           new Dog("Graham"),
     *           new Cat("Ginger"));
     * </pre>
     * </blockquote>
     * It's vaccination season for the dogs in the neighbourhood so we need to
     * get hold of a list of all of them. We can do this using {@code filter}:
     * <blockquote>
     * <pre>
     *   Collection&lt;Pet&gt; names = Eagerly.filter(pets, new Predicate&lt;Pet&gt;() {
     *       &#64;Override public boolean evaluate(Pet pet) {
     *           return pet instanceof Dog;
     *       }
     *   });
     * </pre>
     * </blockquote>
     * Note, we used an anonymous {@code Predicate} instance. The {@code Predicate} interface
     * is equivalent to the {@code UnaryPredicate} interface and exists to simplify the
     * eighty percent case with predicates.
     *
     * <p>The resulting collection is equivalent to the following:</p>
     * <blockquote>
     * <pre>
     *   Collection&lt;Pet&gt; names = Literals.collectionWith(
     *           new Dog("Fido"),
     *           new Dog("Bones"),
     *           new Dog("Graham"));
     * </pre>
     * </blockquote>
     *
     * @param iterable  The {@code Iterable} of elements to be filtered.
     * @param predicate A {@code UnaryPredicate} which, given an element from the input iterable,
     *                  returns {@code true} if that element should be retained and {@code false}
     *                  if it should be discarded.
     * @param <T>       The type of the elements to be filtered.
     * @return A {@code Collection} containing those elements of type {@code T} from the input
     *         {@code Iterable} that evaluate to {@code true} when passed to the supplied
     *         {@code UnaryPredicate}.
     */
    public static <T> Collection<T> filter(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.filter(iterable, predicate));
    }

    /**
     * Rejects those elements from the input {@code Iterable} of type {@code T}
     * that satisfy the supplied {@code Predicate} and returns a {@code Collection}
     * of type {@code T} containing the remaining elements. The {@code UnaryPredicate}
     * will be provided with each element in the input {@code Iterable} and the element
     * will be retained in the output {@code Collection} if and only if the
     * {@code UnaryPredicate} returns {@code false}. If it returns {@code true},
     * the element will be discarded.
     *
     * <p>Since a {@code Collection} instance is returned, the rejection is performed
     * eagerly, i.e., the {@code UnaryPredicate} is applied to each element in the input
     * {@code Iterable} immediately.</p>
     *
     * <p>{@code reject} does not discriminate against {@code null} values in the input
     * {@code Iterable}, they are passed to the {@code UnaryPredicate} in the same way
     * as any other value. Similarly, if the {@code UnaryPredicate} returns {@code false}
     * when called with a {@code null} value, the {@code null} value will be retained in
     * the output {@code Collection}.</p>
     *
     * <h4>Example Usage</h4>
     *
     * Consider a collection of {@code Pet} objects where a {@code Pet} is defined
     * by the following interface:
     * <blockquote>
     * <pre>
     *   public interface Pet {
     *       String getName();
     *   }
     * </pre>
     * </blockquote>
     * Now, consider {@code Pet} has three implementations, {@code Cat},
     * {@code Dog} and {@code Fish}, defined by the following classes:
     * <blockquote>
     * <pre>
     *   public class Dog implements Pet {
     *       private String name;
     *
     *       public Dog(String name) {
     *           this.name = name;
     *       }
     *
     *       &#64;Override
     *       public String getName() {
     *           return String.format("Bark, bark, %s, bark", name);
     *       }
     *   }
     *
     *   public class Cat implements Pet {
     *       private String name;
     *
     *       public Cat(String name) {
     *           this.name = name;
     *       }
     *
     *       &#64;Override
     *       public String getName() {
     *           return String.format("%s, miaow", name);
     *       }
     *   }
     *
     *   public class Fish implements Pet {
     *       private String name;
     *
     *       public Fish(String name) {
     *           this.name = name;
     *       }
     *
     *       &#64;Override
     *       public String getName() {
     *           return String.format("%s, bubbles, bubbles, bubbles!", name);
     *       }
     *   }
     * </pre>
     * </blockquote>
     * Say we have an in memory database of all pets in a pet store:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Pet&gt; pets = Literals.listWith(
     *           new Fish("Goldie"),
     *           new Dog("Fido"),
     *           new Dog("Bones"),
     *           new Cat("Fluff"),
     *           new Dog("Graham"),
     *           new Cat("Ginger"));
     * </pre>
     * </blockquote>
     * A customer comes into the pet store wanting to purchase a new pet.
     * However, they are allergic to cats and want to know all other
     * available pets. We can find out this information using {@code reject}:
     * <blockquote>
     * <pre>
     *   Collection&lt;Pet&gt; nonFelinePets = Eagerly.reject(pets, new Predicate&lt;Pet&gt;() {
     *       &#64;Override public boolean evaluate(Pet pet) {
     *           return pet instanceof Cat;
     *       }
     *   });
     * </pre>
     * </blockquote>
     * Note, we used an anonymous {@code Predicate} instance. The {@code Predicate} interface
     * is equivalent to the {@code UnaryPredicate} interface and exists to simplify the
     * eighty percent case with predicates.
     *
     * <p>The resulting collection is equivalent to the following:</p>
     * <blockquote>
     * <pre>
     *   Collection&lt;Pet&gt; names = Literals.collectionWith(
     *           new Dog("Fido"),
     *           new Dog("Bones"),
     *           new Dog("Graham"));
     * </pre>
     * </blockquote>
     *
     * @param iterable  The {@code Iterable} of elements from which elements should be rejected.
     * @param predicate A {@code UnaryPredicate} which, given an element from the input iterable,
     *                  returns {@code true} if that element should be discarded and {@code false}
     *                  if it should be retained.
     * @param <T>       The type of the elements to be assesses for rejection.
     * @return A {@code Collection} containing those elements of type {@code T} from the input
     *         {@code Iterable} that evaluate to {@code false} when passed to the supplied
     *         {@code UnaryPredicate}.
     */
    public static <T> Collection<T> reject(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.reject(iterable, predicate));
    }

    /**
     * Returns an {@code Option} over the first element in the supplied {@code Iterable}.
     * If the {@code Iterable} is empty, {@code None} is returned, otherwise, a
     * {@code Some} is returned over the first value found.
     *
     * <p>This method has a return type of {@code Option} rather than returning the
     * first value directly since, in the case of an empty {@code Iterable}, an
     * exception would have to be thrown using that approach. Instead, the
     * {@code Option} can be queried for whether it contains a value or not,
     * avoiding any exception handling.</p>
     *
     * <p>Since an {@code Option} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the first element from the underlying
     * {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * The first element in the {@code Iterable} can be obtained as follows:
     * <blockquote>
     * <pre>
     *   Option&lt;Integer&gt; valueOption = first(values);
     *   Integer value = valueOption.get(); // => 5
     * </pre>
     * </blockquote>
     * Similarly, we can handle the empty {@code Iterable} case gracefully:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable();
     *   Option&lt;Integer&gt; valueOption = first(values);
     *   Integer value = valueOption.getOrElse(10); // => 10
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which the first element is required.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Option} instance representing the first element in the supplied
     *         {@code Iterable}.
     */
    public static <T> Option<T> first(Iterable<? extends T> iterable) {
        Iterator<? extends T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            return Option.some(iterator.next());
        } else {
            return Option.none();
        }
    }

    /**
     * Returns an {@code Option} over the first element in the supplied {@code Iterable}
     * that satisfies the supplied {@code UnaryPredicate}. If the {@code Iterable} is
     * empty, {@code None} is returned, otherwise, a {@code Some} is returned over
     * the first matching value found.
     *
     * <p>This method has a return type of {@code Option} rather than returning the
     * first matching value directly since, in the case of an empty {@code Iterable},
     * an exception would have to be thrown using that approach. Instead, the
     * {@code Option} can be queried for whether it contains a value or not,
     * avoiding any exception handling.</p>
     *
     * <p>Since an {@code Option} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the first matching element from the
     * underlying {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * The first even element in the {@code Iterable} can be obtained as follows:
     * <blockquote>
     * <pre>
     *   Option&lt;Integer&gt; valueOption = first(values, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   Integer value = valueOption.get(); // => 4
     * </pre>
     * </blockquote>
     * Note, we used an anonymous {@code Predicate} instance. The {@code Predicate} interface
     * is equivalent to the {@code UnaryPredicate} interface and exists to simplify the
     * eighty percent case with predicates.
     *
     * <p>Thanks to the {@code Option} returned, we can handle the empty {@code Iterable}
     * case gracefully:</p>
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable();
     *   Option&lt;Integer&gt; valueOption = first(values, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   Integer value = valueOption.getOrElse(10); // => 10
     * </pre>
     * </blockquote>
     * Similarly, if no elements match the supplied {@code UnaryPredicate}, we are returned
     * a {@code None}:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable(9, 7, 5, 3, 1);
     *   Option&lt;Integer&gt; valueOption = first(values, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   valueOption.hasValue(); // => false
     * </pre>
     * </blockquote>
     *
     * @param iterable  The {@code Iterable} to search for an element matching the supplied
     *                  {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} that must be satisfied by an element in the
     *                  supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Option} instance representing the first element in the supplied
     *         {@code Iterable} satisfying the supplied {@code UnaryPredicate}.
     */
    public static <T> Option<T> first(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return first(filter(iterable, predicate));
    }

    /**
     * Returns a {@code Collection} containing the first <em>n</em> elements in the supplied
     * {@code Iterable} where <em>n</em> is given by the supplied integer value. If the
     * {@code Iterable} is empty, an empty {@code Collection} is returned, otherwise,
     * a {@code Collection} containing the first <em>n</em> elements is returned.
     *
     * <p>In the case that the supplied {@code Iterable} does not contain enough
     * elements to satisfy the required number, a {@code Collection} containing
     * as many elements as possible is returned.</p>
     *
     * <p>Since a {@code Collection} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the elements from the underlying
     * {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Using {@code first}, we can obtain the first three elements in the {@code Iterable}.
     * The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstThreeValues = first(values, 3);
     *   Collection&lt;Integer&gt; equivalentValues = Literals.collectionWith(5, 4, 3);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} does not contain enough elements, we are returned a
     * {@code Collection} with as many elements as possible. The following two lines are
     * equivalent:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstSixValues = first(values, 6);
     *   Collection&lt;Integer&gt; equivalentValues = Literals.collectionWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Similarly, if the input {@code Iterable} contains no elements, an empty
     * {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable();
     *   Collection&lt;Integer&gt; firstThreeElements = first(values, 3);
     *   firstThreeElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which the first <em>n</em> elements
     *                 should be taken.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the required number of elements
     *         (or less) from the supplied {@code Iterable}.
     */
    public static <T> Collection<T> first(
            Iterable<T> iterable,
            int numberOfElementsRequired) {
        return take(iterable, numberOfElementsRequired);
    }

    /**
     * Returns a {@code Collection} containing the first <em>n</em> elements in the supplied
     * {@code Iterable} that satisfy the supplied {@code UnaryPredicate} where <em>n</em>
     * is given by the supplied integer value. If the {@code Iterable} is empty,
     * an empty {@code Collection} is returned, otherwise, a {@code Collection} containing
     * the first <em>n</em> matching elements is returned.
     *
     * <p>In the case that the supplied {@code Iterable} does not contain enough
     * matching elements to satisfy the required number, a {@code Collection} containing
     * as many elements as possible is returned.</p>
     *
     * <p>Since a {@code Collection} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve matching elements from the underlying
     * {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterableWith(8, 7, 6, 5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Using {@code first}, we can obtain the first three even elements in the {@code Iterable}.
     * The following two expressions are equivalent:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstThreeEvens = first(values, 3, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentEvens = Literals.collectionWith(8, 6, 4);
     * </pre>
     * </blockquote>
     * Note, we used an anonymous {@code Predicate} instance. The {@code Predicate} interface
     * is equivalent to the {@code UnaryPredicate} interface and exists to simplify the
     * eighty percent case with predicates.
     *
     * <p>If the input {@code Iterable} does not contain enough elements satisfying the
     * supplied {@code UnaryPredicate}, we are returned a {@code Collection} with as
     * many matching elements as possible. The following two lines are equivalent:</p>
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstFiveEvens = first(values, 5, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentEvens = Literals.collectionWith(8, 6, 4, 2);
     * </pre>
     * </blockquote>
     * Similarly, if no elements match the supplied {@code UnaryPredicate}, we are returned an
     * empty {@code Collection}:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable(9, 7, 5, 3, 1);
     *   Collection&lt;Integer&gt; firstThreeEvens = first(values, 3, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   firstThreeEvens.isEmpty(); // => true
     * </pre>
     * </blockquote>
     *
     * @param iterable  The {@code Iterable} to search for elements matching the supplied
     *                  {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} that must be satisfied by elements in the
     *                  supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the required number of elements
     *         (or less) from the supplied {@code Iterable} matching the supplied
     *         {@code UnaryPredicate}.
     */
    public static <T> Collection<T> first(
            Iterable<T> iterable,
            int numberOfElementsRequired,
            UnaryPredicate<? super T> predicate) {
        return first(filter(iterable, predicate), numberOfElementsRequired);
    }

    /**
     * Returns an {@code Option} over the last element in the supplied {@code Iterable}.
     * If the {@code Iterable} is empty, {@code None} is returned, otherwise, a
     * {@code Some} is returned over the last element yielded by the {@code Iterable}.
     *
     * <p>This method has a return type of {@code Option} rather than returning the
     * last value directly since, in the case of an empty {@code Iterable}, an
     * exception would have to be thrown using that approach. Instead, the
     * {@code Option} can be queried for whether it contains a value or not,
     * avoiding any exception handling.</p>
     *
     * <p>Since an {@code Option} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the last element from the underlying
     * {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code String} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;String&gt; values = Literals.iterableWith("first", "middle", "last");
     * </pre>
     * </blockquote>
     * The last element in the {@code Iterable} can be obtained as follows:
     * <blockquote>
     * <pre>
     *   Option&lt;String&gt; valueOption = last(values);
     *   String value = valueOption.get(); // => "last"
     * </pre>
     * </blockquote>
     * Similarly, we can handle the empty {@code Iterable} case gracefully:
     * <blockquote>
     * <pre>
     *   Iterable&lt;String&gt; values = Literals.iterable();
     *   Option&lt;String&gt; valueOption = last(values);
     *   String value = valueOption.getOrElse("some string"); // => "some string"
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which the last element is required.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Option} instance representing the last element in the supplied
     *         {@code Iterable}.
     */
    public static <T> Option<T> last(Iterable<? extends T> iterable) {
        return first(slice(iterable, -1, null));
    }

    /**
     * Returns an {@code Option} over the last element in the supplied {@code Iterable}
     * that satisfies the supplied {@code UnaryPredicate}. If the {@code Iterable} is
     * empty, {@code None} is returned, otherwise, a {@code Some} is returned over
     * the last matching value found.
     *
     * <p>This method has a return type of {@code Option} rather than returning the
     * last matching value directly since, in the case of an empty {@code Iterable},
     * an exception would have to be thrown using that approach. Instead, the
     * {@code Option} can be queried for whether it contains a value or not,
     * avoiding any exception handling.</p>
     *
     * <p>Since an {@code Option} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the last matching element from the
     * underlying {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * The last even element in the {@code Iterable} can be obtained as follows:
     * <blockquote>
     * <pre>
     *   Option&lt;Integer&gt; valueOption = last(values, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   Integer value = valueOption.get(); // => 2
     * </pre>
     * </blockquote>
     * Note, we used an anonymous {@code Predicate} instance. The {@code Predicate} interface
     * is equivalent to the {@code UnaryPredicate} interface and exists to simplify the
     * eighty percent case with predicates.
     *
     * <p>Thanks to the {@code Option} returned, we can handle the empty {@code Iterable}
     * case gracefully:</p>
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable();
     *   Option&lt;Integer&gt; valueOption = last(values, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   Integer value = valueOption.getOrElse(10); // => 10
     * </pre>
     * </blockquote>
     * Similarly, if no elements match the supplied {@code UnaryPredicate}, we are returned
     * a {@code None}:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable(9, 7, 5, 3, 1);
     *   Option&lt;Integer&gt; valueOption = last(values, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   valueOption.hasValue(); // => false
     * </pre>
     * </blockquote>
     *
     * @param iterable  The {@code Iterable} to search for an element matching the supplied
     *                  {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} that must be satisfied by an element in the
     *                  supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Option} instance representing the last element in the supplied
     *         {@code Iterable} satisfying the supplied {@code UnaryPredicate}.
     */
    public static <T> Option<T> last(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return last(filter(iterable, predicate));
    }

    /**
     * Returns a {@code Collection} containing the last <em>n</em> elements in the supplied
     * {@code Iterable} where <em>n</em> is given by the supplied integer value. If the
     * {@code Iterable} is empty, an empty {@code Collection} is returned, otherwise,
     * a {@code Collection} containing the last <em>n</em> elements is returned.
     *
     * <p>In the case that the supplied {@code Iterable} does not contain enough
     * elements to satisfy the required number, a {@code Collection} containing
     * as many elements as possible is returned.</p>
     *
     * <p>Since a {@code Collection} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the elements from the underlying
     * {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Using {@code last}, we can obtain the last three elements in the {@code Iterable}.
     * The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; lastThreeValues = last(values, 3);
     *   Collection&lt;Integer&gt; equivalentValues = Literals.collectionWith(3, 2, 1);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} does not contain enough elements, we are returned a
     * {@code Collection} with as many elements as possible. The following two lines are
     * equivalent:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; lastSixValues = last(values, 6);
     *   Collection&lt;Integer&gt; equivalentValues = Literals.collectionWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Similarly, if the input {@code Iterable} contains no elements, an empty
     * {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable();
     *   Collection&lt;Integer&gt; lastThreeElements = last(values, 3);
     *   lastThreeElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which the last <em>n</em> elements
     *                 should be taken.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the required number of elements
     *         (or less) from the supplied {@code Iterable}.
     */
    public static <T> Collection<T> last(
            Iterable<T> iterable,
            int numberOfElementsRequired) {
        if (numberOfElementsRequired < 0) {
            throw new IllegalArgumentException(
                    "Number of elements required cannot be negative");
        }
        if (numberOfElementsRequired == 0) {
            return emptyList();
        }
        return slice(iterable, -numberOfElementsRequired, null);
    }

    /**
     * Returns a {@code Collection} containing the last <em>n</em> elements in the supplied
     * {@code Iterable} that satisfy the supplied {@code UnaryPredicate} where <em>n</em>
     * is given by the supplied integer value. If the {@code Iterable} is empty,
     * an empty {@code Collection} is returned, otherwise, a {@code Collection} containing
     * the last <em>n</em> matching elements is returned.
     *
     * <p>In the case that the supplied {@code Iterable} does not contain enough
     * matching elements to satisfy the required number, a {@code Collection} containing
     * as many elements as possible is returned.</p>
     *
     * <p>Since a {@code Collection} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve matching elements from the underlying
     * {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterableWith(8, 7, 6, 5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Using {@code last}, we can obtain the last three even elements in the {@code Iterable}.
     * The following two expressions are equivalent:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; lastThreeEvens = last(values, 3, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentEvens = Literals.collectionWith(6, 4, 2);
     * </pre>
     * </blockquote>
     * Note, we used an anonymous {@code Predicate} instance. The {@code Predicate} interface
     * is equivalent to the {@code UnaryPredicate} interface and exists to simplify the
     * eighty percent case with predicates.
     *
     * <p>If the input {@code Iterable} does not contain enough elements satisfying the
     * supplied {@code UnaryPredicate}, we are returned a {@code Collection} with as
     * many matching elements as possible. The following two lines are equivalent:</p>
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; lastFiveEvens = last(values, 5, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentEvens = Literals.collectionWith(8, 6, 4, 2);
     * </pre>
     * </blockquote>
     * Similarly, if no elements match the supplied {@code UnaryPredicate}, we are returned an
     * empty {@code Collection}:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable(9, 7, 5, 3, 1);
     *   Collection&lt;Integer&gt; lastThreeEvens = last(values, 3, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   lastThreeEvens.isEmpty(); // => true
     * </pre>
     * </blockquote>
     *
     * @param iterable  The {@code Iterable} to search for elements matching the supplied
     *                  {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} that must be satisfied by elements in the
     *                  supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the required number of elements
     *         (or less) from the supplied {@code Iterable} matching the supplied
     *         {@code UnaryPredicate}.
     */
    public static <T> Collection<T> last(
            Iterable<T> iterable,
            int numberOfElementsRequired,
            UnaryPredicate<? super T> predicate) {
        return last(filter(iterable, predicate), numberOfElementsRequired);
    }

    public static <T> Collection<T> rest(Iterable<T> iterable) {
        return slice(iterable, 1, null);
    }

    /**
     * Takes the first <em>n</em> elements from the supplied {@code Iterable} where <em>n</em>
     * is given by the supplied integer value and returns them in a {@code Collection}. If the
     * {@code Iterable} is empty, an empty {@code Collection} is returned, otherwise,
     * a {@code Collection} containing the first <em>n</em> elements is returned.
     *
     * <p>In the case that the supplied {@code Iterable} does not contain enough
     * elements to satisfy the required number, a {@code Collection} containing
     * as many elements as possible is returned.</p>
     *
     * <p>If the supplied integer value is negative, an {@code IllegalArgumentException} is
     * thrown.</p>
     *
     * <p>Since a {@code Collection} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the elements from the underlying
     * {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Using {@code take}, we can take the first three elements from the {@code Iterable}.
     * The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstThreeElements = take(elements, 3);
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(5, 4, 3);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} does not contain enough elements, we are returned a
     * {@code Collection} with as many elements as possible. The following two lines are
     * equivalent:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstSixElements = take(elements, 6);
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Similarly, if the input {@code Iterable} contains no elements, an empty
     * {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable();
     *   Collection&lt;Integer&gt; firstThreeElements = take(elements, 3);
     *   firstThreeElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which to take <em>n</em> elements.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the required number of elements
     *         (or less) from the supplied {@code Iterable}.
     * @throws IllegalArgumentException if the required number of elements to take
     *                                  is negative.
     */
    public static <T> Collection<T> take(Iterable<T> iterable, int numberToTake) {
        return materialize(Lazily.take(iterable, numberToTake));
    }

    /**
     * Drops the first <em>n</em> elements from the supplied {@code Iterable} where <em>n</em>
     * is given by the supplied integer value and returns a {@code Collection} containing
     * the remaining elements. If the {@code Iterable} is empty, an empty {@code Collection}
     * is returned, otherwise, a {@code Collection} containing the elements that remain after
     * the first <em>n</em> elements have been discarded is returned.
     *
     * <p>In the case that the supplied {@code Iterable} contains less elements than the
     * required number of elements to drop, all elements are dropped and an empty
     * {@code Collection} is returned.</p>
     *
     * <p>If the supplied integer value is negative, an {@code IllegalArgumentException} is
     * thrown.</p>
     *
     * <p>Since a {@code Collection} instance is returned, the element discardal is performed
     * eagerly, i.e., the elements are discarded from the underlying {@code Iterable}
     * immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Using {@code drop}, we can drop the first three elements from the {@code Iterable}.
     * The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; remainingElements = drop(elements, 3);
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(2, 1);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} does not contain enough elements, we are returned
     * an empty {@code Collection}. The following two lines are equivalent:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; remainingElements = drop(elements, 6);
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collection();
     * </pre>
     * </blockquote>
     * Similarly, if the input {@code Iterable} contains no elements, an empty
     * {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable();
     *   Collection&lt;Integer&gt; remainingElements = drop(elements, 3);
     *   remainingElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which to drop <em>n</em> elements
     *                 and return the remainder.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the remaining elements after
     *         the required number of elements have been dropped from the supplied
     *         {@code Iterable}.
     * @throws IllegalArgumentException if the required number of elements to drop
     *                                  is negative.
     */
    public static <T> Collection<T> drop(Iterable<T> iterable, int numberToDrop) {
        return materialize(Lazily.drop(iterable, numberToDrop));
    }

    public static <T> Pair<Collection<T>, Collection<T>> partition(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        Pair<Iterable<T>, Iterable<T>> partition = Lazily.partition(iterable, predicate);
        return tuple(materialize(partition.getFirst()), materialize(partition.getSecond()));
    }

    /**
     * Returns a collection {@code Collection} instance containing batches of elements
     * of the specified size from the supplied {@code Iterable}.
     *
     * <p>In the case that the number of elements in the supplied {@code Iterable}
     * does not evenly divide by the supplied batch size, the last {@code Collection}
     * in the returned {@code Collection} will contain less than the batch size. If
     * the supplied batch size is not positive, an {@code IllegalArgumentException}
     * will be thrown.</p>
     *
     * <p>As an example, the following two {@code Collection} instances
     * are effectively equivalent:
     * <blockquote>
     * <pre>
     *      Collection&lt;Collection&lt;Integer&gt;&gt; batches1 = collectionWith(collectionWith(1, 2, 3), collectionWith(4, 5, 6), collectionWith(7));
     *      Collection&lt;Collection&lt;Integer&gt;&gt; batches2 = batch(collectionWith(1, 2, 3, 4, 5, 6, 7), 3);
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param iterable  The {@code Iterable} to batch into batches of the specified
     *                  number of elements.
     * @param batchSize The number of elements required in each batch in the
     *                  returned {@code Collection}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance of {@code Collection} instances each
     *         containing the required number of elements, bar the last which may
     *         have less dependent on availability.
     * @throws IllegalArgumentException if the required number of elements to take
     *                                  is not positive.
     */
    public static <T> Collection<Collection<T>> batch(Iterable<T> iterable, int batchSize) {
        Collection<Collection<T>> result = new ArrayList<Collection<T>>();
        Iterable<Iterable<T>> batches = Lazily.batch(iterable, batchSize);
        for (Iterable<T> batch : batches) {
            result.add(materialize(batch));
        }
        return result;
    }

    public static void times(
            int numberOfTimes,
            UnaryProcedure<? super Integer> procedure) {
        if (numberOfTimes < 0) {
            throw new IllegalArgumentException(
                    "The number of times to execute the function cannot be less than zero.");
        }
        for (int i = 0; i < numberOfTimes; i++) {
            procedure.execute(i);
        }
    }

    public static void times(int numberOfTimes, Action<? super Integer> action) {
        times(numberOfTimes, actionUnaryProcedure(action));
    }

    /**
     * Takes elements from the supplied {@code Iterable} while the supplied {@code UnaryPredicate}
     * evaluates true for those elements and returns them in a {@code Collection}. Each element
     * taken from the {@code Iterable} is evaluated by the {@code UnaryPredicate} and as soon as
     * an element is found that does not satisfy the {@code UnaryPredicate}, no more elements are taken
     * and the {@code Collection} of satisfactory elements is returned. If the supplied {@code Iterable}
     * is empty, an empty {@code Collection} is returned.
     *
     * <p>Since a {@code Collection} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the sequence of satisfactory elements from the
     * underlying {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Using {@code takeWhile}, we can take elements from the {@code Iterable} while those elements
     * are greater than 2. The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstElementsGreaterThanTwo = takeWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(5, 4, 3);
     * </pre>
     * </blockquote>
     * If the first element retrieved from the {@code Iterable} does not satisfy the supplied
     * {@code UnaryPredicate}, an empty {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(2, 1, 4, 5, 6);
     *   Collection&lt;Integer&gt; firstElementsGreaterThanTwo = takeWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   firstElementsGreaterThanTwo.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * If no elements retrieved from the {@code Iterable} fail to satisfy the supplied
     * {@code Predicate}, a {@code Collection} containing all elements is returned.
     * The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(4, 5, 6, 7, 8);
     *   Collection&lt;Integer&gt; firstElementsGreaterThanTwo = takeWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(4, 5, 6, 7, 8);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} contains no elements, an empty {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable();
     *   Collection&lt;Integer&gt; firstElementsGreaterThanTwo = takeWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   firstElementsGreaterThanTwo.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * Note, we used an anonymous {@code Predicate} instance. The {@code Predicate} interface
     * is equivalent to the {@code UnaryPredicate} interface and exists to simplify the
     * eighty percent case with predicates.
     *
     * @param iterable  The {@code Iterable} from which to take the first sequence of elements
     *                  satisfying the supplied {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} to evaluate each element against whilst
     *                  taking from the supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the first sequence of elements
     *         satisfying the supplied {@code UnaryPredicate}.
     */
    public static <T> Collection<T> takeWhile(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.takeWhile(iterable, predicate));
    }

    /**
     * Takes elements from the supplied {@code Iterable} until the supplied {@code UnaryPredicate}
     * evaluates true for an element and returns them in a {@code Collection}. Each element
     * taken from the {@code Iterable} is evaluated by the {@code UnaryPredicate} and as soon as
     * an element is found that satisfies the {@code UnaryPredicate}, no more elements are taken
     * and the {@code Collection} of elements not satisfying the {@code UnaryPredicate} is returned.
     * If the supplied {@code Iterable} is empty, an empty {@code Collection} is returned.
     *
     * <p>Since a {@code Collection} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the sequence of elements not satisfying the
     * {@code UnaryPredicate} from the underlying {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterableWith(1, 2, 3, 4, 5);
     * </pre>
     * </blockquote>
     * Using {@code takeUntil}, we can take elements from the {@code Iterable} until an element is
     * found that is greater than 2. The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstElementsNotGreaterThanTwo = takeUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(1, 2);
     * </pre>
     * </blockquote>
     * If the first element retrieved from the {@code Iterable} satisfies the supplied
     * {@code UnaryPredicate}, an empty {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(4, 5, 6, 7, 8);
     *   Collection&lt;Integer&gt; firstElementsNotGreaterThanTwo = takeUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   firstElementsNotGreaterThanTwo.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * If no elements retrieved from the {@code Iterable} satisfy the supplied {@code Predicate},
     * a {@code Collection} containing all elements is returned. The following two lines are
     * equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(-1, 0, 1, 2);
     *   Collection&lt;Integer&gt; firstElementsNotGreaterThanTwo = takeUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(-1, 0, 1, 2);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} contains no elements, an empty {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable();
     *   Collection&lt;Integer&gt; firstElementsNotGreaterThanTwo = takeUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   firstElementsNotGreaterThanTwo.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * Note, we used an anonymous {@code Predicate} instance. The {@code Predicate} interface
     * is equivalent to the {@code UnaryPredicate} interface and exists to simplify the
     * eighty percent case with predicates.
     *
     * @param iterable  The {@code Iterable} from which to take a sequence of elements
     *                  until the supplied {@code UnaryPredicate} is satisfied.
     * @param predicate A {@code UnaryPredicate} to evaluate each element against whilst
     *                  taking from the supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the sequence of elements
     *         taken from the supplied {@code Iterable} up until the supplied {@code UnaryPredicate}
     *         is satisfied.
     */
    public static <T> Collection<T> takeUntil(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.takeUntil(iterable, predicate));
    }

    /**
     * Drops elements from the supplied {@code Iterable} while the supplied {@code UnaryPredicate}
     * evaluates true for those elements and returns a {@code Collection} containing
     * the remaining elements. Each element retrieved from the {@code Iterable} is evaluated by the
     * {@code UnaryPredicate} and as soon as an element is found that does not satisfy the
     * {@code UnaryPredicate}, a {@code Collection} with that element as the head and the remaining
     * elements in the {@code Iterable} as the tail is returned. If the supplied {@code Iterable}
     * is empty, an empty {@code Collection} is returned.
     *
     * <p>Since a {@code Collection} instance is returned, the element discardal is performed
     * eagerly, i.e., the elements are discarded from the underlying {@code Iterable}
     * immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Using {@code dropWhile}, we can drop elements from the {@code Iterable} while those elements
     * are greater than 2. The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; remainingElements = dropWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(2, 1);
     * </pre>
     * </blockquote>
     * If the first element retrieved from the {@code Iterable} does not satisfy the supplied
     * {@code UnaryPredicate}, no elements are dropped and a {@code Collection} containing
     * all elements from the input {@code Iterable} is returned. The following two lines are
     * equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(2, 1, 4, 5, 3);
     *   Collection&lt;Integer&gt; remainingElements = dropWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(2, 1, 4, 5, 3);
     * </pre>
     * </blockquote>
     * If all elements retrieved from the {@code Iterable} satisfy the supplied
     * {@code UnaryPredicate}, all elements are dropped and an empty {@code Collection} is returned.
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(4, 5, 6, 7, 8);
     *   Collection&lt;Integer&gt; remainingElements = dropWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   remainingElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * Similarly, if the input {@code Iterable} contains no elements, an empty {@code Collection}
     * is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable();
     *   Collection&lt;Integer&gt; remainingElements = dropWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   remainingElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * Note, we used an anonymous {@code Predicate} instance. The {@code Predicate} interface
     * is equivalent to the {@code UnaryPredicate} interface and exists to simplify the
     * eighty percent case with predicates.
     *
     * @param iterable  The {@code Iterable} from which to drop the first sequence of elements
     *                  satisfying the supplied {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} to evaluate each element against whilst
     *                  dropping from the supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the remaining elements from the supplied
     *         {@code Iterable} after dropping those elements that satisfy the supplied
     *         {@code UnaryPredicate}.
     */
    public static <T> Collection<T> dropWhile(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.dropWhile(iterable, predicate));
    }

    /**
     * Drops elements from the supplied {@code Iterable} until the supplied {@code UnaryPredicate}
     * evaluates true for an element and returns a {@code Collection} containing the remaining
     * elements. Each element retrieved from the {@code Iterable} is evaluated by the
     * {@code UnaryPredicate} and as soon as an element is found that satisfies the
     * {@code UnaryPredicate}, a {@code Collection} with that element as the head and the remaining
     * elements in the {@code Iterable} as the tail is returned. If the supplied {@code Iterable}
     * is empty, an empty {@code Collection} is returned.
     *
     * <p>Since a {@code Collection} instance is returned, the element discardal is performed
     * eagerly, i.e., the elements are discarded from the underlying {@code Iterable}
     * immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterableWith(1, 2, 3, 4, 5);
     * </pre>
     * </blockquote>
     * Using {@code dropUntil}, we can drop elements from the {@code Iterable} until an element is
     * found that is greater than 2. The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; remainingElements = dropUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(3, 4, 5);
     * </pre>
     * </blockquote>
     * If the first element retrieved from the {@code Iterable} satisfies the supplied
     * {@code UnaryPredicate}, no elements are dropped and a {@code Collection} containing all
     * elements from the input {@code Iterable} is returned. The following two lines
     * are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(4, 5, 6, 7, 8);
     *   Collection&lt;Integer&gt; remainingElements = dropUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(4, 5, 6, 7, 8);
     * </pre>
     * </blockquote>
     * If no elements retrieved from the {@code Iterable} satisfy the supplied {@code UnaryPredicate},
     * all elements are dropped and an empty {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(-1, 0, 1, 2);
     *   Collection&lt;Integer&gt; remainingElements = dropUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   remainingElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} contains no elements, an empty {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable();
     *   Collection&lt;Integer&gt; remainingElements = dropUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   remainingElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * Note, we used an anonymous {@code Predicate} instance. The {@code Predicate} interface
     * is equivalent to the {@code UnaryPredicate} interface and exists to simplify the
     * eighty percent case with predicates.
     *
     * @param iterable  The {@code Iterable} from which to drop a sequence of elements
     *                  until the supplied {@code UnaryPredicate} is satisfied.
     * @param predicate A {@code UnaryPredicate} to evaluate each element against whilst
     *                  dropping from the supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the remaining elements from the supplied
     *         {@code Iterable} after dropping elements until the supplied {@code UnaryPredicate}
     *         is satisfied.
     */
    public static <T> Collection<T> dropUntil(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return materialize(Lazily.dropUntil(iterable, predicate));
    }

    public static <T> Collection<T> slice(
            Iterable<T> iterable,
            Integer start,
            Integer stop,
            Integer step) {
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

    public static <T> Collection<T> slice(
            Iterable<T> iterable,
            Integer start,
            Integer stop) {
        return slice(iterable, start, stop, 1);
    }

    public static <T> Collection<T> repeat(
            Iterable<T> iterable,
            int numberOfTimesToRepeat) {
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
