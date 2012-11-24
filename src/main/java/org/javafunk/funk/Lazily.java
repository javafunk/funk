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
     * <p>This override of {@link #each(Iterable, UnaryProcedure)} is provided to allow an
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
     * <p>This override of {@link #map(Iterable, UnaryFunction)} is provided to allow a
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

    /**
     * Filters and retains those elements from the input {@code Iterable} that
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
     * lazily, i.e., the {@code UnaryPredicate} is applied to each element in the input
     * {@code Iterable} until the returned {@code Iterable} is iterated.</p>
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
     * @return An {@code Iterable} effectively containing those elements of type {@code T}
     *         from the input {@code Iterable} that evaluate to {@code true} when passed to
     *         the supplied {@code UnaryPredicate}.
     */
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
