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
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.functors.adapters.ActionUnaryProcedureAdapter.actionUnaryProcedure;
import static org.javafunk.funk.functors.adapters.EquivalenceBinaryPredicateAdapter.equivalenceBinaryPredicate;
import static org.javafunk.funk.functors.adapters.IndexerUnaryFunctionAdapter.indexerUnaryFunction;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;
import static org.javafunk.funk.functors.adapters.ReducerBinaryFunctionAdapter.reducerBinaryFunction;

/**
 * @since 1.0
 */
public class Eagerly {
    private Eagerly() {
    }

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
            Iterable<? extends T> iterable,
            BinaryFunction<T, ? super T, T> function) {
        Iterator<? extends T> iterator = iterable.iterator();
        T firstElement = iterator.next();
        Iterable<? extends T> restOfElements = asIterable(iterator);
        return reduce(restOfElements, firstElement, function);
    }

    public static <T> T reduce(
            Iterable<? extends T> iterable,
            Reducer<? super T, T> reducer) {
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
     * <p>This overload of {@link #map(Iterable, UnaryFunction)} is provided to allow a
     * {@code Mapper} to be used in place of a {@code UnaryFunction} to enhance
     * readability and better express intent. The contract of the function
     * is identical to that of the {@code UnaryFunction} version of {@code map}.</p>
     *
     * <p>For example usage and further documentation, see
     * {@link #map(Iterable, UnaryFunction)}.</p>
     *
     * @param iterable The {@code Iterable} of elements to be mapped.
     * @param mapper   A {@code Mapper} which, given an element from the input iterable,
     *                 returns that element mapped to a new value potentially of a different type.
     * @param <S>      The type of the input elements, i.e., the elements to map.
     * @param <T>      The type of the output elements, i.e., the mapped elements.
     * @return A {@code Collection} containing each instance of {@code S} from the input
     *         {@code Iterable} mapped to an instance of {@code T}.
     * @see #map(Iterable, UnaryFunction)
     */
    public static <S, T> Collection<T> map(Iterable<S> iterable, Mapper<? super S, T> mapper) {
        return map(iterable, mapperUnaryFunction(mapper));
    }

    /**
     * Zips the elements from the two supplied {@code Iterable} instances
     * into a tuple of size two. The returned {@code Iterable} contains
     * a {@code Pair} instance for each element in the shortest supplied
     * {@code Iterable} with an element from the first supplied {@code Iterable}
     * in the first slot and an element from the second supplied {@code Iterable}
     * in the second slot.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the zipping is performed eagerly,
     * i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Pair} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is empty.</p>
     *
     * <h4>Example Usage:</h4>
     * Given an {@code Iterable} of {@code Integer} instances representing the
     * first ten values in the sequence of natural numbers and an {@code Iterable}
     * of {@code String} instances representing the textual equivalents of those
     * numbers as follows:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Integer&gt; numbers = Literals.iterableWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
     *     Iterable&lt;String&gt; numberNames = Literals.iterableWith(
     *             "one", "two", "three", "four", "five",
     *             "six", "seven", "eight", "nine", "ten",
     *             "eleven", "twelve");
     * </pre>
     * </blockquote>
     * a {@code Collection} containing {@code Pair}s with the number associated
     * to the textual name can be obtained as follows:
     * <blockquote>
     * <pre>
     *     Collection&ltPair&ltInteger, String&gt;&gt; associations = zip(numbers, numberNames);
     * </pre>
     * </blockquote>
     * This is effectively equivalent to the following:
     * <blockquote>
     * <pre>
     *     Collection&lt;Pair&lt;Integer, String&gt;&gt; associations = Literals.collectionWith(
     *             pair(1, "one"), pair(2, "two"), pair(3, "three"), pair(4, "four"), pair(5, "five"),
     *             pair(6, "six"), pair(7, "seven"), pair(8, "eight"), pair(9, "nine"), pair(10, "ten"));
     * </pre>
     * </blockquote>
     *
     * @param first  The first {@code Iterable} from which to construct a zip.
     * @param second The second {@code Iterable} from which to construct a zip.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @return A {@code Collection} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
    public static <R, S> Collection<Pair<R, S>> zip(
            Iterable<R> first,
            Iterable<S> second) {
        return materialize(Lazily.zip(first, second));
    }

    /**
     * Zips the elements from the three supplied {@code Iterable} instances
     * into a tuple of size three. The returned {@code Iterable} contains
     * a {@code Triple} instance for each element in the shortest supplied
     * {@code Iterable} with an element from the first supplied {@code Iterable}
     * in the first slot, an element from the second supplied {@code Iterable}
     * in the second slot and an element from the third supplied {@code Iterable}
     * in the third slot.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the zipping is performed eagerly,
     * i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Triple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is empty.</p>
     *
     * <p>This overload of {@code zip} is provided to allow three {@code Iterable}
     * instances to be zipped. For equivalent example usage for the two
     * {@code Iterable} case, see {@link #zip(Iterable, Iterable)}.</p>
     *
     * @param first  The first {@code Iterable} from which to construct a zip.
     * @param second The second {@code Iterable} from which to construct a zip.
     * @param third  The third {@code Iterable} from which to construct a zip.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @param <T>    The type of the elements in the third {@code Iterable}.
     * @return A {@code Collection} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
    public static <R, S, T> Collection<Triple<R, S, T>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third) {
        return materialize(Lazily.zip(first, second, third));
    }

    /**
     * Zips the elements from the four supplied {@code Iterable} instances
     * into a tuple of size four. The returned {@code Iterable} contains
     * a {@code Quadruple} instance for each element in the shortest supplied
     * {@code Iterable} with an element from the first supplied {@code Iterable}
     * in the first slot, an element from the second supplied {@code Iterable}
     * in the second slot and an element from the third supplied {@code Iterable}
     * in the third slot and so on.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the zipping is performed eagerly,
     * i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Quadruple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is empty.</p>
     *
     * <p>This overload of {@code zip} is provided to allow four {@code Iterable}
     * instances to be zipped. For equivalent example usage for the two
     * {@code Iterable} case, see {@link #zip(Iterable, Iterable)}.</p>
     *
     * @param first  The first {@code Iterable} from which to construct a zip.
     * @param second The second {@code Iterable} from which to construct a zip.
     * @param third  The third {@code Iterable} from which to construct a zip.
     * @param fourth The fourth {@code Iterable} from which to construct a zip.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @param <T>    The type of the elements in the third {@code Iterable}.
     * @param <U>    The type of the elements in the fourth {@code Iterable}.
     * @return A {@code Collection} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
    public static <R, S, T, U> Collection<Quadruple<R, S, T, U>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth) {
        return materialize(Lazily.zip(first, second, third, fourth));
    }

    /**
     * Zips the elements from the five supplied {@code Iterable} instances
     * into a tuple of size five. The returned {@code Iterable} contains
     * a {@code Quintuple} instance for each element in the shortest supplied
     * {@code Iterable} with an element from the first supplied {@code Iterable}
     * in the first slot, an element from the second supplied {@code Iterable}
     * in the second slot and an element from the third supplied {@code Iterable}
     * in the third slot and so on.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the zipping is performed eagerly,
     * i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Quintuple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is empty.</p>
     *
     * <p>This overload of {@code zip} is provided to allow five {@code Iterable}
     * instances to be zipped. For equivalent example usage for the two
     * {@code Iterable} case, see {@link #zip(Iterable, Iterable)}.</p>
     *
     * @param first  The first {@code Iterable} from which to construct a zip.
     * @param second The second {@code Iterable} from which to construct a zip.
     * @param third  The third {@code Iterable} from which to construct a zip.
     * @param fourth The fourth {@code Iterable} from which to construct a zip.
     * @param fifth  The fifth {@code Iterable} from which to construct a zip.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @param <T>    The type of the elements in the third {@code Iterable}.
     * @param <U>    The type of the elements in the fourth {@code Iterable}.
     * @param <V>    The type of the elements in the fifth {@code Iterable}.
     * @return A {@code Collection} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
    public static <R, S, T, U, V> Collection<Quintuple<R, S, T, U, V>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth) {
        return materialize(Lazily.zip(first, second, third, fourth, fifth));
    }

    /**
     * Zips the elements from the six supplied {@code Iterable} instances
     * into a tuple of size six. The returned {@code Iterable} contains
     * a {@code Sextuple} instance for each element in the shortest supplied
     * {@code Iterable} with an element from the first supplied {@code Iterable}
     * in the first slot, an element from the second supplied {@code Iterable}
     * in the second slot and an element from the third supplied {@code Iterable}
     * in the third slot and so on.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the zipping is performed eagerly,
     * i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Sextuple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is empty.</p>
     *
     * <p>This overload of {@code zip} is provided to allow six {@code Iterable}
     * instances to be zipped. For equivalent example usage for the two
     * {@code Iterable} case, see {@link #zip(Iterable, Iterable)}.</p>
     *
     * @param first  The first {@code Iterable} from which to construct a zip.
     * @param second The second {@code Iterable} from which to construct a zip.
     * @param third  The third {@code Iterable} from which to construct a zip.
     * @param fourth The fourth {@code Iterable} from which to construct a zip.
     * @param fifth  The fifth {@code Iterable} from which to construct a zip.
     * @param sixth  The sixth {@code Iterable} from which to construct a zip.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @param <T>    The type of the elements in the third {@code Iterable}.
     * @param <U>    The type of the elements in the fourth {@code Iterable}.
     * @param <V>    The type of the elements in the fifth {@code Iterable}.
     * @param <W>    The type of the elements in the sixth {@code Iterable}.
     * @return A {@code Collection} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
    public static <R, S, T, U, V, W> Collection<Sextuple<R, S, T, U, V, W>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth) {
        return materialize(Lazily.zip(first, second, third, fourth, fifth, sixth));
    }

    /**
     * Zips the elements from the seven supplied {@code Iterable} instances
     * into a tuple of size seven. The returned {@code Iterable} contains
     * a {@code Septuple} instance for each element in the shortest supplied
     * {@code Iterable} with an element from the first supplied {@code Iterable}
     * in the first slot, an element from the second supplied {@code Iterable}
     * in the second slot and an element from the third supplied {@code Iterable}
     * in the third slot and so on.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the zipping is performed eagerly,
     * i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Septuple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is empty.</p>
     *
     * <p>This overload of {@code zip} is provided to allow seven {@code Iterable}
     * instances to be zipped. For equivalent example usage for the two
     * {@code Iterable} case, see {@link #zip(Iterable, Iterable)}.</p>
     *
     * @param first   The first {@code Iterable} from which to construct a zip.
     * @param second  The second {@code Iterable} from which to construct a zip.
     * @param third   The third {@code Iterable} from which to construct a zip.
     * @param fourth  The fourth {@code Iterable} from which to construct a zip.
     * @param fifth   The fifth {@code Iterable} from which to construct a zip.
     * @param sixth   The sixth {@code Iterable} from which to construct a zip.
     * @param seventh The seventh {@code Iterable} from which to construct a zip.
     * @param <R>     The type of the elements in the first {@code Iterable}.
     * @param <S>     The type of the elements in the second {@code Iterable}.
     * @param <T>     The type of the elements in the third {@code Iterable}.
     * @param <U>     The type of the elements in the fourth {@code Iterable}.
     * @param <V>     The type of the elements in the fifth {@code Iterable}.
     * @param <W>     The type of the elements in the sixth {@code Iterable}.
     * @param <X>     The type of the elements in the seventh {@code Iterable}.
     * @return A {@code Collection} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
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

    /**
     * Zips the elements from the eight supplied {@code Iterable} instances
     * into a tuple of size eight. The returned {@code Iterable} contains
     * a {@code Octuple} instance for each element in the shortest supplied
     * {@code Iterable} with an element from the first supplied {@code Iterable}
     * in the first slot, an element from the second supplied {@code Iterable}
     * in the second slot and an element from the third supplied {@code Iterable}
     * in the third slot and so on.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the zipping is performed eagerly,
     * i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Octuple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is empty.</p>
     *
     * <p>This overload of {@code zip} is provided to allow eight {@code Iterable}
     * instances to be zipped. For equivalent example usage for the two
     * {@code Iterable} case, see {@link #zip(Iterable, Iterable)}.</p>
     *
     * @param first   The first {@code Iterable} from which to construct a zip.
     * @param second  The second {@code Iterable} from which to construct a zip.
     * @param third   The third {@code Iterable} from which to construct a zip.
     * @param fourth  The fourth {@code Iterable} from which to construct a zip.
     * @param fifth   The fifth {@code Iterable} from which to construct a zip.
     * @param sixth   The sixth {@code Iterable} from which to construct a zip.
     * @param seventh The seventh {@code Iterable} from which to construct a zip.
     * @param eighth  The eighth {@code Iterable} from which to construct a zip.
     * @param <R>     The type of the elements in the first {@code Iterable}.
     * @param <S>     The type of the elements in the second {@code Iterable}.
     * @param <T>     The type of the elements in the third {@code Iterable}.
     * @param <U>     The type of the elements in the fourth {@code Iterable}.
     * @param <V>     The type of the elements in the fifth {@code Iterable}.
     * @param <W>     The type of the elements in the sixth {@code Iterable}.
     * @param <X>     The type of the elements in the seventh {@code Iterable}.
     * @param <Y>     The type of the elements in the eighth {@code Iterable}.
     * @return A {@code Collection} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
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

    /**
     * Zips the elements from the nine supplied {@code Iterable} instances
     * into a tuple of size nine. The returned {@code Iterable} contains
     * a {@code Nonuple} instance for each element in the shortest supplied
     * {@code Iterable} with an element from the first supplied {@code Iterable}
     * in the first slot, an element from the second supplied {@code Iterable}
     * in the second slot and an element from the third supplied {@code Iterable}
     * in the third slot and so on.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the zipping is performed eagerly,
     * i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Nonuple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is empty.</p>
     *
     * <p>This overload of {@code zip} is provided to allow nine {@code Iterable}
     * instances to be zipped. For equivalent example usage for the two
     * {@code Iterable} case, see {@link #zip(Iterable, Iterable)}.</p>
     *
     * @param first   The first {@code Iterable} from which to construct a zip.
     * @param second  The second {@code Iterable} from which to construct a zip.
     * @param third   The third {@code Iterable} from which to construct a zip.
     * @param fourth  The fourth {@code Iterable} from which to construct a zip.
     * @param fifth   The fifth {@code Iterable} from which to construct a zip.
     * @param sixth   The sixth {@code Iterable} from which to construct a zip.
     * @param seventh The seventh {@code Iterable} from which to construct a zip.
     * @param eighth  The eighth {@code Iterable} from which to construct a zip.
     * @param ninth   The ninth {@code Iterable} from which to construct a zip.
     * @param <R>     The type of the elements in the first {@code Iterable}.
     * @param <S>     The type of the elements in the second {@code Iterable}.
     * @param <T>     The type of the elements in the third {@code Iterable}.
     * @param <U>     The type of the elements in the fourth {@code Iterable}.
     * @param <V>     The type of the elements in the fifth {@code Iterable}.
     * @param <W>     The type of the elements in the sixth {@code Iterable}.
     * @param <X>     The type of the elements in the seventh {@code Iterable}.
     * @param <Y>     The type of the elements in the eighth {@code Iterable}.
     * @param <Z>     The type of the elements in the ninth {@code Iterable}.
     * @return A {@code Collection} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
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

    /**
     * Zips the elements from all {@code Iterable} instances in the supplied
     * {@code Iterable} into a {@code Collection} of {@code Collections}s with each having
     * as many elements as there were {@code Iterable} instances in the input
     * {@code Iterable}. The returned {@code Collection} contains a {@code Collection}
     * for each element in the shortest supplied {@code Iterable} with an element from
     * the first supplied {@code Iterable} in the first slot, an element from the second
     * supplied {@code Iterable} in the second slot and so on.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the zipping is performed eagerly,
     * i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Collection} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is empty.</p>
     *
     * <p>Note that this overload of {@code zip} does not preserve type information
     * and so the returned {@code Collection} will contain {@code Collection} instances
     * over the wildcard type {@code ?}. If the number of {@code Iterable} instances
     * to be zipped is less than ten, use the explicit arities of {@code zip}.</p>
     *
     * <h4>Example Usage:</h4>
     * Given three {@code Iterable} instances of varying types:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Integer&gt; first = iterableWith(1, 2, 3, 4, 5);
     *     Iterable&lt;String&gt; second = iterableWith("first", "second", "third");
     *     Iterable&lt;Boolean&gt; third = iterableWith(false, true, false, true);
     * </pre>
     * </blockquote>
     * we can zip them into a {@code Collection} of {@code Collection} instances as follows:
     * <blockquote>
     * <pre>
     *     Iterable&ltIterable&lt?&gt;&gt; iterables = Literals.&lt;Iterable&lt?&gt;&gt;iterableWith(first, second, third);
     *     Collection&ltCollection&lt;?&gt;&gt; zippedResult = zip(iterables);
     * </pre>
     * </blockquote>
     * This is effectively equivalent to the following:
     * <blockquote>
     * <pre>
     *     Collection&lt;Collection&lt;?&gt;&gt; expectedOutput = Literals.&lt;Collection&lt;?&gt;&gt;collectionWith(
     *             collectionWith(1, "first", false),
     *             collectionWith(2, "second", true),
     *             collectionWith(3, "third", false));
     * </pre>
     * </blockquote>
     *
     * @param iterables An {@code Iterable} of {@code Iterable} instances to be zipped.
     * @return A {@code Collection} of {@code Collection} instances representing the
     *         zipped contents of the supplied {@code Iterable} of {@code Iterable}s.
     */
    public static Collection<Collection<?>> zip(Iterable<? extends Iterable<?>> iterables) {
        return Eagerly.map(Lazily.zip(iterables), new Mapper<Iterable<?>, Collection<?>>() {
            @Override public Collection<?> map(Iterable<?> iterable) {
                return collectionFrom(iterable);
            }
        });
    }

    /**
     * Takes the cartesian product of the two supplied {@code Iterable}
     * instances generating a {@code Collection} of tuples of size two. The
     * returned {@code Collection} will contain {@code Pair} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable} and the second slot is occupied by an element from
     * the second supplied {@code Iterable}. The {@code Collection} will
     * effectively contain a {@code Pair} for each possible selection of
     * elements for the slots from each of the corresponding {@code Iterable}
     * instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the cartesian product is taken
     * eagerly, i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Pair} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is also empty.</p>
     *
     * <h4>Example Usage:</h4>
     * Given an {@code Iterable} of {@code Name} instances and an {@code Iterable}
     * of {@code Location} instances as follows:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Name&gt; names = Literals.iterableWith(name("Joe"), name("Tim"), name("Laura"));
     *     Iterable&lt;Location&gt; locations = Literals.iterableWith(location("London"), location("Berlin"));
     * </pre>
     * </blockquote>
     * all possible combinations of names to locations can be obtained as follows:
     * <blockquote>
     * <pre>
     *     Collection&lt;Pair&lt;Name, Location&gt;&gt; mappings = cartesianProduct(names, locations);
     * </pre>
     * </blockquote>
     * This is effectively equivalent to the following:
     * <blockquote>
     * <pre>
     *     Collection&lt;Pair&lt;Name, Location&gt;&gt; equivalent = collectionWith(
     *             tuple(name("Joe"), location("London")), tuple(name("Tim"), location("London")), tuple(name("Laura"), location("London")),
     *             tuple(name("Joe"), location("Berlin")), tuple(name("Tim"), location("Berlin")), tuple(name("Laura"), location("Berlin")));
     * </pre>
     * </blockquote>
     *
     * @param first  The first {@code Iterable} from which to form a cartesian product.
     * @param second The second {@code Iterable} from which to form a cartesian product.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @return A {@code Collection} containing the cartesian product of all elements
     *         in the supplied {@code Iterable} instances.
     */
    public static <R, S> Collection<Pair<R, S>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second) {
        return materialize(Lazily.cartesianProduct(first, second));
    }

    /**
     * Takes the cartesian product of the three supplied {@code Iterable}
     * instances generating a {@code Collection} of tuples of size three. The
     * returned {@code Collection} will contain {@code Triple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable} and the third slot is occupied by
     * an element from the third supplied {@code Iterable}. The {@code Collection}
     * will effectively contain a {@code Triple} for each possible selection of
     * elements for the slots from each of the corresponding {@code Iterable}
     * instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the cartesian product is taken
     * eagerly, i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Triple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is also empty.</p>
     *
     * <p>This overload of {@code cartesianProduct} is provided to allow the cartesian
     * product to be taken for three {@code Iterable} instances. For equivalent example
     * usage for the two {@code Iterable} case, see
     * {@link #cartesianProduct(Iterable, Iterable)}.</p>
     *
     * @param first  The first {@code Iterable} from which to form a cartesian product.
     * @param second The second {@code Iterable} from which to form a cartesian product.
     * @param third  The third {@code Iterable} from which to form a cartesian product.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @param <T>    The type of the elements in the third {@code Iterable}.
     * @return A {@code Collection} containing the cartesian product of all elements
     *         in the supplied {@code Iterable} instances.
     */
    public static <R, S, T> Collection<Triple<R, S, T>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third) {
        return materialize(Lazily.cartesianProduct(first, second, third));
    }

    /**
     * Takes the cartesian product of the four supplied {@code Iterable}
     * instances generating a {@code Collection} of tuples of size four. The
     * returned {@code Collection} will contain {@code Quadruple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable} and the third slot is occupied by
     * an element from the third supplied {@code Iterable}. The {@code Collection}
     * will effectively contain a {@code Quadruple} for each possible selection of
     * elements for the slots from each of the corresponding {@code Iterable}
     * instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the cartesian product is taken
     * eagerly, i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Quadruple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is also empty.</p>
     *
     * <p>This overload of {@code cartesianProduct} is provided to allow the cartesian
     * product to be taken for four {@code Iterable} instances. For equivalent example
     * usage for the two {@code Iterable} case, see
     * {@link #cartesianProduct(Iterable, Iterable)}.</p>
     *
     * @param first  The first {@code Iterable} from which to form a cartesian product.
     * @param second The second {@code Iterable} from which to form a cartesian product.
     * @param third  The third {@code Iterable} from which to form a cartesian product.
     * @param fourth The fourth {@code Iterable} from which to form a cartesian product.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @param <T>    The type of the elements in the third {@code Iterable}.
     * @param <U>    The type of the elements in the fourth {@code Iterable}.
     * @return A {@code Collection} containing the cartesian product of all elements
     *         in the supplied {@code Iterable} instances.
     */
    public static <R, S, T, U> Collection<Quadruple<R, S, T, U>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth) {
        return materialize(Lazily.cartesianProduct(first, second, third, fourth));
    }

    /**
     * Takes the cartesian product of the five supplied {@code Iterable}
     * instances generating a {@code Collection} of tuples of size five. The
     * returned {@code Collection} will contain {@code Quintuple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable} and the third slot is occupied by
     * an element from the third supplied {@code Iterable}. The {@code Collection}
     * will effectively contain a {@code Quintuple} for each possible selection of
     * elements for the slots from each of the corresponding {@code Iterable}
     * instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the cartesian product is taken
     * eagerly, i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Quintuple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is also empty.</p>
     *
     * <p>This overload of {@code cartesianProduct} is provided to allow the cartesian
     * product to be taken for five {@code Iterable} instances. For equivalent example
     * usage for the two {@code Iterable} case, see
     * {@link #cartesianProduct(Iterable, Iterable)}.</p>
     *
     * @param first  The first {@code Iterable} from which to form a cartesian product.
     * @param second The second {@code Iterable} from which to form a cartesian product.
     * @param third  The third {@code Iterable} from which to form a cartesian product.
     * @param fourth The fourth {@code Iterable} from which to form a cartesian product.
     * @param fifth  The fifth {@code Iterable} from which to form a cartesian product.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @param <T>    The type of the elements in the third {@code Iterable}.
     * @param <U>    The type of the elements in the fourth {@code Iterable}.
     * @param <V>    The type of the elements in the fifth {@code Iterable}.
     * @return A {@code Collection} containing the cartesian product of all elements
     *         in the supplied {@code Iterable} instances.
     */
    public static <R, S, T, U, V> Collection<Quintuple<R, S, T, U, V>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth) {
        return materialize(Lazily.cartesianProduct(first, second, third, fourth, fifth));
    }

    /**
     * Takes the cartesian product of the six supplied {@code Iterable}
     * instances generating a {@code Collection} of tuples of size six. The
     * returned {@code Collection} will contain {@code Sextuple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable} and the third slot is occupied by
     * an element from the third supplied {@code Iterable}. The {@code Collection}
     * will effectively contain a {@code Sextuple} for each possible selection of
     * elements for the slots from each of the corresponding {@code Iterable}
     * instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the cartesian product is taken
     * eagerly, i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Sextuple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is also empty.</p>
     *
     * <p>This overload of {@code cartesianProduct} is provided to allow the cartesian
     * product to be taken for six {@code Iterable} instances. For equivalent example
     * usage for the two {@code Iterable} case, see
     * {@link #cartesianProduct(Iterable, Iterable)}.</p>
     *
     * @param first  The first {@code Iterable} from which to form a cartesian product.
     * @param second The second {@code Iterable} from which to form a cartesian product.
     * @param third  The third {@code Iterable} from which to form a cartesian product.
     * @param fourth The fourth {@code Iterable} from which to form a cartesian product.
     * @param fifth  The fifth {@code Iterable} from which to form a cartesian product.
     * @param sixth  The sixth {@code Iterable} from which to form a cartesian product.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @param <T>    The type of the elements in the third {@code Iterable}.
     * @param <U>    The type of the elements in the fourth {@code Iterable}.
     * @param <V>    The type of the elements in the fifth {@code Iterable}.
     * @param <W>    The type of the elements in the sixth {@code Iterable}.
     * @return A {@code Collection} containing the cartesian product of all elements
     *         in the supplied {@code Iterable} instances.
     */
    public static <R, S, T, U, V, W> Collection<Sextuple<R, S, T, U, V, W>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth) {
        return materialize(Lazily.cartesianProduct(first, second, third, fourth, fifth, sixth));
    }

    /**
     * Takes the cartesian product of the seven supplied {@code Iterable}
     * instances generating a {@code Collection} of tuples of size seven. The
     * returned {@code Collection} will contain {@code Septuple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable} and the third slot is occupied by
     * an element from the third supplied {@code Iterable}. The {@code Collection}
     * will effectively contain a {@code Septuple} for each possible selection of
     * elements for the slots from each of the corresponding {@code Iterable}
     * instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the cartesian product is taken
     * eagerly, i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Septuple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is also empty.</p>
     *
     * <p>This overload of {@code cartesianProduct} is provided to allow the cartesian
     * product to be taken for seven {@code Iterable} instances. For equivalent example
     * usage for the two {@code Iterable} case, see
     * {@link #cartesianProduct(Iterable, Iterable)}.</p>
     *
     * @param first   The first {@code Iterable} from which to form a cartesian product.
     * @param second  The second {@code Iterable} from which to form a cartesian product.
     * @param third   The third {@code Iterable} from which to form a cartesian product.
     * @param fourth  The fourth {@code Iterable} from which to form a cartesian product.
     * @param fifth   The fifth {@code Iterable} from which to form a cartesian product.
     * @param sixth   The sixth {@code Iterable} from which to form a cartesian product.
     * @param seventh The seventh {@code Iterable} from which to form a cartesian product.
     * @param <R>     The type of the elements in the first {@code Iterable}.
     * @param <S>     The type of the elements in the second {@code Iterable}.
     * @param <T>     The type of the elements in the third {@code Iterable}.
     * @param <U>     The type of the elements in the fourth {@code Iterable}.
     * @param <V>     The type of the elements in the fifth {@code Iterable}.
     * @param <W>     The type of the elements in the sixth {@code Iterable}.
     * @param <X>     The type of the elements in the seventh {@code Iterable}.
     * @return A {@code Collection} containing the cartesian product of all elements
     *         in the supplied {@code Iterable} instances.
     */
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

    /**
     * Takes the cartesian product of the eight supplied {@code Iterable}
     * instances generating a {@code Collection} of tuples of size eight. The
     * returned {@code Collection} will contain {@code Octuple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable} and the third slot is occupied by
     * an element from the third supplied {@code Iterable}. The {@code Collection}
     * will effectively contain a {@code Octuple} for each possible selection of
     * elements for the slots from each of the corresponding {@code Iterable}
     * instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the cartesian product is taken
     * eagerly, i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Octuple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is also empty.</p>
     *
     * <p>This overload of {@code cartesianProduct} is provided to allow the cartesian
     * product to be taken for eight {@code Iterable} instances. For equivalent example
     * usage for the two {@code Iterable} case, see
     * {@link #cartesianProduct(Iterable, Iterable)}.</p>
     *
     * @param first   The first {@code Iterable} from which to form a cartesian product.
     * @param second  The second {@code Iterable} from which to form a cartesian product.
     * @param third   The third {@code Iterable} from which to form a cartesian product.
     * @param fourth  The fourth {@code Iterable} from which to form a cartesian product.
     * @param fifth   The fifth {@code Iterable} from which to form a cartesian product.
     * @param sixth   The sixth {@code Iterable} from which to form a cartesian product.
     * @param seventh The seventh {@code Iterable} from which to form a cartesian product.
     * @param eighth  The eighth {@code Iterable} from which to form a cartesian product.
     * @param <R>     The type of the elements in the first {@code Iterable}.
     * @param <S>     The type of the elements in the second {@code Iterable}.
     * @param <T>     The type of the elements in the third {@code Iterable}.
     * @param <U>     The type of the elements in the fourth {@code Iterable}.
     * @param <V>     The type of the elements in the fifth {@code Iterable}.
     * @param <W>     The type of the elements in the sixth {@code Iterable}.
     * @param <X>     The type of the elements in the seventh {@code Iterable}.
     * @param <Y>     The type of the elements in the eighth {@code Iterable}.
     * @return A {@code Collection} containing the cartesian product of all elements
     *         in the supplied {@code Iterable} instances.
     */
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

    /**
     * Takes the cartesian product of the nine supplied {@code Iterable}
     * instances generating a {@code Collection} of tuples of size nine. The
     * returned {@code Collection} will contain {@code Nonuple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable} and the third slot is occupied by
     * an element from the third supplied {@code Iterable}. The {@code Collection}
     * will effectively contain a {@code Nonuple} for each possible selection of
     * elements for the slots from each of the corresponding {@code Iterable}
     * instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the cartesian product is taken
     * eagerly, i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Nonuple} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is also empty.</p>
     *
     * <p>This overload of {@code cartesianProduct} is provided to allow the cartesian
     * product to be taken for nine {@code Iterable} instances. For equivalent example
     * usage for the two {@code Iterable} case, see
     * {@link #cartesianProduct(Iterable, Iterable)}.</p>
     *
     * @param first   The first {@code Iterable} from which to form a cartesian product.
     * @param second  The second {@code Iterable} from which to form a cartesian product.
     * @param third   The third {@code Iterable} from which to form a cartesian product.
     * @param fourth  The fourth {@code Iterable} from which to form a cartesian product.
     * @param fifth   The fifth {@code Iterable} from which to form a cartesian product.
     * @param sixth   The sixth {@code Iterable} from which to form a cartesian product.
     * @param seventh The seventh {@code Iterable} from which to form a cartesian product.
     * @param eighth  The eighth {@code Iterable} from which to form a cartesian product.
     * @param ninth   The ninth {@code Iterable} from which to form a cartesian product.
     * @param <R>     The type of the elements in the first {@code Iterable}.
     * @param <S>     The type of the elements in the second {@code Iterable}.
     * @param <T>     The type of the elements in the third {@code Iterable}.
     * @param <U>     The type of the elements in the fourth {@code Iterable}.
     * @param <V>     The type of the elements in the fifth {@code Iterable}.
     * @param <W>     The type of the elements in the sixth {@code Iterable}.
     * @param <X>     The type of the elements in the seventh {@code Iterable}.
     * @param <Y>     The type of the elements in the eighth {@code Iterable}.
     * @param <Z>     The type of the elements in the ninth {@code Iterable}.
     * @return A {@code Collection} containing the cartesian product of all elements
     *         in the supplied {@code Iterable} instances.
     */
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

    /**
     * Takes the cartesian product of the elements from all {@code Iterable}
     * instances in the supplied {@code Iterable} instance generating a {@code Collection}
     * of {@code Collection}s with each having as many elements as there were
     * {@code Iterable} instances in the input {@code Iterable}. The returned
     * {@code Collection} will contain {@code Collection} instances where the first position
     * is occupied by an element from the first {@code Iterable} in the supplied
     * {@code Iterable}, the second position is occupied by an element from the second
     * {@code Iterable} in the input {@code Iterable} and so on for each available
     * {@code Iterable}. The {@code Collection} will effectively contain a
     * {@code Collection} for each possible selection of elements for the positions from
     * each of the corresponding {@code Iterable} instances in the supplied {@code Iterable}.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a {@code Collection} is returned, the cartesian product is taken
     * eagerly, i.e., the supplied {@code Iterable} instances are iterated immediately and
     * the {@code Collection} instances are constructed before this method returns.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Collection} is empty.</p>
     *
     * <p>Note that this overload of {@code cartesianProduct} does not preserve type
     * information and so the returned {@code Iterable} will contain {@code Iterable}
     * instances over the wildcard type {@code ?}. If the number of {@code Iterable}
     * instances for which a cartesian product is required is less than ten,
     * use the explicit arities of {@code cartesianProduct}.</p>
     *
     * <h4>Example Usage:</h4>
     * Given three {@code Iterable} instances of varying types:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Integer&gt; first = iterableWith(1, 2);
     *     Iterable&lt;String&gt; second = iterableWith("first", "second", "third");
     *     Iterable&lt;Boolean&gt; third = iterableWith(false);
     * </pre>
     * </blockquote>
     * we can generate the cartesian product as follows:
     * <blockquote>
     * <pre>
     *     Iterable&ltIterable&lt?&gt;&gt; iterables = Literals.&lt;Iterable&lt?&gt;&gt;iterableWith(first, second, third);
     *     Collection&ltCollection&lt;?&gt;&gt; cartesianProduct = cartesianProduct(iterables);
     * </pre>
     * </blockquote>
     * This is effectively equivalent to the following:
     * <blockquote>
     * <pre>
     *     Collection&lt;Collection&lt;?&gt;&gt; equivalentCollections = Literals.&ltCollection&lt?&gt;&gt;collectionWith(
     *             collectionWith(1, "first", false),
     *             collectionWith(1, "second", false),
     *             collectionWith(1, "third", false));
     *             collectionWith(2, "first", false),
     *             collectionWith(2, "second", false),
     *             collectionWith(2, "third", false));
     * </pre>
     * </blockquote>
     *
     * @param iterables An {@code Iterable} of {@code Iterable} instances for
     *                  which a cartesian product should be generated.
     * @return A {@code Collection} of {@code Collection} instances representing the
     *         cartesian product of the supplied {@code Iterable} of {@code Iterable}s.
     */
    public static Collection<Collection<?>> cartesianProduct(Iterable<? extends Iterable<?>> iterables) {
        return Eagerly.map(Lazily.cartesianProduct(iterables), new Mapper<Iterable<?>, Collection<?>>() {
            @Override public Collection<?> map(Iterable<?> iterable) {
                return collectionFrom(iterable);
            }
        });
    }

    /**
     * Associates each element in the supplied {@code Iterable} with its related index in
     * that {@code Iterable}. Returns a {@code Collection} of {@code Pair} instances where
     * the first entry in the {@code Pair} is the index, starting from zero, and the second
     * element is the element from the supplied {@code Iterable} at that index.
     *
     * <p>Since a {@code Collection} instance is returned, the enumeration of the
     * supplied {@code Iterable} is performed eagerly, i.e., the supplied {@code Iterable}
     * is iterated immediately with its elements being associated to their index before the
     * {@code Collection} is returned.</p>
     *
     * <p>If the supplied {@code Iterable} is empty, so is the returned {@code Collection}.</p>
     *
     * <h4>Example Usage:</h4>
     * Assume we have a collection of {@code Name} instances representing people entered into
     * a prize draw. We wish to choose a winner at random from that collection. We can do this
     * as follows:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Name&gt; eligibleEntries = entriesDatabase.loadAll();
     *     Collection&lt;Pair&lt;Integer, Name&gt;&gt; indexedEntries = enumerate(eligibleEntries);
     *     Map&lt;Integer, Name&gt; lookupableEntries = Literals.mapFromPairs(indexedEntries);
     *     Name winner = lookupableEntries.get(Random.nextInt(lookupableEntries.size());
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} to be enumerated by index.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} containing {@code Pair} instances associating
     *         each element from the supplied {@code Iterable} to its zero based
     *         index in that {@code Iterable}.
     */
    public static <T> Collection<Pair<Integer, T>> enumerate(Iterable<T> iterable) {
        return materialize(Lazily.enumerate(iterable));
    }

    /**
     * Equates the elements in each of the supplied {@code Iterable} instances
     * using the supplied {@code BinaryPredicate} in the order in which they are yielded.
     * An element is yielded from each of the supplied {@code Iterable} instances and
     * passed to the supplied {@code BinaryPredicate}. The {@code boolean} returned by the
     * {@code BinaryPredicate} is used in place of the input elements in the returned
     * {@code Collection}. The Collection is considered fully populated when
     * one or both of the {@code Iterable} instances are exhausted. The order of the
     * {@code boolean} elements in the returned {@code Collection} is equal to the
     * order in which the equated elements are yielded from the supplied {@code Iterable}s.
     *
     * <p>Since a {@code Collection} instance is returned, the equation of the
     * supplied {@code Iterable} instances is eager, i.e., the supplied {@code Iterable}
     * instances are iterated and their elements are not equated immediately.</p>
     *
     * <h4>Example Usage:</h4>
     * Given two {@code Iterable} instances that contain elements that do not implement
     * equality in the desired way, we can determine equality using an externalised
     * function as follows:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Message&gt; firstIterable = systemOneMessageQueue.readAll();
     *     Iterable&lt;Message&gt; secondIterable = systemTwoMessageQueue.readAll();
     *     Collection&ltBoolean&gt equalityResults = equate(firstIterable, secondIterable, new BinaryPredicate&lt;Message, Message&gt() {
     *        &#64;Override public boolean evaluate(Message first, Message second) {
     *            return first.getMessageIdentifier().equals(second.getMessageIdentifier());
     *        }
     *     });
     *     equalityResults.contains(false); // true if not equal
     * </pre>
     * </blockquote>
     *
     * @param first     The first {@code Iterable} of elements to be compared.
     * @param second    The second {@code Iterable} of elements to be compared.
     * @param predicate A {@code BinaryPredicate} to be used to equate elements sequentially
     *                  from the supplied {@code Iterable} instances.
     * @param <T>       The type of the elements in the supplied {@code Iterables}.
     * @return A {@code Collection} containing the {@code boolean} results of equating
     *         the elements from the supplied {@code Iterable} instances in the
     *         order in which they are yielded.
     */
    public static <T> Collection<Boolean> equate(
            Iterable<T> first,
            Iterable<T> second,
            BinaryPredicate<? super T, ? super T> predicate) {
        return materialize(Lazily.equate(first, second, predicate));
    }

    /**
     * Equates the elements in each of the supplied {@code Iterable} instances
     * using the supplied {@code BinaryPredicate} in the order in which they are yielded.
     * An element is yielded from each of the supplied {@code Iterable} instances and
     * passed to the supplied {@code BinaryPredicate}. The {@code boolean} returned by the
     * {@code BinaryPredicate} is used in place of the input elements in the returned
     * {@code Collection}. The Collection is considered fully populated when
     * one or both of the {@code Iterable} instances are exhausted. The order of the
     * {@code boolean} elements in the returned {@code Collection} is equal to the
     * order in which the equated elements are yielded from the supplied {@code Iterable}s.
     *
     * <p>Since a {@code Collection} instance is returned, the equation of the
     * supplied {@code Iterable} instances is eager, i.e., the supplied {@code Iterable}
     * instances are iterated and their elements are not equated immediately.</p>
     *
     * <p>This overload of {@link #equate(Iterable, Iterable, BinaryPredicate)} is provided
     * to allow an {@code Equivalence} to be used in place of a {@code BinaryPredicate} to
     * enhance readability and better express intent. The contract of the function is
     * identical to that of the {@code BinaryPredicate} version of {@code equate}.</p>
     *
     * <p>For example usage and further documentation, see
     * {@link #equate(Iterable, Iterable, BinaryPredicate)}.</p>
     *
     * @param first       The first {@code Iterable} of elements to be compared.
     * @param second      The second {@code Iterable} of elements to be compared.
     * @param equivalence A {@code Equivalence} to be used to equate elements sequentially
     *                    from the supplied {@code Iterable} instances.
     * @param <T>         The type of the elements in the supplied {@code Iterables}.
     * @return A {@code Collection} containing the {@code boolean} results of equating
     *         the elements from the supplied {@code Iterable} instances in the
     *         order in which they are yielded.
     */
    public static <T> Collection<Boolean> equate(
            Iterable<T> first,
            Iterable<T> second,
            Equivalence<? super T> equivalence) {
        return equate(first, second, equivalenceBinaryPredicate(equivalence));
    }

    /**
     * Indexes the supplied {@code Iterable} using the supplied {@code UnaryFunction}
     * and returns a {@code Collection} of {@code Pair} instances representing the
     * indexed elements. The {@code UnaryFunction} will be provided with each
     * element in the input {@code Iterable}. The value returned by the
     * {@code UnaryFunction} will occupy the first slot in the {@code Pair}
     * instance for that element and the element itself will occupy the second slot.
     * The returned {@code Collection} contains the indexed elements in the order in
     * which the elements are yielded from the supplied {@code Iterable}.
     *
     * <p>Since a {@code Collection} instance is returned, the element indexing is performed
     * eagerly, i.e., elements are retrieved from the underlying {@code Iterable} and indexed
     * immediately.</p>
     *
     * <h4>Example Usage:</h4>
     * Consider an {@code Iterable} of {@code String} instances representing words from a book.
     * Each word can be indexed using its length using to the following.
     * <blockquote>
     * <pre>
     *     Iterable&lt;String&gt; words = book.getCompleteWordList();
     *     Collection&lt;Pair&lt;Integer, String&gt;&gt; wordsIndexedByLength = index(words, new UnaryFunction&ltString, Integer&gt;() {
     *         &#64;Override public Integer call(String word) {
     *             return word.length();
     *         }
     *     }
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} to be indexed.
     * @param function The {@code UnaryFunction} to use to index each element.
     * @param <S>      The type of the elements in the supplied {@code Iterable}.
     * @param <T>      The type of the index values returned by the supplied
     *                 indexing function.
     * @return A {@code Collection} containing {@code Pair} instances representing
     *         each element from the supplied {@code Iterable} indexed by the value
     *         returned by the supplied {@code UnaryFunction} when passed that element.
     */
    public static <S, T> Collection<Pair<T, S>> index(
            Iterable<S> iterable,
            UnaryFunction<? super S, T> function) {
        return materialize(Lazily.index(iterable, function));
    }

    /**
     * Indexes the supplied {@code Iterable} using the supplied {@code Indexer}
     * and returns a {@code Collection} of {@code Pair} instances representing the
     * indexed elements. The {@code Indexer} will be provided with each element
     * in the input {@code Iterable}. The value returned by the {@code Indexer}
     * will occupy the first slot in the {@code Pair} instance for that element
     * and the element itself will occupy the second slot. The returned
     * {@code Collection} contains the indexed elements in the order in
     * which the elements are yielded from the supplied {@code Iterable}.
     *
     * <p>Since a {@code Collection} instance is returned, the element indexing is performed
     * eagerly, i.e., elements are retrieved from the underlying {@code Iterable} and indexed
     * immediately.</p>
     *
     * <p>This overload of {@link #index(Iterable, UnaryFunction)} is provided to allow an
     * {@code Indexer} to be used in place of a {@code UnaryFunction} to enhance readability
     * and better express intent. The contract of the function is identical to that of the
     * {@code UnaryFunction} version of {@code index}.</p>
     *
     * <p>For example usage and further documentation, see {@link #index(Iterable, UnaryFunction)}.</p>
     *
     * @param iterable The {@code Iterable} to be indexed.
     * @param indexer  The {@code Indexer} to use to index each element.
     * @param <S>      The type of the elements in the supplied {@code Iterable}.
     * @param <T>      The type of the index values returned by the supplied
     *                 indexing function.
     * @return A {@code Collection} containing {@code Pair} instances representing
     *         each element from the supplied {@code Iterable} indexed by the value
     *         returned by the supplied {@code Indexer} when passed that element.
     */
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

    /**
     * Applies the supplied {@code UnaryProcedure} to each element in the
     * supplied {@code Iterable}. Each element in the supplied {@code Iterable} is
     * passed to the {@link UnaryProcedure#execute(Object)} method in the order in
     * which it is yielded.
     *
     * <p>The application of the supplied {@code UnaryProcedure} is eager, i.e.,
     * the procedure is immediately applied to each element in the
     * {@code Iterable}.</p>
     *
     * <p>Since the function being applied to each element is a procedure
     * it is inherently impure and thus must have side effects for any perceivable
     * outcome to be observed.</p>
     *
     * <h4>Example Usage:</h4>
     * Consider a {@code UnaryProcedure} that persists {@code Person} instances to a
     * database:
     * <blockquote>
     * <pre>
     *     public class PersonPersistingProcedure implements UnaryProcedure&lt;Person&gt; {
     *         private Database database;
     *         private Logger log;
     *
     *         public void execute(Person person) {
     *             log.info("Persisting {}", person.getName());
     *             database.store(person);
     *         }
     *     }
     * </pre>
     * </blockquote>
     * Given an {@code Iterable} of {@code Person} instances, each person can
     * be persisted as follows:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Person&gt; people = listWith(
     *             new Person("James"),
     *             new Person("Rodrigo"),
     *             new Person("Jane"));
     *     Eagerly.each(people, new PersonPersistingProcedure());
     * </pre>
     * </blockquote>
     * Once the {@code each} expression has evaluated, all elements in the people
     * {@code Iterable} will have been persisted along with a log statement.
     *
     * @param targets   The {@code Iterable} whose elements should each have the supplied
     *                  {@code UnaryProcedure} applied to them.
     * @param procedure A {@code UnaryProcedure} to apply to each element in the
     *                  supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     */
    public static <T> void each(
            Iterable<T> targets,
            UnaryProcedure<? super T> procedure) {
        materialize(Lazily.each(targets, procedure));
    }

    /**
     * Applies the supplied {@code Action} to each element in the supplied
     * {@code Iterable}. Each element in the supplied {@code Iterable} is
     * passed to the {@link Action#on(Object)} method in the order in
     * which it is yielded.
     *
     * <p>The application of the supplied {@code Action} is eager, i.e.,
     * the action is immediately applied to each element in the
     * {@code Iterable}.</p>
     *
     * <p>Since the function being applied to each element is an action
     * it is inherently impure and thus must have side effects for any perceivable
     * outcome to be observed.</p>
     *
     * <p>This overload of {@link #each(Iterable, UnaryProcedure)} is provided to allow an
     * {@code Action} to be used in place of a {@code UnaryProcedure} to enhance readability
     * and better express intent. The contract of the function is identical to that of the
     * {@code UnaryProcedure} version of {@code each}.</p>
     *
     * <p>For example usage and further documentation, see {@link #each(Iterable, UnaryProcedure)}.</p>
     *
     * @param targets The {@code Iterable} whose elements should each have the supplied
     *                {@code Action} applied to them.
     * @param action  An {@code Action} to apply to each element in the supplied
     *                {@code Iterable}.
     * @param <T>     The type of the elements in the supplied {@code Iterable}.
     */
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
     *           new Fish("Goldie"),
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
     * @param <T>       The type of the elements to be assessed for rejection.
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
     * Returns an {@code Option} over the second element in the supplied {@code Iterable}.
     * If the {@code Iterable} is empty, {@code None} is returned, otherwise, a
     * {@code Some} is returned over the second value found.
     *
     * <p>This method has a return type of {@code Option} rather than returning the
     * second value directly since, in the case of an empty {@code Iterable}, an
     * exception would have to be thrown using that approach. Instead, the
     * {@code Option} can be queried for whether it contains a value or not,
     * avoiding any exception handling.</p>
     *
     * <p>Since an {@code Option} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the second element from the underlying
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
     * The second element in the {@code Iterable} can be obtained as follows:
     * <blockquote>
     * <pre>
     *   Option&lt;Integer&gt; valueOption = second(values);
     *   Integer value = valueOption.get(); // => 4
     * </pre>
     * </blockquote>
     * Similarly, we can handle the empty {@code Iterable} case gracefully:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable();
     *   Option&lt;Integer&gt; valueOption = second(values);
     *   Integer value = valueOption.getOrElse(10); // => 10
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which the second element is required.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Option} instance representing the second element in the supplied
     *         {@code Iterable}.
     */
    public static <T> Option<T> second(Iterable<T> iterable) {
        return first(Lazily.rest(iterable));
    }

    /**
     * Returns an {@code Option} over the third element in the supplied {@code Iterable}.
     * If the {@code Iterable} is empty, {@code None} is returned, otherwise, a
     * {@code Some} is returned over the third value found.
     *
     * <p>This method has a return type of {@code Option} rather than returning the
     * third value directly since, in the case of an empty {@code Iterable}, an
     * exception would have to be thrown using that approach. Instead, the
     * {@code Option} can be queried for whether it contains a value or not,
     * avoiding any exception handling.</p>
     *
     * <p>Since an {@code Option} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the third element from the underlying
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
     * The third element in the {@code Iterable} can be obtained as follows:
     * <blockquote>
     * <pre>
     *   Option&lt;Integer&gt; valueOption = third(values);
     *   Integer value = valueOption.get(); // => 3
     * </pre>
     * </blockquote>
     * Similarly, we can handle the empty {@code Iterable} case gracefully:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable();
     *   Option&lt;Integer&gt; valueOption = third(values);
     *   Integer value = valueOption.getOrElse(10); // => 10
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which the third element is required.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Option} instance representing the third element in the supplied
     *         {@code Iterable}.
     */
    public static <T> Option<T> third(Iterable<T> iterable) {
        return first(Lazily.rest(Lazily.rest(iterable)));
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
     *   Option&lt;Integer&gt; valueOption = firstMatching(values, new Predicate&lt;Integer&gt;(){
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
     *   Option&lt;Integer&gt; valueOption = firstMatching(values, new Predicate&lt;Integer&gt;(){
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
     *   Option&lt;Integer&gt; valueOption = firstMatching(values, new Predicate&lt;Integer&gt;(){
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
    public static <T> Option<T> firstMatching(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return first(filter(iterable, predicate));
    }

    /**
     * Returns an {@code Option} over the second element in the supplied {@code Iterable}
     * that satisfies the supplied {@code UnaryPredicate}. If the {@code Iterable} is
     * empty or contains one or less matching elements, {@code None} is returned, otherwise,
     * a {@code Some} is returned over the second matching value found.
     *
     * <p>This method has a return type of {@code Option} rather than returning the
     * second matching value directly since, in the case of an empty {@code Iterable},
     * an exception would have to be thrown using that approach. Instead, the
     * {@code Option} can be queried for whether it contains a value or not,
     * avoiding any exception handling.</p>
     *
     * <p>Since an {@code Option} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the second matching element from the
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
     * The second even element in the {@code Iterable} can be obtained as follows:
     * <blockquote>
     * <pre>
     *   Option&lt;Integer&gt; valueOption = secondMatching(values, new Predicate&lt;Integer&gt;(){
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
     *   Option&lt;Integer&gt; valueOption = secondMatching(values, new Predicate&lt;Integer&gt;(){
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
     *   Option&lt;Integer&gt; valueOption = secondMatching(values, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   valueOption.hasValue(); // => false
     * </pre>
     * </blockquote>
     *
     * @param iterable  The {@code Iterable} to search for the second element matching the
     *                  supplied {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} that must be satisfied by elements in the
     *                  supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Option} instance representing the second element in the supplied
     *         {@code Iterable} satisfying the supplied {@code UnaryPredicate}.
     */
    public static <T> Option<T> secondMatching(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return second(filter(iterable, predicate));
    }

    /**
     * Returns an {@code Option} over the third element in the supplied {@code Iterable}
     * that satisfies the supplied {@code UnaryPredicate}. If the {@code Iterable} is
     * empty or contains two or less matching elements, {@code None} is returned,
     * otherwise, a {@code Some} is returned over the third matching value found.
     *
     * <p>This method has a return type of {@code Option} rather than returning the
     * third matching value directly since, in the case of an empty {@code Iterable},
     * an exception would have to be thrown using that approach. Instead, the
     * {@code Option} can be queried for whether it contains a value or not,
     * avoiding any exception handling.</p>
     *
     * <p>Since an {@code Option} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the third matching element from the
     * underlying {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterableWith(7, 6, 5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * The third even element in the {@code Iterable} can be obtained as follows:
     * <blockquote>
     * <pre>
     *   Option&lt;Integer&gt; valueOption = thirdMatching(values, new Predicate&lt;Integer&gt;(){
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
     *   Option&lt;Integer&gt; valueOption = thirdMatching(values, new Predicate&lt;Integer&gt;(){
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
     *   Option&lt;Integer&gt; valueOption = thirdMatching(values, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   valueOption.hasValue(); // => false
     * </pre>
     * </blockquote>
     *
     * @param iterable  The {@code Iterable} to search for the third element matching the
     *                  supplied {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} that must be satisfied by elements in the
     *                  supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Option} instance representing the third element in the supplied
     *         {@code Iterable} satisfying the supplied {@code UnaryPredicate}.
     */
    public static <T> Option<T> thirdMatching(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return third(filter(iterable, predicate));
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
     * Using {@code firstN}, we can obtain the first three elements in the {@code Iterable}.
     * The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstThreeValues = firstN(values, 3);
     *   Collection&lt;Integer&gt; equivalentValues = Literals.collectionWith(5, 4, 3);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} does not contain enough elements, we are returned a
     * {@code Collection} with as many elements as possible. The following two lines are
     * equivalent:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstSixValues = firstN(values, 6);
     *   Collection&lt;Integer&gt; equivalentValues = Literals.collectionWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Similarly, if the input {@code Iterable} contains no elements, an empty
     * {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable();
     *   Collection&lt;Integer&gt; firstThreeElements = firstN(values, 3);
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
    public static <T> Collection<T> firstN(
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
     * Using {@code firstNMatching}, we can obtain the first three even elements in the
     * {@code Iterable}. The following two expressions are equivalent:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstThreeEvens = firstNMatching(values, 3, new Predicate&lt;Integer&gt;(){
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
     *   Collection&lt;Integer&gt; firstFiveEvens = firstNMatching(values, 5, new Predicate&lt;Integer&gt;(){
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
     *   Collection&lt;Integer&gt; firstThreeEvens = firstNMatching(values, 3, new Predicate&lt;Integer&gt;(){
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
    public static <T> Collection<T> firstNMatching(
            Iterable<T> iterable,
            int numberOfElementsRequired,
            UnaryPredicate<? super T> predicate) {
        return firstN(filter(iterable, predicate), numberOfElementsRequired);
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
     * Returns an {@code Option} over the second to last element in the supplied
     * {@code Iterable}. If the {@code Iterable} is empty or contains only one element,
     * {@code None} is returned, otherwise, a {@code Some} is returned over the
     * second to last element yielded by the {@code Iterable}.
     *
     * <p>This method has a return type of {@code Option} rather than returning the
     * second to last value directly since, in the case of an empty {@code Iterable},
     * an exception would have to be thrown using that approach. Instead, the
     * {@code Option} can be queried for whether it contains a value or not,
     * avoiding any exception handling.</p>
     *
     * <p>Since an {@code Option} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the second to last element from the
     * underlying {@code Iterable} immediately.</p>
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
     *   Option&lt;String&gt; valueOption = secondLast(values);
     *   String value = valueOption.get(); // => "middle"
     * </pre>
     * </blockquote>
     * Similarly, we can handle the empty {@code Iterable} case gracefully:
     * <blockquote>
     * <pre>
     *   Iterable&lt;String&gt; values = Literals.iterable();
     *   Option&lt;String&gt; valueOption = secondLast(values);
     *   String value = valueOption.getOrElse("some string"); // => "some string"
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which the second to last element is required.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Option} instance representing the second to last element in the
     *         supplied {@code Iterable}.
     */
    public static <T> Option<T> secondLast(Iterable<T> iterable) {
        return first(slice(iterable, -2, -1));
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
     *   Option&lt;Integer&gt; valueOption = lastMatching(values, new Predicate&lt;Integer&gt;(){
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
     *   Option&lt;Integer&gt; valueOption = lastMatching(values, new Predicate&lt;Integer&gt;(){
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
     *   Option&lt;Integer&gt; valueOption = lastMatching(values, new Predicate&lt;Integer&gt;(){
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
    public static <T> Option<T> lastMatching(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return last(filter(iterable, predicate));
    }

    /**
     * Returns an {@code Option} over the second to last element in the supplied
     * {@code Iterable} that satisfies the supplied {@code UnaryPredicate}. If the
     * {@code Iterable} is empty or contains only one matching element,
     * {@code None} is returned, otherwise, a {@code Some} is returned over the
     * second to last matching value found.
     *
     * <p>This method has a return type of {@code Option} rather than returning the
     * second to last matching value directly since, in the case of an empty
     * {@code Iterable}, an exception would have to be thrown using that approach.
     * Instead, the {@code Option} can be queried for whether it contains a value or
     * not, avoiding any exception handling.</p>
     *
     * <p>Since an {@code Option} instance is returned, the element retrieval is performed
     * eagerly, i.e., an attempt is made to retrieve the second last matching element from
     * the underlying {@code Iterable} immediately.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * Given an {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * The second to last even element in the {@code Iterable} can be obtained as follows:
     * <blockquote>
     * <pre>
     *   Option&lt;Integer&gt; valueOption = secondLastMatching(values, new Predicate&lt;Integer&gt;(){
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
     *   Option&lt;Integer&gt; valueOption = secondLastMatching(values, new Predicate&lt;Integer&gt;(){
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
     *   Option&lt;Integer&gt; valueOption = secondLastMatching(values, new Predicate&lt;Integer&gt;(){
     *       &#64;Override public boolean evaluate(Integer integer) {
     *           return integer % 2 == 0;
     *       }
     *   });
     *   valueOption.hasValue(); // => false
     * </pre>
     * </blockquote>
     *
     * @param iterable  The {@code Iterable} to search for elements matching the supplied
     *                  {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} that must be satisfied by elements in the
     *                  supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Option} instance representing the second to last element in the supplied
     *         {@code Iterable} satisfying the supplied {@code UnaryPredicate}.
     */
    public static <T> Option<T> secondLastMatching(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        return secondLast(filter(iterable, predicate));
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
     * Using {@code lastN}, we can obtain the last three elements in the {@code Iterable}.
     * The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; lastThreeValues = lastN(values, 3);
     *   Collection&lt;Integer&gt; equivalentValues = Literals.collectionWith(3, 2, 1);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} does not contain enough elements, we are returned a
     * {@code Collection} with as many elements as possible. The following two lines are
     * equivalent:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; lastSixValues = lastN(values, 6);
     *   Collection&lt;Integer&gt; equivalentValues = Literals.collectionWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Similarly, if the input {@code Iterable} contains no elements, an empty
     * {@code Collection} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; values = Literals.iterable();
     *   Collection&lt;Integer&gt; lastThreeElements = lastN(values, 3);
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
    public static <T> Collection<T> lastN(
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
     * Using {@code lastNMatching}, we can obtain the last three even elements in the {@code Iterable}.
     * The following two expressions are equivalent:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; lastThreeEvens = lastNMatching(values, 3, new Predicate&lt;Integer&gt;(){
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
     *   Collection&lt;Integer&gt; lastFiveEvens = lastNMatching(values, 5, new Predicate&lt;Integer&gt;(){
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
     *   Collection&lt;Integer&gt; lastThreeEvens = lastNMatching(values, 3, new Predicate&lt;Integer&gt;(){
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
    public static <T> Collection<T> lastNMatching(
            Iterable<T> iterable,
            int numberOfElementsRequired,
            UnaryPredicate<? super T> predicate) {
        return lastN(filter(iterable, predicate), numberOfElementsRequired);
    }

    /**
     * Removes the first element from the supplied {@code Iterable} and
     * returns all remaining elements in a {@code Collection}. The ordering
     * of the elements in the retured {@code Collection} will be the same as
     * the input {@code Iterable}.
     *
     * <p>Since a {@code Collection} instance is returned, the removal of the first
     * element is performed eagerly, i.e., the input {@code Iterable} is iterated
     * immediately.</p>
     *
     * <p>If the supplied {@code Iterable} contains only one element, the
     * returned {@code Collection} will be empty. Similarly, if the supplied
     * {@code Iterable} is empty, the returned {@code Collection} will
     * be empty.</p>
     *
     * <h4>Example Usage:</h4>
     * Given the following {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Integer&gt; numbers = Literals.listWith(1, 2, 3, 4, 5, 6);
     * </pre>
     * </blockquote>
     * using {@code rest}, we can obtain a collection containing all but the first
     * element. The following two lines are equivalent:
     * <blockquote>
     * <pre>
     *     Collection&lt;Integer&gt; remainingElements = rest(numbers);
     *     Collection&lt;Integer&gt; equivalentElements = Literals.collectionWith(2, 3, 4, 5, 6);
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which to remove the first element.
     * @param <T>      The type of the elements in the supplied {@code Iterable};
     * @return A {@code Collection} containing all elements from the supplied
     *         {@code Iterable} but the first in the same order as in the supplied
     *         {@code Iterable}.
     */
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

    /**
     * Partitions the supplied {@code Iterable} into those elements that
     * satisfy the supplied {@code UnaryPredicate} and those elements that do not
     * satisfy the supplied {@code UnaryPredicate}. A {@code Pair} containing two
     * {@code Collection} instances is returned. Those elements that satisfy the
     * supplied {@code UnaryPredicate} form a {@code Collection} in the first slot
     * of the {@code Pair} and those that do not satisfy the supplied
     * {@code UnaryPredicate} form a {@code Collection} in the second slot of the
     * {@code Pair}. The ordering of the {@code Iterable} instance is maintained
     * according to the partition in the returned {@code Collection} instances.
     *
     * <p>Since a {@code Pair} containing {@code Collection} instances is returned,
     * the partitioning is performed eagerly, i.e., the {@code UnaryPredicate} is
     * applied to each element in the input {@code Iterable} immediately.</p>
     *
     * <p>If no elements in the supplied {@code Iterable} satisfy the supplied
     * {@code UnaryPredicate}, the first slot in the returned {@code Pair}
     * will be occupied by an empty {@code Collection}. Similarly, if all elements
     * in the supplied {@code Iterable} satisfy the supplied {@code UnaryPredicate},
     * the second slot in the returned {@code Pair} will be occupied by an
     * empty {@code Collection}.</p>
     *
     * <h4>Example Usage:</h4>
     * Given an {@code Iterable} of {@code Account} instances where {@code Account} is
     * defined as follows:
     * <blockquote>
     * <pre>
     *     public class Account {
     *         private final BigDecimal balance;
     *
     *         public Account(BigDecimal balance) {
     *             this.balance = balance;
     *         }
     *
     *         public boolean isOverdrawn() {
     *             return balance < 0;
     *         }
     *
     *         ...
     *     }
     * </pre>
     * </blockquote>
     * we can divide those that are overdrawn from those that are not using the following:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Account&gt; accounts = accountRepository.getAccounts();
     *     Pair&lt;Collection&ltAccount&gt;, Collection&lt;Account&gt&gt overdrawnPartition = partition(accounts, new Predicate&ltAccount&gt;() {
     *        &#64;Override public boolean evaluate(Account account) {
     *            return account.isOverdrawn();
     *        }
     *     });
     *     Collection&lt;Account&gt; inTheRedAccounts = overdrawnPartition.getFirst();
     *     Collection&lt;Account&gt; inTheBlackAccounts = overdrawnPartition.getSecond();
     * </pre>
     * </blockquote>
     *
     * @param iterable  An {@code Iterable} of elements to be partitioned based on
     *                  whether or not they satisfy the supplied {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} to be used to evaluate which side of the
     *                  partition each element in the supplied {@code Iterable} should
     *                  reside.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Pair} instance which contains those elements from the supplied
     *         {@code Iterable} which satisfy the supplied {@code UnaryPredicate} in
     *         the first slot and those elements from the supplied {@code Iterable}
     *         which do not satisfy the supplied {@code UnaryPredicate} in the second
     *         slot.
     */
    public static <T> Pair<Collection<T>, Collection<T>> partition(
            Iterable<T> iterable,
            UnaryPredicate<? super T> predicate) {
        Pair<Iterable<T>, Iterable<T>> partition = Lazily.partition(iterable, predicate);
        return tuple(materialize(partition.getFirst()), materialize(partition.getSecond()));
    }

    /**
     * Returns a collection {@code Collection} instance containing batches of elements
     * of the specified size from the supplied {@code Iterable} in the order that
     * they are yielded..
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
     * Slices a sub-sequence from the supplied {@code Iterable} according
     * to the supplied start index, stop index and step size. The start and stop
     * indices are both zero based and the step size is one based. The element
     * at the start index in the supplied {@code Iterable} will be included in the
     * returned {@code Iterable} whilst the element at the stop index will be
     * excluded from it. The step size indicates the number of steps to take
     * between included elements in the returned {@code Iterable}.
     *
     * <p>Since a {@code Collection} is returned, the sub-sequence is sliced
     * eagerly, i.e., the supplied {@code Iterable} is iterated for the
     * required sub-sequence immediately.</p>
     *
     * <p>If the supplied start index is {@code null} or zero, the sub-sequence starts
     * at the beginning of the supplied {@code Iterable}. If it is negative then the
     * sub-sequence starts that many elements back from the end of the supplied
     * {@code Iterable} and will be inclusive of that element.</p>
     *
     * <p>If the supplied stop index is {@code null} then the sub-sequence  ends at
     * the end of the supplied {@code Iterable}. If it is negative then the
     * sub-sequence ends that many elements back from the end of the supplied
     * {@code Iterable} and will be exclusive of that element.</p>
     *
     * <p>If the supplied step size is {@code null} then a default step size of
     * one will be assumed. If the supplied step size is zero then an
     * {@code IllegalArgumentException} is thrown. If the supplied step size
     * is less than zero, the sub-sequence will be taken in reverse through
     * the supplied {@code Iterable}.</p>
     *
     * <p>If the start and stop indices are equal, an empty {@code Collection}
     * is returned. If the supplied step size is positive and the start index
     * is greater than the stop index, an empty {@code Collection} is returned.
     * Similarly, if the supplied step size is negative and the start index
     * is less than the stop index, an empty {@code Collection} is returned.</p>
     *
     * <p>In the case that the supplied start or stop value is positive and
     * greater than the number of elements in the supplied {@code Iterable},
     * the end of the {@code Iterable} will be used for that index. In the case that
     * the supplied start or stop value is negative and greater than the number
     * of elements in the supplied {@code Iterable}, the start of the
     * {@code Iterable} will be used for that index.</p>
     *
     * <h4>Example Usage:</h4>
     * Given an {@code Iterable} of {@code WeekDay} instances starting at
     * Sunday:
     * <blockquote>
     * <pre>
     *     Iterable&lt;WeekDay&gt; weekdays = listWith(
     *             weekDay("Sunday"),
     *             weekDay("Monday"),
     *             weekDay("Tuesday"),
     *             weekDay("Wednesday"),
     *             weekDay("Thursday"),
     *             weekDay("Friday"),
     *             weekDay("Saturday"),
     * </pre>
     * </blockquote>
     * we can obtain an {@code Iterable} of the working days in reverse
     * using the following:
     * <blockquote>
     * <pre>
     *     Iterable&lt;WeekDay&gt; workingDaysReversed = slice(weekdays, -1, 1, -1);
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which to slice a sub-sequence.
     * @param start    The index from which to start the slicing, inclusive.
     * @param stop     The index at which to stop the slicing, exclusive.
     * @param step     The number of steps to take between elements inside the
     *                 sub-sequence.
     * @param <T>      The type of the elements contained in the supplied
     *                 {@code Iterable}
     * @return A {@code Collection} containing the sub-sequence of elements
     *         from the supplied {@code Iterable} specified by the supplied
     *         start and stop indices and the supplied step size.
     */
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

    /**
     * Slices a sub-sequence from the supplied {@code Iterable} according
     * to the supplied start index, stop index. The start and stop indices
     * are both zero based. The element at the start index in the supplied
     * {@code Iterable} will be included in the returned {@code Iterable}
     * whilst the element at the stop index will be excluded from it.
     *
     * <p>Since a {@code Collection} is returned, the sub-sequence is sliced
     * eagerly, i.e., the supplied {@code Iterable} is iterated for the
     * required sub-sequence immediately.</p>
     *
     * <p>This overload of {@link #slice(Iterable, Integer, Integer, Integer)}
     * is provided to assist in the common case of a sub-sequence in the
     * positive direction through the supplied {@code Iterable} with a step size
     * of one. The contract of the function is identical to that of the
     * {@code slice} function with a hardcoded step size of one. For example
     * usage and further documentation, see
     * {@link #slice(Iterable, Integer, Integer, Integer)}.</p>
     *
     * @param iterable The {@code Iterable} from which to slice a sub-sequence.
     * @param start    The index from which to start the slicing, inclusive.
     * @param stop     The index at which to stop the slicing, exclusive.
     * @param <T>      The type of the elements contained in the supplied
     *                 {@code Iterable}
     * @return A {@code Collection} containing the sub-sequence of elements
     *         from the supplied {@code Iterable} specified by the supplied
     *         start and stop indices.
     */
    public static <T> Collection<T> slice(
            Iterable<T> iterable,
            Integer start,
            Integer stop) {
        return slice(iterable, start, stop, 1);
    }

    /**
     * Returns a {@code Collection} instance representing a repetition of the elements
     * in the supplied {@code Iterable} in the order in which they are yielded.
     *
     * <p>For example, given an {@code Iterable} of {@code Group} instances and
     * a randomly ordered {@code Iterable} of many {@code Candidate} instances, we
     * can calculate candidate assignments so that we form groups of exactly
     * {@code 5} candidates each as follows:
     * <blockquote>
     * <pre>
     *     Collection&lt;Candidate&gt; candidates = candidateRepository.getAll();
     *     Collection&lt;Group&gt; groups = collectionWith(group(1), group(2), group(3));
     *     Collection&lt;Group&gt; groupPositions = Eagerly.repeat(groups, 5);
     *     Collection&lt;Pair&lt;Candidate, Group&gt;&gt; groupAssignments = Eagerly.zip(candidates, groupPositions);
     * </pre>
     * </blockquote>
     * </p>
     *
     * <p>Note, if zero is specified as the number of times to repeat, an empty
     * {@code Collection} will be returned. Similarly, if the supplied {@code Iterable}
     * is empty, regardless of the number of repeats specified, the returned
     * {@code Collection} will be empty.</p>
     *
     * @param iterable              The {@code Iterable} whose contents should be repeated the
     *                              specified number of times.
     * @param numberOfTimesToRepeat The number of repetitions of the supplied {@code Iterable}
     *                              required.
     * @param <T>                   The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Collection} instance containing the required number of
     *         repetitions of the supplied {@code Iterable}.
     * @throws IllegalArgumentException if the specified number of times to repeat is negative.
     */
    public static <T> Collection<T> repeat(
            Iterable<T> iterable,
            int numberOfTimesToRepeat) {
        return materialize(Lazily.repeat(iterable, numberOfTimesToRepeat));
    }

    /**
     * Returns a reversed instance of the supplied {@code Iterable}
     *
     * <p>Since a {@code Collection} instance is returned, the enumeration of the
     * supplied {@code Iterable} is performed eagerly, i.e., the supplied {@code Iterable}
     * is iterated immediately before the {@code Collection} is returned.</p>
     *
     * <p>If the supplied {@code Iterable} is empty, so is the returned
     * {@code Collection}.</p>
     *
     * <p>{@code reverse} does not discriminate against {@code null} values in the input
     * {@code Iterable}, any {@code null} values returned are retained in the output
     * {@code Collection}. Thus, the input and output collections will always be of
     * the same size.</p>
     *
     * @param iterable The {@code Iterable} to reverse
     * @return A {@code Collection} instance containing the elements of the supplied
     *         {@code Iterable} in reverse order.
     */
    public static <T> Collection<T> reverse(final Iterable<T> iterable) {
        return collectionFrom(new Iterable<T>() {
            public Iterator<T> iterator() {
                List<T> list = listFrom(iterable);
                final ListIterator<T> listIterator = list.listIterator(list.size());
                return new Iterator<T>() {
                    public boolean hasNext() {
                        return listIterator.hasPrevious();
                    }
                    public T next() {
                        return listIterator.previous();
                    }
                    public void remove() {
                        listIterator.remove();
                    }
                };
            }
        });
    }

    /**
     * Provides an eagerly evaluated, set-builder notation style list comprehension.
     * Returns a {@code Collection} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function   An {@code UnaryFunction} output function that produces members
     *                   of the resultant set from members of the input set that satisfy
     *                   the predicate functions.
     * @param iterable   The {@code Iterable} of the input set.
     * @param predicates The {@code UnaryPredicate} functions acting as a filter on
     *                   members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final Iterable<? extends UnaryPredicate<? super S>> predicates) {
        return materialize(Lazily.comprehension(function, iterable, predicates));
    }

    /**
     * Provides an eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Collection} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper     A {@code Mapper} output function that produces members of the
     *                   resultant set from members of the input set that satisfy the
     *                   predicate functions.
     * @param iterable   The {@code Iterable} of the input set.
     * @param predicates The {@code UnaryPredicate} functions acting as a filter on
     *                   members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final Iterable<? extends UnaryPredicate<? super S>> predicates) {
        return materialize(Lazily.comprehension(mapper, iterable, predicates));
    }

    /**
     * Provides an eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The {@code UnaryPredicate} function acting as a filter on members
     *                 of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate function, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<? super S> p1) {
        return comprehension(function, iterable, iterableWith(p1));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The {@code UnaryPredicate} function acting as a filter on members
     *                 of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate function, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<? super S> p1) {
        return comprehension(mapperUnaryFunction(mapper), iterable, iterableWith(p1));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2) {
        return comprehension(function, iterable, iterableWith(p1, p2));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2) {
        return comprehension(
                mapperUnaryFunction(mapper),
                iterable,
                iterableWith(p1, p2));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3) {
        return comprehension(
                function,
                iterable,
                iterableWith(p1, p2, p3));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3) {
        return comprehension(
                mapperUnaryFunction(mapper),
                iterable,
                iterableWith(p1, p2, p3));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4) {
        return comprehension(
                function,
                iterable,
                iterableWith(p1, p2, p3, p4));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4) {
        return comprehension(
                mapperUnaryFunction(mapper),
                iterable,
                iterableWith(p1, p2, p3, p4));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5) {
        return comprehension(
                function,
                iterable,
                iterableWith(p1, p2, p3, p4, p5));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5) {
        return comprehension(
                mapperUnaryFunction(mapper),
                iterable,
                iterableWith(p1, p2, p3, p4, p5));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6) {
        return comprehension(
                function,
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6) {
        return comprehension(
                mapperUnaryFunction(mapper),
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p7       The seventh {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6,
            final UnaryPredicate<S> p7) {
        return comprehension(
                function,
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p7       The seventh {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6,
            final UnaryPredicate<S> p7) {
        return comprehension(
                mapperUnaryFunction(mapper),
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p7       The seventh {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p8       The eighth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6,
            final UnaryPredicate<S> p7,
            final UnaryPredicate<S> p8) {
        return comprehension(
                function,
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p7       The seventh {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p8       The eighth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6,
            final UnaryPredicate<S> p7,
            final UnaryPredicate<S> p8) {
        return comprehension(
                mapperUnaryFunction(mapper),
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p7       The seventh {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p8       The eighth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p9       The ninth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6,
            final UnaryPredicate<S> p7,
            final UnaryPredicate<S> p8,
            final UnaryPredicate<S> p9) {
        return comprehension(
                function,
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p7       The seventh {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p8       The eighth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p9       The ninth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6,
            final UnaryPredicate<S> p7,
            final UnaryPredicate<S> p8,
            final UnaryPredicate<S> p9) {
        return comprehension(
                mapperUnaryFunction(mapper),
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p7       The seventh {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p8       The eighth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p9       The ninth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p10      The tenth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6,
            final UnaryPredicate<S> p7,
            final UnaryPredicate<S> p8,
            final UnaryPredicate<S> p9,
            final UnaryPredicate<S> p10) {
        return comprehension(
                function,
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p7       The seventh {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p8       The eighth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p9       The ninth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p10      The tenth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6,
            final UnaryPredicate<S> p7,
            final UnaryPredicate<S> p8,
            final UnaryPredicate<S> p9,
            final UnaryPredicate<S> p10) {
        return comprehension(
                mapperUnaryFunction(mapper),
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass all of the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code UnaryFunction}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param function An {@code UnaryFunction} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p7       The seventh {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p8       The eighth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p9       The ninth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p10      The tenth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p11on    The remaining {@code UnaryPredicate} functions acting as filters on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapping function.
     */
    public static <S, T> Collection<T> comprehension(
            final UnaryFunction<? super S, T> function,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6,
            final UnaryPredicate<S> p7,
            final UnaryPredicate<S> p8,
            final UnaryPredicate<S> p9,
            final UnaryPredicate<S> p10,
            final UnaryPredicate<S>... p11on) {
        return comprehension(
                function,
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11on));
    }

    /**
     * Provides a eagerly evaluated, set-builder notation style list comprehension.
     * Returns an {@code Iterable} of the elements that pass the supplied
     * {@code UnaryPredicate}s, mapped by the supplied {@code Mapper}.
     *
     * <p>Since a {@code Collection} instance is returned, the list comprehension
     * is performed eagerly.
     *
     * <p>If the supplied source {@code Iterable} instance is empty, the
     * returned {@code Collection} is empty.
     *
     * @param mapper   A {@code Mapper} output function that produces members
     *                 of the resultant set from members of the input set that satisfy
     *                 the predicate functions.
     * @param iterable The {@code Iterable} of the input set.
     * @param p1       The first {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p2       The second {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p3       The third {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p4       The fourth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p5       The fifth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p6       The sixth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p7       The seventh {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p8       The eighth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p9       The ninth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p10      The tenth {@code UnaryPredicate} function acting as a filter on
     *                 members of the input set.
     * @param p11on    The remaining {@code UnaryPredicate} functions acting as filters on
     *                 members of the input set.
     * @return A {@code Collection} of the resultant set from members of the input set
     *         that satisfy the predicate functions, as mapped by the mapper.
     */
    public static <S, T> Collection<T> comprehension(
            final Mapper<? super S, T> mapper,
            final Iterable<S> iterable,
            final UnaryPredicate<S> p1,
            final UnaryPredicate<S> p2,
            final UnaryPredicate<S> p3,
            final UnaryPredicate<S> p4,
            final UnaryPredicate<S> p5,
            final UnaryPredicate<S> p6,
            final UnaryPredicate<S> p7,
            final UnaryPredicate<S> p8,
            final UnaryPredicate<S> p9,
            final UnaryPredicate<S> p10,
            final UnaryPredicate<S>... p11on) {
        return comprehension(
                mapperUnaryFunction(mapper),
                iterable,
                iterableWith(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11on));
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
