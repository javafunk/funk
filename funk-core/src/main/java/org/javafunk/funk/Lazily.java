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
import org.javafunk.funk.functors.*;
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
import static org.javafunk.funk.iterators.ComprehensionIterator.checkContainsNoNulls;

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
 * <blockquote>
 * <pre>
 *      Iterable&lt;BigInteger&gt; naturalNumbers = naturalNumbers.getAll();
 *      Iterable&lt;BigDecimal&gt; doubledNaturals = Lazily.map(naturalNumbers, new Mapper&lt;BigInteger, BigDecimal&gt;() {
 *        &#64;Override public BigDecimal map(BigInteger number) {
 *          return new BigDecimal(number.multiply(new BigInteger("2")));
 *        }
 *      });
 *      Iterable&lt;Pair&lt;Integer, BigDecimal&gt;&gt; enumeratedDoubledNaturals = Lazily.enumerate(doubledNaturals);
 *      Iterable&lt;Pair&lt;Integer, BigDecimal&gt;&gt; firstHundredEnumeratedDoubledNaturals = Lazily.take(enumeratedDoubledNaturals, 100);
 *      Map&lt;Integer, BigDecimal&gt; firstHundredDoubledNaturals = Literals.mapFromPairs(firstHundredEnumeratedDoubledNaturals);
 * </pre>
 * </blockquote>
 * Here a number of lazy operations are performed and then the final {@code Iterable} is
 * materialised into a {@code Map} instance. The original {@code Iterable} is an
 * infinite sequence of elements however this series of operations will only iterate and
 * map over the first hundred elements.
 * </p>
 *
 * <p>This example has been written in full but of course, via static imports and method extraction, it can
 * be made more concise as shown in the following:
 * <blockquote>
 * <pre>
 *      Map&lt;Integer, BigDecimal&gt; firstHundredDoubledNaturals = mapFromPairs(take(enumerate(map(naturalNumbers.getAll(), toDoubledBigDecimals())), 100);
 * </pre>
 * </blockquote>
 * </p>
 *
 * <p>Note that none of the values returned by these functions memoise their contents
 * upon iteration. Thus, if the input {@code Iterable} instance(s) have expensive side
 * effects on iteration such as loading from the file system or a database or performing
 * expensive computations, the laziness of these functions will mean this cost will
 * be incurred on every iteration. Instead consider materialising either the input
 * or output values prior to repeated iteration.</p>
 *
 * @see Eagerly
 * @since 1.0
 */
public class Lazily {
    private Lazily() {
    }

    /**
     * Returns a lazy {@code Iterable} instance containing batches of elements
     * of the specified size from the supplied {@code Iterable}.
     *
     * <p>In the case that the number of elements in the supplied {@code Iterable}
     * does not evenly divide by the supplied batch size, the last {@code Iterable}
     * in the returned {@code Iterable} will contain less than the batch size. If
     * the supplied batch size is not positive, an {@code IllegalArgumentException}
     * will be thrown.</p>
     *
     * <p>As an example, the following two {@code Iterable} instances
     * are effectively equivalent:
     * <blockquote>
     * <pre>
     *      Iterable&lt;Iterable&lt;Integer&gt;&gt; batches1 = iterableWith(iterableWith(1, 2, 3), iterableWith(4, 5, 6), iterableWith(7));
     *      Iterable&lt;Iterable&lt;Integer&gt;&gt; batches2 = Lazily.batch(iterableWith(1, 2, 3, 4, 5, 6, 7), 3);
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param iterable  The {@code Iterable} to batch into batches of the specified
     *                  number of elements.
     * @param batchSize The number of elements required in each batch in the
     *                  returned {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Iterable} instance of {@code Iterable} instances each
     *         containing the required number of elements, bar the last which may
     *         have less dependent on availability.
     * @throws IllegalArgumentException if the required number of elements to take
     *                                  is not positive.
     */
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

    /**
     * Returns an infinite lazy {@code Iterable} which repeatedly cycles through the
     * elements in the supplied {@code Iterable} in the order in which they are
     * yielded.
     *
     * <p>For example, given an {@code Iterable} of {@code Team} instances, randomly
     * ordered, we can assign each {@code Team} to a group identified by an
     * {@code Integer} between {@code 1} and {@code 4} as follows:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Team&gt; teams = teamRepository.findByCountyName("Kent");
     *     Iterable&lt;Integer&gt; groupNumbers = Lazily.cycle(iterableWith(1, 2, 3, 4));
     *     Iterable&lt;Pair&lt;Team, Integer&gt;&gt; groupAssignments = Lazily.zip(teams, groupNumbers);
     * </pre>
     * </blockquote>
     * </p>
     *
     * <p>Note, if the supplied {@code Iterable} is empty, the returned {@code Iterable}
     * will also be empty.</p>
     *
     * @param iterable The {@code Iterable} whose contents should be infinitely
     *                 cycled.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Iterable} instance containing an infinite number of
     *         cycles through the supplied {@code Iterable}.
     */
    public static <T> Iterable<T> cycle(final Iterable<T> iterable) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new CyclicIterator<T>(iterable.iterator());
            }
        };
    }

    /**
     * Returns a lazy {@code Iterable} which represents a repetition of the elements
     * in the supplied {@code Iterable} in the order in which they are yielded.
     *
     * <p>For example, given an {@code Iterable} of {@code Group} instances and
     * a randomly ordered {@code Iterable} of many {@code Candidate} instances, we
     * can calculate candidate assignments so that we form groups of exactly
     * {@code 5} candidates each as follows:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Candidate&gt; candidates = candidateRepository.getAll();
     *     Iterable&lt;Group&gt; groups = iterableWith(group(1), group(2), group(3));
     *     Iterable&lt;Group&gt; groupPositions = repeat(groups, 5);
     *     Iterable&lt;Pair&lt;Candidate, Group&gt;&gt; groupAssignments = zip(candidates, groupPositions);
     * </pre>
     * </blockquote>
     * </p>
     *
     * <p>Note, if zero is specified as the number of times to repeat, an empty
     * {@code Iterable} will be returned. Similarly, if the supplied {@code Iterable}
     * is empty, regardless of the number of repeats specified, the returned
     * {@code Iterable} will be empty.</p>
     *
     * @param iterable              The {@code Iterable} whose contents should be repeated the
     *                              specified number of times.
     * @param numberOfTimesToRepeat The number of repetitions of the supplied {@code Iterable}
     *                              required.
     * @param <T>                   The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Iterable} instance containing the required number of
     *         repetitions of the supplied {@code Iterable}.
     * @throws IllegalArgumentException if the specified number of times to repeat is negative.
     */
    public static <T> Iterable<T> repeat(final Iterable<T> iterable, final int numberOfTimesToRepeat) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new CyclicIterator<T>(iterable.iterator(), numberOfTimesToRepeat);
            }
        };
    }

    /**
     * Takes the first <em>n</em> elements from the supplied {@code Iterable} where <em>n</em>
     * is given by the supplied integer value and returns them in an {@code Iterable}. If the
     * {@code Iterable} is empty, an empty {@code Iterable} is returned, otherwise, an
     * {@code Iterable} effectively containing the first <em>n</em> elements is returned.
     *
     * <p>In the case that the supplied {@code Iterable} does not contain enough
     * elements to satisfy the required number, an {@code Iterable} containing
     * as many elements as possible is returned.</p>
     *
     * <p>If the supplied integer value is negative, an {@code IllegalArgumentException} is
     * thrown.</p>
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the element retrieval is
     * performed lazily, i.e., the elements are not retrieved from the underlying
     * {@code Iterable} until it is iterated.</p>
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
     * The following two lines are effectively equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; firstThreeElements = take(elements, 3);
     *   Iterable&lt;Integer&gt; equivalentElements = Literals.iterableWith(5, 4, 3);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} does not contain enough elements, we are returned an
     * {@code Iterable} with as many elements as possible. The following two lines are
     * effectively equivalent:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; firstSixElements = take(elements, 6);
     *   Iterable&lt;Integer&gt; equivalentElements = Literals.iterableWith(5, 4, 3, 2, 1);
     * </pre>
     * </blockquote>
     * Similarly, if the input {@code Iterable} contains no elements, an empty
     * {@code Iterable} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable();
     *   Iterable&lt;Integer&gt; firstThreeElements = take(elements, 3);
     *   firstThreeElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which to take <em>n</em> elements.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Iterable} instance effectively containing the required
     *         number of elements (or less) from the supplied {@code Iterable}.
     * @throws IllegalArgumentException if the required number of elements to take
     *                                  is negative.
     */
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

    /**
     * Takes elements from the supplied {@code Iterable} while the supplied {@code UnaryPredicate}
     * evaluates true for those elements and returns an {@code Iterable} effectively containing those
     * elements that evaluated to {@code true}. On iteration, each element taken from the {@code Iterable}
     * is evaluated by the {@code UnaryPredicate}. If it satisfies the {@code UnaryPredicate}, it is
     * returned. If it does not satisfy the {@code UnaryPredicate}, no further elements are taken
     * and iteration finishes. If the supplied {@code Iterable} is empty, an effectively empty
     * {@code Iterable} is returned.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the element retrieval is performed
     * lazily, i.e., no attempt is made to retrieve the sequence of satisfactory elements from the
     * underlying {@code Iterable} until it is iterated.</p>
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
     *   Iterable&lt;Integer&gt; firstElementsGreaterThanTwo = takeWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Iterable&lt;Integer&gt; equivalentElements = Literals.iterableWith(5, 4, 3);
     * </pre>
     * </blockquote>
     * If the first element retrieved from the {@code Iterable} on iteration does not satisfy the
     * supplied {@code UnaryPredicate}, no elements are taken and the input {@code Iterable}
     * is not iterated. The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(2, 1, 4, 5, 6);
     *   Iterable&lt;Integer&gt; firstElementsGreaterThanTwo = takeWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   firstElementsGreaterThanTwo.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * If all elements retrieved from the {@code Iterable} satisfy the supplied
     * {@code Predicate}, all elements are iterated. The following two lines are effectively
     * equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(4, 5, 6, 7, 8);
     *   Iterable&lt;Integer&gt; firstElementsGreaterThanTwo = takeWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Iterable&lt;Integer&gt; equivalentElements = Literals.iterableWith(4, 5, 6, 7, 8);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} contains no elements, an empty {@code Iterable} is
     * returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable();
     *   Iterable&lt;Integer&gt; firstElementsGreaterThanTwo = takeWhile(elements, new Predicate&lt;Integer&gt;() {
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
     * @return An {@code Iterable} instance containing the sequence of elements
     *         taken from the supplied {@code Iterable} on iteration up until the supplied
     *         {@code UnaryPredicate} is no longer satisfied.
     */
    public static <T> Iterable<T> takeWhile(final Iterable<T> iterable, final UnaryPredicate<? super T> predicate) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new PredicatedIterator<T>(iterable.iterator(), predicate);
            }
        };
    }

    /**
     * Takes elements from the supplied {@code Iterable} until the supplied {@code UnaryPredicate}
     * evaluates true for an element and returns an {@code Iterable} effectively containing those
     * elements that evaluated to false. On iteration, each element taken from the {@code Iterable}
     * is evaluated by the {@code UnaryPredicate}. If it does not satisfy the {@code UnaryPredicate},
     * it is returned. If it satisfies the {@code UnaryPredicate}, no further elements are taken and
     * iteration finishes. If the supplied {@code Iterable} is empty, an effectively empty
     * {@code Iterable} is returned.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the element retrieval is performed
     * lazily, i.e., no attempt is made to retrieve the sequence of elements not satisfying the
     * {@code UnaryPredicate} from the underlying {@code Iterable} until it is iterated.</p>
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
     * found that is greater than 2. The following two lines are effectively equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; firstElementsNotGreaterThanTwo = takeUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Iterable&lt;Integer&gt; equivalentElements = Literals.iterableWith(1, 2);
     * </pre>
     * </blockquote>
     * If the first element retrieved from the {@code Iterable} satisfies the supplied
     * {@code UnaryPredicate}, an effectively empty {@code Iterable} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(4, 5, 6, 7, 8);
     *   Iterable&lt;Integer&gt; firstElementsNotGreaterThanTwo = takeUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   firstElementsNotGreaterThanTwo.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * If no elements retrieved from the {@code Iterable} satisfy the supplied {@code UnaryPredicate}
     * on iteration, all elements are iterated. The following two lines are effectively equivalent in
     * this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(-1, 0, 1, 2);
     *   Iterable&lt;Integer&gt; firstElementsNotGreaterThanTwo = takeUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Iterable&lt;Integer&gt; equivalentElements = Literals.collectionWith(-1, 0, 1, 2);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} contains no elements, an empty {@code Iterable} is returned:
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
     * @return An {@code Iterable} instance containing the sequence of elements
     *         taken from the supplied {@code Iterable} on iteration up until the supplied
     *         {@code UnaryPredicate} is satisfied.
     */
    public static <T> Iterable<T> takeUntil(final Iterable<T> iterable, final UnaryPredicate<? super T> predicate) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new PredicatedIterator<T>(iterable.iterator(), new NotPredicate<T>(predicate));
            }
        };
    }

    /**
     * Drops the first <em>n</em> elements from the supplied {@code Iterable} where <em>n</em>
     * is given by the supplied integer value and returns an {@code Iterable} containing
     * the remaining elements. If the {@code Iterable} is empty, an empty {@code Iterable}
     * is returned, otherwise, an {@code Iterable} containing the elements that remain after
     * the first <em>n</em> elements have been discarded is returned.
     *
     * <p>In the case that the supplied {@code Iterable} contains less elements than the
     * required number of elements to drop, all elements are dropped and an effectively empty
     * {@code Iterable} is returned.</p>
     *
     * <p>If the supplied integer value is negative, an {@code IllegalArgumentException} is
     * thrown.</p>
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the element discardal is performed
     * lazily, i.e., the elements are not discarded from the underlying {@code Iterable}
     * until it is iterated.</p>
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
     * The following two lines are effectively equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; remainingElements = drop(elements, 3);
     *   Iterable&lt;Integer&gt; equivalentElements = iterableWith(2, 1);
     * </pre>
     * </blockquote>
     * If the input {@code Iterable} does not contain enough elements, we are returned
     * an empty {@code Iterable}. The following two lines are effectively equivalent:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; remainingElements = drop(elements, 6);
     *   Iterable&lt;Integer&gt; equivalentElements = iterable();
     * </pre>
     * </blockquote>
     * Similarly, if the input {@code Iterable} contains no elements, an empty
     * {@code Iterable} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = iterable();
     *   Iterable&lt;Integer&gt; remainingElements = drop(elements, 3);
     *   remainingElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     *
     * @param iterable The {@code Iterable} from which to drop <em>n</em> elements
     *                 and return the remainder.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Iterable} instance with the required number of elements
     *         dropped from the supplied {@code Iterable} on iteration, effectively
     *         containing the remaining elements .
     * @throws IllegalArgumentException if the required number of elements to drop
     *                                  is negative.
     */
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

    /**
     * Drops elements from the supplied {@code Iterable} while the supplied {@code UnaryPredicate}
     * evaluates true for those elements and returns an {@code Iterable} effectively containing
     * the remaining elements. On iteration, each element retrieved from the {@code Iterable} is
     * evaluated by the {@code UnaryPredicate} and as soon as an element is found that does not satisfy the
     * {@code UnaryPredicate}, that element is returned as the first element in the {@code Iterable}
     * and subsequent element retrieval will retrieve the remaining elements in the {@code Iterable}.
     * If the supplied {@code Iterable} is empty, an effectively empty {@code Iterable} is returned.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the element discardal is performed
     * lazily, i.e., the elements are not discarded from the underlying {@code Iterable}
     * until it is iterated.</p>
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
     * are greater than 2. The following two lines are effectively equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; remainingElements = dropWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Iterable&lt;Integer&gt; equivalentElements = Literals.iterableWith(2, 1);
     * </pre>
     * </blockquote>
     * If the first element retrieved from the {@code Iterable} on iteration does not satisfy the
     * supplied {@code UnaryPredicate}, no elements are dropped and the entire inpu {@code Iterable}
     * is iterated. The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(2, 1, 4, 5, 3);
     *   Iterable&lt;Integer&gt; remainingElements = dropWhile(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Iterable&lt;Integer&gt; equivalentElements = Literals.iterableWith(2, 1, 4, 5, 3);
     * </pre>
     * </blockquote>
     * If all elements retrieved from the {@code Iterable} satisfy the supplied
     * {@code UnaryPredicate}, all elements are dropped and an effectively empty {@code Iterable} is returned.
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
     * Similarly, if the input {@code Iterable} contains no elements, an empty {@code Iterable}
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
     * @return An {@code Iterable} instance with all elements that satisfy the
     *         supplied {@code UnaryPredicate} dropped on iteration, effectively containing
     *         all remaining elements .
     */
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

    /**
     * Drops elements from the supplied {@code Iterable} until the supplied {@code UnaryPredicate}
     * evaluates true for an element and returns an {@code Iterable} effectively containing the remaining
     * elements. On iteration, each element retrieved from the {@code Iterable} is evaluated by the
     * {@code UnaryPredicate} and as soon as an element is found that satisfies the
     * {@code UnaryPredicate}, that element is returned as the first element in the {@code Iterable}
     * and subsequent element retrieval will retrieve the remaining elements in the {@code Iterable}.
     * If the supplied {@code Iterable} is empty, an effectively empty {@code Iterable} is returned.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the element discardal is performed
     * lazily, i.e., the elements are not discarded from the underlying {@code Iterable}
     * until it is iterated.</p>
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
     * found that is greater than 2. The following two lines are effectively equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; remainingElements = dropUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Iterable&lt;Integer&gt; equivalentElements = Literals.iterableWith(3, 4, 5);
     * </pre>
     * </blockquote>
     * If the first element retrieved from the {@code Iterable} on iteration satisfies the supplied
     * {@code UnaryPredicate}, no elements are dropped and the entire input {@code Iterable} is
     * iterated. The following two lines are equivalent in this case:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(4, 5, 6, 7, 8);
     *   Iterable&lt;Integer&gt; remainingElements = dropUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   Iterable&lt;Integer&gt; equivalentElements = Literals.iterableWith(4, 5, 6, 7, 8);
     * </pre>
     * </blockquote>
     * If no elements retrieved from the {@code Iterable} satisfy the supplied {@code UnaryPredicate},
     * all elements are dropped and an effectively empty {@code Iterable} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable(-1, 0, 1, 2);
     *   Iterable&lt;Integer&gt; remainingElements = dropUntil(elements, new Predicate&lt;Integer&gt;() {
     *       &#64;Override public boolean evaluate(Integer integer) {
     *          return integer > 2;
     *       }
     *   });
     *   remainingElements.isEmpty(); // => true
     * </pre>
     * </blockquote>
     * Similarly, if the input {@code Iterable} contains no elements, an empty {@code Iterable} is returned:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; elements = Literals.iterable();
     *   Iterable&lt;Integer&gt; remainingElements = dropUntil(elements, new Predicate&lt;Integer&gt;() {
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
     * @return An {@code Iterable} instance with all elements that do not satisfy the
     *         supplied {@code UnaryPredicate} dropped on iteration, effectively containing
     *         all remaining elements .
     */
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

    /**
     * Lazily applies the supplied {@code UnaryProcedure} to each element in the
     * supplied {@code Iterable}. As the returned {@code Iterable} is iterated,
     * each element is first passed to the {@link UnaryProcedure#execute(Object)} method
     * and then returned.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the application of the
     * supplied {@code UnaryProcedure} is also lazy, i.e., the procedure is not
     * applied to each element in the {@code Iterable} until it is iterated.</p>
     *
     * <p>Since the function being applied to each element is a procedure
     * it is inherently impure and thus must have side effects for any perceivable
     * difference to be observed.</p>
     *
     * <h4>Example Usage:</h4>
     * Consider a collection of {@code ApplicationForm} objects where an
     * {@code ApplicationForm} is defined by the following class:
     * <blockquote>
     * <pre>
     *     public class ApplicationForm {
     *         private String firstName;
     *         private String lastName;
     *         private String dateOfBirth;
     *
     *         public ApplicationForm(String firstName, String lastName, String dateOfBirth) {
     *             this.firstName = firstName;
     *             this.lastName = lastName;
     *             this.dateOfBirth = dateOfBirth;
     *         }
     *
     *         public String getFullName() {
     *             return firstName + lastName;
     *         };
     *
     *         ...
     *     }
     * </pre>
     * </blockquote>
     * Say we have an in memory repository of all submitted forms
     * <blockquote>
     * <pre>
     *     public class ApplicationFormRepository {
     *         private final Iterable&lt;ApplicationForm&gt; applicationForms = Literals.listWith(
     *                 new ApplicationForm("Joe", "Bloggs", "1985-01-14"),
     *                 new ApplicationForm("Amanda", "James", "1988-08-10"),
     *                 new ApplicationForm("Misha", "Kumar", "1983-12-28"));
     *
     *         ...
     *     }
     * </pre>
     * </blockquote>
     * and we wish to perform some form of batch processing using that collection.
     * However, to assist our production support team, we want to log out details
     * from each {@code ApplicationForm} instance before it is processed. We can
     * prepare the collection of {@code ApplicationForm} instances with logging
     * before returning it to the part of the codebase that requires it as follows:
     * <blockquote>
     * <pre>
     *     public class ApplicationFormRepository {
     *         ...
     *         private final Logger log = LogFactory.getInstance(this);
     *
     *         public Iterable&lt;ApplicationForm&gt; getAll() {
     *             return Lazily.each(applicationForms, new UnaryProcedure&lt;ApplicationForm&gt;() {
     *                 &#64;Override public void execute(ApplicationForm applicationForm) {
     *                     log.info("Currently processing application form for {}", applicationForm.getFullName());
     *                 }
     *             }
     *         }
     *     }
     * </pre>
     * </blockquote>
     * In this way, whenever the returned {@code Iterable} of {@code ApplicationForm}
     * instances is iterated, log statements will be generated using a mechanism transparent
     * to the consumer.
     *
     * @param iterable  The {@code Iterable} whose elements should each have the supplied
     *                  {@code UnaryProcedure} applied to them.
     * @param procedure A {@code UnaryProcedure} to lazily apply to each element in the
     *                  supplied {@code Iterable}.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Iterable} that on iteration will apply the supplied
     *         {@code UnaryProcedure} to each element before returning it.
     */
    public static <T> Iterable<T> each(final Iterable<T> iterable, final UnaryProcedure<? super T> procedure) {
        checkNotNull(procedure);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new EachIterator<T>(iterable.iterator(), procedure);
            }
        };
    }

    /**
     * Lazily applies the supplied {@code Action} to each element in the
     * supplied {@code Iterable}. As the returned {@code Iterable} is iterated,
     * each element is first passed to the {@link Action#on(Object)} method
     * and then returned.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the application of the
     * supplied {@code Action} is also lazy, i.e., the action is not
     * applied to each element in the {@code Iterable} until it is iterated.</p>
     *
     * <p>Since the function being applied to each element is an action
     * it is inherently impure and thus must have side effects for any perceivable
     * difference to be observed.</p>
     *
     * <p>This overload of {@link #each(Iterable, UnaryProcedure)} is provided to allow an
     * {@code Action} to be used in place of a {@code UnaryProcedure} to enhance readability
     * and better express intent. The contract of the function is identical to that of the
     * {@code UnaryProcedure} version of {@code each}.</p>
     *
     * <p>For example usage and further documentation, see {@link #each(Iterable, UnaryProcedure)}.</p>
     *
     * @param iterable The {@code Iterable} whose elements should each have the supplied
     *                 {@code Action} applied to them.
     * @param action   An {@code Action} to lazily apply to each element in the
     *                 supplied {@code Iterable}.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Iterable} that on iteration will apply the supplied
     *         {@code Action} to each element before returning it.
     */
    public static <T> Iterable<T> each(final Iterable<T> iterable, final Action<? super T> action) {
        return each(iterable, actionUnaryProcedure(checkNotNull(action)));
    }

    /**
     * Associates each element in the supplied {@code Iterable} with its related index in
     * that {@code Iterable}. Returns an {@code Iterable} of {@code Pair} instances where
     * the first entry in the {@code Pair} is the index, starting from zero, and the second
     * element is the element from the supplied {@code Iterable} at that index.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the enumeration of the
     * supplied {@code Iterable} is also lazy, i.e., the supplied {@code Iterable}
     * is not iterated with its elements being associated with their index until the
     * returned {@code Iterable} is iterated.</p>
     *
     * <p>If the supplied {@code Iterable} is empty, so is the returned {@code Iterable}.</p>
     *
     * <h4>Example Usage:</h4>
     * Given we have an {@code Iterable} of {@code Customer} instances for an online store
     * and they are ordered in the order they made purchases. The store owners decide to
     * run a campaign where every thousandth customer wins a prize. We can model this as
     * follows:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Customer&gt; customers = customerStore.getAll();
     *     Iterable&lt;Pair&lt;Integer, Customer&gt; enumeratedCustomers = enumerate(customers);
     *     Iterable&lt;Pair&lt;Integer, Customer&gt; prizeWinningEnumeratedCustomers = filter(customers, new Predicate&lt;Pair&lt;Integer, Customer&gt;&gt;() {
     *         public boolean evaluate(Pair&lt;Integer, Customer&gt; enumeratedCustomer) {
     *             return (enumeratedCustomer.getFirst() + 1) % 1000 == 0;
     *         }
     *     }
     *     Iterable&lt;Customer&gt; prizedCustomers = map(prizeWinningEnumeratedCustomers, new Mapper&lt;Pair&lt;Integer, Customer&gt;, Customer&gt;() {
     *         public Customer map(Pair&lt;Integer, Customer&gt; prizeWinningEnumeratedCustomer) {
     *             return prizeWinningEnumeratedCustomer.getSecond().withPrize();
     *         }
     *     }
     * </pre>
     * </blockquote>
     * In this case we added 1 to the index to account for zero based indexing. Note that the
     * entire calculation of determining the customers eligible for prizes is lazy in this
     * example.
     *
     * @param iterable The {@code Iterable} to be enumerated by index.
     * @param <T>      The type of the elements in the supplied {@code Iterable}.
     * @return An {@code Iterable} containing the elements from the supplied
     *         {@code Iterable} lazily associated to their index in that
     *         {@code Iterable} using {@code Pair} instances.
     */
    public static <T> Iterable<Pair<Integer, T>> enumerate(final Iterable<T> iterable) {
        return zip(integers(increasing()), iterable);
    }

    /**
     * Lazily indexes the supplied {@code Iterable} using the supplied {@code UnaryFunction}
     * on iteration of the returned {@code Iterable}. On iteration, the {@code UnaryFunction}
     * will be provided with each element in the input {@code Iterable}. The value returned
     * by the {@code UnaryFunction} will occupy the first slot in the returned {@code Pair}
     * instance and the element itself will occupy the second slot.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the element indexing is performed
     * lazily, i.e., no attempt is made to retrieve and index elements from the underlying
     * {@code Iterable} until it is iterated.</p>
     *
     * <h4>Example Usage:</h4>
     * Consider an {@code Iterable} of {@code String} instances representing words from a book.
     * Each word can be indexed using its length using to the following.
     * <blockquote>
     * <pre>
     *     Iterable&lt;String&gt; words = book.getCompleteWordList();
     *     Iterable&lt;Pair&lt;Integer, String&gt;&gt; wordsIndexedByLength = index(words, new UnaryFunction&ltString, Integer&gt;() {
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
     * @return An {@code Iterable} effectively containing {@code Pair} instances
     *         representing each element from the supplied {@code Iterable}
     *         indexed by the value returned by the supplied {@code UnaryFunction}
     *         when passed that element.
     */
    public static <S, T> Iterable<Pair<T, S>> index(Iterable<S> iterable, final UnaryFunction<? super S, T> function) {
        checkNotNull(function);
        return zip(map(iterable, new Mapper<S, T>() {
            public T map(S input) {
                return function.call(input);
            }
        }), iterable);
    }

    /**
     * Lazily indexes the supplied {@code Iterable} using the supplied {@code Indexer}
     * on iteration of the returned {@code Iterable}. On iteration, the {@code Indexer}
     * will be provided with each element in the input {@code Iterable}. The value returned
     * by the {@code Indexer} will occupy the first slot in the returned {@code Pair}
     * instance and the element itself will occupy the second slot.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the element indexing is performed
     * lazily, i.e., no attempt is made to retrieve and index elements from the underlying
     * {@code Iterable} until it is iterated.</p>
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
     * @return An {@code Iterable} effectively containing {@code Pair} instances
     *         representing each element from the supplied {@code Iterable}
     *         indexed by the value returned by the supplied {@code Indexer}
     *         when passed that element.
     */
    public static <S, T> Iterable<Pair<T, S>> index(Iterable<S> iterable, final Indexer<? super S, T> indexer) {
        checkNotNull(indexer);
        return index(iterable, indexerUnaryFunction(indexer));
    }

    /**
     * Lazily maps an {@code Iterable} of elements of type {@code S} into an
     * {@code Iterable} of elements of type {@code T} using the supplied
     * {@code UnaryFunction}.
     *
     * <p>As the returned {@code Iterable} is iterated, each element from the
     * input {@code Iterable} will be passed to the supplied {@code UnaryFunction}
     * and the value returned by the {@code UnaryFunction} will be returned
     * by the output {@code Iterable} in the input value's place. Thus, the order
     * in which elements are yielded from the input {@code Iterable} is maintained
     * in the output {@code Iterable}. For a more mathematical description of the
     * map higher order function, see the
     * <a href="http://en.wikipedia.org/wiki/Map_(higher-order_function)">
     * map article on Wikipedia</a>.
     *
     * <p>{@code map} does not discriminate against {@code null} values in the input
     * {@code Iterable}, they are passed to the function in the same way as any other
     * value. Similarly, any {@code null} values returned at iteration time are returned
     * by the output {@code Iterable}. Thus, the input and output {@code Iterable} instances
     * will always contain the same number of elements.</p>
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
     *   Iterable&lt;String&gt; names = Lazily.map(people, new UnaryFunction&lt;Person, String&gt;() {
     *       &#64;Override public String call(Person person) {
     *           return person.getLastName() + "-" + person.getFirstName;
     *       }
     *   });
     * </pre>
     * </blockquote>
     * The resulting {@code Iterable} is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Iterable&lt;String&gt; names = Literals.collectionWith(
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
     * @return An {@code Iterable} mapping each instance of {@code S} from the input
     *         {@code Iterable} to an instance of {@code T} using the supplied {@code UnaryFunction}.
     */
    public static <S, T> Iterable<T> map(final Iterable<S> iterable, final UnaryFunction<? super S, T> function) {
        checkNotNull(function);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new MappedIterator<S, T>(iterable.iterator(), function);
            }
        };
    }

    /**
     * Lazily maps an {@code Iterable} of elements of type {@code S} into a {@code Iterable}
     * of elements of type {@code T} using the supplied {@code Mapper}.
     *
     * <p>As the returned {@code Iterable} is iterated, each element from the input
     * {@code Iterable} will be passed to the supplied {@code Mapper} and the value
     * returned by the {@code Mapper} will be returned by the output {@code Iterable}
     * in the input value's place. Thus, the order in which elements are yielded from
     * the input {@code Iterable} is maintained in the output {@code Iterable}.
     * For a more mathematical description of the map higher order function, see the
     * <a href="http://en.wikipedia.org/wiki/Map_(higher-order_function)">
     * map article on Wikipedia</a>.
     *
     * <p>This overload of {@link #map(Iterable, UnaryFunction)} is provided to allow a
     * {@code Mapper} to be used in place of a {@code UnaryFunction} to enhance readability
     * and better express intent. The contract of the function is identical to that of the
     * {@code UnaryFunction} version of {@code map}.</p>
     *
     * <p>For example usage and further documentation, see {@link #map(Iterable, UnaryFunction)}.</p>
     *
     * @param iterable The {@code Iterable} of elements to be mapped.
     * @param mapper   A {@code Mapper} which, given an element from the input iterable,
     *                 returns that element mapped to a new value potentially of a different type.
     * @param <S>      The type of the input elements, i.e., the elements to map.
     * @param <T>      The type of the output elements, i.e., the mapped elements.
     * @return An {@code Iterable} mapping each instance of {@code S} from the input
     *         {@code Iterable} to an instance of {@code T} using the supplied {@code Mapper}.
     * @see #map(Iterable, UnaryFunction)
     */
    public static <S, T> Iterable<T> map(final Iterable<S> iterable, final Mapper<? super S, T> mapper) {
        checkNotNull(mapper);
        return map(iterable, mapperUnaryFunction(mapper));
    }

    /**
     * Lazily equates the elements in each of the supplied {@code Iterable} instances
     * using the supplied {@code BinaryPredicate} in the order in which they are yielded.
     * On iteration of the returned {@code Iterable}, an element is yielded from each
     * of the supplied {@code Iterable} instances and passed to the supplied
     * {@code BinaryPredicate}. The resulting {@code boolean} returned by the
     * {@code BinaryPredicate} is used as the return value for that iteration. Iteration
     * ends when one or both of the supplied {@code Iterable} instances is exhausted.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the equation of the
     * supplied {@code Iterable} instances is also lazy, i.e., the supplied
     * {@code Iterable} instances are not iterated and their elements are not equated
     * until the returned {@code Iterable} is iterated.</p>
     *
     * <h4>Example Usage:</h4>
     * Consider the situation where we have two very large {@code Iterable} instances
     * containing {@code Integer}s and we want to assess whether they are equal. In
     * order to ensure that they are equal we must iterate the entire {@code Iterable}.
     * In order to be sure they are not, it is enough to find the first pair of
     * values that are not equal. There is a likelihood this will happen before
     * the entire {@code Iterable} is traversed. This can be encoded as follows:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Integer&gt; first = firstNumberSource.fetch();
     *     Iterable&lt;Integer&gt; second = secondNumberSource.fetch();
     *     Iterable&lt;Boolean&gt; equalityIterable = equate(first, second, new BinaryPredicate&ltInteger, Integer&gt;() {
     *         &#64;Override public boolean evaluate(Integer first, Integer second) {
     *             return first.equals(second);
     *         }
     *     });
     *     Option&lt;Boolean&gt; equalityResult = Eagerly.first(equalityIterable, Predicates.equals(false));
     *     equalityResult.hasValue() // => not equal
     * </pre>
     * </blockquote>
     *
     * @param first     The first {@code Iterable} of elements to be compared.
     * @param second    The second {@code Iterable} of elements to be compared.
     * @param predicate A {@code BinaryPredicate} to be used to equate elements sequentially
     *                  from the supplied {@code Iterable} instances.
     * @param <T>       The type of the elements in the supplied {@code Iterables}.
     * @return An {@code Iterable} effectively containing the {@code boolean} results of
     *         equating the elements from the supplied {@code Iterable} instances in the
     *         order in which they are yielded.
     */
    public static <T> Iterable<Boolean> equate(Iterable<T> first, Iterable<T> second, final BinaryPredicate<? super T, ? super T> predicate) {
        checkNotNull(predicate);
        return map(zip(first, second), new Mapper<Pair<T, T>, Boolean>() {
            public Boolean map(Pair<T, T> input) {
                return predicate.evaluate(input.getFirst(), input.getSecond());
            }
        });
    }

    /**
     * Lazily equates the elements in each of the supplied {@code Iterable} instances
     * using the supplied {@code Equivalence} in the order in which they are yielded.
     * On iteration of the returned {@code Iterable}, an element is yielded from each
     * of the supplied {@code Iterable} instances and passed to the supplied
     * {@code Equivalence}. The resulting {@code boolean} returned by the
     * {@code Equivalence} is used as the return value for that iteration. Iteration
     * ends when one or both of the supplied {@code Iterable} instances is exhausted.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the equation of the
     * supplied {@code Iterable} instances is also lazy, i.e., the supplied
     * {@code Iterable} instances are not iterated and their elements are not equated
     * until the returned {@code Iterable} is iterated.</p>
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
     * @param equivalence An {@code Equivalence} to be used to equate elements sequentially
     *                    from the supplied {@code Iterable} instances.
     * @param <T>         The type of the elements in the supplied {@code Iterables}.
     * @return An {@code Iterable} effectively containing the {@code boolean} results of
     *         equating the elements from the supplied {@code Iterable} instances in the
     *         order in which they are yielded.
     */
    public static <T> Iterable<Boolean> equate(Iterable<T> first, Iterable<T> second, final Equivalence<? super T> equivalence) {
        return equate(first, second, equivalenceBinaryPredicate(checkNotNull(equivalence)));
    }

    /**
     * Lazily filters those elements from the input {@code Iterable} that
     * satisfy the supplied {@code UnaryPredicate} on iteration of the returned
     * {@code Iterable}. On iteration, the {@code UnaryPredicate} will be
     * provided with each element in the input {@code Iterable} and the element
     * will be returned if and only if the {@code UnaryPredicate} returns
     * {@code true}. If it returns {@code false}, the element will be discarded.
     *
     * <p>For a more complete description of the filter higher order function,
     * see the <a href="http://en.wikipedia.org/wiki/Filter_(higher-order_function)">
     * filter article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the filtering is performed
     * lazily, i.e., the {@code UnaryPredicate} is not applied to each element in the
     * input {@code Iterable} until the returned {@code Iterable} is iterated.</p>
     *
     * <p>{@code filter} does not discriminate against {@code null} values in the input
     * {@code Iterable}, they are passed to the {@code UnaryPredicate} in the same way as
     * any other value. Similarly, if the {@code UnaryPredicate} returns {@code true}
     * when called with a {@code null} value upon iteration, the {@code null} value will
     * be returned.</p>
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
     *   Iterable&lt;Pet&gt; names = filter(pets, new Predicate&lt;Pet&gt;() {
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
     * <p>The resulting {@code Iterable} is effectively equivalent to the following:</p>
     * <blockquote>
     * <pre>
     *   Iterable&lt;Pet&gt; names = Literals.iterableWith(
     *           new Dog("Fido"),
     *           new Dog("Bones"),
     *           new Dog("Graham"));
     * </pre>
     * </blockquote>
     *
     * @param iterable  The {@code Iterable} of elements to be filtered.
     * @param predicate A {@code UnaryPredicate} which, given an element from the input
     *                  {@code Iterable} returns {@code true} if that element should be
     *                  retained and {@code false} if it should be discarded.
     * @param <T>       The type of the elements to be filtered.
     * @return An {@code Iterable} effectively containing those elements from the input
     *         {@code Iterable} that evaluate to {@code true} when passed to the
     *         supplied {@code UnaryPredicate}.
     */
    public static <T> Iterable<T> filter(final Iterable<T> iterable, final UnaryPredicate<? super T> predicate) {
        checkNotNull(predicate);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new FilteredIterator<T>(iterable.iterator(), predicate);
            }
        };
    }

    /**
     * Lazily rejects those elements from the input {@code Iterable} that satisfy the
     * supplied {@code UnaryPredicate} on iteration of the returned {@code Iterable}.
     * On iteration, the {@code UnaryPredicate} will be provided with each element in
     * the input {@code Iterable} and the element will be returned if and only if the
     * {@code UnaryPredicate} returns {@code false}. If it returns {@code true},
     * the element will be discarded.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the rejection is performed
     * lazily, i.e., the {@code UnaryPredicate} is not applied to each element in the input
     * {@code Iterable} until the returned {@code Iterable} is iterated.</p>
     *
     * <p>{@code reject} does not discriminate against {@code null} values in the input
     * {@code Iterable}, they are passed to the {@code UnaryPredicate} in the same way
     * as any other value. Similarly, if the {@code UnaryPredicate} returns {@code false}
     * when called with a {@code null} value upon iteration, the {@code null} value will
     * be returned.</p>
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
     *   Iterable&lt;Pet&gt; nonFelinePets = reject(pets, new Predicate&lt;Pet&gt;() {
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
     * <p>The resulting {@code Iterable} is equivalent to the following:</p>
     * <blockquote>
     * <pre>
     *   Iterable&lt;Pet&gt; names = Literals.iterableWith(
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
     * @return An {@code Iterable} effectively containing those elements from the input
     *         {@code Iterable} that evaluate to {@code false} when passed to the
     *         supplied {@code UnaryPredicate}.
     */
    public static <T> Iterable<T> reject(final Iterable<T> iterable, final UnaryPredicate<? super T> predicate) {
        checkNotNull(predicate);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new FilteredIterator<T>(iterable.iterator(), new NotPredicate<T>(predicate));
            }
        };
    }

    /**
     * Lazily partitions the supplied {@code Iterable} into those elements that
     * satisfy the supplied {@code UnaryPredicate} and those elements that do not
     * satisfy the supplied {@code UnaryPredicate}. A {@code Pair} containing two
     * {@code Iterable} instances is returned. Those elements that satisfy the
     * supplied {@code UnaryPredicate} form an {@code Iterable} in the first slot
     * of the {@code Pair} and those that do not satisfy the supplied
     * {@code UnaryPredicate} form an {@code Iterable} in the second slot of the
     * {@code Pair}.
     *
     * <p>Since a {@code Pair} containing lazy {@code Iterable} instances is returned,
     * the partitioning is performed lazily, i.e., the {@code UnaryPredicate} is not
     * applied to each element in the input {@code Iterable} until the returned
     * {@code Iterable} instances are iterated.</p>
     *
     * <p>If no elements in the supplied {@code Iterable} satisfy the supplied
     * {@code UnaryPredicate}, the first slot in the returned {@code Pair}
     * will be occupied by an effectively empty {@code Iterable}. Similarly, if all
     * elements in the supplied {@code Iterable} satisfy the supplied
     * {@code UnaryPredicate}, the second slot in the returned {@code Pair} will be
     * occupied by an empty {@code Iterable}.</p>
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
     *     Pair&lt;Iterable&ltAccount&gt;, Iterable&lt;Account&gt&gt overdrawnPartition = partition(accounts, new Predicate&ltAccount&gt;() {
     *        &#64;Override public boolean evaluate(Account account) {
     *            return account.isOverdrawn();
     *        }
     *     });
     *     Iterable&lt;Account&gt; inTheRedAccounts = overdrawnPartition.getFirst();
     *     Iterable&lt;Account&gt; inTheBlackAccounts = overdrawnPartition.getSecond();
     * </pre>
     * </blockquote>
     *
     * @param iterable  An {@code Iterable} of elements to be partitioned based on
     *                  whether or not they satisfy the supplied {@code UnaryPredicate}.
     * @param predicate A {@code UnaryPredicate} to be used to evaluate which side of the
     *                  partition each element in the supplied {@code Iterable} should
     *                  reside.
     * @param <T>       The type of the elements in the supplied {@code Iterable}.
     * @return A {@code Pair} instance which effectively contains those elements
     *         from the supplied {@code Iterable} which satisfy the supplied
     *         {@code UnaryPredicate} in the first slot and those elements from
     *         the supplied {@code Iterable} which do not satisfy the supplied
     *         {@code UnaryPredicate} in the second slot.
     */
    public static <T> Pair<Iterable<T>, Iterable<T>> partition(Iterable<T> iterable, UnaryPredicate<? super T> predicate) {
        checkNotNull(predicate);
        return tuple(filter(iterable, predicate), reject(iterable, predicate));
    }

    /**
     * Lazily removes the first element from the supplied {@code Iterable} and
     * returns all remaining elements in an {@code Iterable}.
     *
     * <p>Since a lazy {@code Iterable} instance is returned, the removal of the first
     * element is performed lazily, i.e., the input {@code Iterable} is not
     * iterated until the returned {@code Iterable} instance is iterated.</p>
     *
     * <p>If the supplied {@code Iterable} contains only one element, the
     * returned {@code Iterable} will be effectively empty. Similarly, if the
     * supplied {@code Iterable} is empty, the returned {@code Iterable} will
     * be empty.</p>
     *
     * <h4>Example Usage:</h4>
     * Given the following {@code Iterable} of {@code Integer} instances:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Integer&gt; numbers = listWith(1, 2, 3, 4, 5, 6);
     * </pre>
     * </blockquote>
     * we can recursively sum the contents using the following method:
     * <blockquote>
     * <pre>
     *     public static class Mathematics {
     *         public static Integer sum(Iterable&lt;Integer&gt; numbers) {
     *             if (!numbers.hasNext()) {
     *                 return 0;
     *             }
     *             return first(numbers).get() + sum(rest(number));
     *         }
     *     }
     *
     *     ...
     *
     *     Integer result = Mathematics.sum(numbers); // => 21
     * </pre>
     * </blockquote>
     * Note that in reality, this would exhaust the stack if the input {@code Iterable}
     * was large enough and so instead should be replaced by some other form of
     * iteration.
     *
     * @param iterable The {@code Iterable} from which to remove the first element.
     * @param <T>      The type of the elements in the supplied {@code Iterable};
     * @return An {@code Iterable} effectively containing all elements from the supplied
     *         {@code Iterable} but the first in the same order as in the supplied
     *         {@code Iterable}.
     */
    public static <T> Iterable<T> rest(final Iterable<T> iterable) {
        return slice(iterable, 1, null, 1);
    }

    /**
     * Lazily slices a sub-sequence from the supplied {@code Iterable} according
     * to the supplied start index, stop index and step size. The start and stop
     * indices are both zero based and the step size is one based. The element
     * at the start index in the supplied {@code Iterable} will be included in the
     * returned {@code Iterable} whilst the element at the stop index will be
     * excluded from it. The step size indicates the number of steps to take
     * between included elements in the returned {@code Iterable}.
     *
     * <p>Since a lazy {@code Iterable} is returned, the sub-sequence is sliced
     * lazily, i.e., the supplied {@code Iterable} is not iterated for the
     * required sub-sequence until the returned {@code Iterable} is iterated.</p>
     *
     * <p>If the supplied start index is {@code null} or zero, the sub-sequence starts
     * at the beginning of the supplied {@code Iterable}. If it is negative then an
     * {@code IllegalArgumentException} is thrown.</p>
     *
     * <p>If the supplied stop index is {@code null} then the sub-sequence
     * ends at the end of the supplied {@code Iterable}. If it is negative then an
     * {@code IllegalArgumentException} is thrown.</p>
     *
     * <p>If the supplied step size is {@code null} then a default step size of
     * one will be assumed. If the supplied step size is less than one then
     * an {@code IllegalArgumentException} is thrown.</p>
     *
     * <p>If the start and stop indices are equal, an effectively empty
     * {@code Iterable} is returned. If the start index is greater than the
     * stop index then an {@code IllegalArgumentException} is thrown since
     * the sub-sequence can only be directed in the forward direction of the
     * elements in the {@code Iterable}. The slice semantics supported by
     * {@link Eagerly#slice(Iterable, Integer, Integer, Integer)} allow for
     * much more flexible sub-sequence slicing due to the eager nature of
     * the algorithm.</p>
     *
     * <p>In the case that the supplied start, stop and step values cannot
     * be satisfied due to a lack of elements in the supplied
     * {@code Iterable}, as many elements as possible will be present in the
     * returned {@code Iterable}. If the start index is greater than the
     * greatest index of all elements in the {@code Iterable} then an
     * effectively empty {@code Iterable} is returned.</p>
     *
     * <h4>Example Usage:</h4>
     * Given an {@code Iterable} of {@code DateTime} instances representing
     * some part of a year and given that the first {@code DateTime} is known
     * to be a Friday, we can slice out the first four Sundays as follows:
     * <blockquote>
     * <pre>
     *     Iterable&lt;DateTime&gt; availableBookingDates = travelCalendar.availableDatesFrom(DateTimes.thisFriday());
     *     Iterable&lt;DateTime&gt; nextFourAvailableSundays = slice(availableBookingDates, 2, 31, 7);
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
     * @return An {@code Iterable} effectively containing the sub-sequence of
     *         elements from the supplied {@code Iterable} specified by the
     *         supplied start and stop indices and the supplied step size.
     */
    public static <T> Iterable<T> slice(final Iterable<T> iterable, final Integer start, final Integer stop, final Integer step) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new SubSequenceIterator<T>(iterable.iterator(), start, stop, step);
            }
        };
    }

    /**
     * Lazily takes the cartesian product of the two supplied {@code Iterable}
     * instances generating an {@code Iterable} of tuples of size two. The
     * returned {@code Iterable} will contain {@code Pair} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable} and the second slot is occupied by an element from
     * the second supplied {@code Iterable}. The {@code Iterable} will
     * effectively contain a {@code Pair} for each possible selection of
     * elements for the slots from each of the corresponding {@code Iterable}
     * instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the cartesian product is taken
     * lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If any of the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite. Be aware that in the case of
     * an infinite {@code Iterable}, the current algorithm will lead to
     * minimal variability in the returned {@code Pair} instances, i.e.,
     * there is a possibility only one slot of the returned {@code Pair}
     * instances will ever change.</p>
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
     *     Iterable&lt;Pair&lt;Name, Location&gt;&gt; mappings = cartesianProduct(names, locations);
     * </pre>
     * </blockquote>
     * This is effectively equivalent to the following:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Pair&lt;Name, Location&gt;&gt; equivalent = iterableWith(
     *             tuple(name("Joe"), location("London")), tuple(name("Tim"), location("London")), tuple(name("Laura"), location("London")),
     *             tuple(name("Joe"), location("Berlin")), tuple(name("Tim"), location("Berlin")), tuple(name("Laura"), location("Berlin")));
     * </pre>
     * </blockquote>
     *
     * @param first  The first {@code Iterable} from which to form a cartesian product.
     * @param second The second {@code Iterable} from which to form a cartesian product.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @return An {@code Iterable} effectively containing the cartesian product
     *         of all elements in the supplied {@code Iterable} instances.
     */
    public static <R, S> Iterable<Pair<R, S>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second) {
        return map(
                cartesianProduct(iterableWith(first, second)),
                Mappers.<R, S>toPair());
    }

    /**
     * Lazily takes the cartesian product of the three supplied {@code Iterable}
     * instances generating an {@code Iterable} of tuples of size three. The
     * returned {@code Iterable} will contain {@code Triple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable} and the third slot is occupied
     * by an element from the third supplied {@code Iterable}. The {@code Iterable}
     * will effectively contain a {@code Triple} for each possible selection of
     * elements for the slots from each of the corresponding {@code Iterable}
     * instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the cartesian product is taken
     * lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If any of the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite. Be aware that in the case of
     * an infinite {@code Iterable}, the current algorithm will lead to
     * minimal variability in the returned {@code Triple} instances, i.e.,
     * there is a possibility only one slot of the returned {@code Triple}
     * instances will ever change.</p>
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
     * @return An {@code Iterable} effectively containing the cartesian product
     *         of all elements in the supplied {@code Iterable} instances.
     */
    public static <R, S, T> Iterable<Triple<R, S, T>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third) {
        return map(
                cartesianProduct(iterableWith(first, second, third)),
                Mappers.<R, S, T>toTriple());
    }

    /**
     * Lazily takes the cartesian product of the four supplied {@code Iterable}
     * instances generating an {@code Iterable} of tuples of size four. The
     * returned {@code Iterable} will contain {@code Quadruple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable}, the third slot is occupied
     * by an element from the third supplied {@code Iterable} and so on. The
     * {@code Iterable} will effectively contain a {@code Quadruple} for each
     * possible selection of elements for the slots from each of the corresponding
     * {@code Iterable} instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the cartesian product is taken
     * lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If any of the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite. Be aware that in the case of
     * an infinite {@code Iterable}, the current algorithm will lead to
     * minimal variability in the returned {@code Quadruple} instances, i.e.,
     * there is a possibility only one slot of the returned {@code Quadruple}
     * instances will ever change.</p>
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
     * @return An {@code Iterable} effectively containing the cartesian product
     *         of all elements in the supplied {@code Iterable} instances.
     */
    public static <R, S, T, U> Iterable<Quadruple<R, S, T, U>> cartesianProduct(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth) {
        return map(
                cartesianProduct(iterableWith(first, second, third, fourth)),
                Mappers.<R, S, T, U>toQuadruple());
    }

    /**
     * Lazily takes the cartesian product of the five supplied {@code Iterable}
     * instances generating an {@code Iterable} of tuples of size five. The
     * returned {@code Iterable} will contain {@code Quintuple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable}, the third slot is occupied
     * by an element from the third supplied {@code Iterable} and so on. The
     * {@code Iterable} will effectively contain a {@code Quintuple} for each
     * possible selection of elements for the slots from each of the corresponding
     * {@code Iterable} instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the cartesian product is taken
     * lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If any of the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite. Be aware that in the case of
     * an infinite {@code Iterable}, the current algorithm will lead to
     * minimal variability in the returned {@code Quintuple} instances, i.e.,
     * there is a possibility only one slot of the returned {@code Quintuple}
     * instances will ever change.</p>
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
     * @return An {@code Iterable} effectively containing the cartesian product
     *         of all elements in the supplied {@code Iterable} instances.
     */
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

    /**
     * Lazily takes the cartesian product of the six supplied {@code Iterable}
     * instances generating an {@code Iterable} of tuples of size six. The
     * returned {@code Iterable} will contain {@code Sextuple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable}, the third slot is occupied
     * by an element from the third supplied {@code Iterable} and so on. The
     * {@code Iterable} will effectively contain a {@code Sextuple} for each
     * possible selection of elements for the slots from each of the corresponding
     * {@code Iterable} instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the cartesian product is taken
     * lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If any of the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite. Be aware that in the case of
     * an infinite {@code Iterable}, the current algorithm will lead to
     * minimal variability in the returned {@code Sextuple} instances, i.e.,
     * there is a possibility only one slot of the returned {@code Sextuple}
     * instances will ever change.</p>
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
     * @return An {@code Iterable} effectively containing the cartesian product
     *         of all elements in the supplied {@code Iterable} instances.
     */
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

    /**
     * Lazily takes the cartesian product of the seven supplied {@code Iterable}
     * instances generating an {@code Iterable} of tuples of size seven. The
     * returned {@code Iterable} will contain {@code Septuple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable}, the third slot is occupied
     * by an element from the third supplied {@code Iterable} and so on. The
     * {@code Iterable} will effectively contain a {@code Septuple} for each
     * possible selection of elements for the slots from each of the corresponding
     * {@code Iterable} instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the cartesian product is taken
     * lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If any of the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite. Be aware that in the case of
     * an infinite {@code Iterable}, the current algorithm will lead to
     * minimal variability in the returned {@code Septuple} instances, i.e.,
     * there is a possibility only one slot of the returned {@code Septuple}
     * instances will ever change.</p>
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
     * @return An {@code Iterable} effectively containing the cartesian product
     *         of all elements in the supplied {@code Iterable} instances.
     */
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

    /**
     * Lazily takes the cartesian product of the eight supplied {@code Iterable}
     * instances generating an {@code Iterable} of tuples of size eight. The
     * returned {@code Iterable} will contain {@code Octuple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable}, the third slot is occupied
     * by an element from the third supplied {@code Iterable} and so on. The
     * {@code Iterable} will effectively contain a {@code Octuple} for each
     * possible selection of elements for the slots from each of the corresponding
     * {@code Iterable} instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the cartesian product is taken
     * lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If any of the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite. Be aware that in the case of
     * an infinite {@code Iterable}, the current algorithm will lead to
     * minimal variability in the returned {@code Octuple} instances, i.e.,
     * there is a possibility only one slot of the returned {@code Octuple}
     * instances will ever change.</p>
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
     * @return An {@code Iterable} effectively containing the cartesian product
     *         of all elements in the supplied {@code Iterable} instances.
     */
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

    /**
     * Lazily takes the cartesian product of the nine supplied {@code Iterable}
     * instances generating an {@code Iterable} of tuples of size nine. The
     * returned {@code Iterable} will contain {@code Nonuple} instances where
     * the first slot is occupied by an element from the first supplied
     * {@code Iterable}, the second slot is occupied by an element from
     * the second supplied {@code Iterable}, the third slot is occupied
     * by an element from the third supplied {@code Iterable} and so on. The
     * {@code Iterable} will effectively contain a {@code Nonuple} for each
     * possible selection of elements for the slots from each of the corresponding
     * {@code Iterable} instances.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the cartesian product is taken
     * lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If any of the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite. Be aware that in the case of
     * an infinite {@code Iterable}, the current algorithm will lead to
     * minimal variability in the returned {@code Nonuple} instances, i.e.,
     * there is a possibility only one slot of the returned {@code Nonuple}
     * instances will ever change.</p>
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
     * @return An {@code Iterable} effectively containing the cartesian product
     *         of all elements in the supplied {@code Iterable} instances.
     */
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

    /**
     * Lazily takes the cartesian product of the elements from all {@code Iterable}
     * instances in the supplied {@code Iterable} instance generating an {@code Iterable}
     * of {@code Iterable}s with each having as many elements as there were
     * {@code Iterable} instances in the input {@code Iterable}. The returned
     * {@code Iterable} will contain {@code Iterable} instances where the first position
     * is occupied by an element from the first {@code Iterable} in the supplied
     * {@code Iterable}, the second position is occupied by an element from the second
     * {@code Iterable} in the input {@code Iterable} and so on for each available
     * {@code Iterable}. The {@code Iterable} will effectively contain an {@code Iterable}
     * for each possible selection of elements for the positions from each of the
     * corresponding {@code Iterable} instances in the supplied {@code Iterable}. It is
     * understood that there are an absurd number of references to {@code Iterable} in
     * this definition, hopefully the example usage will be more descriptive.
     *
     * <p>For a more mathematical description of the cartesian product, see the
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">
     * cartesian product article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the cartesian product is taken
     * lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If any of the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite. Be aware that in the case of
     * an infinite {@code Iterable}, the current algorithm will lead to
     * minimal variability in the returned {@code Iterable} instances, i.e.,
     * there is a possibility only one position of the returned {@code Iterable}
     * instances will ever change.</p>
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
     *     Iterable&lt? extends Iterable&lt;?&gt;&gt; cartesianProduct = cartesianProduct(iterables);
     * </pre>
     * </blockquote>
     * This is effectively equivalent to the following:
     * <blockquote>
     * <pre>
     *     Iterable&lt;? extends Iterable&lt;?&gt;&gt; equivalentIterables = Literals.&ltIterable&lt?&gt;&gt;iterableWith(
     *             iterableWith(1, "first", false),
     *             iterableWith(1, "second", false),
     *             iterableWith(1, "third", false));
     *             iterableWith(2, "first", false),
     *             iterableWith(2, "second", false),
     *             iterableWith(2, "third", false));
     * </pre>
     * </blockquote>
     *
     * @param iterables An {@code Iterable} of {@code Iterable} instances for
     *                  which a cartesian product should be generated.
     * @return An {@code Iterable} of {@code Iterable} instances representing the
     *         cartesian product of the supplied {@code Iterable} of {@code Iterable}s.
     */
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

    /**
     * Lazily zips the elements from the two supplied {@code Iterable} instances
     * into a tuple of size two. On iteration of the returned {@code Iterable},
     * a {@code Pair} instance is instantiated with an element from the first
     * supplied {@code Iterable} in the first slot and an element from the
     * second supplied {@code Iterable} in the second slot. The iteration
     * is complete when one or both of the supplied {@code Iterable} instances
     * is exhausted.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the zipping is also
     * performed lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite.</p>
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
     * an {@code Iterable} containing {@code Pair}s with the number associated
     * to the textual name can be obtained as follows:
     * <blockquote>
     * <pre>
     *     Iterable&ltPair&ltInteger, String&gt;&gt; associations = zip(numbers, numberNames);
     * </pre>
     * </blockquote>
     * This is effectively equivalent to the following:
     * <blockquote>
     * <pre>
     *     Iterable&lt;Pair&lt;Integer, String&gt;&gt; associations = Literals.iterableWith(
     *             pair(1, "one"), pair(2, "two"), pair(3, "three"), pair(4, "four"), pair(5, "five"),
     *             pair(6, "six"), pair(7, "seven"), pair(8, "eight"), pair(9, "nine"), pair(10, "ten"));
     * </pre>
     * </blockquote>
     *
     * @param first  The first {@code Iterable} from which to construct a zip.
     * @param second The second {@code Iterable} from which to construct a zip.
     * @param <R>    The type of the elements in the first {@code Iterable}.
     * @param <S>    The type of the elements in the second {@code Iterable}.
     * @return An {@code Iterable} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
    public static <R, S> Iterable<Pair<R, S>> zip(
            Iterable<R> first,
            Iterable<S> second) {
        return map(zip(iterableWith(first, second)), Mappers.<R, S>toPair());
    }

    /**
     * Lazily zips the elements from the three supplied {@code Iterable} instances
     * into a tuple of size three. On iteration of the returned {@code Iterable},
     * a {@code Triple} instance is instantiated with an element from the first
     * supplied {@code Iterable} in the first slot, an element from the
     * second supplied {@code Iterable} in the second slot and an element from
     * the third supplied {@code Iterable} in the third slot. The iteration
     * is complete when one or more of the supplied {@code Iterable} instances
     * is exhausted.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the zipping is also
     * performed lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite.</p>
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
     * @return An {@code Iterable} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
    public static <R, S, T> Iterable<Triple<R, S, T>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third) {
        return map(zip(iterableWith(first, second, third)), Mappers.<R, S, T>toTriple());
    }

    /**
     * Lazily zips the elements from the four supplied {@code Iterable} instances
     * into a tuple of size four. On iteration of the returned {@code Iterable},
     * a {@code Quadruple} instance is instantiated with an element from the first
     * supplied {@code Iterable} in the first slot, an element from the second
     * supplied {@code Iterable} in the second slot, an element from the third
     * supplied {@code Iterable} in the third slot and so on. The iteration
     * is complete when one or more of the supplied {@code Iterable} instances
     * is exhausted.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the zipping is also
     * performed lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite.</p>
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
     * @return An {@code Iterable} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
    public static <R, S, T, U> Iterable<Quadruple<R, S, T, U>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth) {
        return map(zip(iterableWith(first, second, third, fourth)), Mappers.<R, S, T, U>toQuadruple());
    }

    /**
     * Lazily zips the elements from the five supplied {@code Iterable} instances
     * into a tuple of size five. On iteration of the returned {@code Iterable},
     * a {@code Quintuple} instance is instantiated with an element from the first
     * supplied {@code Iterable} in the first slot, an element from the second
     * supplied {@code Iterable} in the second slot, an element from the third
     * supplied {@code Iterable} in the third slot and so on. The iteration
     * is complete when one or more of the supplied {@code Iterable} instances
     * is exhausted.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the zipping is also
     * performed lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite.</p>
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
     * @return An {@code Iterable} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
    public static <R, S, T, U, V> Iterable<Quintuple<R, S, T, U, V>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth) {
        return map(zip(iterableWith(first, second, third, fourth, fifth)), Mappers.<R, S, T, U, V>toQuintuple());
    }

    /**
     * Lazily zips the elements from the six supplied {@code Iterable} instances
     * into a tuple of size six. On iteration of the returned {@code Iterable},
     * a {@code Quintuple} instance is instantiated with an element from the first
     * supplied {@code Iterable} in the first slot, an element from the second
     * supplied {@code Iterable} in the second slot, an element from the third
     * supplied {@code Iterable} in the third slot and so on. The iteration
     * is complete when one or more of the supplied {@code Iterable} instances
     * is exhausted.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the zipping is also
     * performed lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite.</p>
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
     * @return An {@code Iterable} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
    public static <R, S, T, U, V, W> Iterable<Sextuple<R, S, T, U, V, W>> zip(
            Iterable<R> first,
            Iterable<S> second,
            Iterable<T> third,
            Iterable<U> fourth,
            Iterable<V> fifth,
            Iterable<W> sixth) {
        return map(zip(iterableWith(first, second, third, fourth, fifth, sixth)), Mappers.<R, S, T, U, V, W>toSextuple());
    }

    /**
     * Lazily zips the elements from the seven supplied {@code Iterable} instances
     * into a tuple of size seven. On iteration of the returned {@code Iterable},
     * a {@code Quintuple} instance is instantiated with an element from the first
     * supplied {@code Iterable} in the first slot, an element from the second
     * supplied {@code Iterable} in the second slot, an element from the third
     * supplied {@code Iterable} in the third slot and so on. The iteration
     * is complete when one or more of the supplied {@code Iterable} instances
     * is exhausted.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the zipping is also
     * performed lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite.</p>
     *
     * <p>This overload of {@code zip} is provided to allow seven
     * {@code Iterable} instances to be zipped. For equivalent example usage for
     * the two {@code Iterable} case, see {@link #zip(Iterable, Iterable)}.</p>
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
     * @return An {@code Iterable} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
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

    /**
     * Lazily zips the elements from the eight supplied {@code Iterable} instances
     * into a tuple of size eight. On iteration of the returned {@code Iterable},
     * a {@code Quintuple} instance is instantiated with an element from the first
     * supplied {@code Iterable} in the first slot, an element from the second
     * supplied {@code Iterable} in the second slot, an element from the third
     * supplied {@code Iterable} in the third slot and so on. The iteration
     * is complete when one or more of the supplied {@code Iterable} instances
     * is exhausted.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the zipping is also
     * performed lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite.</p>
     *
     * <p>This overload of {@code zip} is provided to allow eight
     * {@code Iterable} instances to be zipped. For equivalent example usage for
     * the two {@code Iterable} case, see {@link #zip(Iterable, Iterable)}.</p>
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
     * @return An {@code Iterable} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
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

    /**
     * Lazily zips the elements from the nine supplied {@code Iterable} instances
     * into a tuple of size nine. On iteration of the returned {@code Iterable},
     * a {@code Quintuple} instance is instantiated with an element from the first
     * supplied {@code Iterable} in the first slot, an element from the second
     * supplied {@code Iterable} in the second slot, an element from the third
     * supplied {@code Iterable} in the third slot and so on. The iteration
     * is complete when one or more of the supplied {@code Iterable} instances
     * is exhausted.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the zipping is also
     * performed lazily, i.e., none of the supplied {@code Iterable} instances
     * is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite.</p>
     *
     * <p>This overload of {@code zip} is provided to allow nine
     * {@code Iterable} instances to be zipped. For equivalent example usage for
     * the two {@code Iterable} case, see {@link #zip(Iterable, Iterable)}.</p>
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
     * @return An {@code Iterable} containing tuples representing zipped
     *         elements from the supplied {@code Iterable} instances.
     */
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

    /**
     * Lazily zips the elements from all {@code Iterable} instances in the supplied
     * {@code Iterable} into an {@code Iterable} of {@code Iterable}s with each having
     * as many elements as there were {@code Iterable} instances in the input
     * {@code Iterable}. On iteration of the returned {@code Iterable}, an
     * {@code Iterable} is instantiated with an element from the first {@code Iterable}
     * in the first slot, an element from the second {@code Iterable} in the second
     * slot and so on. The iteration is complete when any one of the {@code Iterable}
     * instances in the supplied {@code Iterable} is exhausted. It is understood that
     * there are an absurd number of references to {@code Iterable} in this definition,
     * hopefully the example usage will be more descriptive.
     *
     * <p>For a more mathematical description of the zip function, see the
     * <a href="http://en.wikipedia.org/wiki/Convolution_(computer_science)">
     * zip article on Wikipedia</a>.</p>
     *
     * <p>Since a lazy {@code Iterable} is returned, the zipping is also performed
     * lazily, i.e., none of the {@code Iterable} instances in the supplied
     * {@code Iterable} instances is iterated until elements are yielded from the returned
     * {@code Iterable}.</p>
     *
     * <p>If any of the supplied {@code Iterable} instances is empty, the
     * returned {@code Iterable} is effectively empty. If the supplied
     * {@code Iterable} instances are infinite, then the returned
     * {@code Iterable} will also be infinite.</p>
     *
     * <p>Note that this overload of {@code zip} does not preserve type information
     * and so the returned {@code Iterable} will contain {@code Iterable} instances
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
     * we can zip them into an {@code Iterable} of {@code Iterable} instances as follows:
     * <blockquote>
     * <pre>
     *     Iterable&ltIterable&lt?&gt;&gt; iterables = Literals.&lt;Iterable&lt?&gt;&gt;iterableWith(first, second, third);
     *     Iterable&lt? extends Iterable&lt;?&gt;&gt; zippedIterables = zip(iterables);
     * </pre>
     * </blockquote>
     * This is effectively equivalent to the following:
     * <blockquote>
     * <pre>
     *     Iterable&lt;? extends Iterable&lt;?&gt;&gt; equivalentIterables = Literals.&ltIterable&lt?&gt;&gt;iterableWith(
     *             iterableWith(1, "first", false),
     *             iterableWith(2, "second", true),
     *             iterableWith(3, "third", false));
     * </pre>
     * </blockquote>
     *
     * @param iterables An {@code Iterable} of {@code Iterable} instances to be zipped.
     * @return An {@code Iterable} of {@code Iterable} instances representing the
     *         zipped contents of the supplied {@code Iterable} of {@code Iterable}s.
     */
    public static Iterable<? extends Iterable<?>> zip(final Iterable<? extends Iterable<?>> iterables) {
        return new Iterable<Iterable<?>>() {
            public Iterator<Iterable<?>> iterator() {
                final Iterable<? extends Iterator<?>> iterators = Eagerly.map(iterables, toIterators());
                return new ZippedIterator(iterators);
            }
        };
    }

    public static <S, T> Iterable<T> comprehension(final Mapper<? super S, T> mapper, final Iterable<S> source, final Iterable<Predicate<S>> predicates) {
        checkNotNull(mapper);
        checkContainsNoNulls(predicates);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new ComprehensionIterator<S, T>(mapper, source.iterator(), predicates);
            }
        };
    }
    public static <S, T> Iterable<T> comprehension(Mapper<? super S, T> mapper, final Iterable<S> source, Predicate<S> predicate) {
        return comprehension(mapper, source, iterableWith(predicate));
    }
}
