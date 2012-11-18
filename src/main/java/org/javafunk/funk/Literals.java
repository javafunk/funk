/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.google.common.collect.Multiset;
import org.javafunk.funk.builders.ArrayBuilder;
import org.javafunk.funk.builders.CollectionBuilder;
import org.javafunk.funk.builders.IterableBuilder;
import org.javafunk.funk.builders.IteratorBuilder;
import org.javafunk.funk.builders.ListBuilder;
import org.javafunk.funk.builders.MapBuilder;
import org.javafunk.funk.builders.MultisetBuilder;
import org.javafunk.funk.builders.SetBuilder;
import org.javafunk.funk.datastructures.tuples.Nonuple;
import org.javafunk.funk.datastructures.tuples.Octuple;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.datastructures.tuples.Quadruple;
import org.javafunk.funk.datastructures.tuples.Quintuple;
import org.javafunk.funk.datastructures.tuples.Septuple;
import org.javafunk.funk.datastructures.tuples.Sextuple;
import org.javafunk.funk.datastructures.tuples.Single;
import org.javafunk.funk.datastructures.tuples.Triple;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.javafunk.funk.Classes.uncheckedInstantiate;
import static org.javafunk.funk.datastructures.tuples.Nonuple.nonuple;
import static org.javafunk.funk.datastructures.tuples.Octuple.octuple;
import static org.javafunk.funk.datastructures.tuples.Pair.pair;
import static org.javafunk.funk.datastructures.tuples.Quadruple.quadruple;
import static org.javafunk.funk.datastructures.tuples.Quintuple.quintuple;
import static org.javafunk.funk.datastructures.tuples.Septuple.septuple;
import static org.javafunk.funk.datastructures.tuples.Sextuple.sextuple;
import static org.javafunk.funk.datastructures.tuples.Single.single;
import static org.javafunk.funk.datastructures.tuples.Triple.triple;

public class Literals {
    private Literals() {}

    /**
     * Returns an empty immutable {@code Iterable} instance.
     *
     * <p>This form of literal is most suited to direct assignment to a variable
     * since in this case, the type {@code E} is inferred from the variable
     * declaration. For example:
     * <blockquote>
     * <pre>
     *   Iterable&lt;String&gt; strings = iterable();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param <E> The type of the elements contained in the {@code Iterable}.
     * @return An {@code Iterable} instance over the type {@code E} containing no elements.
     */
    public static <E> Iterable<E> iterable() {
        return new IterableBuilder<E>().build();
    }

    /**
     * Returns an empty immutable {@code Iterable} instance of the supplied concrete class.
     *
     * <p>The supplied class must have a public no-argument constructor, otherwise
     * an {@code IllegalArgumentException} will be thrown.</p>
     *
     * @param iterableClass The class of the {@code Iterable} implementation to be
     *                      instantiated.
     * @param <E>           The type of the elements contained in the {@code Iterable}.
     * @return An {@code Iterable} instance over the type {@code E} of the concrete
     *         type specified by the supplied {@code Class}.
     * @throws IllegalArgumentException if the supplied class does not have
     *                                  a public no-argument constructor.
     */
    @SuppressWarnings("unchecked")
    public static <E> Iterable<E> iterable(Class<? extends Iterable> iterableClass) {
        return uncheckedInstantiate(iterableClass);
    }

    /**
     * Returns an empty immutable {@code Iterable} instance over the type
     * of the supplied {@code Class}.
     *
     * <p>This form of literal is most suited to inline usage such as when passing an
     * empty iterable as a parameter in a method call since it reads more clearly than
     * {@link #iterable()}. For example, compare the following:
     * <blockquote>
     * <pre>
     *   public class Account {
     *       public Account(Money balance, List&lt;Money&gt; transactions) {
     *           ...
     *       }
     *
     *       ...
     *   }
     *
     *   Account account1 = new Account(new Money(0), Literals.&lt;Money&gt;iterable());
     *   Account account2 = new Account(new Money(0), iterableOf(Money.class));
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code Iterable}
     * @param <E>          The type of the elements contained in the {@code Iterable}.
     * @return An {@code Iterable} instance over the type {@code E} containing no elements.
     */
    public static <E> Iterable<E> iterableOf(Class<E> elementClass) {
        return new IterableBuilder<E>().build();
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E}
     * containing all elements from the supplied {@code Iterable}. The order of
     * the elements in the resulting {@code Iterable} is determined by the order in
     * which they are yielded from the supplied {@code Iterable}.
     *
     * <p>This form of literal is useful when an immutable copy of an {@code Iterable}
     * is required. For example:
     * <blockquote>
     * <pre>
     *   public class Product {
     *     public Iterable&lt;Integer&gt; getSizes() {
     *       return iterableFrom(sizes);
     *     }
     *
     *     ...
     *   }
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elements An {@code Iterable} of elements from which an {@code Iterable} should
     *                 be constructed.
     * @param <E>      The type of the elements to be contained in the returned
     *                 {@code Iterable}.
     * @return An {@code Iterable} over the type {@code E} containing all elements from the
     *         supplied {@code Iterable} in the order they are yielded.
     */
    public static <E> Iterable<E> iterableFrom(Iterable<? extends E> elements) {
        return new IterableBuilder<E>().with(elements).build();
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E}
     * containing all elements from the supplied array. The order of the elements
     * in the resulting {@code Iterable} is the same as the order of the elements
     * in the array.
     *
     * <p>For example, the following:
     * <blockquote>
     * <pre>
     *   String[] strings = new String[]{"one", "two", "three"};
     *   Iterable&lt;String&gt; iterableOfStrings = Literals.iterableFrom(strings);
     * </pre>
     * </blockquote>
     * is equivalent to:
     * <blockquote>
     * <pre>
     *   Iterable&ltString&gt; iterableOfStrings = Literals.iterableWith("one", "two", "three");
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementArray An array of elements from which an {@code Iterable} should be
     *                     constructed.
     * @param <E>          The type of the elements to be contained in the returned
     *                     {@code Iterable}.
     * @return An {@code Iterable} over the type {@code E} containing all elements from the
     *         supplied array in the same order as the supplied array.
     */
    public static <E> Iterable<E> iterableFrom(E[] elementArray) {
        return new IterableBuilder<E>().with(elementArray).build();
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied element.
     *
     * @param e   An element from which to construct an {@code Iterable}.
     * @param <E> The type of the element contained in the returned {@code Iterable}.
     * @return An {@code Iterable} instance over type {@code E} containing the supplied element.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e) {
        return iterableFrom(asList(e));
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterable} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterable}.
     * @param e2  The second element from which to construct an {@code Iterable}.
     * @param <E> The type of the elements contained in the returned {@code Iterable}.
     * @return An {@code Iterable} instance over type {@code E} containing the supplied elements.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2) {
        return iterableFrom(asList(e1, e2));
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterable} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterable}.
     * @param e2  The second element from which to construct an {@code Iterable}.
     * @param e3  The third element from which to construct an {@code Iterable}.
     * @param <E> The type of the elements contained in the returned {@code Iterable}.
     * @return An {@code Iterable} instance over type {@code E} containing the supplied elements.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3) {
        return iterableFrom(asList(e1, e2, e3));
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterable} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterable}.
     * @param e2  The second element from which to construct an {@code Iterable}.
     * @param e3  The third element from which to construct an {@code Iterable}.
     * @param e4  The fourth element from which to construct an {@code Iterable}.
     * @param <E> The type of the elements contained in the returned {@code Iterable}.
     * @return An {@code Iterable} instance over type {@code E} containing the supplied elements.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4) {
        return iterableFrom(asList(e1, e2, e3, e4));
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterable} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterable}.
     * @param e2  The second element from which to construct an {@code Iterable}.
     * @param e3  The third element from which to construct an {@code Iterable}.
     * @param e4  The fourth element from which to construct an {@code Iterable}.
     * @param e5  The fifth element from which to construct an {@code Iterable}.
     * @param <E> The type of the elements contained in the returned {@code Iterable}.
     * @return An {@code Iterable} instance over type {@code E} containing the supplied elements.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5) {
        return iterableFrom(asList(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterable} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterable}.
     * @param e2  The second element from which to construct an {@code Iterable}.
     * @param e3  The third element from which to construct an {@code Iterable}.
     * @param e4  The fourth element from which to construct an {@code Iterable}.
     * @param e5  The fifth element from which to construct an {@code Iterable}.
     * @param e6  The sixth element from which to construct an {@code Iterable}.
     * @param <E> The type of the elements contained in the returned {@code Iterable}.
     * @return An {@code Iterable} instance over type {@code E} containing the supplied elements.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterable} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterable}.
     * @param e2  The second element from which to construct an {@code Iterable}.
     * @param e3  The third element from which to construct an {@code Iterable}.
     * @param e4  The fourth element from which to construct an {@code Iterable}.
     * @param e5  The fifth element from which to construct an {@code Iterable}.
     * @param e6  The sixth element from which to construct an {@code Iterable}.
     * @param e7  The seventh element from which to construct an {@code Iterable}.
     * @param <E> The type of the elements contained in the returned {@code Iterable}.
     * @return An {@code Iterable} instance over type {@code E} containing the supplied elements.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterable} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterable}.
     * @param e2  The second element from which to construct an {@code Iterable}.
     * @param e3  The third element from which to construct an {@code Iterable}.
     * @param e4  The fourth element from which to construct an {@code Iterable}.
     * @param e5  The fifth element from which to construct an {@code Iterable}.
     * @param e6  The sixth element from which to construct an {@code Iterable}.
     * @param e7  The seventh element from which to construct an {@code Iterable}.
     * @param e8  The eighth element from which to construct an {@code Iterable}.
     * @param <E> The type of the elements contained in the returned {@code Iterable}.
     * @return An {@code Iterable} instance over type {@code E} containing the supplied elements.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterable} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterable}.
     * @param e2  The second element from which to construct an {@code Iterable}.
     * @param e3  The third element from which to construct an {@code Iterable}.
     * @param e4  The fourth element from which to construct an {@code Iterable}.
     * @param e5  The fifth element from which to construct an {@code Iterable}.
     * @param e6  The sixth element from which to construct an {@code Iterable}.
     * @param e7  The seventh element from which to construct an {@code Iterable}.
     * @param e8  The eighth element from which to construct an {@code Iterable}.
     * @param e9  The ninth element from which to construct an {@code Iterable}.
     * @param <E> The type of the elements contained in the returned {@code Iterable}.
     * @return An {@code Iterable} instance over type {@code E} containing the supplied elements.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterable} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterable}.
     * @param e2  The second element from which to construct an {@code Iterable}.
     * @param e3  The third element from which to construct an {@code Iterable}.
     * @param e4  The fourth element from which to construct an {@code Iterable}.
     * @param e5  The fifth element from which to construct an {@code Iterable}.
     * @param e6  The sixth element from which to construct an {@code Iterable}.
     * @param e7  The seventh element from which to construct an {@code Iterable}.
     * @param e8  The eighth element from which to construct an {@code Iterable}.
     * @param e9  The ninth element from which to construct an {@code Iterable}.
     * @param e10 The tenth element from which to construct an {@code Iterable}.
     * @param <E> The type of the elements contained in the returned {@code Iterable}.
     * @return An {@code Iterable} instance over type {@code E} containing the supplied elements.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an immutable {@code Iterable} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterable} is the same as the order
     * of the elements in the argument list.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, an {@link IterableBuilder} can be used instead.</p>
     *
     * @param e1    The first element from which to construct an {@code Iterable}.
     * @param e2    The second element from which to construct an {@code Iterable}.
     * @param e3    The third element from which to construct an {@code Iterable}.
     * @param e4    The fourth element from which to construct an {@code Iterable}.
     * @param e5    The fifth element from which to construct an {@code Iterable}.
     * @param e6    The sixth element from which to construct an {@code Iterable}.
     * @param e7    The seventh element from which to construct an {@code Iterable}.
     * @param e8    The eighth element from which to construct an {@code Iterable}.
     * @param e9    The ninth element from which to construct an {@code Iterable}.
     * @param e10   The tenth element from which to construct an {@code Iterable}.
     * @param e11on The remaining elements from which to construct an {@code Iterable}.
     * @param <E>   The type of the elements contained in the returned {@code Iterable}.
     * @return an {@code Iterable} instance over type {@code E} containing the supplied elements.
     */
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build();
    }

    /**
     * Returns an {@code IterableBuilder} containing no elements.
     *
     * <h4>Example Usage:</h4>
     * An {@code IterableBuilder} can be used to assemble an {@code Iterable} as follows:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; iterable = Literals.&lt;Integer&gt;iterableBuilder()
     *           .with(1, 2, 3)
     *           .and(4, 5, 6)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Integer&gt; iterable = Literals.iterableWith(1, 2, 3, 4, 5, 6);
     * </pre>
     * </blockquote>
     * The advantage of the {@code IterableBuilder} is that the iterable can be built up from
     * individual objects, arrays or existing iterables. See {@link IterableBuilder} for
     * further details.
     *
     * @param <E> The type of the elements contained in the {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over the type {@code E} containing no elements.
     */
    public static <E> IterableBuilder<E> iterableBuilder() {
        return new IterableBuilder<E>();
    }

    /**
     * Returns an {@code IterableBuilder} over the type of the supplied {@code Class}
     * containing no elements.
     *
     * <h4>Example Usage:</h4>
     * An {@code IterableBuilder} can be used to assemble an {@code Iterable} as follows:
     * <blockquote>
     * <pre>
     *   Iterable&lt;String&gt; iterable = iterableBuilderOf(String.class)
     *           .with("first", "second", "third")
     *           .and(new String[]{"fourth", "fifth"})
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Iterable&lt;String&gt; iterable = Literals.iterableWith("first", "second", "third", "fourth", "fifth");
     * </pre>
     * </blockquote>
     * The advantage of the {@code IterableBuilder} is that the iterable can be built
     * up from individual objects, arrays or existing iterables. See {@link IterableBuilder}
     * for further details.
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code IterableBuilder}
     * @param <E>          The type of the elements contained in the {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over the type {@code E} containing no
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderOf(Class<E> elementClass) {
        return new IterableBuilder<E>();
    }

    /**
     * Returns an {@code IterableBuilder} over type {@code E} initialised with the elements
     * contained in the supplied {@code Iterable}.
     *
     * <h4>Example Usage:</h4>
     * An {@code IterableBuilder} can be used to assemble an {@code Iterable} from two existing
     * {@code Collection} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Character&gt; firstCollection = Literals.collectionWith('a', 'b', 'c');
     *   Collection&lt;Character&gt; secondCollection = Literals.collectionWith('d', 'e', 'f');
     *   Iterable&lt;Character&gt; iterable = iterableBuilderFrom(firstCollection)
     *           .with(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Character&gt; iterable = Literals.iterableWith('a', 'b', 'c', 'd', 'e', 'f');
     * </pre>
     * </blockquote>
     * The advantage of the {@code IterableBuilder} is that the iterable can be built up from
     * individual objects, arrays or existing iterables. See {@link IterableBuilder} for
     * further details.
     *
     * @param elements An {@code Iterable} containing elements with which the
     *                 {@code IterableBuilder} should be initialised.
     * @param <E>      The type of the elements contained in the {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> IterableBuilder<E> iterableBuilderFrom(Iterable<? extends E> elements) {
        return new IterableBuilder<E>().with(elements);
    }

    /**
     * Returns an {@code IterableBuilder} over type {@code E} initialised with the elements
     * contained in the supplied array.
     *
     * <h4>Example Usage:</h4>
     * An {@code IterableBuilder} can be used to assemble an {@code Iterable} from two existing
     * arrays as follows:
     * <blockquote>
     * <pre>
     *   Long[] firstArray = new Long[]{1L, 2L, 3L};
     *   Long[] secondArray = new Long[]{3L, 4L, 5L};
     *   Iterable&lt;Long&gt; iterable = iterableBuilderFrom(firstArray)
     *           .with(secondArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Long&gt; iterable = Literals.iterableWith(1L, 2L, 3L, 3L, 4L, 5L);
     * </pre>
     * </blockquote>
     * The advantage of the {@code IterableBuilder} is that the iterable can be built up from
     * individual objects, arrays or existing iterables. See {@link IterableBuilder} for
     * further details.
     *
     * @param elementArray An array containing elements with which the
     *                     {@code IterableBuilder} should be initialised.
     * @param <E>          The type of the elements contained in the {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over the type {@code E} containing
     *         the elements from the supplied array.
     */
    public static <E> IterableBuilder<E> iterableBuilderFrom(E[] elementArray) {
        return new IterableBuilder<E>().with(elementArray);
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing
     * the supplied element.
     *
     * @param e   The element to be added to the {@code IterableBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         element.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e) {
        return iterableBuilderFrom(iterableWith(e));
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IterableBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IterableBuilder}.
     * @param e2  The second element to be added to the {@code IterableBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2) {
        return iterableBuilderFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IterableBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IterableBuilder}.
     * @param e2  The second element to be added to the {@code IterableBuilder}.
     * @param e3  The third element to be added to the {@code IterableBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3) {
        return iterableBuilderFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IterableBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IterableBuilder}.
     * @param e2  The second element to be added to the {@code IterableBuilder}.
     * @param e3  The third element to be added to the {@code IterableBuilder}.
     * @param e4  The fourth element to be added to the {@code IterableBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4) {
        return iterableBuilderFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IterableBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IterableBuilder}.
     * @param e2  The second element to be added to the {@code IterableBuilder}.
     * @param e3  The third element to be added to the {@code IterableBuilder}.
     * @param e4  The fourth element to be added to the {@code IterableBuilder}.
     * @param e5  The fifth element to be added to the {@code IterableBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5) {
        return iterableBuilderFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IterableBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IterableBuilder}.
     * @param e2  The second element to be added to the {@code IterableBuilder}.
     * @param e3  The third element to be added to the {@code IterableBuilder}.
     * @param e4  The fourth element to be added to the {@code IterableBuilder}.
     * @param e5  The fifth element to be added to the {@code IterableBuilder}.
     * @param e6  The sixth element to be added to the {@code IterableBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return iterableBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IterableBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IterableBuilder}.
     * @param e2  The second element to be added to the {@code IterableBuilder}.
     * @param e3  The third element to be added to the {@code IterableBuilder}.
     * @param e4  The fourth element to be added to the {@code IterableBuilder}.
     * @param e5  The fifth element to be added to the {@code IterableBuilder}.
     * @param e6  The sixth element to be added to the {@code IterableBuilder}.
     * @param e7  The seventh element to be added to the {@code IterableBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return iterableBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IterableBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IterableBuilder}.
     * @param e2  The second element to be added to the {@code IterableBuilder}.
     * @param e3  The third element to be added to the {@code IterableBuilder}.
     * @param e4  The fourth element to be added to the {@code IterableBuilder}.
     * @param e5  The fifth element to be added to the {@code IterableBuilder}.
     * @param e6  The sixth element to be added to the {@code IterableBuilder}.
     * @param e7  The seventh element to be added to the {@code IterableBuilder}.
     * @param e8  The eighth element to be added to the {@code IterableBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return iterableBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IterableBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IterableBuilder}.
     * @param e2  The second element to be added to the {@code IterableBuilder}.
     * @param e3  The third element to be added to the {@code IterableBuilder}.
     * @param e4  The fourth element to be added to the {@code IterableBuilder}.
     * @param e5  The fifth element to be added to the {@code IterableBuilder}.
     * @param e6  The sixth element to be added to the {@code IterableBuilder}.
     * @param e7  The seventh element to be added to the {@code IterableBuilder}.
     * @param e8  The eighth element to be added to the {@code IterableBuilder}.
     * @param e9  The ninth element to be added to the {@code IterableBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return iterableBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IterableBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IterableBuilder}.
     * @param e2  The second element to be added to the {@code IterableBuilder}.
     * @param e3  The third element to be added to the {@code IterableBuilder}.
     * @param e4  The fourth element to be added to the {@code IterableBuilder}.
     * @param e5  The fifth element to be added to the {@code IterableBuilder}.
     * @param e6  The sixth element to be added to the {@code IterableBuilder}.
     * @param e7  The seventh element to be added to the {@code IterableBuilder}.
     * @param e8  The eighth element to be added to the {@code IterableBuilder}.
     * @param e9  The ninth element to be added to the {@code IterableBuilder}.
     * @param e10 The tenth element to be added to the {@code IterableBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return iterableBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an {@code IterableBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IterableBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, an {@link IterableBuilder} instance can be used directly with
     * multiple method calls accumulating the builder contents.</p>
     *
     * @param e1    The first element to be added to the {@code IterableBuilder}.
     * @param e2    The second element to be added to the {@code IterableBuilder}.
     * @param e3    The third element to be added to the {@code IterableBuilder}.
     * @param e4    The fourth element to be added to the {@code IterableBuilder}.
     * @param e5    The fifth element to be added to the {@code IterableBuilder}.
     * @param e6    The sixth element to be added to the {@code IterableBuilder}.
     * @param e7    The seventh element to be added to the {@code IterableBuilder}.
     * @param e8    The eighth element to be added to the {@code IterableBuilder}.
     * @param e9    The ninth element to be added to the {@code IterableBuilder}.
     * @param e10   The tenth element to be added to the {@code IterableBuilder}.
     * @param e11on The remaining elements to be added to the {@code IterableBuilder}. The elements
     *              will be added to the {@code IterableBuilder} in the order they are defined in the
     *              variadic argument..
     * @param <E>   The type of the elements contained in the returned {@code IterableBuilder}.
     * @return An {@code IterableBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return iterableBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on);
    }

    /**
     * Returns an empty immutable {@code Iterator} instance.
     *
     * <p>This form of literal is most suited to direct assignment to a variable
     * since in this case, the type {@code E} is inferred from the variable
     * declaration. For example:
     * <blockquote>
     * <pre>
     *   Iterator&lt;String&gt; strings = iterator();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param <E> The type of the elements contained in the {@code Iterator}.
     * @return An {@code Iterator} instance over the type {@code E} containing no elements.
     */
    public static <E> Iterator<E> iterator() {
        return new IteratorBuilder<E>().build();
    }

    /**
     * Returns an empty immutable {@code Iterator} instance of the supplied concrete class.
     *
     * <p>The supplied class must have a public no-argument constructor, otherwise
     * an {@code IllegalArgumentException} will be thrown.</p>
     *
     * @param iteratorClass The class of the {@code Iterator} implementation to be
     *                      instantiated.
     * @param <E>           The type of the elements contained in the {@code Iterator}.
     * @return An {@code Iterator} instance over the type {@code E} of the concrete
     *         type specified by the supplied {@code Class}.
     * @throws IllegalArgumentException if the supplied class does not have
     *                                  a public no-argument constructor.
     */
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iterator(Class<? extends Iterator> iteratorClass) {
        return uncheckedInstantiate(iteratorClass);
    }

    /**
     * Returns an empty immutable {@code Iterator} instance over the type
     * of the supplied {@code Class}.
     *
     * <p>This form of literal is most suited to inline usage such as when passing an
     * empty iterator as a parameter in a method call since it reads more clearly than
     * {@link #iterator()}. For example, compare the following:
     * <blockquote>
     * <pre>
     *   public class Iterators {
     *       public static &lt;T&gt; Iterator&lt;T&gt; buffer(Iterator&lt;T&gt; stream) {
     *           ...
     *       }
     *
     *       ...
     *   }
     *
     *   Iterator&lt;Character&gt; bufferedIterator1 = Iterators.buffer(Literals.&lt;Character&gt;iterator());
     *   Iterator&lt;Character&gt; bufferedIterator2 = Iterators.buffer(iterableOf(Character.class));
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code Iterator}
     * @param <E>          The type of the elements contained in the {@code Iterator}.
     * @return An {@code Iterator} instance over the type {@code E} containing no elements.
     */
    public static <E> Iterator<E> iteratorOf(Class<E> elementClass) {
        return new IteratorBuilder<E>().build();
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E}
     * containing all elements from the supplied {@code Iterable}. The order of
     * the elements in the resulting {@code Iterator} is determined by the order in
     * which they are yielded from the supplied {@code Iterable}.
     *
     * <p>This form of literal is useful when an immutable {@code Iterator} from an
     * {@code Iterable} is required. For example:
     * <blockquote>
     * <pre>
     *   public class DataStructure&lt;T&gt; implements Iterable&lt;T&gt; {
     *     private final Iterable&ltT&gt backingStore;
     *
     *     public Iterator&lt;T&gt; iterator() {
     *       return iteratorFrom(backingStore);
     *     }
     *
     *     ...
     *   }
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elements An {@code Iterable} of elements from which an {@code Iterator} should
     *                 be constructed.
     * @param <E>      The type of the elements to be contained in the returned
     *                 {@code Iterator}.
     * @return An {@code Iterator} over the type {@code E} containing all elements from the
     *         supplied {@code Iterable} in the order they are yielded.
     */
    public static <E> Iterator<E> iteratorFrom(Iterable<? extends E> elements) {
        return new IteratorBuilder<E>().with(elements).build();
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E}
     * containing all elements from the supplied array. The order of the elements
     * in the resulting {@code Iterator} is the same as the order of the elements
     * in the array.
     *
     * <p>For example, the following:
     * <blockquote>
     * <pre>
     *   String[] strings = new String[]{"one", "two", "three"};
     *   Iterator&lt;String&gt; iteratorOfStrings = Literals.iteratorFrom(strings);
     * </pre>
     * </blockquote>
     * is equivalent to:
     * <blockquote>
     * <pre>
     *   Iterator&ltString&gt; iteratorOfStrings = Literals.iteratorWith("one", "two", "three");
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementArray An array of elements from which an {@code Iterator} should be
     *                     constructed.
     * @param <E>          The type of the elements to be contained in the returned
     *                     {@code Iterator}.
     * @return An {@code Iterator} over the type {@code E} containing all elements from the
     *         supplied array in the same order as the supplied array.
     */
    public static <E> Iterator<E> iteratorFrom(E[] elementArray) {
        return new IteratorBuilder<E>().with(elementArray).build();
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied element.
     *
     * @param e   An element from which to construct an {@code Iterator}.
     * @param <E> The type of the element contained in the returned {@code Iterator}.
     * @return An {@code Iterator} instance over type {@code E} containing the supplied element.
     */
    public static <E> Iterator<E> iteratorWith(E e) {
        return iteratorFrom(iterableWith(e));
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterator} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterator}.
     * @param e2  The second element from which to construct an {@code Iterator}.
     * @param <E> The type of the elements contained in the returned {@code Iterator}.
     * @return An {@code Iterator} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Iterator<E> iteratorWith(E e1, E e2) {
        return iteratorFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterator} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterator}.
     * @param e2  The second element from which to construct an {@code Iterator}.
     * @param e3  The third element from which to construct an {@code Iterator}.
     * @param <E> The type of the elements contained in the returned {@code Iterator}.
     * @return An {@code Iterator} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3) {
        return iteratorFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterator} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterator}.
     * @param e2  The second element from which to construct an {@code Iterator}.
     * @param e3  The third element from which to construct an {@code Iterator}.
     * @param e4  The fourth element from which to construct an {@code Iterator}.
     * @param <E> The type of the elements contained in the returned {@code Iterator}.
     * @return An {@code Iterator} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4) {
        return iteratorFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterator} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterator}.
     * @param e2  The second element from which to construct an {@code Iterator}.
     * @param e3  The third element from which to construct an {@code Iterator}.
     * @param e4  The fourth element from which to construct an {@code Iterator}.
     * @param e5  The fifth element from which to construct an {@code Iterator}.
     * @param <E> The type of the elements contained in the returned {@code Iterator}.
     * @return An {@code Iterator} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5) {
        return iteratorFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterator} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterator}.
     * @param e2  The second element from which to construct an {@code Iterator}.
     * @param e3  The third element from which to construct an {@code Iterator}.
     * @param e4  The fourth element from which to construct an {@code Iterator}.
     * @param e5  The fifth element from which to construct an {@code Iterator}.
     * @param e6  The sixth element from which to construct an {@code Iterator}.
     * @param <E> The type of the elements contained in the returned {@code Iterator}.
     * @return An {@code Iterator} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return iteratorFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterator} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterator}.
     * @param e2  The second element from which to construct an {@code Iterator}.
     * @param e3  The third element from which to construct an {@code Iterator}.
     * @param e4  The fourth element from which to construct an {@code Iterator}.
     * @param e5  The fifth element from which to construct an {@code Iterator}.
     * @param e6  The sixth element from which to construct an {@code Iterator}.
     * @param e7  The seventh element from which to construct an {@code Iterator}.
     * @param <E> The type of the elements contained in the returned {@code Iterator}.
     * @return An {@code Iterator} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return iteratorFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterator} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterator}.
     * @param e2  The second element from which to construct an {@code Iterator}.
     * @param e3  The third element from which to construct an {@code Iterator}.
     * @param e4  The fourth element from which to construct an {@code Iterator}.
     * @param e5  The fifth element from which to construct an {@code Iterator}.
     * @param e6  The sixth element from which to construct an {@code Iterator}.
     * @param e7  The seventh element from which to construct an {@code Iterator}.
     * @param e8  The eighth element from which to construct an {@code Iterator}.
     * @param <E> The type of the elements contained in the returned {@code Iterator}.
     * @return An {@code Iterator} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return iteratorFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterator} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterator}.
     * @param e2  The second element from which to construct an {@code Iterator}.
     * @param e3  The third element from which to construct an {@code Iterator}.
     * @param e4  The fourth element from which to construct an {@code Iterator}.
     * @param e5  The fifth element from which to construct an {@code Iterator}.
     * @param e6  The sixth element from which to construct an {@code Iterator}.
     * @param e7  The seventh element from which to construct an {@code Iterator}.
     * @param e8  The eighth element from which to construct an {@code Iterator}.
     * @param e9  The ninth element from which to construct an {@code Iterator}.
     * @param <E> The type of the elements contained in the returned {@code Iterator}.
     * @return An {@code Iterator} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return iteratorFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterator} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct an {@code Iterator}.
     * @param e2  The second element from which to construct an {@code Iterator}.
     * @param e3  The third element from which to construct an {@code Iterator}.
     * @param e4  The fourth element from which to construct an {@code Iterator}.
     * @param e5  The fifth element from which to construct an {@code Iterator}.
     * @param e6  The sixth element from which to construct an {@code Iterator}.
     * @param e7  The seventh element from which to construct an {@code Iterator}.
     * @param e8  The eighth element from which to construct an {@code Iterator}.
     * @param e9  The ninth element from which to construct an {@code Iterator}.
     * @param e10 The tenth element from which to construct an {@code Iterator}.
     * @param <E> The type of the elements contained in the returned {@code Iterator}.
     * @return An {@code Iterator} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return iteratorFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an immutable {@code Iterator} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Iterator} is the same as the order
     * of the elements in the argument list.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, an {@link IteratorBuilder} can be used instead.</p>
     *
     * @param e1    The first element from which to construct an {@code Iterator}.
     * @param e2    The second element from which to construct an {@code Iterator}.
     * @param e3    The third element from which to construct an {@code Iterator}.
     * @param e4    The fourth element from which to construct an {@code Iterator}.
     * @param e5    The fifth element from which to construct an {@code Iterator}.
     * @param e6    The sixth element from which to construct an {@code Iterator}.
     * @param e7    The seventh element from which to construct an {@code Iterator}.
     * @param e8    The eighth element from which to construct an {@code Iterator}.
     * @param e9    The ninth element from which to construct an {@code Iterator}.
     * @param e10   The tenth element from which to construct an {@code Iterator}.
     * @param e11on The remaining elements from which to construct an {@code Iterator}.
     * @param <E>   The type of the elements contained in the returned {@code Iterator}.
     * @return an {@code Iterator} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return iteratorBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on).build();
    }

    /**
     * Returns an {@code IteratorBuilder} containing no elements.
     *
     * <h4>Example Usage:</h4>
     * An {@code IteratorBuilder} can be used to assemble an {@code Iterator} as follows:
     * <blockquote>
     * <pre>
     *   Iterator&lt;Double&gt; iterator = Literals.&lt;Double&gt;iteratorBuilder()
     *           .with(1.34, 2.2, 3.5)
     *           .and(4, 5.78, 6.21)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Iterator&lt;Double&gt; iterator = Literals.iteratorWith(1.34, 2.2, 3.5, 4, 5.78, 6.21);
     * </pre>
     * </blockquote>
     * The advantage of the {@code IteratorBuilder} is that the iterator can be built up from
     * individual objects, arrays or existing iterables. See {@link IteratorBuilder} for
     * further details.
     *
     * @param <E> The type of the elements contained in the {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over the type {@code E} containing no elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilder() {
        return new IteratorBuilder<E>();
    }

    /**
     * Returns an {@code IteratorBuilder} over the type of the supplied {@code Class}
     * containing no elements.
     *
     * <h4>Example Usage:</h4>
     * An {@code IteratorBuilder} can be used to assemble an {@code Iterator} as follows:
     * <blockquote>
     * <pre>
     *   Iterator&lt;Integer&gt; iterator = iteratorBuilderOf(Integer.class)
     *           .with(new Integer[]{65, 72})
     *           .and(95, 43, 20)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Iterator&lt;Integer&gt; iterator = Literals.iteratorWith(65, 72, 95, 43, 20);
     * </pre>
     * </blockquote>
     * The advantage of the {@code IteratorBuilder} is that the iterator can be built
     * up from individual objects, arrays or existing iterables. See {@link IteratorBuilder}
     * for further details.
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code IteratorBuilder}
     * @param <E>          The type of the elements contained in the {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over the type {@code E} containing no
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderOf(Class<E> elementClass) {
        return new IteratorBuilder<E>();
    }

    /**
     * Returns an {@code IteratorBuilder} over type {@code E} initialised with the elements
     * contained in the supplied {@code Iterator}.
     *
     * <h4>Example Usage:</h4>
     * An {@code IteratorBuilder} can be used to assemble an {@code Iterator} from two existing
     * {@code Collection} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;String&gt; firstCollection = Literals.collectionWith("a", "b", "c");
     *   Collection&lt;String&gt; secondCollection = Literals.collectionWith("d", "e", "f");
     *   Iterator&lt;String&gt; iterator = iteratorBuilderFrom(firstCollection)
     *           .with(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Iterator&lt;String&gt; iterator = Literals.iteratorWith("a", "b", "c", "d", "e", "f");
     * </pre>
     * </blockquote>
     * The advantage of the {@code IteratorBuilder} is that the iterator can be built up from
     * individual objects, arrays or existing iterables. See {@link IteratorBuilder} for
     * further details.
     *
     * @param elements An {@code Iterable} containing elements with which the
     *                 {@code IteratorBuilder} should be initialised.
     * @param <E>      The type of the elements contained in the {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderFrom(Iterable<? extends E> elements) {
        return new IteratorBuilder<E>().with(elements);
    }

    /**
     * Returns an {@code IteratorBuilder} over type {@code E} initialised with the elements
     * contained in the supplied array.
     *
     * <h4>Example Usage:</h4>
     * An {@code IteratorBuilder} can be used to assemble an {@code Iterator} from two existing
     * arrays as follows:
     * <blockquote>
     * <pre>
     *   Integer[] firstArray = new Integer[]{1, 2, 3};
     *   Integer[] secondArray = new Long[]{3, 4, 5};
     *   Iterator&lt;Integer&gt; iterator = iteratorBuilderFrom(firstArray)
     *           .with(secondArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Iterator&lt;Integer&gt; iterator = Literals.iteratorWith(1, 2, 3, 3, 4, 5);
     * </pre>
     * </blockquote>
     * The advantage of the {@code IteratorBuilder} is that the iterator can be built up from
     * individual objects, arrays or existing iterables. See {@link IteratorBuilder} for
     * further details.
     *
     * @param elementArray An array containing elements with which the
     *                     {@code IteratorBuilder} should be initialised.
     * @param <E>          The type of the elements contained in the {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over the type {@code E} containing
     *         the elements from the supplied array.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderFrom(E[] elementArray) {
        return new IteratorBuilder<E>().with(elementArray);
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing
     * the supplied element.
     *
     * @param e   The element to be added to the {@code IteratorBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         element.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e) {
        return iteratorBuilderFrom(iterableWith(e));
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IteratorBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IteratorBuilder}.
     * @param e2  The second element to be added to the {@code IteratorBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2) {
        return iteratorBuilderFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IteratorBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IteratorBuilder}.
     * @param e2  The second element to be added to the {@code IteratorBuilder}.
     * @param e3  The third element to be added to the {@code IteratorBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3) {
        return iteratorBuilderFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IteratorBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IteratorBuilder}.
     * @param e2  The second element to be added to the {@code IteratorBuilder}.
     * @param e3  The third element to be added to the {@code IteratorBuilder}.
     * @param e4  The fourth element to be added to the {@code IteratorBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4) {
        return iteratorBuilderFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IteratorBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IteratorBuilder}.
     * @param e2  The second element to be added to the {@code IteratorBuilder}.
     * @param e3  The third element to be added to the {@code IteratorBuilder}.
     * @param e4  The fourth element to be added to the {@code IteratorBuilder}.
     * @param e5  The fifth element to be added to the {@code IteratorBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5) {
        return iteratorBuilderFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IteratorBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IteratorBuilder}.
     * @param e2  The second element to be added to the {@code IteratorBuilder}.
     * @param e3  The third element to be added to the {@code IteratorBuilder}.
     * @param e4  The fourth element to be added to the {@code IteratorBuilder}.
     * @param e5  The fifth element to be added to the {@code IteratorBuilder}.
     * @param e6  The sixth element to be added to the {@code IteratorBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return iteratorBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IteratorBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IteratorBuilder}.
     * @param e2  The second element to be added to the {@code IteratorBuilder}.
     * @param e3  The third element to be added to the {@code IteratorBuilder}.
     * @param e4  The fourth element to be added to the {@code IteratorBuilder}.
     * @param e5  The fifth element to be added to the {@code IteratorBuilder}.
     * @param e6  The sixth element to be added to the {@code IteratorBuilder}.
     * @param e7  The seventh element to be added to the {@code IteratorBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return iteratorBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IteratorBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IteratorBuilder}.
     * @param e2  The second element to be added to the {@code IteratorBuilder}.
     * @param e3  The third element to be added to the {@code IteratorBuilder}.
     * @param e4  The fourth element to be added to the {@code IteratorBuilder}.
     * @param e5  The fifth element to be added to the {@code IteratorBuilder}.
     * @param e6  The sixth element to be added to the {@code IteratorBuilder}.
     * @param e7  The seventh element to be added to the {@code IteratorBuilder}.
     * @param e8  The eighth element to be added to the {@code IteratorBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return iteratorBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IteratorBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IteratorBuilder}.
     * @param e2  The second element to be added to the {@code IteratorBuilder}.
     * @param e3  The third element to be added to the {@code IteratorBuilder}.
     * @param e4  The fourth element to be added to the {@code IteratorBuilder}.
     * @param e5  The fifth element to be added to the {@code IteratorBuilder}.
     * @param e6  The sixth element to be added to the {@code IteratorBuilder}.
     * @param e7  The seventh element to be added to the {@code IteratorBuilder}.
     * @param e8  The eighth element to be added to the {@code IteratorBuilder}.
     * @param e9  The ninth element to be added to the {@code IteratorBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return iteratorBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IteratorBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code IteratorBuilder}.
     * @param e2  The second element to be added to the {@code IteratorBuilder}.
     * @param e3  The third element to be added to the {@code IteratorBuilder}.
     * @param e4  The fourth element to be added to the {@code IteratorBuilder}.
     * @param e5  The fifth element to be added to the {@code IteratorBuilder}.
     * @param e6  The sixth element to be added to the {@code IteratorBuilder}.
     * @param e7  The seventh element to be added to the {@code IteratorBuilder}.
     * @param e8  The eighth element to be added to the {@code IteratorBuilder}.
     * @param e9  The ninth element to be added to the {@code IteratorBuilder}.
     * @param e10 The tenth element to be added to the {@code IteratorBuilder}.
     * @param <E> The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return iteratorBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an {@code IteratorBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code IteratorBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, an {@link IteratorBuilder} instance can be used directly with
     * multiple method calls accumulating the builder contents.</p>
     *
     * @param e1    The first element to be added to the {@code IteratorBuilder}.
     * @param e2    The second element to be added to the {@code IteratorBuilder}.
     * @param e3    The third element to be added to the {@code IteratorBuilder}.
     * @param e4    The fourth element to be added to the {@code IteratorBuilder}.
     * @param e5    The fifth element to be added to the {@code IteratorBuilder}.
     * @param e6    The sixth element to be added to the {@code IteratorBuilder}.
     * @param e7    The seventh element to be added to the {@code IteratorBuilder}.
     * @param e8    The eighth element to be added to the {@code IteratorBuilder}.
     * @param e9    The ninth element to be added to the {@code IteratorBuilder}.
     * @param e10   The tenth element to be added to the {@code IteratorBuilder}.
     * @param e11on The remaining elements to be added to the {@code IteratorBuilder}. The elements
     *              will be added to the {@code IteratorBuilder} in the order they are defined in the
     *              variadic argument.
     * @param <E>   The type of the elements contained in the returned {@code IteratorBuilder}.
     * @return An {@code IteratorBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return iteratorBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on);
    }

    /**
     * Returns an empty immutable {@code Collection} instance.
     *
     * <p>This form of literal is most suited to direct assignment to a variable
     * since in this case, the type {@code E} is inferred from the variable
     * declaration. For example:
     * <blockquote>
     * <pre>
     *   Collection&lt;Long&gt; numbers = collection();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param <E> The type of the elements contained in the {@code Collection}.
     * @return A {@code Collection} instance over the type {@code E} containing no elements.
     */
    public static <E> Collection<E> collection() {
        return new CollectionBuilder<E>().build();
    }

    /**
     * Returns an empty immutable {@code Collection} instance of the supplied concrete class.
     *
     * <p>The supplied class must have a public no-argument constructor, otherwise
     * an {@code IllegalArgumentException} will be thrown.</p>
     *
     * @param collectionClass The class of the {@code Collection} implementation to be
     *                        instantiated.
     * @param <E>             The type of the elements contained in the {@code Collection}.
     * @return A {@code Collection} instance over the type {@code E} of the concrete
     *         type specified by the supplied {@code Class}.
     * @throws IllegalArgumentException if the supplied class does not have
     *                                  a public no-argument constructor.
     */
    public static <E> Collection<E> collection(Class<? extends Collection> collectionClass) {
        return new CollectionBuilder<E>().build(collectionClass);
    }

    /**
     * Returns an empty immutable {@code Collection} instance over the type
     * of the supplied {@code Class}.
     *
     * <p>This form of literal is most suited to inline usage such as when passing an
     * empty collection as a parameter in a method call since it reads more clearly than
     * {@link #collection()}. For example, compare the following:
     * <blockquote>
     * <pre>
     *   public class Tree {
     *       ...
     *
     *       public void addNode(Node node, Collection&lt;Attribute&gt; attributes) {
     *           ...
     *       }
     *   }
     *
     *   tree.addNode(new LeafNode(), Literals.&lt;Attribute&gt;collection());
     *   tree.addNode(new LeafNode(), collectionOf(Attribute.class));
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code Collection}
     * @param <E>          The type of the elements contained in the {@code Collection}.
     * @return A {@code Collection} instance over the type {@code E} containing no elements.
     */
    public static <E> Collection<E> collectionOf(Class<E> elementClass) {
        return new CollectionBuilder<E>().build();
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E}
     * containing all elements from the supplied {@code Iterable}. The order of
     * the elements in the resulting {@code Collection} is determined by the order in
     * which they are yielded from the supplied {@code Iterable}.
     *
     * <p>This form of literal is useful when an object conforming to the {@code Collection}
     * interface is required and only an {@code Iterable} is available. For example:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Word&gt; words = book.getWords();
     *   statusBar.displayWordCount(collectionFrom(words).size());
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elements An {@code Iterable} of elements from which a {@code Collection} should
     *                 be constructed.
     * @param <E>      The type of the elements to be contained in the returned
     *                 {@code Collection}.
     * @return A {@code Collection} over the type {@code E} containing all elements from the
     *         supplied {@code Iterable} in the order they are yielded.
     */
    public static <E> Collection<E> collectionFrom(Iterable<? extends E> elements) {
        return new CollectionBuilder<E>().with(elements).build();
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E}
     * containing all elements from the supplied array. The order of the elements
     * in the resulting {@code Collection} is the same as the order of the elements
     * in the array.
     *
     * <p>For example, the following:
     * <blockquote>
     * <pre>
     *   String[] strings = new String[]{"one", "one", "two", "three"};
     *   Collection&lt;String&gt; collectionOfStrings = Literals.collectionFrom(strings);
     * </pre>
     * </blockquote>
     * is equivalent to:
     * <blockquote>
     * <pre>
     *   Collection&ltString&gt; collectionOfStrings = Literals.collectionWith("one", "one", "two", "three");
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementArray An array of elements from which a {@code Collection} should be
     *                     constructed.
     * @param <E>          The type of the elements to be contained in the returned
     *                     {@code Collection}.
     * @return A {@code Collection} over the type {@code E} containing all elements from the
     *         supplied array in the same order as the supplied array.
     */
    public static <E> Collection<E> collectionFrom(E[] elementArray) {
        return new CollectionBuilder<E>().with(elementArray).build();
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied element.
     *
     * @param e   An element from which to construct a {@code Collection}.
     * @param <E> The type of the element contained in the returned {@code Collection}.
     * @return A {@code Collection} instance over type {@code E} containing the supplied element.
     */
    public static <E> Collection<E> collectionWith(E e) {
        return collectionFrom(iterableWith(e));
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Collection} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code Collection}.
     * @param e2  The second element from which to construct a {@code Collection}.
     * @param <E> The type of the elements contained in the returned {@code Collection}.
     * @return A {@code Collection} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Collection<E> collectionWith(E e1, E e2) {
        return collectionFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Collection} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code Collection}.
     * @param e2  The second element from which to construct a {@code Collection}.
     * @param e3  The third element from which to construct a {@code Collection}.
     * @param <E> The type of the elements contained in the returned {@code Collection}.
     * @return A {@code Collection} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Collection<E> collectionWith(E e1, E e2, E e3) {
        return collectionFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Collection} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code Collection}.
     * @param e2  The second element from which to construct a {@code Collection}.
     * @param e3  The third element from which to construct a {@code Collection}.
     * @param e4  The fourth element from which to construct a {@code Collection}.
     * @param <E> The type of the elements contained in the returned {@code Collection}.
     * @return A {@code Collection} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4) {
        return collectionFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Collection} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code Collection}.
     * @param e2  The second element from which to construct a {@code Collection}.
     * @param e3  The third element from which to construct a {@code Collection}.
     * @param e4  The fourth element from which to construct a {@code Collection}.
     * @param e5  The fifth element from which to construct a {@code Collection}.
     * @param <E> The type of the elements contained in the returned {@code Collection}.
     * @return A {@code Collection} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5) {
        return collectionFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Collection} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code Collection}.
     * @param e2  The second element from which to construct a {@code Collection}.
     * @param e3  The third element from which to construct a {@code Collection}.
     * @param e4  The fourth element from which to construct a {@code Collection}.
     * @param e5  The fifth element from which to construct a {@code Collection}.
     * @param e6  The sixth element from which to construct a {@code Collection}.
     * @param <E> The type of the elements contained in the returned {@code Collection}.
     * @return A {@code Collection} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return collectionFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Collection} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code Collection}.
     * @param e2  The second element from which to construct a {@code Collection}.
     * @param e3  The third element from which to construct a {@code Collection}.
     * @param e4  The fourth element from which to construct a {@code Collection}.
     * @param e5  The fifth element from which to construct a {@code Collection}.
     * @param e6  The sixth element from which to construct a {@code Collection}.
     * @param e7  The seventh element from which to construct a {@code Collection}.
     * @param <E> The type of the elements contained in the returned {@code Collection}.
     * @return A {@code Collection} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return collectionFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Collection} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code Collection}.
     * @param e2  The second element from which to construct a {@code Collection}.
     * @param e3  The third element from which to construct a {@code Collection}.
     * @param e4  The fourth element from which to construct a {@code Collection}.
     * @param e5  The fifth element from which to construct a {@code Collection}.
     * @param e6  The sixth element from which to construct a {@code Collection}.
     * @param e7  The seventh element from which to construct a {@code Collection}.
     * @param e8  The eighth element from which to construct a {@code Collection}.
     * @param <E> The type of the elements contained in the returned {@code Collection}.
     * @return A {@code Collection} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return collectionFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Collection} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code Collection}.
     * @param e2  The second element from which to construct a {@code Collection}.
     * @param e3  The third element from which to construct a {@code Collection}.
     * @param e4  The fourth element from which to construct a {@code Collection}.
     * @param e5  The fifth element from which to construct a {@code Collection}.
     * @param e6  The sixth element from which to construct a {@code Collection}.
     * @param e7  The seventh element from which to construct a {@code Collection}.
     * @param e8  The eighth element from which to construct a {@code Collection}.
     * @param e9  The ninth element from which to construct a {@code Collection}.
     * @param <E> The type of the elements contained in the returned {@code Collection}.
     * @return A {@code Collection} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return collectionFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Collection} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code Collection}.
     * @param e2  The second element from which to construct a {@code Collection}.
     * @param e3  The third element from which to construct a {@code Collection}.
     * @param e4  The fourth element from which to construct a {@code Collection}.
     * @param e5  The fifth element from which to construct a {@code Collection}.
     * @param e6  The sixth element from which to construct a {@code Collection}.
     * @param e7  The seventh element from which to construct a {@code Collection}.
     * @param e8  The eighth element from which to construct a {@code Collection}.
     * @param e9  The ninth element from which to construct a {@code Collection}.
     * @param e10 The tenth element from which to construct a {@code Collection}.
     * @param <E> The type of the elements contained in the returned {@code Collection}.
     * @return A {@code Collection} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return collectionFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an immutable {@code Collection} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code Collection} is the same as the order
     * of the elements in the argument list.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, an {@link CollectionBuilder} can be used instead.</p>
     *
     * @param e1    The first element from which to construct a {@code Collection}.
     * @param e2    The second element from which to construct a {@code Collection}.
     * @param e3    The third element from which to construct a {@code Collection}.
     * @param e4    The fourth element from which to construct a {@code Collection}.
     * @param e5    The fifth element from which to construct a {@code Collection}.
     * @param e6    The sixth element from which to construct a {@code Collection}.
     * @param e7    The seventh element from which to construct a {@code Collection}.
     * @param e8    The eighth element from which to construct a {@code Collection}.
     * @param e9    The ninth element from which to construct a {@code Collection}.
     * @param e10   The tenth element from which to construct a {@code Collection}.
     * @param e11on The remaining elements from which to construct a {@code Collection}.
     * @param <E>   The type of the elements contained in the returned {@code Collection}.
     * @return a {@code Collection} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return collectionBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on).build();
    }

    /**
     * Returns a {@code CollectionBuilder} containing no elements.
     *
     * <h4>Example Usage:</h4>
     * A {@code CollectionBuilder} can be used to assemble a {@code Collection} as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Float&gt; collection = Literals.&lt;Float&gt;collectionBuilder()
     *           .with(1.34F, 2.2F, 3.5F)
     *           .and(4F, 5.78F, 6.21F)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Collection&lt;Float&gt; collection = Literals.collectionWith(1.34F, 2.2F, 3.5F, 4F, 5.78F, 6.21F);
     * </pre>
     * </blockquote>
     * The advantage of the {@code CollectionBuilder} is that the collection can be built up from
     * individual objects, arrays or existing iterables. See {@link CollectionBuilder} for
     * further details.
     *
     * @param <E> The type of the elements contained in the {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over the type {@code E} containing no elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilder() {
        return new CollectionBuilder<E>();
    }

    /**
     * Returns a {@code CollectionBuilder} over the type of the supplied {@code Class}
     * containing no elements.
     *
     * <h4>Example Usage:</h4>
     * A {@code CollectionBuilder} can be used to assemble a {@code Collection} as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; collection = collectionBuilderOf(Integer.class)
     *           .with(new Integer[]{65, 72})
     *           .and(95, 43, 20)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; collection = Literals.collectionWith(65, 72, 95, 43, 20);
     * </pre>
     * </blockquote>
     * The advantage of the {@code CollectionBuilder} is that the collection can be built
     * up from individual objects, arrays or existing iterables. See {@link CollectionBuilder}
     * for further details.
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code CollectionBuilder}
     * @param <E>          The type of the elements contained in the {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over the type {@code E} containing no
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderOf(Class<E> elementClass) {
        return new CollectionBuilder<E>();
    }

    /**
     * Returns a {@code CollectionBuilder} over type {@code E} initialised with the elements
     * contained in the supplied {@code Collection}.
     *
     * <h4>Example Usage:</h4>
     * A {@code CollectionBuilder} can be used to assemble a {@code Collection} from two existing
     * {@code List} instances as follows:
     * <blockquote>
     * <pre>
     *   List&lt;String&gt; firstList = Literals.listWith("a", "b", "c");
     *   List&lt;String&gt; secondList = Literals.listWith("d", "e", "f");
     *   Collection&lt;String&gt; collection = collectionBuilderFrom(firstList)
     *           .with(secondList)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Collection&lt;String&gt; collection = Literals.collectionWith("a", "b", "c", "d", "e", "f");
     * </pre>
     * </blockquote>
     * The advantage of the {@code CollectionBuilder} is that the collection can be built up from
     * individual objects, arrays or existing iterables. See {@link CollectionBuilder} for
     * further details.
     *
     * @param elements An {@code Collection} containing elements with which the
     *                 {@code CollectionBuilder} should be initialised.
     * @param <E>      The type of the elements contained in the {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> CollectionBuilder<E> collectionBuilderFrom(Iterable<? extends E> elements) {
        return new CollectionBuilder<E>().with(elements);
    }

    /**
     * Returns a {@code CollectionBuilder} over type {@code E} initialised with the elements
     * contained in the supplied array.
     *
     * <h4>Example Usage:</h4>
     * A {@code CollectionBuilder} can be used to assemble a {@code Collection} from two existing
     * arrays as follows:
     * <blockquote>
     * <pre>
     *   Integer[] firstArray = new Integer[]{1, 2, 3};
     *   Integer[] secondArray = new Long[]{3, 4, 5};
     *   Collection&lt;Integer&gt; collection = collectionBuilderFrom(firstArray)
     *           .with(secondArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; collection = Literals.collectionWith(1, 2, 3, 3, 4, 5);
     * </pre>
     * </blockquote>
     * The advantage of the {@code CollectionBuilder} is that the collection can be built up from
     * individual objects, arrays or existing iterables. See {@link CollectionBuilder} for
     * further details.
     *
     * @param elementArray An array containing elements with which the
     *                     {@code CollectionBuilder} should be initialised.
     * @param <E>          The type of the elements contained in the {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over the type {@code E} containing
     *         the elements from the supplied array.
     */
    public static <E> CollectionBuilder<E> collectionBuilderFrom(E[] elementArray) {
        return new CollectionBuilder<E>().with(elementArray);
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing
     * the supplied element.
     *
     * @param e   The element to be added to the {@code CollectionBuilder}.
     * @param <E> The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         element.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e) {
        return collectionBuilderFrom(iterableWith(e));
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code CollectionBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code CollectionBuilder}.
     * @param e2  The second element to be added to the {@code CollectionBuilder}.
     * @param <E> The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2) {
        return collectionBuilderFrom(iterableWith(e1, e2));
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code CollectionBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code CollectionBuilder}.
     * @param e2  The second element to be added to the {@code CollectionBuilder}.
     * @param e3  The third element to be added to the {@code CollectionBuilder}.
     * @param <E> The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3) {
        return collectionBuilderFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code CollectionBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code CollectionBuilder}.
     * @param e2  The second element to be added to the {@code CollectionBuilder}.
     * @param e3  The third element to be added to the {@code CollectionBuilder}.
     * @param e4  The fourth element to be added to the {@code CollectionBuilder}.
     * @param <E> The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4) {
        return collectionBuilderFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code CollectionBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code CollectionBuilder}.
     * @param e2  The second element to be added to the {@code CollectionBuilder}.
     * @param e3  The third element to be added to the {@code CollectionBuilder}.
     * @param e4  The fourth element to be added to the {@code CollectionBuilder}.
     * @param e5  The fifth element to be added to the {@code CollectionBuilder}.
     * @param <E> The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5) {
        return collectionBuilderFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code CollectionBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code CollectionBuilder}.
     * @param e2  The second element to be added to the {@code CollectionBuilder}.
     * @param e3  The third element to be added to the {@code CollectionBuilder}.
     * @param e4  The fourth element to be added to the {@code CollectionBuilder}.
     * @param e5  The fifth element to be added to the {@code CollectionBuilder}.
     * @param e6  The sixth element to be added to the {@code CollectionBuilder}.
     * @param <E> The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return collectionBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code CollectionBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code CollectionBuilder}.
     * @param e2  The second element to be added to the {@code CollectionBuilder}.
     * @param e3  The third element to be added to the {@code CollectionBuilder}.
     * @param e4  The fourth element to be added to the {@code CollectionBuilder}.
     * @param e5  The fifth element to be added to the {@code CollectionBuilder}.
     * @param e6  The sixth element to be added to the {@code CollectionBuilder}.
     * @param e7  The seventh element to be added to the {@code CollectionBuilder}.
     * @param <E> The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return collectionBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code CollectionBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code CollectionBuilder}.
     * @param e2  The second element to be added to the {@code CollectionBuilder}.
     * @param e3  The third element to be added to the {@code CollectionBuilder}.
     * @param e4  The fourth element to be added to the {@code CollectionBuilder}.
     * @param e5  The fifth element to be added to the {@code CollectionBuilder}.
     * @param e6  The sixth element to be added to the {@code CollectionBuilder}.
     * @param e7  The seventh element to be added to the {@code CollectionBuilder}.
     * @param e8  The eighth element to be added to the {@code CollectionBuilder}.
     * @param <E> The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return collectionBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code CollectionBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code CollectionBuilder}.
     * @param e2  The second element to be added to the {@code CollectionBuilder}.
     * @param e3  The third element to be added to the {@code CollectionBuilder}.
     * @param e4  The fourth element to be added to the {@code CollectionBuilder}.
     * @param e5  The fifth element to be added to the {@code CollectionBuilder}.
     * @param e6  The sixth element to be added to the {@code CollectionBuilder}.
     * @param e7  The seventh element to be added to the {@code CollectionBuilder}.
     * @param e8  The eighth element to be added to the {@code CollectionBuilder}.
     * @param e9  The ninth element to be added to the {@code CollectionBuilder}.
     * @param <E> The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return collectionBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code CollectionBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code CollectionBuilder}.
     * @param e2  The second element to be added to the {@code CollectionBuilder}.
     * @param e3  The third element to be added to the {@code CollectionBuilder}.
     * @param e4  The fourth element to be added to the {@code CollectionBuilder}.
     * @param e5  The fifth element to be added to the {@code CollectionBuilder}.
     * @param e6  The sixth element to be added to the {@code CollectionBuilder}.
     * @param e7  The seventh element to be added to the {@code CollectionBuilder}.
     * @param e8  The eighth element to be added to the {@code CollectionBuilder}.
     * @param e9  The ninth element to be added to the {@code CollectionBuilder}.
     * @param e10 The tenth element to be added to the {@code CollectionBuilder}.
     * @param <E> The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return A {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return collectionBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns a {@code CollectionBuilder} instance over the type {@code E} containing the
     * supplied elements. The supplied elements are added to the {@code CollectionBuilder}
     * instance in the same order as they are defined in the argument list.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, a {@link CollectionBuilder} instance can be used directly with
     * multiple method calls accumulating the builder contents.</p>
     *
     * @param e1    The first element to be added to the {@code CollectionBuilder}.
     * @param e2    The second element to be added to the {@code CollectionBuilder}.
     * @param e3    The third element to be added to the {@code CollectionBuilder}.
     * @param e4    The fourth element to be added to the {@code CollectionBuilder}.
     * @param e5    The fifth element to be added to the {@code CollectionBuilder}.
     * @param e6    The sixth element to be added to the {@code CollectionBuilder}.
     * @param e7    The seventh element to be added to the {@code CollectionBuilder}.
     * @param e8    The eighth element to be added to the {@code CollectionBuilder}.
     * @param e9    The ninth element to be added to the {@code CollectionBuilder}.
     * @param e10   The tenth element to be added to the {@code CollectionBuilder}.
     * @param e11on The remaining elements to be added to the {@code CollectionBuilder}. The elements
     *              will be added to the {@code CollectionBuilder} in the order they are defined in the
     *              variadic argument.
     * @param <E>   The type of the elements contained in the returned {@code CollectionBuilder}.
     * @return An {@code CollectionBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return collectionBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on);
    }

    /**
     * Returns an empty immutable {@code List} instance.
     *
     * <p>This form of literal is most suited to direct assignment to a variable
     * since in this case, the type {@code E} is inferred from the variable
     * declaration. For example:
     * <blockquote>
     * <pre>
     *   List&lt;String&gt; strings = list();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param <E> The type of the elements contained in the {@code List}.
     * @return A {@code List} instance over the type {@code E} containing no elements.
     */
    public static <E> List<E> list() {
        return new ListBuilder<E>().build();
    }

    /**
     * Returns an empty {@code List} instance of the supplied concrete class.
     *
     * <p>The supplied class must have a public no-argument constructor, otherwise
     * an {@code IllegalArgumentException} will be thrown.</p>
     *
     * @param listClass The class of the {@code List} implementation to be
     *                  instantiated.
     * @param <E>       The type of the elements contained in the {@code List}.
     * @return A {@code List} instance over the type {@code E} of the concrete
     *         type specified by the supplied {@code Class}.
     * @throws IllegalArgumentException if the supplied class does not have
     *                                  a public no-argument constructor.
     */
    public static <E> List<E> list(Class<? extends List> listClass) {
        return new ListBuilder<E>().build(listClass);
    }

    /**
     * Returns an empty immutable {@code List} instance over the type
     * of the supplied {@code Class}.
     *
     * <p>This form of literal is most suited to inline usage such as when passing an
     * empty list as a parameter in a method call since it reads more clearly than
     * {@link #list()}. For example, compare the following:
     * <blockquote>
     * <pre>
     *   public class Calculation {
     *       public Calculation(Type type, List&lt;Integer&gt; values) {
     *           ...
     *       }
     *
     *       ...
     *   }
     *
     *   Calculation sum1 = new Calculation(Type.SUM, Literals.&lt;Integer&gt;list());
     *   Calculation sum2 = new Calculation(Type.SUM, listOf(Integer.class));
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code List}
     * @param <E>          The type of the elements contained in the {@code List}.
     * @return A {@code List} instance over the type {@code E} containing no elements.
     */
    public static <E> List<E> listOf(Class<E> elementClass) {
        return new ListBuilder<E>().build();
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing
     * all elements from the supplied {@code Iterable}. The order of the elements
     * in the resulting {@code List} is determined by the order in which they are
     * yielded from the {@code Iterable}.
     *
     * <p>This form of literal is useful when a number of lazy operations have been
     * performed resulting in an {@code Iterable} where a collection implementing
     * the {@code List} contract is required. For example:
     * <blockquote>
     * <pre>
     *   List&lt;Person&gt; people = Literals.listWith(firstPerson, secondPerson, thirdPerson);
     *   Iterable&lt;Address&gt; addresses = Lazily.map(people, toAddress());
     *   Iterable&lt;StreetName&gt; streetNames = Lazily.map(addresses, toStreetName());
     *   Iterable&lt;StreetName&gt; avenueStreetNames = Lazily.filter(streetNames, whereIsAvenue());
     *   List&lt;StreetName&gt; listOfAvenues = Literals.listFrom(avenueStreetNames);
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elements An {@code Iterable} of elements from which a {@code List} should be
     *                 constructed.
     * @param <E>      The type of the elements to be contained in the returned {@code List}.
     * @return A {@code List} over the type {@code E} containing all elements from the
     *         supplied {@code Iterable} in the order they are yielded.
     */
    public static <E> List<E> listFrom(Iterable<? extends E> elements) {
        return new ListBuilder<E>().with(elements).build();
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing all
     * elements from the supplied array. The order of the elements in the resulting
     * {@code List} is the same as the order of the elements in the array.
     *
     * <p>For example, the following:
     * <blockquote>
     * <pre>
     *   String[] strings = new String[]{"one", "two", "three"};
     *   List&lt;String&gt; listOfStrings = Literals.listFrom(strings);
     * </pre>
     * </blockquote>
     * is equivalent to:
     * <blockquote>
     * <pre>
     *   List&ltString&gt; listOfStrings = Literals.listWith("one", "two", "three");
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementArray An array of elements from which a {@code List} should be
     *                     constructed.
     * @param <E>          The type of the elements to be contained in the returned {@code List}.
     * @return A {@code List} over the type {@code E} containing all elements from the
     *         supplied array in the same order as the supplied array.
     */
    public static <E> List<E> listFrom(E[] elementArray) {
        return new ListBuilder<E>().with(elementArray).build();
    }

    /**
     * Returns a {@code List} instance over the type {@code E} containing the supplied element.
     *
     * @param e   An element from which to construct a {@code List}.
     * @param <E> The type of the element contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied element.
     */
    public static <E> List<E> listWith(E e) {
        return listFrom(iterableWith(e));
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code List} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code List}.
     * @param e2  The second element from which to construct a {@code List}.
     * @param <E> The type of the elements contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied elements.
     */
    public static <E> List<E> listWith(E e1, E e2) {
        return listFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code List} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code List}.
     * @param e2  The second element from which to construct a {@code List}.
     * @param e3  The third element from which to construct a {@code List}.
     * @param <E> The type of the elements contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied elements.
     */
    public static <E> List<E> listWith(E e1, E e2, E e3) {
        return listFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code List} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code List}.
     * @param e2  The second element from which to construct a {@code List}.
     * @param e3  The third element from which to construct a {@code List}.
     * @param e4  The fourth element from which to construct a {@code List}.
     * @param <E> The type of the elements contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied elements.
     */
    public static <E> List<E> listWith(E e1, E e2, E e3, E e4) {
        return listFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code List} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code List}.
     * @param e2  The second element from which to construct a {@code List}.
     * @param e3  The third element from which to construct a {@code List}.
     * @param e4  The fourth element from which to construct a {@code List}.
     * @param e5  The fifth element from which to construct a {@code List}.
     * @param <E> The type of the elements contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied elements.
     */
    public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5) {
        return listFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code List} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code List}.
     * @param e2  The second element from which to construct a {@code List}.
     * @param e3  The third element from which to construct a {@code List}.
     * @param e4  The fourth element from which to construct a {@code List}.
     * @param e5  The fifth element from which to construct a {@code List}.
     * @param e6  The sixth element from which to construct a {@code List}.
     * @param <E> The type of the elements contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied elements.
     */
    public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return listFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code List} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code List}.
     * @param e2  The second element from which to construct a {@code List}.
     * @param e3  The third element from which to construct a {@code List}.
     * @param e4  The fourth element from which to construct a {@code List}.
     * @param e5  The fifth element from which to construct a {@code List}.
     * @param e6  The sixth element from which to construct a {@code List}.
     * @param e7  The seventh element from which to construct a {@code List}.
     * @param <E> The type of the elements contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied elements.
     */
    public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return listFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code List} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code List}.
     * @param e2  The second element from which to construct a {@code List}.
     * @param e3  The third element from which to construct a {@code List}.
     * @param e4  The fourth element from which to construct a {@code List}.
     * @param e5  The fifth element from which to construct a {@code List}.
     * @param e6  The sixth element from which to construct a {@code List}.
     * @param e7  The seventh element from which to construct a {@code List}.
     * @param e8  The eighth element from which to construct a {@code List}.
     * @param <E> The type of the elements contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied elements.
     */
    public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return listFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code List} is the same as the order
     * of the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code List}.
     * @param e2  The second element from which to construct a {@code List}.
     * @param e3  The third element from which to construct a {@code List}.
     * @param e4  The fourth element from which to construct a {@code List}.
     * @param e5  The fifth element from which to construct a {@code List}.
     * @param e6  The sixth element from which to construct a {@code List}.
     * @param e7  The seventh element from which to construct a {@code List}.
     * @param e8  The eighth element from which to construct a {@code List}.
     * @param e9  The ninth element from which to construct a {@code List}.
     * @param <E> The type of the elements contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied elements.
     */
    public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return listFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code List} is the same as the order of
     * the elements in the argument list.
     *
     * @param e1  The first element from which to construct a {@code List}.
     * @param e2  The second element from which to construct a {@code List}.
     * @param e3  The third element from which to construct a {@code List}.
     * @param e4  The fourth element from which to construct a {@code List}.
     * @param e5  The fifth element from which to construct a {@code List}.
     * @param e6  The sixth element from which to construct a {@code List}.
     * @param e7  The seventh element from which to construct a {@code List}.
     * @param e8  The eighth element from which to construct a {@code List}.
     * @param e9  The ninth element from which to construct a {@code List}.
     * @param e10 The tenth element from which to construct a {@code List}.
     * @param <E> The type of the elements contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied elements.
     */
    public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return listFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an immutable {@code List} instance over the type {@code E} containing the
     * supplied elements. The order of the resultant {@code List} is the same as the order of the
     * elements in the argument list.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, a {@link ListBuilder} can be used instead.</p>
     *
     * @param e1    The first element from which to construct a {@code List}.
     * @param e2    The second element from which to construct a {@code List}.
     * @param e3    The third element from which to construct a {@code List}.
     * @param e4    The fourth element from which to construct a {@code List}.
     * @param e5    The fifth element from which to construct a {@code List}.
     * @param e6    The sixth element from which to construct a {@code List}.
     * @param e7    The seventh element from which to construct a {@code List}.
     * @param e8    The eighth element from which to construct a {@code List}.
     * @param e9    The ninth element from which to construct a {@code List}.
     * @param e10   The tenth element from which to construct a {@code List}.
     * @param e11on The remaining elements from which to construct a {@code List}.
     * @param <E>   The type of the elements contained in the returned {@code List}.
     * @return A {@code List} instance over type {@code E} containing the supplied elements.
     */
    public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return listBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on).build();
    }

    /**
     * Returns a {@code ListBuilder} containing no elements.
     *
     * <h4>Example Usage:</h4>
     * A {@code ListBuilder} can be used to assemble a {@code List} as follows:
     * <blockquote>
     * <pre>
     *   List&lt;Integer&gt; list = Literals.&lt;Integer&gt;listBuilder()
     *           .with(1, 2, 3)
     *           .and(4, 5, 6)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   List&lt;Integer&gt; list = Literals.listWith(1, 2, 3, 4, 5, 6);
     * </pre>
     * </blockquote>
     * The advantage of the {@code ListBuilder} is that the list can be built up from
     * individual objects, arrays or existing iterables. See {@link ListBuilder} for
     * further details.
     *
     * @param <E> The type of the elements contained in the {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over the type {@code E} containing no elements.
     */
    public static <E> ListBuilder<E> listBuilder() {
        return new ListBuilder<E>();
    }

    /**
     * Returns a {@code ListBuilder} over the type of the supplied {@code Class}
     * containing no elements.
     *
     * <h4>Example Usage:</h4>
     * A {@code ListBuilder} can be used to assemble a {@code List} as follows:
     * <blockquote>
     * <pre>
     *   List&lt;Integer&gt; list = listBuilderOf(Integer.class)
     *           .with(1, 2, 3)
     *           .and(4, 5, 6)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   List&lt;Integer&gt; list = Literals.listWith(1, 2, 3, 4, 5, 6);
     * </pre>
     * </blockquote>
     * The advantage of the {@code ListBuilder} is that the list can be built up from
     * individual objects, arrays or existing iterables. See {@link ListBuilder} for
     * further details.
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code ListBuilder}
     * @param <E>          The type of the elements contained in the {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over the type {@code E} containing no
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderOf(Class<E> elementClass) {
        return new ListBuilder<E>();
    }

    /**
     * Returns a {@code ListBuilder} over type {@code E} initialised with the elements
     * contained in the supplied {@code Iterable}.
     *
     * <h4>Example Usage:</h4>
     * A {@code ListBuilder} can be used to assemble a {@code List} from two existing
     * {@code Collection} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstCollection = Literals.collectionWith(1, 2, 3);
     *   Collection&lt;Integer&gt; secondCollection = Literals.collectionWith(3, 4, 5);
     *   List&lt;Integer&gt; list = listBuilderFrom(firstCollection)
     *           .with(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   List&lt;Integer&gt; list = Literals.listWith(1, 2, 3, 3, 4, 5);
     * </pre>
     * </blockquote>
     * The advantage of the {@code ListBuilder} is that the list can be built up from
     * individual objects, arrays or existing iterables. See {@link ListBuilder} for
     * further details.
     *
     * @param elements An {@code Iterable} containing elements with which the
     *                 {@code ListBuilder} should be initialised.
     * @param <E>      The type of the elements contained in the {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> ListBuilder<E> listBuilderFrom(Iterable<? extends E> elements) {
        return new ListBuilder<E>().with(elements);
    }

    /**
     * Returns a {@code ListBuilder} over type {@code E} initialised with the elements
     * contained in the supplied array.
     *
     * <h4>Example Usage:</h4>
     * A {@code ListBuilder} can be used to assemble a {@code List} from two existing
     * arrays as follows:
     * <blockquote>
     * <pre>
     *   Integer[] firstArray = new Integer[]{1, 2, 3};
     *   Integer[] secondArray = new Integer[]{3, 4, 5};
     *   List&lt;Integer&gt; list = listBuilderFrom(firstArray)
     *           .with(secondArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   List&lt;Integer&gt; list = Literals.listWith(1, 2, 3, 3, 4, 5);
     * </pre>
     * </blockquote>
     * The advantage of the {@code ListBuilder} is that the list can be built up from
     * individual objects, arrays or existing iterables. See {@link ListBuilder} for
     * further details.
     *
     * @param elementArray An array containing elements with which the
     *                     {@code ListBuilder} should be initialised.
     * @param <E>          The type of the elements contained in the {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over the type {@code E} containing
     *         the elements from the supplied array.
     */
    public static <E> ListBuilder<E> listBuilderFrom(E[] elementArray) {
        return new ListBuilder<E>().with(elementArray);
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied element.
     *
     * @param e   The element to be added to the {@code ListBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         element.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e) {
        return listBuilderFrom(iterableWith(e));
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ListBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code ListBuilder}.
     * @param e2  The second element to be added to the {@code ListBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e1, E e2) {
        return listBuilderFrom(iterableWith(e1, e2));
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ListBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code ListBuilder}.
     * @param e2  The second element to be added to the {@code ListBuilder}.
     * @param e3  The third element to be added to the {@code ListBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3) {
        return listBuilderFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ListBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code ListBuilder}.
     * @param e2  The second element to be added to the {@code ListBuilder}.
     * @param e3  The third element to be added to the {@code ListBuilder}.
     * @param e4  The fourth element to be added to the {@code ListBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4) {
        return listBuilderFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ListBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code ListBuilder}.
     * @param e2  The second element to be added to the {@code ListBuilder}.
     * @param e3  The third element to be added to the {@code ListBuilder}.
     * @param e4  The fourth element to be added to the {@code ListBuilder}.
     * @param e5  The fifth element to be added to the {@code ListBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5) {
        return listBuilderFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ListBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code ListBuilder}.
     * @param e2  The second element to be added to the {@code ListBuilder}.
     * @param e3  The third element to be added to the {@code ListBuilder}.
     * @param e4  The fourth element to be added to the {@code ListBuilder}.
     * @param e5  The fifth element to be added to the {@code ListBuilder}.
     * @param e6  The sixth element to be added to the {@code ListBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return listBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ListBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code ListBuilder}.
     * @param e2  The second element to be added to the {@code ListBuilder}.
     * @param e3  The third element to be added to the {@code ListBuilder}.
     * @param e4  The fourth element to be added to the {@code ListBuilder}.
     * @param e5  The fifth element to be added to the {@code ListBuilder}.
     * @param e6  The sixth element to be added to the {@code ListBuilder}.
     * @param e7  The seventh element to be added to the {@code ListBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return listBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ListBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code ListBuilder}.
     * @param e2  The second element to be added to the {@code ListBuilder}.
     * @param e3  The third element to be added to the {@code ListBuilder}.
     * @param e4  The fourth element to be added to the {@code ListBuilder}.
     * @param e5  The fifth element to be added to the {@code ListBuilder}.
     * @param e6  The sixth element to be added to the {@code ListBuilder}.
     * @param e7  The seventh element to be added to the {@code ListBuilder}.
     * @param e8  The eighth element to be added to the {@code ListBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return listBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ListBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code ListBuilder}.
     * @param e2  The second element to be added to the {@code ListBuilder}.
     * @param e3  The third element to be added to the {@code ListBuilder}.
     * @param e4  The fourth element to be added to the {@code ListBuilder}.
     * @param e5  The fifth element to be added to the {@code ListBuilder}.
     * @param e6  The sixth element to be added to the {@code ListBuilder}.
     * @param e7  The seventh element to be added to the {@code ListBuilder}.
     * @param e8  The eighth element to be added to the {@code ListBuilder}.
     * @param e9  The ninth element to be added to the {@code ListBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return listBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ListBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * @param e1  The first element to be added to the {@code ListBuilder}.
     * @param e2  The second element to be added to the {@code ListBuilder}.
     * @param e3  The third element to be added to the {@code ListBuilder}.
     * @param e4  The fourth element to be added to the {@code ListBuilder}.
     * @param e5  The fifth element to be added to the {@code ListBuilder}.
     * @param e6  The sixth element to be added to the {@code ListBuilder}.
     * @param e7  The seventh element to be added to the {@code ListBuilder}.
     * @param e8  The eighth element to be added to the {@code ListBuilder}.
     * @param e9  The ninth element to be added to the {@code ListBuilder}.
     * @param e10 The tenth element to be added to the {@code ListBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return listBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns a {@code ListBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ListBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, a {@link ListBuilder} instance can be used directly with
     * multiple method calls accumulating the builder contents.</p>
     *
     * @param e1    The first element to be added to the {@code ListBuilder}.
     * @param e2    The second element to be added to the {@code ListBuilder}.
     * @param e3    The third element to be added to the {@code ListBuilder}.
     * @param e4    The fourth element to be added to the {@code ListBuilder}.
     * @param e5    The fifth element to be added to the {@code ListBuilder}.
     * @param e6    The sixth element to be added to the {@code ListBuilder}.
     * @param e7    The seventh element to be added to the {@code ListBuilder}.
     * @param e8    The eighth element to be added to the {@code ListBuilder}.
     * @param e9    The ninth element to be added to the {@code ListBuilder}.
     * @param e10   The tenth element to be added to the {@code ListBuilder}.
     * @param e11on The remaining elements to be added to the {@code ListBuilder}. The elements
     *              will be added to the {@code ListBuilder} in the order they are defined in the
     *              variadic argument.
     * @param <E>   The type of the elements contained in the returned {@code ListBuilder}.
     * @return A {@code ListBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return listBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on);
    }

    /**
     * Returns an empty immutable {@code Set} instance.
     *
     * <p>This form of literal is most suited to direct assignment to a variable
     * since in this case, the type {@code E} is inferred from the variable
     * declaration. For example:
     * <blockquote>
     * <pre>
     *   Set&lt;String&gt; strings = set();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param <E> The type of the elements contained in the {@code Set}.
     * @return A {@code Set} instance over the type {@code E} containing no elements.
     */
    public static <E> Set<E> set() {
        return new SetBuilder<E>().build();
    }

    /**
     * Returns an empty {@code Set} instance of the supplied concrete class.
     *
     * <p>The supplied class must have a public no-argument constructor, otherwise
     * an {@code IllegalArgumentException} will be thrown.</p>
     *
     * @param setClass The class of the {@code Set} implementation to be
     *                 instantiated.
     * @param <E>      The type of the elements contained in the {@code Set}.
     * @return A {@code Set} instance over the type {@code E} of the concrete
     *         type specified by the supplied {@code Class}.
     * @throws IllegalArgumentException if the supplied class does not have
     *                                  a public no-argument constructor.
     */
    public static <E> Set<E> set(Class<? extends Set> setClass) {
        return new SetBuilder<E>().build(setClass);
    }

    /**
     * Returns an empty immutable {@code Set} instance over the type
     * of the supplied {@code Class}.
     *
     * <p>This form of literal is most suited to inline usage such as when passing an
     * empty set as a parameter in a method call since it reads more clearly than
     * {@link #set()}. For example, compare the following:
     * <blockquote>
     * <pre>
     *   public class OrderRepository {
     *       public void save(Customer customer, Set&lt;LineItem&gt; lineItems) {
     *           ...
     *       }
     *
     *       ...
     *   }
     *
     *   orderRepository.save(customer, Literals.&lt;LineItem&gt;set());
     *   orderRepository.save(customer, setOf(LineItem.class));
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code Set}.
     * @param <E>          The type of the elements contained in the {@code Set}.
     * @return A {@code Set} instance over the type {@code E} containing no elements.
     */
    public static <E> Set<E> setOf(Class<E> elementClass) {
        return new SetBuilder<E>().build();
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing
     * all elements from the supplied {@code Iterable}. Due to the nature of a
     * {@code Set}, any duplicate elements in the supplied {@code Iterable} will
     * be removed.
     *
     * <p>This form of literal is useful when a number of lazy operations have been
     * performed resulting in an {@code Iterable} where a collection implementing
     * the {@code Set} contract is required. For example:
     * <blockquote>
     * <pre>
     *   Set&lt;Person&gt; people = Literals.setWith(firstPerson, secondPerson, thirdPerson);
     *   Iterable&lt;Address&gt; addresses = Lazily.map(people, toAddress());
     *   Iterable&lt;StreetName&gt; streetNames = Lazily.map(addresses, toStreetName());
     *   Iterable&lt;StreetName&gt; avenueStreetNames = Lazily.filter(streetNames, whereIsAvenue());
     *   Set&lt;StreetName&gt; relevantAvenues = Literals.setFrom(avenueStreetNames);
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elements An {@code Iterable} of elements from which a {@code Set} should be
     *                 constructed.
     * @param <E>      The type of the elements to be contained in the returned {@code Set}.
     * @return A {@code Set} over the type {@code E} containing all unique elements from the
     *         supplied {@code Iterable}.
     */
    public static <E> Set<E> setFrom(Iterable<? extends E> elements) {
        return new SetBuilder<E>().with(elements).build();
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing
     * all elements from the supplied array. Due to the nature of a {@code Set}, any
     * duplicate elements in the supplied {@code Iterable} will be removed.
     *
     * <p>For example, the following:
     * <blockquote>
     * <pre>
     *   String[] strings = new String[]{"one", "two", "two", "three"};
     *   Set&lt;String&gt; setOfStrings = Literals.setFrom(strings);
     * </pre>
     * </blockquote>
     * is equivalent to:
     * <blockquote>
     * <pre>
     *   Set&ltString&gt; setOfStrings = Literals.setWith("one", "two", "three");
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementArray An array of elements from which a {@code Set} should be
     *                     constructed.
     * @param <E>          The type of the elements to be contained in the returned {@code Set}.
     * @return A {@code Set} over the type {@code E} containing all unique elements from the
     *         supplied array.
     */
    public static <E> Set<E> setFrom(E[] elementArray) {
        return new SetBuilder<E>().with(elementArray).build();
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied element.
     *
     * @param e   An element from which to construct a {@code Set}.
     * @param <E> The type of the element contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied element.
     */
    public static <E> Set<E> setWith(E e) {
        return setFrom(iterableWith(e));
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied elements. Due to the nature of a {@code Set}, any supplied duplicate
     * elements will be removed.
     *
     * @param e1  The first element from which to construct a {@code Set}.
     * @param e2  The second element from which to construct a {@code Set}.
     * @param <E> The type of the elements contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Set<E> setWith(E e1, E e2) {
        return setFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied elements. Due to the nature of a {@code Set}, any supplied duplicate elements
     * will be removed.
     *
     * @param e1  The first element from which to construct a {@code Set}.
     * @param e2  The second element from which to construct a {@code Set}.
     * @param e3  The third element from which to construct a {@code Set}.
     * @param <E> The type of the elements contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Set<E> setWith(E e1, E e2, E e3) {
        return setFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied elements. Due to the nature of a {@code Set}, any supplied duplicate
     * elements will be removed.
     *
     * @param e1  The first element from which to construct a {@code Set}.
     * @param e2  The second element from which to construct a {@code Set}.
     * @param e3  The third element from which to construct a {@code Set}.
     * @param e4  The fourth element from which to construct a {@code Set}.
     * @param <E> The type of the elements contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Set<E> setWith(E e1, E e2, E e3, E e4) {
        return setFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied elements. Due to the nature of a {@code Set}, any supplied duplicate
     * elements will be removed.
     *
     * @param e1  The first element from which to construct a {@code Set}.
     * @param e2  The second element from which to construct a {@code Set}.
     * @param e3  The third element from which to construct a {@code Set}.
     * @param e4  The fourth element from which to construct a {@code Set}.
     * @param e5  The fifth element from which to construct a {@code Set}.
     * @param <E> The type of the elements contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5) {
        return setFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied elements. Due to the nature of a {@code Set}, any supplied duplicate
     * elements will be removed.
     *
     * @param e1  The first element from which to construct a {@code Set}.
     * @param e2  The second element from which to construct a {@code Set}.
     * @param e3  The third element from which to construct a {@code Set}.
     * @param e4  The fourth element from which to construct a {@code Set}.
     * @param e5  The fifth element from which to construct a {@code Set}.
     * @param e6  The sixth element from which to construct a {@code Set}.
     * @param <E> The type of the elements contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return setFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied elements. Due to the nature of a {@code Set}, any supplied duplicate
     * elements will be removed.
     *
     * @param e1  The first element from which to construct a {@code Set}.
     * @param e2  The second element from which to construct a {@code Set}.
     * @param e3  The third element from which to construct a {@code Set}.
     * @param e4  The fourth element from which to construct a {@code Set}.
     * @param e5  The fifth element from which to construct a {@code Set}.
     * @param e6  The sixth element from which to construct a {@code Set}.
     * @param e7  The seventh element from which to construct a {@code Set}.
     * @param <E> The type of the elements contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return setFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied elements. Due to the nature of a {@code Set}, any supplied duplicate
     * elements will be removed.
     *
     * @param e1  The first element from which to construct a {@code Set}.
     * @param e2  The second element from which to construct a {@code Set}.
     * @param e3  The third element from which to construct a {@code Set}.
     * @param e4  The fourth element from which to construct a {@code Set}.
     * @param e5  The fifth element from which to construct a {@code Set}.
     * @param e6  The sixth element from which to construct a {@code Set}.
     * @param e7  The seventh element from which to construct a {@code Set}.
     * @param e8  The eighth element from which to construct a {@code Set}.
     * @param <E> The type of the elements contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return setFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied elements. Due to the nature of a {@code Set}, any supplied duplicate
     * elements will be removed.
     *
     * @param e1  The first element from which to construct a {@code Set}.
     * @param e2  The second element from which to construct a {@code Set}.
     * @param e3  The third element from which to construct a {@code Set}.
     * @param e4  The fourth element from which to construct a {@code Set}.
     * @param e5  The fifth element from which to construct a {@code Set}.
     * @param e6  The sixth element from which to construct a {@code Set}.
     * @param e7  The seventh element from which to construct a {@code Set}.
     * @param e8  The eighth element from which to construct a {@code Set}.
     * @param e9  The ninth element from which to construct a {@code Set}.
     * @param <E> The type of the elements contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return setFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied elements. Due to the nature of a {@code Set}, any supplied duplicate
     * elements will be removed.
     *
     * @param e1  The first element from which to construct a {@code Set}.
     * @param e2  The second element from which to construct a {@code Set}.
     * @param e3  The third element from which to construct a {@code Set}.
     * @param e4  The fourth element from which to construct a {@code Set}.
     * @param e5  The fifth element from which to construct a {@code Set}.
     * @param e6  The sixth element from which to construct a {@code Set}.
     * @param e7  The seventh element from which to construct a {@code Set}.
     * @param e8  The eighth element from which to construct a {@code Set}.
     * @param e9  The ninth element from which to construct a {@code Set}.
     * @param e10 The tenth element from which to construct a {@code Set}.
     * @param <E> The type of the elements contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return setFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an immutable {@code Set} instance over the type {@code E} containing the
     * supplied elements. Due to the nature of a {@code Set}, any supplied duplicate
     * elements will be removed.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, a {@link SetBuilder} can be used instead.</p>
     *
     * @param e1    The first element from which to construct a {@code Set}.
     * @param e2    The second element from which to construct a {@code Set}.
     * @param e3    The third element from which to construct a {@code Set}.
     * @param e4    The fourth element from which to construct a {@code Set}.
     * @param e5    The fifth element from which to construct a {@code Set}.
     * @param e6    The sixth element from which to construct a {@code Set}.
     * @param e7    The seventh element from which to construct a {@code Set}.
     * @param e8    The eighth element from which to construct a {@code Set}.
     * @param e9    The ninth element from which to construct a {@code Set}.
     * @param e10   The tenth element from which to construct a {@code Set}.
     * @param e11on The remaining elements from which to construct a {@code Set}.
     * @param <E>   The type of the elements contained in the returned {@code Set}.
     * @return A {@code Set} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11on) {
        return setBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on).build();
    }

    /**
     * Returns a {@code SetBuilder} containing no elements.
     *
     * <h4>Example Usage:</h4>
     * A {@code SetBuilder} can be used to assemble a {@code Set} as follows:
     * <blockquote>
     * <pre>
     *   Set&lt;Double&gt; set = Literals.&lt;Double&gt;setBuilder()
     *           .with(1.56, 2.33, 3.1)
     *           .and(4.04, 5.3, 6)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Set&lt;Double&gt; set = Literals.setWith(4.04, 2.33, 3.1, 5.3, 6, 1.56);
     * </pre>
     * </blockquote>
     * The advantage of the {@code SetBuilder} is that the set can be built up from
     * individual objects, arrays or existing iterables. See {@link SetBuilder} for
     * further details.
     *
     * @param <E> The type of the elements contained in the {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over the type {@code E} containing no elements.
     */
    public static <E> SetBuilder<E> setBuilder() {
        return new SetBuilder<E>();
    }

    /**
     * Returns a {@code SetBuilder} over the type of the supplied {@code Class}
     * containing no elements.
     *
     * <h4>Example Usage:</h4>
     * A {@code SetBuilder} can be used to assemble a {@code Set} as follows:
     * <blockquote>
     * <pre>
     *   Set&lt;Integer&gt; set = setBuilderOf(Integer.class)
     *           .with(1, 1, 2)
     *           .and(4, 5, 5)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Set&lt;Integer&gt; set = Literals.setWith(1, 1, 2, 4, 5, 5);
     * </pre>
     * </blockquote>
     * The advantage of the {@code SetBuilder} is that the set can be built up from
     * individual objects, arrays or existing iterables. See {@link SetBuilder} for
     * further details.
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code SetBuilder}
     * @param <E>          The type of the elements contained in the {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over the type {@code E} containing no
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderOf(Class<E> elementClass) {
        return new SetBuilder<E>();
    }

    /**
     * Returns a {@code SetBuilder} over type {@code E} initialised with the elements
     * contained in the supplied {@code Iterable}.
     *
     * <h4>Example Usage:</h4>
     * A {@code SetBuilder} can be used to assemble a {@code Set} from two existing
     * {@code Collection} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstCollection = Literals.collectionWith(1, 2, 3);
     *   Collection&lt;Integer&gt; secondCollection = Literals.collectionWith(3, 4, 5);
     *   Set&lt;Integer&gt; set = setBuilderFrom(firstCollection)
     *           .with(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Set&lt;Integer&gt; set = Literals.setWith(1, 2, 3, 4, 5);
     * </pre>
     * </blockquote>
     * The advantage of the {@code SetBuilder} is that the set can be built up from
     * individual objects, arrays or existing iterables. See {@link SetBuilder} for
     * further details.
     *
     * @param elements An {@code Iterable} containing elements with which the
     *                 {@code SetBuilder} should be initialised.
     * @param <E>      The type of the elements contained in the {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> SetBuilder<E> setBuilderFrom(Iterable<? extends E> elements) {
        return new SetBuilder<E>().with(elements);
    }

    /**
     * Returns a {@code SetBuilder} over type {@code E} initialised with the elements
     * contained in the supplied array.
     *
     * <h4>Example Usage:</h4>
     * A {@code SetBuilder} can be used to assemble a {@code Set} from two existing
     * arrays as follows:
     * <blockquote>
     * <pre>
     *   Integer[] firstArray = new Integer[]{1, 2, 3};
     *   Integer[] secondArray = new Integer[]{3, 4, 5};
     *   Set&lt;Integer&gt; set = setBuilderFrom(firstArray)
     *           .with(secondArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Set&lt;Integer&gt; set = Literals.setWith(1, 2, 3, 4, 5);
     * </pre>
     * </blockquote>
     * The advantage of the {@code SetBuilder} is that the set can be built up from
     * individual objects, arrays or existing iterables. See {@link SetBuilder} for
     * further details.
     *
     * @param elementArray An array containing elements with which the
     *                     {@code SetBuilder} should be initialised.
     * @param <E>          The type of the elements contained in the {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over the type {@code E} containing
     *         the elements from the supplied array.
     */
    public static <E> SetBuilder<E> setBuilderFrom(E[] elementArray) {
        return new SetBuilder<E>().with(elementArray);
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * element.
     *
     * @param e   The element to be added to the {@code SetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         element.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e) {
        return setBuilderFrom(iterableWith(e));
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * elements. Due to the nature of a {@code Set}, any supplied duplicate elements will be
     * removed.
     *
     * @param e1  The first element to be added to the {@code SetBuilder}.
     * @param e2  The second element to be added to the {@code SetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e1, E e2) {
        return setBuilderFrom(iterableWith(e1, e2));
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * elements. Due to the nature of a {@code Set}, any supplied duplicate elements will be
     * removed.
     *
     * @param e1  The first element to be added to the {@code SetBuilder}.
     * @param e2  The second element to be added to the {@code SetBuilder}.
     * @param e3  The third element to be added to the {@code SetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3) {
        return setBuilderFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * elements. Due to the nature of a {@code Set}, any supplied duplicate elements will be
     * removed.
     *
     * @param e1  The first element to be added to the {@code SetBuilder}.
     * @param e2  The second element to be added to the {@code SetBuilder}.
     * @param e3  The third element to be added to the {@code SetBuilder}.
     * @param e4  The fourth element to be added to the {@code SetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4) {
        return setBuilderFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * elements. Due to the nature of a {@code Set}, any supplied duplicate elements will be
     * removed.
     *
     * @param e1  The first element to be added to the {@code SetBuilder}.
     * @param e2  The second element to be added to the {@code SetBuilder}.
     * @param e3  The third element to be added to the {@code SetBuilder}.
     * @param e4  The fourth element to be added to the {@code SetBuilder}.
     * @param e5  The fifth element to be added to the {@code SetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5) {
        return setBuilderFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * elements. Due to the nature of a {@code Set}, any supplied duplicate elements will be
     * removed.
     *
     * @param e1  The first element to be added to the {@code SetBuilder}.
     * @param e2  The second element to be added to the {@code SetBuilder}.
     * @param e3  The third element to be added to the {@code SetBuilder}.
     * @param e4  The fourth element to be added to the {@code SetBuilder}.
     * @param e5  The fifth element to be added to the {@code SetBuilder}.
     * @param e6  The sixth element to be added to the {@code SetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return setBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * elements. Due to the nature of a {@code Set}, any supplied duplicate elements will be
     * removed.
     *
     * @param e1  The first element to be added to the {@code SetBuilder}.
     * @param e2  The second element to be added to the {@code SetBuilder}.
     * @param e3  The third element to be added to the {@code SetBuilder}.
     * @param e4  The fourth element to be added to the {@code SetBuilder}.
     * @param e5  The fifth element to be added to the {@code SetBuilder}.
     * @param e6  The sixth element to be added to the {@code SetBuilder}.
     * @param e7  The seventh element to be added to the {@code SetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return setBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * elements. Due to the nature of a {@code Set}, any supplied duplicate elements will be
     * removed.
     *
     * @param e1  The first element to be added to the {@code SetBuilder}.
     * @param e2  The second element to be added to the {@code SetBuilder}.
     * @param e3  The third element to be added to the {@code SetBuilder}.
     * @param e4  The fourth element to be added to the {@code SetBuilder}.
     * @param e5  The fifth element to be added to the {@code SetBuilder}.
     * @param e6  The sixth element to be added to the {@code SetBuilder}.
     * @param e7  The seventh element to be added to the {@code SetBuilder}.
     * @param e8  The eighth element to be added to the {@code SetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return setBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * elements. Due to the nature of a {@code Set}, any supplied duplicate elements will be
     * removed.
     *
     * @param e1  The first element to be added to the {@code SetBuilder}.
     * @param e2  The second element to be added to the {@code SetBuilder}.
     * @param e3  The third element to be added to the {@code SetBuilder}.
     * @param e4  The fourth element to be added to the {@code SetBuilder}.
     * @param e5  The fifth element to be added to the {@code SetBuilder}.
     * @param e6  The sixth element to be added to the {@code SetBuilder}.
     * @param e7  The seventh element to be added to the {@code SetBuilder}.
     * @param e8  The eighth element to be added to the {@code SetBuilder}.
     * @param e9  The ninth element to be added to the {@code SetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return setBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * elements. Due to the nature of a {@code Set}, any supplied duplicate elements will be
     * removed.
     *
     * @param e1  The first element to be added to the {@code SetBuilder}.
     * @param e2  The second element to be added to the {@code SetBuilder}.
     * @param e3  The third element to be added to the {@code SetBuilder}.
     * @param e4  The fourth element to be added to the {@code SetBuilder}.
     * @param e5  The fifth element to be added to the {@code SetBuilder}.
     * @param e6  The sixth element to be added to the {@code SetBuilder}.
     * @param e7  The seventh element to be added to the {@code SetBuilder}.
     * @param e8  The eighth element to be added to the {@code SetBuilder}.
     * @param e9  The ninth element to be added to the {@code SetBuilder}.
     * @param e10 The tenth element to be added to the {@code SetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return setBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns a {@code SetBuilder} instance over the type {@code E} containing the supplied
     * elements. Due to the nature of a {@code Set}, any supplied duplicate elements will be
     * removed.
     *
     * @param e1    The first element to be added to the {@code SetBuilder}.
     * @param e2    The second element to be added to the {@code SetBuilder}.
     * @param e3    The third element to be added to the {@code SetBuilder}.
     * @param e4    The fourth element to be added to the {@code SetBuilder}.
     * @param e5    The fifth element to be added to the {@code SetBuilder}.
     * @param e6    The sixth element to be added to the {@code SetBuilder}.
     * @param e7    The seventh element to be added to the {@code SetBuilder}.
     * @param e8    The eighth element to be added to the {@code SetBuilder}.
     * @param e9    The ninth element to be added to the {@code SetBuilder}.
     * @param e10   The tenth element to be added to the {@code SetBuilder}.
     * @param e11on The remaining elements to be added to the {@code SetBuilder}. The elements
     *              will be added to the {@code SetBuilder} in the order they are defined in the
     *              variadic argument.
     * @param <E>   The type of the elements contained in the returned {@code SetBuilder}.
     * @return A {@code SetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11on) {
        return setBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on);
    }

    /**
     * Returns an empty immutable {@code Multiset} instance.
     *
     * <p>This form of literal is most suited to direct assignment to a variable
     * since in this case, the type {@code E} is inferred from the variable
     * declaration. For example:
     * <blockquote>
     * <pre>
     *   Multiset&lt;String&gt; strings = multiset();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param <E> The type of the elements contained in the {@code Multiset}.
     * @return A {@code Multiset} instance over the type {@code E} containing no elements.
     */
    public static <E> Multiset<E> multiset() {
        return new MultisetBuilder<E>().build();
    }

    /**
     * Returns an empty {@code Multiset} instance of the supplied concrete class.
     *
     * <p>The supplied class must have a public no-argument constructor, otherwise
     * an {@code IllegalArgumentException} will be thrown.</p>
     *
     * @param multisetClass The class of the {@code Multiset} implementation to be
     *                      instantiated.
     * @param <E>           The type of the elements contained in the {@code Multiset}.
     * @return A {@code Multiset} instance over the type {@code E} of the concrete
     *         type specified by the supplied {@code Class}.
     * @throws IllegalArgumentException if the supplied class does not have
     *                                  a public no-argument constructor.
     */
    public static <E> Multiset<E> multiset(Class<? extends Multiset> multisetClass) {
        return new MultisetBuilder<E>().build(multisetClass);
    }

    /**
     * Returns an empty immutable {@code Multiset} instance over the type
     * of the supplied {@code Class}.
     *
     * <p>This form of literal is most suited to inline usage such as when passing an
     * empty multiset as a parameter in a method call since it reads more clearly than
     * {@link #multiset()}. For example, compare the following:
     * <blockquote>
     * <pre>
     *   public class WordAnalyser {
     *       public WordAnalyser(Multiset&lt;Word&gt; words) {
     *           ...
     *       }
     *
     *       ...
     *   }
     *
     *   new WordAnalyser(Literals.&lt;Word&gt;multiset());
     *   new WordAnalyser(multisetOf(Word.class));
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code Multiset}.
     * @param <E>          The type of the elements contained in the {@code Multiset}.
     * @return A {@code Multiset} instance over the type {@code E} containing no elements.
     */
    public static <E> Multiset<E> multisetOf(Class<E> elementClass) {
        return new MultisetBuilder<E>().build();
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing
     * all elements from the supplied {@code Iterable}.
     *
     * <p>This form of literal is useful when an object with {@code Multiset} semantics
     * is needed but only another form of {@code Iterable} is available. For example:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Jpeg&gt; veryManyImagesWithDuplicates = library.loadAll();
     *   Multiset&lt;Jpeg&gt; imageMultiset = Literals.multisetFrom(veryManyImagesWithDuplicates);
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elements A {@code Multiset} of elements from which a {@code Multiset} should be
     *                 constructed.
     * @param <E>      The type of the elements to be contained in the returned {@code Multiset}.
     * @return A {@code Multiset} over the type {@code E} containing all elements from the
     *         supplied {@code Iterable}.
     */
    public static <E> Multiset<E> multisetFrom(Iterable<? extends E> elements) {
        return new MultisetBuilder<E>().with(elements).build();
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing
     * all elements from the supplied array.
     *
     * <p>For example, the following:
     * <blockquote>
     * <pre>
     *   String[] strings = new String[]{"one", "two", "two", "three"};
     *   Multiset&lt;String&gt; multisetOfStrings = Literals.multisetFrom(strings);
     * </pre>
     * </blockquote>
     * is equivalent to:
     * <blockquote>
     * <pre>
     *   Multiset&ltString&gt; multisetOfStrings = Literals.multisetWith("one", "two", "three");
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementArray An array of elements from which a {@code Multiset} should be
     *                     constructed.
     * @param <E>          The type of the elements to be contained in the returned {@code Multiset}.
     * @return A {@code Multiset} over the type {@code E} containing all elements from the
     *         supplied array.
     */
    public static <E> Multiset<E> multisetFrom(E[] elementArray) {
        return new MultisetBuilder<E>().with(elementArray).build();
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied element.
     *
     * @param e   An element from which to construct a {@code Multiset}.
     * @param <E> The type of the element contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied element.
     */
    public static <E> Multiset<E> multisetWith(E e) {
        return multisetFrom(iterableWith(e));
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied elements.
     *
     * @param e1  The first element from which to construct a {@code Multiset}.
     * @param e2  The second element from which to construct a {@code Multiset}.
     * @param <E> The type of the elements contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Multiset<E> multisetWith(E e1, E e2) {
        return multisetFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied elements.
     *
     * @param e1  The first element from which to construct a {@code Multiset}.
     * @param e2  The second element from which to construct a {@code Multiset}.
     * @param e3  The third element from which to construct a {@code Multiset}.
     * @param <E> The type of the elements contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Multiset<E> multisetWith(E e1, E e2, E e3) {
        return multisetFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied elements.
     *
     * @param e1  The first element from which to construct a {@code Multiset}.
     * @param e2  The second element from which to construct a {@code Multiset}.
     * @param e3  The third element from which to construct a {@code Multiset}.
     * @param e4  The fourth element from which to construct a {@code Multiset}.
     * @param <E> The type of the elements contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4) {
        return multisetFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied elements.
     *
     * @param e1  The first element from which to construct a {@code Multiset}.
     * @param e2  The second element from which to construct a {@code Multiset}.
     * @param e3  The third element from which to construct a {@code Multiset}.
     * @param e4  The fourth element from which to construct a {@code Multiset}.
     * @param e5  The fifth element from which to construct a {@code Multiset}.
     * @param <E> The type of the elements contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5) {
        return multisetFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied elements.
     *
     * @param e1  The first element from which to construct a {@code Multiset}.
     * @param e2  The second element from which to construct a {@code Multiset}.
     * @param e3  The third element from which to construct a {@code Multiset}.
     * @param e4  The fourth element from which to construct a {@code Multiset}.
     * @param e5  The fifth element from which to construct a {@code Multiset}.
     * @param e6  The sixth element from which to construct a {@code Multiset}.
     * @param <E> The type of the elements contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return multisetFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied elements.
     *
     * @param e1  The first element from which to construct a {@code Multiset}.
     * @param e2  The second element from which to construct a {@code Multiset}.
     * @param e3  The third element from which to construct a {@code Multiset}.
     * @param e4  The fourth element from which to construct a {@code Multiset}.
     * @param e5  The fifth element from which to construct a {@code Multiset}.
     * @param e6  The sixth element from which to construct a {@code Multiset}.
     * @param e7  The seventh element from which to construct a {@code Multiset}.
     * @param <E> The type of the elements contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return multisetFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied elements.
     *
     * @param e1  The first element from which to construct a {@code Multiset}.
     * @param e2  The second element from which to construct a {@code Multiset}.
     * @param e3  The third element from which to construct a {@code Multiset}.
     * @param e4  The fourth element from which to construct a {@code Multiset}.
     * @param e5  The fifth element from which to construct a {@code Multiset}.
     * @param e6  The sixth element from which to construct a {@code Multiset}.
     * @param e7  The seventh element from which to construct a {@code Multiset}.
     * @param e8  The eighth element from which to construct a {@code Multiset}.
     * @param <E> The type of the elements contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return multisetFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied elements.
     *
     * @param e1  The first element from which to construct a {@code Multiset}.
     * @param e2  The second element from which to construct a {@code Multiset}.
     * @param e3  The third element from which to construct a {@code Multiset}.
     * @param e4  The fourth element from which to construct a {@code Multiset}.
     * @param e5  The fifth element from which to construct a {@code Multiset}.
     * @param e6  The sixth element from which to construct a {@code Multiset}.
     * @param e7  The seventh element from which to construct a {@code Multiset}.
     * @param e8  The eighth element from which to construct a {@code Multiset}.
     * @param e9  The ninth element from which to construct a {@code Multiset}.
     * @param <E> The type of the elements contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return multisetFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied elements.
     *
     * @param e1  The first element from which to construct a {@code Multiset}.
     * @param e2  The second element from which to construct a {@code Multiset}.
     * @param e3  The third element from which to construct a {@code Multiset}.
     * @param e4  The fourth element from which to construct a {@code Multiset}.
     * @param e5  The fifth element from which to construct a {@code Multiset}.
     * @param e6  The sixth element from which to construct a {@code Multiset}.
     * @param e7  The seventh element from which to construct a {@code Multiset}.
     * @param e8  The eighth element from which to construct a {@code Multiset}.
     * @param e9  The ninth element from which to construct a {@code Multiset}.
     * @param e10 The tenth element from which to construct a {@code Multiset}.
     * @param <E> The type of the elements contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return multisetFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an immutable {@code Multiset} instance over the type {@code E} containing the
     * supplied elements.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, a {@link MultisetBuilder} can be used instead.</p>
     *
     * @param e1    The first element from which to construct a {@code Multiset}.
     * @param e2    The second element from which to construct a {@code Multiset}.
     * @param e3    The third element from which to construct a {@code Multiset}.
     * @param e4    The fourth element from which to construct a {@code Multiset}.
     * @param e5    The fifth element from which to construct a {@code Multiset}.
     * @param e6    The sixth element from which to construct a {@code Multiset}.
     * @param e7    The seventh element from which to construct a {@code Multiset}.
     * @param e8    The eighth element from which to construct a {@code Multiset}.
     * @param e9    The ninth element from which to construct a {@code Multiset}.
     * @param e10   The tenth element from which to construct a {@code Multiset}.
     * @param e11on The remaining elements from which to construct a {@code Multiset}.
     * @param <E>   The type of the elements contained in the returned {@code Multiset}.
     * @return A {@code Multiset} instance over type {@code E} containing the supplied elements.
     */
    public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return multisetBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on).build();
    }

    /**
     * Returns a {@code MultisetBuilder} containing no elements.
     *
     * <h4>Example Usage:</h4>
     * A {@code MultisetBuilder} can be used to assemble a {@code Multiset} as follows:
     * <blockquote>
     * <pre>
     *   Multiset&lt;Boolean&gt; multiset = Literals.&lt;Boolean&gt;multisetBuilder()
     *           .with(false, true, true)
     *           .and(false, true, false)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Multiset&lt;Boolean&gt; multiset = Literals.multisetWith(true, true, true, false, false, false);
     * </pre>
     * </blockquote>
     * The advantage of the {@code MultisetBuilder} is that the multiset can be built up from
     * individual objects, arrays or existing iterables. See {@link MultisetBuilder} for
     * further details.
     *
     * @param <E> The type of the elements contained in the {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over the type {@code E} containing no elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilder() {
        return new MultisetBuilder<E>();
    }

    /**
     * Returns a {@code MultisetBuilder} over the type of the supplied {@code Class}
     * containing no elements.
     *
     * <h4>Example Usage:</h4>
     * A {@code MultisetBuilder} can be used to assemble a {@code Multiset} as follows:
     * <blockquote>
     * <pre>
     *   Multiset&lt;Long&gt; multiset = multisetBuilderOf(Long.class)
     *           .with(1L, 1L, 2L)
     *           .and(4L, 5L, 5L)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Multiset&lt;Long&gt; multiset = Literals.multisetWith(1L, 1L, 2L, 4L, 5L, 5L);
     * </pre>
     * </blockquote>
     * The advantage of the {@code MultisetBuilder} is that the multiset can be built up from
     * individual objects, arrays or existing iterables. See {@link MultisetBuilder} for
     * further details.
     *
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code MultisetBuilder}
     * @param <E>          The type of the elements contained in the {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over the type {@code E} containing no
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderOf(Class<E> elementClass) {
        return new MultisetBuilder<E>();
    }

    /**
     * Returns a {@code MultisetBuilder} over type {@code E} initialised with the elements
     * contained in the supplied {@code Iterable}.
     *
     * <h4>Example Usage:</h4>
     * A {@code MultisetBuilder} can be used to assemble a {@code Multiset} from two existing
     * {@code Collection} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstCollection = Literals.collectionWith(1, 2, 3);
     *   Collection&lt;Integer&gt; secondCollection = Literals.collectionWith(3, 4, 5);
     *   Multiset&lt;Integer&gt; multiset = multisetBuilderFrom(firstCollection)
     *           .with(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Multiset&lt;Integer&gt; multiset = Literals.multisetWith(1, 2, 3, 4, 5);
     * </pre>
     * </blockquote>
     * The advantage of the {@code MultisetBuilder} is that the multiset can be built up from
     * individual objects, arrays or existing iterables. See {@link MultisetBuilder} for
     * further details.
     *
     * @param elements An {@code Iterable} containing elements with which the
     *                 {@code MultisetBuilder} should be initialised.
     * @param <E>      The type of the elements contained in the {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> MultisetBuilder<E> multisetBuilderFrom(Iterable<? extends E> elements) {
        return new MultisetBuilder<E>().with(elements);
    }

    /**
     * Returns a {@code MultisetBuilder} over type {@code E} initialised with the elements
     * contained in the supplied array.
     *
     * <h4>Example Usage:</h4>
     * A {@code MultisetBuilder} can be used to assemble a {@code Multiset} from two existing
     * arrays as follows:
     * <blockquote>
     * <pre>
     *   Integer[] firstArray = new Integer[]{1, 2, 3};
     *   Integer[] secondArray = new Integer[]{3, 4, 5};
     *   Multiset&lt;Integer&gt; multiset = multisetBuilderFrom(firstArray)
     *           .with(secondArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Multiset&lt;Integer&gt; multiset = Literals.multisetWith(1, 2, 3, 4, 5);
     * </pre>
     * </blockquote>
     * The advantage of the {@code MultisetBuilder} is that the multiset can be built up from
     * individual objects, arrays or existing iterables. See {@link MultisetBuilder} for
     * further details.
     *
     * @param elementArray An array containing elements with which the
     *                     {@code MultisetBuilder} should be initialised.
     * @param <E>          The type of the elements contained in the {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over the type {@code E} containing
     *         the elements from the supplied array.
     */
    public static <E> MultisetBuilder<E> multisetBuilderFrom(E[] elementArray) {
        return new MultisetBuilder<E>().with(elementArray);
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * element.
     *
     * @param e   The element to be added to the {@code MultisetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         element.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e) {
        return multisetBuilderFrom(iterableWith(e));
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * elements.
     *
     * @param e1  The first element to be added to the {@code MultisetBuilder}.
     * @param e2  The second element to be added to the {@code MultisetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2) {
        return multisetBuilderFrom(iterableWith(e1, e2));
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * elements.
     *
     * @param e1  The first element to be added to the {@code MultisetBuilder}.
     * @param e2  The second element to be added to the {@code MultisetBuilder}.
     * @param e3  The third element to be added to the {@code MultisetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3) {
        return multisetBuilderFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * elements.
     *
     * @param e1  The first element to be added to the {@code MultisetBuilder}.
     * @param e2  The second element to be added to the {@code MultisetBuilder}.
     * @param e3  The third element to be added to the {@code MultisetBuilder}.
     * @param e4  The fourth element to be added to the {@code MultisetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4) {
        return multisetBuilderFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * elements.
     *
     * @param e1  The first element to be added to the {@code MultisetBuilder}.
     * @param e2  The second element to be added to the {@code MultisetBuilder}.
     * @param e3  The third element to be added to the {@code MultisetBuilder}.
     * @param e4  The fourth element to be added to the {@code MultisetBuilder}.
     * @param e5  The fifth element to be added to the {@code MultisetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5) {
        return multisetBuilderFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * elements.
     *
     * @param e1  The first element to be added to the {@code MultisetBuilder}.
     * @param e2  The second element to be added to the {@code MultisetBuilder}.
     * @param e3  The third element to be added to the {@code MultisetBuilder}.
     * @param e4  The fourth element to be added to the {@code MultisetBuilder}.
     * @param e5  The fifth element to be added to the {@code MultisetBuilder}.
     * @param e6  The sixth element to be added to the {@code MultisetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return multisetBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * elements.
     *
     * @param e1  The first element to be added to the {@code MultisetBuilder}.
     * @param e2  The second element to be added to the {@code MultisetBuilder}.
     * @param e3  The third element to be added to the {@code MultisetBuilder}.
     * @param e4  The fourth element to be added to the {@code MultisetBuilder}.
     * @param e5  The fifth element to be added to the {@code MultisetBuilder}.
     * @param e6  The sixth element to be added to the {@code MultisetBuilder}.
     * @param e7  The seventh element to be added to the {@code MultisetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return multisetBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * elements.
     *
     * @param e1  The first element to be added to the {@code MultisetBuilder}.
     * @param e2  The second element to be added to the {@code MultisetBuilder}.
     * @param e3  The third element to be added to the {@code MultisetBuilder}.
     * @param e4  The fourth element to be added to the {@code MultisetBuilder}.
     * @param e5  The fifth element to be added to the {@code MultisetBuilder}.
     * @param e6  The sixth element to be added to the {@code MultisetBuilder}.
     * @param e7  The seventh element to be added to the {@code MultisetBuilder}.
     * @param e8  The eighth element to be added to the {@code MultisetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return multisetBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * elements.
     *
     * @param e1  The first element to be added to the {@code MultisetBuilder}.
     * @param e2  The second element to be added to the {@code MultisetBuilder}.
     * @param e3  The third element to be added to the {@code MultisetBuilder}.
     * @param e4  The fourth element to be added to the {@code MultisetBuilder}.
     * @param e5  The fifth element to be added to the {@code MultisetBuilder}.
     * @param e6  The sixth element to be added to the {@code MultisetBuilder}.
     * @param e7  The seventh element to be added to the {@code MultisetBuilder}.
     * @param e8  The eighth element to be added to the {@code MultisetBuilder}.
     * @param e9  The ninth element to be added to the {@code MultisetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return multisetBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * elements.
     *
     * @param e1  The first element to be added to the {@code MultisetBuilder}.
     * @param e2  The second element to be added to the {@code MultisetBuilder}.
     * @param e3  The third element to be added to the {@code MultisetBuilder}.
     * @param e4  The fourth element to be added to the {@code MultisetBuilder}.
     * @param e5  The fifth element to be added to the {@code MultisetBuilder}.
     * @param e6  The sixth element to be added to the {@code MultisetBuilder}.
     * @param e7  The seventh element to be added to the {@code MultisetBuilder}.
     * @param e8  The eighth element to be added to the {@code MultisetBuilder}.
     * @param e9  The ninth element to be added to the {@code MultisetBuilder}.
     * @param e10 The tenth element to be added to the {@code MultisetBuilder}.
     * @param <E> The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return multisetBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns a {@code MultisetBuilder} instance over the type {@code E} containing the supplied
     * elements.
     *
     * @param e1    The first element to be added to the {@code MultisetBuilder}.
     * @param e2    The second element to be added to the {@code MultisetBuilder}.
     * @param e3    The third element to be added to the {@code MultisetBuilder}.
     * @param e4    The fourth element to be added to the {@code MultisetBuilder}.
     * @param e5    The fifth element to be added to the {@code MultisetBuilder}.
     * @param e6    The sixth element to be added to the {@code MultisetBuilder}.
     * @param e7    The seventh element to be added to the {@code MultisetBuilder}.
     * @param e8    The eighth element to be added to the {@code MultisetBuilder}.
     * @param e9    The ninth element to be added to the {@code MultisetBuilder}.
     * @param e10   The tenth element to be added to the {@code MultisetBuilder}.
     * @param e11on The remaining elements to be added to the {@code MultisetBuilder}. The elements
     *              will be added to the {@code MultisetBuilder} in the order they are defined in the
     *              variadic argument.
     * @param <E>   The type of the elements contained in the returned {@code MultisetBuilder}.
     * @return A {@code MultisetBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return multisetBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(e11on);
    }

    /**
     * Returns an empty immutable {@code Map} instance.
     *
     * <p>This form of literal is most suited to direct assignment to a variable
     * since in this case, the types {@code K} and {@code V} are inferred from the
     * variable declaration. For example:
     * <blockquote>
     * <pre>
     *   Map&lt;String, Integer&gt; mappings = map();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param <K> The type of the keys in the {@code Map}.
     * @param <V> The type of the values in the {@code Map}.
     * @return A {@code Map} with keys of type {@code K} and values of type {@code V}
     *         containing no elements.
     */
    public static <K, V> Map<K, V> map() {
        return new MapBuilder<K, V>().build();
    }

    /**
     * Returns an empty {@code Map} instance of the supplied concrete class.
     *
     * <p>The supplied class must have a public no-argument constructor, otherwise
     * an {@code IllegalArgumentException} will be thrown.</p>
     *
     * @param mapClass The class of the {@code Map} implementation to be
     *                 instantiated.
     * @param <K>      The type of the keys in the {@code Map}.
     * @param <V>      The type of the values in the {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} of the concrete type specified by the supplied {@code Class}.
     * @throws IllegalArgumentException if the supplied class does not have
     *                                  a public no-argument constructor.
     */
    public static <K, V> Map<K, V> map(Class<? extends Map> mapClass) {
        return new MapBuilder<K, V>().build(mapClass);
    }

    /**
     * Returns an empty immutable {@code Map} with keys of the type
     * of the first supplied {@code Class} and values of the type of
     * the second supplied {@code Class}.
     *
     * <p>This form of literal is most suited to inline usage such as when passing an
     * empty map as a parameter in a method call since it reads more clearly than
     * {@link #map()}. For example, compare the following:
     * <blockquote>
     * <pre>
     *   public class Server {
     *       public Server(Map&lt;String, String&gt; initialisationParameters) {
     *           ...
     *       }
     *
     *       ...
     *   }
     *
     *   new Server(Literals.&lt;String, String&gt;map());
     *   new Server(mapOf(String.class, String.class));
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param keyClass   A {@code Class} representing the type of the keys
     *                   contained in this {@code Map}.
     * @param valueClass A {@code Class} representing the type of the values
     *                   contained in this {@code Map}.
     * @param <K>        The type of the keys in the {@code Map}.
     * @param <V>        The type of the values in the {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V}.
     */
    public static <K, V> Map<K, V> mapOf(Class<K> keyClass, Class<V> valueClass) {
        return new MapBuilder<K, V>().build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all {@code Map.Entry} instances from the supplied
     * {@code Iterable}.
     *
     * <p>This form of literal is useful for converting an entry set back into a
     * {@code Map} instance. For example:
     * <blockquote>
     * <pre>
     *   Set&lt;Map.Entry&lt;Key, Value&gt;&gt; storeContents = store.loadKeyValues();
     *   Map&lt;Key, Value&gt; inMemoryStore = Literals.mapFromEntries(storeContents);
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elements An {@code Iterable} of {@code Map.Entry} elements from which a
     *                 {@code Map} should be constructed.
     * @param <K>      The type of the keys to be in the returned {@code Map}.
     * @param <V>      The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all {@code Map.Entry} elements from the supplied
     *         {@code Iterable}.
     */
    public static <K, V> Map<K, V> mapFromEntries(Iterable<? extends Map.Entry<K, V>> elements) {
        return new MapBuilder<K, V>().with(elements).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all {@code Map.Entry} instances from the supplied
     * array.
     *
     * <p>For example, the following:
     * <blockquote>
     * <pre>
     *   Map.Entry&lt;String, Integer&gt;[] entries = new Map.Entry[]{
     *          mapEntryFor("one", 1),
     *          mapEntryFor("two", 2)};
     *   Map&lt;String, Integer&gt; naturalNumbers = Literals.mapFromEntries(entries);
     * </pre>
     * </blockquote>
     * is equivalent to:
     * <blockquote>
     * <pre>
     *   Map&ltString, Integer&gt; naturalNumbers = Literals.mapWith(
     *          tuple("one", 1),
     *          tuple("two", 2));
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementArray An array of {@code Map.Entry} elements from which a
     *                     {@code Map} should be constructed.
     * @param <K>          The type of the keys to be in the returned {@code Map}.
     * @param <V>          The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all {@code Map.Entry} elements from the supplied
     *         array.
     */
    public static <K, V> Map<K, V> mapFromEntries(Map.Entry<K, V>[] elementArray) {
        return new MapBuilder<K, V>().with(elementArray).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each {@code Pair} in the supplied
     * {@code Iterable}.
     *
     * <p>This form of literal is useful when it is required to represent an
     * {@code Iterable} of {@code Pair} instances as a {@code Map}. For example:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Pair&lt;Key, Value&gt;&gt; storeContents = store.loadKeyValues();
     *   Map&lt;Key, Value&gt; inMemoryStore = Literals.mapFromPairs(storeContents);
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elements An {@code Iterable} of {@code Pair} instances from which a
     *                 {@code Map} should be constructed.
     * @param <K>      The type of the keys to be in the returned {@code Map}.
     * @param <V>      The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each {@code Pair} in the
     *         supplied {@code Iterable}.
     */
    public static <K, V> Map<K, V> mapFromPairs(Iterable<? extends Pair<K, V>> elements) {
        return new MapBuilder<K, V>().withPairs(elements).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each {@code Pair} in the supplied
     * array.
     *
     * <p>For example, the following:
     * <blockquote>
     * <pre>
     *   Pair&lt;String, Integer&gt;[] pairs = new Pair[]{
     *          tuple("one", 1),
     *          tuple("two", 2)};
     *   Map&lt;String, Integer&gt; naturalNumbers = Literals.mapFromPairs(entries);
     * </pre>
     * </blockquote>
     * is equivalent to:
     * <blockquote>
     * <pre>
     *   Map&ltString, Integer&gt; naturalNumbers = Literals.mapWith(
     *          mapEntryFor("one", 1),
     *          mapEntryFor("two", 2));
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementArray An array of {@code Pair} instances from which a
     *                     {@code Map} should be constructed.
     * @param <K>          The type of the keys to be in the returned {@code Map}.
     * @param <V>          The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each {@code Pair} in the
     *         supplied {@code Iterable}.
     */
    public static <K, V> Map<K, V> mapFromPairs(Pair<K, V>[] elementArray) {
        return new MapBuilder<K, V>().withPairs(elementArray).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all {@code Map} instances in the
     * supplied {@code Iterable}.
     *
     * <p>If later {@code Map} instances in the {@code Iterable} contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     * <p>This form of literal is useful when it is required to collect an
     * {@code Iterable} of {@code Map} instances into an accumulated {@code Map}.
     * For example:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Map&lt;Key, Value&gt;&gt; allStoreContents = keyValueStores.loadAll();
     *   Map&lt;Key, Value&gt; completeInMemoryStore = Literals.mapFromMaps(allStoreContents);
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elements An {@code Iterable} of {@code Map} instances from which a
     *                 {@code Map} should be constructed.
     * @param <K>      The type of the keys to be in the returned {@code Map}.
     * @param <V>      The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from all {@code Map} instances in the
     *         supplied {@code Iterable}.
     */
    public static <K, V> Map<K, V> mapFromMaps(Iterable<? extends Map<K, V>> elements) {
        return new MapBuilder<K, V>().withMaps(elements).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all {@code Map} instances in the
     * array.
     *
     * <p>If later {@code Map} instances in the array contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     * <p>For example, the following:
     * <blockquote>
     * <pre>
     *   Map&lt;String, Integer&gt;[] maps = new Map[]{
     *          mapWithKeyValuePairs("one", 1, "two", 3),
     *          mapWithKeyValuePairs("two", 2, "three", 3),};
     *   Map&lt;String, Integer&gt; map = Literals.mapFromMaps(maps);
     * </pre>
     * </blockquote>
     * is equivalent to:
     * <blockquote>
     * <pre>
     *   Map&ltString, Integer&gt; map = Literals.mapWith(
     *          mapEntryFor("one", 1),
     *          mapEntryFor("two", 2),
     *          mapEntryFor("three", 3));
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param elementArray An array of {@code Map} instances from which a
     *                     {@code Map} should be constructed.
     * @param <K>          The type of the keys to be in the returned {@code Map}.
     * @param <V>          The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from all {@code Map} instances in the
     *         supplied {@code Iterable}.
     */
    public static <K, V> Map<K, V> mapFromMaps(Map<K, V>[] elementArray) {
        return new MapBuilder<K, V>().withMaps(elementArray).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the entry defined by the supplied {@code Map.Entry}.
     *
     * @param e   A {@code Map.Entry} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry}.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e) {
        return mapFromEntries(iterableWith(e));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} from which to construct a {@code Map}.
     * @param e2  The second {@code Map.Entry} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
        return mapFromEntries(iterableWith(e1, e2));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} from which to construct a {@code Map}.
     * @param e2  The second {@code Map.Entry} from which to construct a {@code Map}.
     * @param e3  The third {@code Map.Entry} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3) {
        return mapFromEntries(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} from which to construct a {@code Map}.
     * @param e2  The second {@code Map.Entry} from which to construct a {@code Map}.
     * @param e3  The third {@code Map.Entry} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Map.Entry} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4) {
        return mapFromEntries(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} from which to construct a {@code Map}.
     * @param e2  The second {@code Map.Entry} from which to construct a {@code Map}.
     * @param e3  The third {@code Map.Entry} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Map.Entry} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5) {
        return mapFromEntries(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} from which to construct a {@code Map}.
     * @param e2  The second {@code Map.Entry} from which to construct a {@code Map}.
     * @param e3  The third {@code Map.Entry} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e6  The sixth {@code Map.Entry} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6) {
        return mapFromEntries(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} from which to construct a {@code Map}.
     * @param e2  The second {@code Map.Entry} from which to construct a {@code Map}.
     * @param e3  The third {@code Map.Entry} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e6  The sixth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e7  The seventh {@code Map.Entry} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7) {
        return mapFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} from which to construct a {@code Map}.
     * @param e2  The second {@code Map.Entry} from which to construct a {@code Map}.
     * @param e3  The third {@code Map.Entry} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e6  The sixth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e7  The seventh {@code Map.Entry} from which to construct a {@code Map}.
     * @param e8  The eighth {@code Map.Entry} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8) {
        return mapFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} from which to construct a {@code Map}.
     * @param e2  The second {@code Map.Entry} from which to construct a {@code Map}.
     * @param e3  The third {@code Map.Entry} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e6  The sixth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e7  The seventh {@code Map.Entry} from which to construct a {@code Map}.
     * @param e8  The eighth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e9  The ninth {@code Map.Entry} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9) {
        return mapFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} from which to construct a {@code Map}.
     * @param e2  The second {@code Map.Entry} from which to construct a {@code Map}.
     * @param e3  The third {@code Map.Entry} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e6  The sixth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e7  The seventh {@code Map.Entry} from which to construct a {@code Map}.
     * @param e8  The eighth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e9  The ninth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e10 The tenth {@code Map.Entry} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9, Map.Entry<K, V> e10) {
        return mapFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied {@code Map.Entry} instances.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, a {@link MapBuilder} can be used instead.</p>
     *
     * @param e1    The first {@code Map.Entry} from which to construct a {@code Map}.
     * @param e2    The second {@code Map.Entry} from which to construct a {@code Map}.
     * @param e3    The third {@code Map.Entry} from which to construct a {@code Map}.
     * @param e4    The fourth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e5    The fifth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e6    The sixth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e7    The seventh {@code Map.Entry} from which to construct a {@code Map}.
     * @param e8    The eighth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e9    The ninth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e10   The tenth {@code Map.Entry} from which to construct a {@code Map}.
     * @param e11on The remaining {@code Map.Entry} instances from which to construct
     *              a {@code Map}.
     * @param <K>   The type of the keys to be in the returned {@code Map}.
     * @param <V>   The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9, Map.Entry<K, V> e10, Map.Entry<K, V>... e11on) {
        return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).and(e11on).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with a key-value mapping for the supplied {@code Pair}.
     *
     * @param e   A {@code Pair} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing a key-value mapping for the supplied {@code Pair}.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e) {
        return mapFromPairs(iterableWith(e));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each of the supplied {@code Pair}
     * instances.
     *
     * @param e1  The first {@code Pair} from which to construct a {@code Map}.
     * @param e2  The second {@code Pair} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each of the supplied
     *         {@code Pair} instances.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2) {
        return mapFromPairs(iterableWith(e1, e2));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each of the supplied {@code Pair}
     * instances.
     *
     * @param e1  The first {@code Pair} from which to construct a {@code Map}.
     * @param e2  The second {@code Pair} from which to construct a {@code Map}.
     * @param e3  The third {@code Pair} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each of the supplied
     *         {@code Pair} instances.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3) {
        return mapFromPairs(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each of the supplied {@code Pair}
     * instances.
     *
     * @param e1  The first {@code Pair} from which to construct a {@code Map}.
     * @param e2  The second {@code Pair} from which to construct a {@code Map}.
     * @param e3  The third {@code Pair} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Pair} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each of the supplied
     *         {@code Pair} instances.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4) {
        return mapFromPairs(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each of the supplied {@code Pair}
     * instances.
     *
     * @param e1  The first {@code Pair} from which to construct a {@code Map}.
     * @param e2  The second {@code Pair} from which to construct a {@code Map}.
     * @param e3  The third {@code Pair} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Pair} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Pair} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each of the supplied
     *         {@code Pair} instances.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5) {
        return mapFromPairs(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each of the supplied {@code Pair}
     * instances.
     *
     * @param e1  The first {@code Pair} from which to construct a {@code Map}.
     * @param e2  The second {@code Pair} from which to construct a {@code Map}.
     * @param e3  The third {@code Pair} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Pair} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Pair} from which to construct a {@code Map}.
     * @param e6  The sixth {@code Pair} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each of the supplied
     *         {@code Pair} instances.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6) {
        return mapFromPairs(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each of the supplied {@code Pair}
     * instances.
     *
     * @param e1  The first {@code Pair} from which to construct a {@code Map}.
     * @param e2  The second {@code Pair} from which to construct a {@code Map}.
     * @param e3  The third {@code Pair} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Pair} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Pair} from which to construct a {@code Map}.
     * @param e6  The sixth {@code Pair} from which to construct a {@code Map}.
     * @param e7  The seventh {@code Pair} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each of the supplied
     *         {@code Pair} instances.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7) {
        return mapFromPairs(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each of the supplied {@code Pair}
     * instances.
     *
     * @param e1  The first {@code Pair} from which to construct a {@code Map}.
     * @param e2  The second {@code Pair} from which to construct a {@code Map}.
     * @param e3  The third {@code Pair} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Pair} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Pair} from which to construct a {@code Map}.
     * @param e6  The sixth {@code Pair} from which to construct a {@code Map}.
     * @param e7  The seventh {@code Pair} from which to construct a {@code Map}.
     * @param e8  The eighth {@code Pair} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each of the supplied
     *         {@code Pair} instances.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8) {
        return mapFromPairs(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each of the supplied {@code Pair}
     * instances.
     *
     * @param e1  The first {@code Pair} from which to construct a {@code Map}.
     * @param e2  The second {@code Pair} from which to construct a {@code Map}.
     * @param e3  The third {@code Pair} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Pair} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Pair} from which to construct a {@code Map}.
     * @param e6  The sixth {@code Pair} from which to construct a {@code Map}.
     * @param e7  The seventh {@code Pair} from which to construct a {@code Map}.
     * @param e8  The eighth {@code Pair} from which to construct a {@code Map}.
     * @param e9  The ninth {@code Pair} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each of the supplied
     *         {@code Pair} instances.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9) {
        return mapFromPairs(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each of the supplied {@code Pair}
     * instances.
     *
     * @param e1  The first {@code Pair} from which to construct a {@code Map}.
     * @param e2  The second {@code Pair} from which to construct a {@code Map}.
     * @param e3  The third {@code Pair} from which to construct a {@code Map}.
     * @param e4  The fourth {@code Pair} from which to construct a {@code Map}.
     * @param e5  The fifth {@code Pair} from which to construct a {@code Map}.
     * @param e6  The sixth {@code Pair} from which to construct a {@code Map}.
     * @param e7  The seventh {@code Pair} from which to construct a {@code Map}.
     * @param e8  The eighth {@code Pair} from which to construct a {@code Map}.
     * @param e9  The ninth {@code Pair} from which to construct a {@code Map}.
     * @param e10 The tenth {@code Pair} from which to construct a {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each of the supplied
     *         {@code Pair} instances.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9, Pair<K, V> e10) {
        return mapFromPairs(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} with key-value mappings for each of the supplied {@code Pair}
     * instances.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, a {@link MapBuilder} can be used instead.</p>
     *
     * @param e1    The first {@code Pair} from which to construct a {@code Map}.
     * @param e2    The second {@code Pair} from which to construct a {@code Map}.
     * @param e3    The third {@code Pair} from which to construct a {@code Map}.
     * @param e4    The fourth {@code Pair} from which to construct a {@code Map}.
     * @param e5    The fifth {@code Pair} from which to construct a {@code Map}.
     * @param e6    The sixth {@code Pair} from which to construct a {@code Map}.
     * @param e7    The seventh {@code Pair} from which to construct a {@code Map}.
     * @param e8    The eighth {@code Pair} from which to construct a {@code Map}.
     * @param e9    The ninth {@code Pair} from which to construct a {@code Map}.
     * @param e10   The tenth {@code Pair} from which to construct a {@code Map}.
     * @param e11on The remaining {@code Pair} instances from which to construct a
     *              {@code Map}.
     * @param <K>   The type of the keys to be in the returned {@code Map}.
     * @param <V>   The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each of the supplied
     *         {@code Pair} instances.
     */
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9, Pair<K, V> e10, Pair<K, V>... e11on) {
        return mapBuilderFromPairs(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).andPairs(e11on).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from the supplied {@code Map} instance.
     *
     * @param m   A {@code Map} from which to construct this {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the supplied {@code Map} instance.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m) {
        return mapFromMaps(iterableWith(m));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all supplied {@code Map} instances.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     *
     * @param m1  The first {@code Map} from which to construct this {@code Map}.
     * @param m2  The second {@code Map} from which to construct this {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the all supplied {@code Map}
     *         instances.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m1, Map<K, V> m2) {
        return mapFromMaps(iterableWith(m1, m2));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all supplied {@code Map} instances.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     *
     * @param m1  The first {@code Map} from which to construct this {@code Map}.
     * @param m2  The second {@code Map} from which to construct this {@code Map}.
     * @param m3  The third {@code Map} from which to construct this {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the all supplied {@code Map}
     *         instances.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3) {
        return mapFromMaps(iterableWith(m1, m2, m3));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all supplied {@code Map} instances.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     *
     * @param m1  The first {@code Map} from which to construct this {@code Map}.
     * @param m2  The second {@code Map} from which to construct this {@code Map}.
     * @param m3  The third {@code Map} from which to construct this {@code Map}.
     * @param m4  The fourth {@code Map} from which to construct this {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the all supplied {@code Map}
     *         instances.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4) {
        return mapFromMaps(iterableWith(m1, m2, m3, m4));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all supplied {@code Map} instances.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     *
     * @param m1  The first {@code Map} from which to construct this {@code Map}.
     * @param m2  The second {@code Map} from which to construct this {@code Map}.
     * @param m3  The third {@code Map} from which to construct this {@code Map}.
     * @param m4  The fourth {@code Map} from which to construct this {@code Map}.
     * @param m5  The fifth {@code Map} from which to construct this {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the all supplied {@code Map}
     *         instances.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5) {
        return mapFromMaps(iterableWith(m1, m2, m3, m4, m5));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all supplied {@code Map} instances.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     *
     * @param m1  The first {@code Map} from which to construct this {@code Map}.
     * @param m2  The second {@code Map} from which to construct this {@code Map}.
     * @param m3  The third {@code Map} from which to construct this {@code Map}.
     * @param m4  The fourth {@code Map} from which to construct this {@code Map}.
     * @param m5  The fifth {@code Map} from which to construct this {@code Map}.
     * @param m6  The sixth {@code Map} from which to construct this {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the all supplied {@code Map}
     *         instances.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6) {
        return mapFromMaps(iterableWith(m1, m2, m3, m4, m5, m6));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all supplied {@code Map} instances.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     *
     * @param m1  The first {@code Map} from which to construct this {@code Map}.
     * @param m2  The second {@code Map} from which to construct this {@code Map}.
     * @param m3  The third {@code Map} from which to construct this {@code Map}.
     * @param m4  The fourth {@code Map} from which to construct this {@code Map}.
     * @param m5  The fifth {@code Map} from which to construct this {@code Map}.
     * @param m6  The sixth {@code Map} from which to construct this {@code Map}.
     * @param m7  The seventh {@code Map} from which to construct this {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the all supplied {@code Map}
     *         instances.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6, Map<K, V> m7) {
        return mapFromMaps(iterableWith(m1, m2, m3, m4, m5, m6, m7));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all supplied {@code Map} instances.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     *
     * @param m1  The first {@code Map} from which to construct this {@code Map}.
     * @param m2  The second {@code Map} from which to construct this {@code Map}.
     * @param m3  The third {@code Map} from which to construct this {@code Map}.
     * @param m4  The fourth {@code Map} from which to construct this {@code Map}.
     * @param m5  The fifth {@code Map} from which to construct this {@code Map}.
     * @param m6  The sixth {@code Map} from which to construct this {@code Map}.
     * @param m7  The seventh {@code Map} from which to construct this {@code Map}.
     * @param m8  The eighth {@code Map} from which to construct this {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the all supplied {@code Map}
     *         instances.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6, Map<K, V> m7, Map<K, V> m8) {
        return mapFromMaps(iterableWith(m1, m2, m3, m4, m5, m6, m7, m8));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all supplied {@code Map} instances.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     *
     * @param m1  The first {@code Map} from which to construct this {@code Map}.
     * @param m2  The second {@code Map} from which to construct this {@code Map}.
     * @param m3  The third {@code Map} from which to construct this {@code Map}.
     * @param m4  The fourth {@code Map} from which to construct this {@code Map}.
     * @param m5  The fifth {@code Map} from which to construct this {@code Map}.
     * @param m6  The sixth {@code Map} from which to construct this {@code Map}.
     * @param m7  The seventh {@code Map} from which to construct this {@code Map}.
     * @param m8  The eighth {@code Map} from which to construct this {@code Map}.
     * @param m9  The ninth {@code Map} from which to construct this {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the all supplied {@code Map}
     *         instances.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6, Map<K, V> m7, Map<K, V> m8, Map<K, V> m9) {
        return mapFromMaps(iterableWith(m1, m2, m3, m4, m5, m6, m7, m8, m9));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all supplied {@code Map} instances.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     *
     * @param m1  The first {@code Map} from which to construct this {@code Map}.
     * @param m2  The second {@code Map} from which to construct this {@code Map}.
     * @param m3  The third {@code Map} from which to construct this {@code Map}.
     * @param m4  The fourth {@code Map} from which to construct this {@code Map}.
     * @param m5  The fifth {@code Map} from which to construct this {@code Map}.
     * @param m6  The sixth {@code Map} from which to construct this {@code Map}.
     * @param m7  The seventh {@code Map} from which to construct this {@code Map}.
     * @param m8  The eighth {@code Map} from which to construct this {@code Map}.
     * @param m9  The ninth {@code Map} from which to construct this {@code Map}.
     * @param m10 The tenth {@code Map} from which to construct this {@code Map}.
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the all supplied {@code Map}
     *         instances.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6, Map<K, V> m7, Map<K, V> m8, Map<K, V> m9, Map<K, V> m10) {
        return mapFromMaps(iterableWith(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10));
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing all entries from all supplied {@code Map} instances.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value in the returned {@code Map}.
     * </p>
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, a {@link MapBuilder} can be used instead.</p>
     *
     * @param m1    The first {@code Map} from which to construct this {@code Map}.
     * @param m2    The second {@code Map} from which to construct this {@code Map}.
     * @param m3    The third {@code Map} from which to construct this {@code Map}.
     * @param m4    The fourth {@code Map} from which to construct this {@code Map}.
     * @param m5    The fifth {@code Map} from which to construct this {@code Map}.
     * @param m6    The sixth {@code Map} from which to construct this {@code Map}.
     * @param m7    The seventh {@code Map} from which to construct this {@code Map}.
     * @param m8    The eighth {@code Map} from which to construct this {@code Map}.
     * @param m9    The ninth {@code Map} from which to construct this {@code Map}.
     * @param m10   The tenth {@code Map} from which to construct this {@code Map}.
     * @param m11on The remaining {@code Map} instances from which to construct
     *              this {@code Map}
     * @param <K>   The type of the keys to be in the returned {@code Map}.
     * @param <V>   The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing all entries from the all supplied {@code Map}
     *         instances.
     */
    public static <K, V> Map<K, V> mapWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6, Map<K, V> m7, Map<K, V> m8, Map<K, V> m9, Map<K, V> m10, Map<K, V>... m11on) {
        return mapBuilderFromMaps(iterableWith(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10)).andMaps(m11on).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied key mapped to the supplied value.
     *
     * @param k1  The key for the first entry from which to construct a {@code Map}.
     * @param v1  The value for the first entry from which to construct a {@code Map}
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied key mapped to the supplied value.
     */
    public static <K, V> Map<K, V> mapWithKeyValuePair(K k1, V v1) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1)).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry from which to construct a {@code Map}.
     * @param v1  The value for the first entry from which to construct a {@code Map}
     * @param k2  The key for the second entry from which to construct a {@code Map}.
     * @param v2  The value for the second entry from which to construct a {@code Map}
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> Map<K, V> mapWithKeyValuePairs(K k1, V v1, K k2, V v2) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2)).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry from which to construct a {@code Map}.
     * @param v1  The value for the first entry from which to construct a {@code Map}
     * @param k2  The key for the second entry from which to construct a {@code Map}.
     * @param v2  The value for the second entry from which to construct a {@code Map}
     * @param k3  The key for the third entry from which to construct a {@code Map}.
     * @param v3  The value for the third entry from which to construct a {@code Map}
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> Map<K, V> mapWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3)).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry from which to construct a {@code Map}.
     * @param v1  The value for the first entry from which to construct a {@code Map}
     * @param k2  The key for the second entry from which to construct a {@code Map}.
     * @param v2  The value for the second entry from which to construct a {@code Map}
     * @param k3  The key for the third entry from which to construct a {@code Map}.
     * @param v3  The value for the third entry from which to construct a {@code Map}
     * @param k4  The key for the fourth entry from which to construct a {@code Map}.
     * @param v4  The value for the fourth entry from which to construct a {@code Map}
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> Map<K, V> mapWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4)).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry from which to construct a {@code Map}.
     * @param v1  The value for the first entry from which to construct a {@code Map}
     * @param k2  The key for the second entry from which to construct a {@code Map}.
     * @param v2  The value for the second entry from which to construct a {@code Map}
     * @param k3  The key for the third entry from which to construct a {@code Map}.
     * @param v3  The value for the third entry from which to construct a {@code Map}
     * @param k4  The key for the fourth entry from which to construct a {@code Map}.
     * @param v4  The value for the fourth entry from which to construct a {@code Map}
     * @param k5  The key for the fifth entry from which to construct a {@code Map}.
     * @param v5  The value for the fifth entry from which to construct a {@code Map}
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> Map<K, V> mapWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5)).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry from which to construct a {@code Map}.
     * @param v1  The value for the first entry from which to construct a {@code Map}
     * @param k2  The key for the second entry from which to construct a {@code Map}.
     * @param v2  The value for the second entry from which to construct a {@code Map}
     * @param k3  The key for the third entry from which to construct a {@code Map}.
     * @param v3  The value for the third entry from which to construct a {@code Map}
     * @param k4  The key for the fourth entry from which to construct a {@code Map}.
     * @param v4  The value for the fourth entry from which to construct a {@code Map}
     * @param k5  The key for the fifth entry from which to construct a {@code Map}.
     * @param v5  The value for the fifth entry from which to construct a {@code Map}
     * @param k6  The key for the sixth entry from which to construct a {@code Map}.
     * @param v6  The value for the sixth entry from which to construct a {@code Map}
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> Map<K, V> mapWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6)).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry from which to construct a {@code Map}.
     * @param v1  The value for the first entry from which to construct a {@code Map}
     * @param k2  The key for the second entry from which to construct a {@code Map}.
     * @param v2  The value for the second entry from which to construct a {@code Map}
     * @param k3  The key for the third entry from which to construct a {@code Map}.
     * @param v3  The value for the third entry from which to construct a {@code Map}
     * @param k4  The key for the fourth entry from which to construct a {@code Map}.
     * @param v4  The value for the fourth entry from which to construct a {@code Map}
     * @param k5  The key for the fifth entry from which to construct a {@code Map}.
     * @param v5  The value for the fifth entry from which to construct a {@code Map}
     * @param k6  The key for the sixth entry from which to construct a {@code Map}.
     * @param v6  The value for the sixth entry from which to construct a {@code Map}
     * @param k7  The key for the seventh entry from which to construct a {@code Map}.
     * @param v7  The value for the seventh entry from which to construct a {@code Map}
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> Map<K, V> mapWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7)).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry from which to construct a {@code Map}.
     * @param v1  The value for the first entry from which to construct a {@code Map}
     * @param k2  The key for the second entry from which to construct a {@code Map}.
     * @param v2  The value for the second entry from which to construct a {@code Map}
     * @param k3  The key for the third entry from which to construct a {@code Map}.
     * @param v3  The value for the third entry from which to construct a {@code Map}
     * @param k4  The key for the fourth entry from which to construct a {@code Map}.
     * @param v4  The value for the fourth entry from which to construct a {@code Map}
     * @param k5  The key for the fifth entry from which to construct a {@code Map}.
     * @param v5  The value for the fifth entry from which to construct a {@code Map}
     * @param k6  The key for the sixth entry from which to construct a {@code Map}.
     * @param v6  The value for the sixth entry from which to construct a {@code Map}
     * @param k7  The key for the seventh entry from which to construct a {@code Map}.
     * @param v7  The value for the seventh entry from which to construct a {@code Map}
     * @param k8  The key for the eighth entry from which to construct a {@code Map}.
     * @param v8  The value for the eighth entry from which to construct a {@code Map}
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> Map<K, V> mapWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8)).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry from which to construct a {@code Map}.
     * @param v1  The value for the first entry from which to construct a {@code Map}
     * @param k2  The key for the second entry from which to construct a {@code Map}.
     * @param v2  The value for the second entry from which to construct a {@code Map}
     * @param k3  The key for the third entry from which to construct a {@code Map}.
     * @param v3  The value for the third entry from which to construct a {@code Map}
     * @param k4  The key for the fourth entry from which to construct a {@code Map}.
     * @param v4  The value for the fourth entry from which to construct a {@code Map}
     * @param k5  The key for the fifth entry from which to construct a {@code Map}.
     * @param v5  The value for the fifth entry from which to construct a {@code Map}
     * @param k6  The key for the sixth entry from which to construct a {@code Map}.
     * @param v6  The value for the sixth entry from which to construct a {@code Map}
     * @param k7  The key for the seventh entry from which to construct a {@code Map}.
     * @param v7  The value for the seventh entry from which to construct a {@code Map}
     * @param k8  The key for the eighth entry from which to construct a {@code Map}.
     * @param v8  The value for the eighth entry from which to construct a {@code Map}
     * @param k9  The key for the ninth entry from which to construct a {@code Map}.
     * @param v9  The value for the ninth entry from which to construct a {@code Map}
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> Map<K, V> mapWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9)).build();
    }

    /**
     * Returns an immutable {@code Map} instance with keys of type {@code K} and values
     * of type {@code V} containing the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry from which to construct a {@code Map}.
     * @param v1  The value for the first entry from which to construct a {@code Map}
     * @param k2  The key for the second entry from which to construct a {@code Map}.
     * @param v2  The value for the second entry from which to construct a {@code Map}
     * @param k3  The key for the third entry from which to construct a {@code Map}.
     * @param v3  The value for the third entry from which to construct a {@code Map}
     * @param k4  The key for the fourth entry from which to construct a {@code Map}.
     * @param v4  The value for the fourth entry from which to construct a {@code Map}
     * @param k5  The key for the fifth entry from which to construct a {@code Map}.
     * @param v5  The value for the fifth entry from which to construct a {@code Map}
     * @param k6  The key for the sixth entry from which to construct a {@code Map}.
     * @param v6  The value for the sixth entry from which to construct a {@code Map}
     * @param k7  The key for the seventh entry from which to construct a {@code Map}.
     * @param v7  The value for the seventh entry from which to construct a {@code Map}
     * @param k8  The key for the eighth entry from which to construct a {@code Map}.
     * @param v8  The value for the eighth entry from which to construct a {@code Map}
     * @param k9  The key for the ninth entry from which to construct a {@code Map}.
     * @param v9  The value for the ninth entry from which to construct a {@code Map}
     * @param k10 The key for the tenth entry from which to construct a {@code Map}.
     * @param v10 The value for the tenth entry from which to construct a {@code Map}
     * @param <K> The type of the keys to be in the returned {@code Map}.
     * @param <V> The type of the values to be in the returned {@code Map}.
     * @return A {@code Map} instance with keys of type {@code K} and values of type
     *         {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> Map<K, V> mapWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9), mapEntryFor(k10, v10)).build();
    }

    /**
     * Returns a {@code MapBuilder} containing no elements.
     *
     * <h4>Example Usage:</h4>
     * A {@code MapBuilder} can be used to assemble a {@code Map} as follows:
     * <blockquote>
     * <pre>
     *   Map&lt;String, Boolean&gt; map = Literals.&lt;String, Boolean&gt;mapBuilder()
     *           .with(mapEntryFor("feature1", true), mapEntryFor("feature2", false))
     *           .and(tuple("feature3", true))
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Map&lt;String, Boolean&gt; map = Literals.mapWith("feature1", true, "feature2", false, "feature3", true);
     * </pre>
     * </blockquote>
     * The advantage of the {@code MapBuilder} is that the map can be built up from
     * individual objects, arrays or existing iterables. See {@link MapBuilder} for
     * further details.
     *
     * @param <K> The type of the keys contained in the {@code MapBuilder}.
     * @param <V> The type of the values contained in the {@code MapBuilder}.
     * @return A {@code MapBuilder} instance for keys of type {@code K} and
     *         values of type {@code V} containing no elements.
     */
    public static <K, V> MapBuilder<K, V> mapBuilder() {
        return new MapBuilder<K, V>();
    }

    /**
     * Returns a {@code MapBuilder} with keys of the type of the first
     * supplied {@code Class} and values of the type of the second
     * supplied {@code Class}.
     *
     * <h4>Example Usage:</h4>
     * A {@code MapBuilder} can be used to assemble a {@code Map} as follows:
     * <blockquote>
     * <pre>
     *   Map&lt;String, Boolean&gt; map = mapBuilderOf(String.class, Boolean.class)
     *           .with(mapEntryFor("feature1", true), mapEntryFor("feature2", false))
     *           .and(tuple("feature3", true))
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Map&lt;String, Boolean&gt; map = Literals.mapWith("feature1", true, "feature2", false, "feature3", true);
     * </pre>
     * </blockquote>
     * The advantage of the {@code MapBuilder} is that the map can be built up from
     * individual objects, arrays or existing iterables. See {@link MapBuilder} for
     * further details.
     *
     * @param keyClass   A {@code Class} representing the type of the keys
     *                   contained in this {@code MapBuilder}.
     * @param valueClass A {@code Class} representing the type of the values
     *                   contained in this {@code MapBuilder}.
     * @param <K>        The type of the keys contained in the {@code MapBuilder}.
     * @param <V>        The type of the values contained in the {@code MapBuilder}.
     * @return A {@code MapBuilder} instance for keys of type {@code K} and
     *         values of type {@code V} containing no elements.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderOf(Class<K> keyClass, Class<V> valueClass) {
        return new MapBuilder<K, V>();
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all {@code Map.Entry} instances from
     * the supplied {@code Iterable}.
     *
     * <h4>Example Usage:</h4>
     * A {@code MapBuilder} can be used to assemble a {@code Map} from two existing
     * {@code Collection} instances containing {@code Map.Entry} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Map.Entry&lt;Integer, Long&gt;&gt; firstCollection = Literals.collectionWith(
     *           mapEntryFor(1, 1L),
     *           mapEntryFor(2, 2L));
     *   Collection&lt;Map.Entry&lt;Integer, Long&gt;&gt; secondCollection = Literals.collectionWith(
     *           mapEntryFor(3, 3L),
     *           mapEntryFor(4, 4L));
     *   Map&lt;Integer, Long&gt; map = mapBuilderFromEntries(firstCollection)
     *           .withEntries(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Map&lt;Integer, Long&gt; map = Literals.mapWith(tuple(1, 1L), tuple(2, 2L), tuple(3, 3L), tuple(4, 4L));
     * </pre>
     * </blockquote>
     * The advantage of the {@code MapBuilder} is that the map can be built up from
     * individual objects, arrays or existing iterables. See {@link MapBuilder} for
     * further details.
     *
     * @param entries An {@code Iterable} of {@code Map.Entry} elements with which a
     *                {@code MapBuilder} should be initialised.
     * @param <K>     The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V>     The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of type
     *         {@code V} containing all {@code Map.Entry} elements from the supplied
     *         {@code Iterable}.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderFromEntries(Iterable<? extends Map.Entry<K, V>> entries) {
        return new MapBuilder<K, V>().with(entries);
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all {@code Map.Entry} instances from
     * the supplied array.
     *
     * <h4>Example Usage:</h4>
     * A {@code MapBuilder} can be used to assemble a {@code Map} from two existing
     * array instances containing {@code Map.Entry} instances as follows:
     * <blockquote>
     * <pre>
     *   Map.Entry&lt;Integer, Long&gt;[] firstEntryArray = new Map.Entry[]{
     *           mapEntryFor(1, 1L),
     *           mapEntryFor(2, 2L)};
     *   Map.Entry&lt;Integer, Long&gt;[] secondEntryArray = new Map.Entry[]{
     *           mapEntryFor(3, 3L),
     *           mapEntryFor(4, 4L)};
     *   Map&lt;Integer, Long&gt; map = mapBuilderFromEntries(firstEntryArray)
     *           .withEntries(secondEntryArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Map&lt;Integer, Long&gt; map = Literals.mapWith(tuple(1, 1L), tuple(2, 2L), tuple(3, 3L), tuple(4, 4L));
     * </pre>
     * </blockquote>
     * The advantage of the {@code MapBuilder} is that the map can be built up from
     * individual objects, arrays or existing iterables. See {@link MapBuilder} for
     * further details.
     *
     * @param entries An array of {@code Map.Entry} elements with which a
     *                {@code MapBuilder} should be initialised.
     * @param <K>     The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V>     The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of type
     *         {@code V} containing all {@code Map.Entry} elements from the supplied
     *         array.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderFromEntries(Map.Entry<K, V>[] entries) {
        return new MapBuilder<K, V>().with(entries);
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for each {@code Pair}
     * in the supplied {@code Iterable}.
     *
     * <h4>Example Usage:</h4>
     * A {@code MapBuilder} can be used to assemble a {@code Map} from two existing
     * {@code Collection} instances containing {@code Pair} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Pair&lt;Integer, Long&gt;&gt; firstCollection = Literals.collectionWith(
     *           tuple(1, 1L),
     *           tuple(2, 2L));
     *   Collection&lt;Pair&lt;Integer, Long&gt;&gt; secondCollection = Literals.collectionWith(
     *           tuple(3, 3L),
     *           tuple(4, 4L));
     *   Map&lt;Integer, Long&gt; map = mapBuilderFromPairs(firstCollection)
     *           .withPairs(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Map&lt;Integer, Long&gt; map = Literals.mapWith(
     *           mapEntryFor(1, 1L),
     *           mapEntryFor(2, 2L),
     *           mapEntryFor(3, 3L),
     *           mapEntryFor(4, 4L));
     * </pre>
     * </blockquote>
     * The advantage of the {@code MapBuilder} is that the map can be built up from
     * individual objects, arrays or existing iterables. See {@link MapBuilder} for
     * further details.
     *
     * @param entries An {@code Iterable} of {@code Pair} instance with which a
     *                {@code MapBuilder} should be initialised.
     * @param <K>     The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V>     The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each {@code Pair} in the
     *         supplied {@code Iterable}.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderFromPairs(Iterable<? extends Pair<K, V>> entries) {
        return new MapBuilder<K, V>().withPairs(entries);
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for each {@code Pair}
     * in the supplied array.
     *
     * <h4>Example Usage:</h4>
     * A {@code MapBuilder} can be used to assemble a {@code Map} from two existing
     * array instances containing {@code Pair} instances as follows:
     * <blockquote>
     * <pre>
     *   Pair&lt;Integer, Long&gt;[] firstPairArray = new Pair[]{
     *           tuple(1, 1L),
     *           tuple(2, 2L)};
     *   Pair&lt;Integer, Long&gt;[] secondEntryArray = new Pair[]{
     *           tuple(3, 3L),
     *           tuple(4, 4L)};
     *   Map&lt;Integer, Long&gt; map = mapBuilderFromPairs(firstPairArray)
     *           .withPairs(secondPairArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Map&lt;Integer, Long&gt; map = Literals.mapWith(
     *           mapEntryFor(1, 1L),
     *           mapEntryFor(2, 2L),
     *           mapEntryFor(3, 3L),
     *           mapEntryFor(4, 4L));
     * </pre>
     * </blockquote>
     * The advantage of the {@code MapBuilder} is that the map can be built up from
     * individual objects, arrays or existing iterables. See {@link MapBuilder} for
     * further details.
     *
     * @param entries An array of {@code Pair} instance with which a
     *                {@code MapBuilder} should be initialised.
     * @param <K>     The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V>     The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of type
     *         {@code V} containing key-value mappings for each {@code Pair} in the
     *         supplied array.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderFromPairs(Pair<K, V>[] entries) {
        return new MapBuilder<K, V>().withPairs(entries);
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from all {@code Map} instances
     * in the supplied {@code Iterable}.
     *
     * <h4>Example Usage:</h4>
     * A {@code MapBuilder} can be used to assemble a {@code Multiset} from two existing
     * {@code Iterable} instances containing {@code Map} instances as follows:
     * <blockquote>
     * <pre>
     *   Iterable&lt;Map&lt;Integer, Long&gt;&gt; firstIterable = iterableWith(
     *           mapWith(mapEntryFor(1, 1L), mapEntryFor(2, 2L)),
     *           mapWith(mapEntryFor(3, 3L), mapEntryFor(4, 4L));
     *   Iterable&lt;Map&lt;Integer, Long&gt;&gt; secondIterable = iterableWith(
     *           mapWith(mapEntryFor(5, 5L)));
     *   Map&lt;Integer, Long&gt; map = mapBuilderFromMaps(firstIterable)
     *           .withMaps(secondIterable)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Map&lt;Integer, Long&gt; map = Literals.mapWith(
     *           tuple(1, 1L),
     *           tuple(2, 2L),
     *           tuple(3, 3L),
     *           tuple(4, 4L),
     *           tuple(5, 5L));
     * </pre>
     * </blockquote>
     * The advantage of the {@code MapBuilder} is that the map can be built up from
     * individual objects, arrays or existing iterables. See {@link MapBuilder} for
     * further details.
     *
     * @param entries An {@code Iterable} of {@code Map} instances whose entries should
     *                be used to initialise a {@code MapBuilder}.
     * @param <K>     The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V>     The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} initialised with all entries from all {@code Map}
     *         instances in the supplied {@code Iterable}.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderFromMaps(Iterable<? extends Map<K, V>> entries) {
        return new MapBuilder<K, V>().withMaps(entries);
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from all {@code Map} instances
     * in the supplied array.
     *
     * <h4>Example Usage:</h4>
     * A {@code MapBuilder} can be used to assemble a {@code Multiset} from two existing
     * arrays containing {@code Map} instances as follows:
     * <blockquote>
     * <pre>
     *   Map&lt;Integer, Long&gt;[] firstArray = new Map[]{
     *           mapWith(mapEntryFor(1, 1L), mapEntryFor(2, 2L)),
     *           mapWith(mapEntryFor(3, 3L), mapEntryFor(4, 4L)};
     *   Map&lt;Integer, Long&gt; secondArray = new Map[]{
     *           mapWith(mapEntryFor(5, 5L))};
     *   Map&lt;Integer, Long&gt; map = mapBuilderFromMaps(firstArray)
     *           .withMaps(secondArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Map&lt;Integer, Long&gt; map = Literals.mapWith(
     *           tuple(1, 1L),
     *           tuple(2, 2L),
     *           tuple(3, 3L),
     *           tuple(4, 4L),
     *           tuple(5, 5L));
     * </pre>
     * </blockquote>
     * The advantage of the {@code MapBuilder} is that the map can be built up from
     * individual objects, arrays or existing iterables. See {@link MapBuilder} for
     * further details.
     *
     * @param entries An array of {@code Map} instances whose entries should
     *                be used to initialise a {@code MapBuilder}.
     * @param <K>     The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V>     The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} initialised with all entries from all {@code Map}
     *         instances in the supplied array.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderFromMaps(Map<K, V>[] entries) {
        return new MapBuilder<K, V>().withMaps(entries);
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instance.
     *
     * @param e   The {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instance.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e) {
        return mapBuilderFromEntries(iterableWith(e));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e2  The second {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
        return mapBuilderFromEntries(iterableWith(e1, e2));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e2  The second {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e3  The third {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3) {
        return mapBuilderFromEntries(iterableWith(e1, e2, e3));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e2  The second {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e3  The third {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e4  The fourth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4) {
        return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e2  The second {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e3  The third {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e4  The fourth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e5  The fifth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5) {
        return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e2  The second {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e3  The third {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e4  The fourth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e5  The fifth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e6  The sixth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6) {
        return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e2  The second {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e3  The third {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e4  The fourth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e5  The fifth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e6  The sixth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e7  The seventh {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7) {
        return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e2  The second {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e3  The third {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e4  The fourth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e5  The fifth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e6  The sixth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e7  The seventh {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e8  The eighth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8) {
        return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e2  The second {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e3  The third {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e4  The fourth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e5  The fifth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e6  The sixth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e7  The seventh {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e8  The eighth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e9  The ninth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9) {
        return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instances.
     *
     * @param e1  The first {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e2  The second {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e3  The third {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e4  The fourth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e5  The fifth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e6  The sixth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e7  The seventh {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e8  The eighth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e9  The ninth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e10 The tenth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9, Map.Entry<K, V> e10) {
        return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied {@code Map.Entry} instances.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, subsequent method calls on the {@link MapBuilder}
     * can be used instead.</p>
     *
     * @param e1    The first {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e2    The second {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e3    The third {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e4    The fourth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e5    The fifth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e6    The sixth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e7    The seventh {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e8    The eighth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e9    The ninth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e10   The tenth {@code Map.Entry} instance to be added to this {@code MapBuilder}.
     * @param e11on The remaining {@code Map.Entry} instances to be added to this {@code MapBuilder}.
     * @param <K>   The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V>   The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied {@code Map.Entry} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9, Map.Entry<K, V> e10, Map.Entry<K, V>... e11on) {
        return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).and(e11on);
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with a key-value mapping for the supplied
     * {@code Pair} instance.
     *
     * @param e   The {@code Pair} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing a key-value mapping for the supplied
     *         {@code Pair} instance.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e) {
        return mapBuilderFromPairs(iterableWith(e));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for all supplied
     * {@code Pair} instances.
     *
     * @param e1  The first {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e2  The second {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing key-value mappings for all supplied
     *         {@code Pair} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2) {
        return mapBuilderFromPairs(iterableWith(e1, e2));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for all supplied
     * {@code Pair} instances.
     *
     * @param e1  The first {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e2  The second {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e3  The third {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing key-value mappings for all supplied
     *         {@code Pair} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3) {
        return mapBuilderFromPairs(iterableWith(e1, e2, e3));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for all supplied
     * {@code Pair} instances.
     *
     * @param e1  The first {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e2  The second {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e3  The third {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e4  The fourth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing key-value mappings for all supplied
     *         {@code Pair} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4) {
        return mapBuilderFromPairs(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for all supplied
     * {@code Pair} instances.
     *
     * @param e1  The first {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e2  The second {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e3  The third {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e4  The fourth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e5  The fifth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing key-value mappings for all supplied
     *         {@code Pair} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5) {
        return mapBuilderFromPairs(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for all supplied
     * {@code Pair} instances.
     *
     * @param e1  The first {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e2  The second {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e3  The third {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e4  The fourth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e5  The fifth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e6  The sixth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing key-value mappings for all supplied
     *         {@code Pair} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6) {
        return mapBuilderFromPairs(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for all supplied
     * {@code Pair} instances.
     *
     * @param e1  The first {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e2  The second {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e3  The third {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e4  The fourth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e5  The fifth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e6  The sixth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e7  The seventh {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing key-value mappings for all supplied
     *         {@code Pair} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7) {
        return mapBuilderFromPairs(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for all supplied
     * {@code Pair} instances.
     *
     * @param e1  The first {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e2  The second {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e3  The third {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e4  The fourth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e5  The fifth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e6  The sixth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e7  The seventh {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e8  The eighth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing key-value mappings for all supplied
     *         {@code Pair} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8) {
        return mapBuilderFromPairs(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for all supplied
     * {@code Pair} instances.
     *
     * @param e1  The first {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e2  The second {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e3  The third {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e4  The fourth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e5  The fifth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e6  The sixth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e7  The seventh {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e8  The eighth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e9  The ninth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing key-value mappings for all supplied
     *         {@code Pair} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9) {
        return mapBuilderFromPairs(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for all supplied
     * {@code Pair} instances.
     *
     * @param e1  The first {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e2  The second {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e3  The third {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e4  The fourth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e5  The fifth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e6  The sixth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e7  The seventh {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e8  The eighth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e9  The ninth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e10 The tenth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing key-value mappings for all supplied
     *         {@code Pair} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9, Pair<K, V> e10) {
        return mapBuilderFromPairs(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with key-value mappings for all supplied
     * {@code Pair} instances.
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, subsequent method calls on the {@link MapBuilder}
     * can be used instead.</p>
     *
     * @param e1    The first {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e2    The second {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e3    The third {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e4    The fourth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e5    The fifth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e6    The sixth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e7    The seventh {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e8    The eighth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e9    The ninth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e10   The tenth {@code Pair} instance from which to initialize this {@code MapBuilder}.
     * @param e11on The remaining {@code Pair} instances from which to initialize this
     *              {@code MapBuilder}.
     * @param <K>   The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V>   The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing key-value mappings for all supplied
     *         {@code Pair} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9, Pair<K, V> e10, Pair<K, V>... e11on) {
        return mapBuilderFromPairs(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).andPairs(e11on);
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * @param m   The {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from the supplied
     *         {@code Map} instance.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m) {
        return mapBuilderFromMaps(iterableWith(m));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value associated with that key in the
     * {@code MapBuilder}.
     * </p>
     *
     * @param m1  The first {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m2  The second {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from all supplied
     *         {@code Map} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m1, Map<K, V> m2) {
        return mapBuilderFromMaps(iterableWith(m1, m2));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value associated with that key in the
     * {@code MapBuilder}.
     * </p>
     *
     * @param m1  The first {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m2  The second {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m3  The third {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from all supplied
     *         {@code Map} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3) {
        return mapBuilderFromMaps(iterableWith(m1, m2, m3));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value associated with that key in the
     * {@code MapBuilder}.
     * </p>
     *
     * @param m1  The first {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m2  The second {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m3  The third {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m4  The fourth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from all supplied
     *         {@code Map} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4) {
        return mapBuilderFromMaps(iterableWith(m1, m2, m3, m4));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value associated with that key in the
     * {@code MapBuilder}.
     * </p>
     *
     * @param m1  The first {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m2  The second {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m3  The third {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m4  The fourth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m5  The fifth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from all supplied
     *         {@code Map} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5) {
        return mapBuilderFromMaps(iterableWith(m1, m2, m3, m4, m5));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value associated with that key in the
     * {@code MapBuilder}.
     * </p>
     *
     * @param m1  The first {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m2  The second {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m3  The third {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m4  The fourth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m5  The fifth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m6  The sixth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from all supplied
     *         {@code Map} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6) {
        return mapBuilderFromMaps(iterableWith(m1, m2, m3, m4, m5, m6));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value associated with that key in the
     * {@code MapBuilder}.
     * </p>
     *
     * @param m1  The first {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m2  The second {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m3  The third {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m4  The fourth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m5  The fifth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m6  The sixth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m7  The seventh {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from all supplied
     *         {@code Map} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6, Map<K, V> m7) {
        return mapBuilderFromMaps(iterableWith(m1, m2, m3, m4, m5, m6, m7));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value associated with that key in the
     * {@code MapBuilder}.
     * </p>
     *
     * @param m1  The first {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m2  The second {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m3  The third {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m4  The fourth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m5  The fifth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m6  The sixth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m7  The seventh {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m8  The eighth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from all supplied
     *         {@code Map} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6, Map<K, V> m7, Map<K, V> m8) {
        return mapBuilderFromMaps(iterableWith(m1, m2, m3, m4, m5, m6, m7, m8));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value associated with that key in the
     * {@code MapBuilder}.
     * </p>
     *
     * @param m1  The first {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m2  The second {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m3  The third {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m4  The fourth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m5  The fifth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m6  The sixth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m7  The seventh {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m8  The eighth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m9  The ninth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from all supplied
     *         {@code Map} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6, Map<K, V> m7, Map<K, V> m8, Map<K, V> m9) {
        return mapBuilderFromMaps(iterableWith(m1, m2, m3, m4, m5, m6, m7, m8, m9));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value associated with that key in the
     * {@code MapBuilder}.
     * </p>
     *
     * @param m1  The first {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m2  The second {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m3  The third {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m4  The fourth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m5  The fifth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m6  The sixth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m7  The seventh {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m8  The eighth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m9  The ninth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m10 The tenth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from all supplied
     *         {@code Map} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6, Map<K, V> m7, Map<K, V> m8, Map<K, V> m9, Map<K, V> m10) {
        return mapBuilderFromMaps(iterableWith(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with all entries from the supplied
     * {@code Map} instance.
     *
     * <p>If later {@code Map} instances in the argument list contain any of the same
     * keys as earlier {@code Map} instances then the value from the last {@code Map}
     * instance having that key is used as the value associated with that key in the
     * {@code MapBuilder}.
     * </p>
     *
     * <p>Note that this literal uses a generic varargs parameter as the last argument in the
     * argument list and as such will cause unchecked cast warnings. Explicit argument
     * lists for up to ten arguments have been provided for convenience. In order to avoid
     * the unchecked cast warnings, subsequent method calls on the {@link MapBuilder}
     * can be used instead.</p>
     *
     * @param m1    The first {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m2    The second {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m3    The third {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m4    The fourth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m5    The fifth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m6    The sixth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m7    The seventh {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m8    The eighth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m9    The ninth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m10   The tenth {@code Map} instance with which to initialise this {@code MapBuilder}.
     * @param m11on The remaining {@code Map} instances with which to initialise this
     *              {@code MapBuilder}.
     * @param <K>   The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V>   The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing all entries from all supplied
     *         {@code Map} instances.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map<K, V> m1, Map<K, V> m2, Map<K, V> m3, Map<K, V> m4, Map<K, V> m5, Map<K, V> m6, Map<K, V> m7, Map<K, V> m8, Map<K, V> m9, Map<K, V> m10, Map<K, V>... m11on) {
        return mapBuilderFromMaps(iterableWith(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10)).andMaps(m11on);
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied key mapped to the supplied
     * value.
     *
     * @param k1  The key for the first entry with which to initialise this {@code MapBuilder}.
     * @param v1  The value for the first entry with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied key mapped to the supplied value.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWithKeyValuePair(K k1, V v1) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry with which to initialise this {@code MapBuilder}.
     * @param v1  The value for the first entry with which to initialise this {@code MapBuilder}.
     * @param k2  The key for the second entry with which to initialise this {@code MapBuilder}.
     * @param v2  The value for the second entry with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWithKeyValuePairs(K k1, V v1, K k2, V v2) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry with which to initialise this {@code MapBuilder}.
     * @param v1  The value for the first entry with which to initialise this {@code MapBuilder}.
     * @param k2  The key for the second entry with which to initialise this {@code MapBuilder}.
     * @param v2  The value for the second entry with which to initialise this {@code MapBuilder}.
     * @param k3  The key for the third entry with which to initialise this {@code MapBuilder}.
     * @param v3  The value for the third entry with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry with which to initialise this {@code MapBuilder}.
     * @param v1  The value for the first entry with which to initialise this {@code MapBuilder}.
     * @param k2  The key for the second entry with which to initialise this {@code MapBuilder}.
     * @param v2  The value for the second entry with which to initialise this {@code MapBuilder}.
     * @param k3  The key for the third entry with which to initialise this {@code MapBuilder}.
     * @param v3  The value for the third entry with which to initialise this {@code MapBuilder}.
     * @param k4  The key for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param v4  The value for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry with which to initialise this {@code MapBuilder}.
     * @param v1  The value for the first entry with which to initialise this {@code MapBuilder}.
     * @param k2  The key for the second entry with which to initialise this {@code MapBuilder}.
     * @param v2  The value for the second entry with which to initialise this {@code MapBuilder}.
     * @param k3  The key for the third entry with which to initialise this {@code MapBuilder}.
     * @param v3  The value for the third entry with which to initialise this {@code MapBuilder}.
     * @param k4  The key for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param v4  The value for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param k5  The key for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param v5  The value for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry with which to initialise this {@code MapBuilder}.
     * @param v1  The value for the first entry with which to initialise this {@code MapBuilder}.
     * @param k2  The key for the second entry with which to initialise this {@code MapBuilder}.
     * @param v2  The value for the second entry with which to initialise this {@code MapBuilder}.
     * @param k3  The key for the third entry with which to initialise this {@code MapBuilder}.
     * @param v3  The value for the third entry with which to initialise this {@code MapBuilder}.
     * @param k4  The key for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param v4  The value for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param k5  The key for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param v5  The value for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param k6  The key for the sixth entry with which to initialise this {@code MapBuilder}.
     * @param v6  The value for the sixth entry with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry with which to initialise this {@code MapBuilder}.
     * @param v1  The value for the first entry with which to initialise this {@code MapBuilder}.
     * @param k2  The key for the second entry with which to initialise this {@code MapBuilder}.
     * @param v2  The value for the second entry with which to initialise this {@code MapBuilder}.
     * @param k3  The key for the third entry with which to initialise this {@code MapBuilder}.
     * @param v3  The value for the third entry with which to initialise this {@code MapBuilder}.
     * @param k4  The key for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param v4  The value for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param k5  The key for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param v5  The value for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param k6  The key for the sixth entry with which to initialise this {@code MapBuilder}.
     * @param v6  The value for the sixth entry with which to initialise this {@code MapBuilder}.
     * @param k7  The key for the seventh entry with which to initialise this {@code MapBuilder}.
     * @param v7  The value for the seventh entry with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry with which to initialise this {@code MapBuilder}.
     * @param v1  The value for the first entry with which to initialise this {@code MapBuilder}.
     * @param k2  The key for the second entry with which to initialise this {@code MapBuilder}.
     * @param v2  The value for the second entry with which to initialise this {@code MapBuilder}.
     * @param k3  The key for the third entry with which to initialise this {@code MapBuilder}.
     * @param v3  The value for the third entry with which to initialise this {@code MapBuilder}.
     * @param k4  The key for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param v4  The value for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param k5  The key for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param v5  The value for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param k6  The key for the sixth entry with which to initialise this {@code MapBuilder}.
     * @param v6  The value for the sixth entry with which to initialise this {@code MapBuilder}.
     * @param k7  The key for the seventh entry with which to initialise this {@code MapBuilder}.
     * @param v7  The value for the seventh entry with which to initialise this {@code MapBuilder}.
     * @param k8  The key for the eighth entry with which to initialise this {@code MapBuilder}.
     * @param v8  The value for the eighth entry with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry with which to initialise this {@code MapBuilder}.
     * @param v1  The value for the first entry with which to initialise this {@code MapBuilder}.
     * @param k2  The key for the second entry with which to initialise this {@code MapBuilder}.
     * @param v2  The value for the second entry with which to initialise this {@code MapBuilder}.
     * @param k3  The key for the third entry with which to initialise this {@code MapBuilder}.
     * @param v3  The value for the third entry with which to initialise this {@code MapBuilder}.
     * @param k4  The key for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param v4  The value for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param k5  The key for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param v5  The value for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param k6  The key for the sixth entry with which to initialise this {@code MapBuilder}.
     * @param v6  The value for the sixth entry with which to initialise this {@code MapBuilder}.
     * @param k7  The key for the seventh entry with which to initialise this {@code MapBuilder}.
     * @param v7  The value for the seventh entry with which to initialise this {@code MapBuilder}.
     * @param k8  The key for the eighth entry with which to initialise this {@code MapBuilder}.
     * @param v8  The value for the eighth entry with which to initialise this {@code MapBuilder}.
     * @param k9  The key for the ninth entry with which to initialise this {@code MapBuilder}.
     * @param v9  The value for the ninth entry with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9));
    }

    /**
     * Returns a {@code MapBuilder} with keys of type {@code K} and values
     * of type {@code V} initialised with the supplied keys mapped to the corresponding
     * supplied values.
     *
     * @param k1  The key for the first entry with which to initialise this {@code MapBuilder}.
     * @param v1  The value for the first entry with which to initialise this {@code MapBuilder}.
     * @param k2  The key for the second entry with which to initialise this {@code MapBuilder}.
     * @param v2  The value for the second entry with which to initialise this {@code MapBuilder}.
     * @param k3  The key for the third entry with which to initialise this {@code MapBuilder}.
     * @param v3  The value for the third entry with which to initialise this {@code MapBuilder}.
     * @param k4  The key for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param v4  The value for the fourth entry with which to initialise this {@code MapBuilder}.
     * @param k5  The key for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param v5  The value for the fifth entry with which to initialise this {@code MapBuilder}.
     * @param k6  The key for the sixth entry with which to initialise this {@code MapBuilder}.
     * @param v6  The value for the sixth entry with which to initialise this {@code MapBuilder}.
     * @param k7  The key for the seventh entry with which to initialise this {@code MapBuilder}.
     * @param v7  The value for the seventh entry with which to initialise this {@code MapBuilder}.
     * @param k8  The key for the eighth entry with which to initialise this {@code MapBuilder}.
     * @param v8  The value for the eighth entry with which to initialise this {@code MapBuilder}.
     * @param k9  The key for the ninth entry with which to initialise this {@code MapBuilder}.
     * @param v9  The value for the ninth entry with which to initialise this {@code MapBuilder}.
     * @param k10 The key for the tenth entry with which to initialise this {@code MapBuilder}.
     * @param v10 The value for the tenth entry with which to initialise this {@code MapBuilder}.
     * @param <K> The type of the keys to be in the returned {@code MapBuilder}.
     * @param <V> The type of the values to be in the returned {@code MapBuilder}.
     * @return A {@code MapBuilder} instance with keys of type {@code K} and values of
     *         type {@code V} containing the supplied keys mapped to the corresponding
     *         supplied values.
     */
    public static <K, V> MapBuilder<K, V> mapBuilderWithKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9), mapEntryFor(k10, v10));
    }

    /**
     * Returns a {@code Map.Entry} instance over types {@code K} and {@code V} containing
     * the supplied key and value.
     *
     * @param key   The key to be used in the constructed {@code Map.Entry} instance.
     * @param value The value to be used in the constructed {@code Map.Entry} instance.
     * @param <K>   The type of the key in the {@code Map.Entry}.
     * @param <V>   The type of the value in the {@code Map.Entry}.
     * @return a {@code Map.Entry} instance containing the supplied key and value.
     */
    public static <K, V> Map.Entry<K, V> mapEntryFor(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
    }

    /**
     * Returns a {@code Map.Entry} instance over types {@code K} and {@code V} with the
     * first value in the supplied {@code Pair} as the key and the second value in the
     * supplied {@code Pair} as the value.
     *
     * @param pair A {@code pair} representing the key and value to be used in
     *             the constructed {@code Map.Entry} instance.
     * @param <K>  The type of the key in the {@code Map.Entry}.
     * @param <V>  The type of the value in the {@code Map.Entry}.
     * @return a {@code Map.Entry} instance containing a key-value mapping for the
     *         supplied {@code Pair} instance.
     */
    public static <K, V> Map.Entry<K, V> mapEntryFor(Pair<K, V> pair) {
        return mapEntryFor(pair.getFirst(), pair.getSecond());
    }

    /**
     * Returns an empty array instance over the type {@code E}.
     *
     * @param <E> The type of the elements that would be contained by this array
     *            if it contained any.
     * @return An array instance over the type {@code E} containing no elements.
     */
    @SuppressWarnings("unchecked")
    public static <E> E[] arrayOf(Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass).build();
    }

    /**
     * Returns an array instance over the type {@code E} containing all elements
     * from the supplied {@code Iterable}. The order of the elements in the resulting
     * array is determined by the order in which they are yielded from the
     * {@code Iterable}.
     *
     * <p>The supplied {@code Iterable} must contain at least one element so that
     * the type E can be correctly inferred when constructing the array. In the
     * case that the {@code Iterable} is empty, an {@code IllegalArgumentException}
     * will be thrown.</p>
     *
     * <p>The elements in the supplied {@code Iterable} must all be of the same
     * concrete type so that the type E can be inferred deterministically when
     * constructing the array. In the case that the {@code Iterable} contains
     * elements of different concrete types, an {@code IllegalArgumentException}
     * will be thrown. If an array containing multiple concrete types of some
     * supertype is required, use the {@link #arrayFrom(Iterable, Class)}
     * variant.</p>
     *
     * @param elements An {@code Iterable} of elements from which an array should be
     *                 constructed.
     * @param <E>      The type of the elements to be contained in the returned array.
     * @return An array over the type {@code E} containing all elements from the
     *         supplied {@code Iterable} in the order they are yielded.
     * @throws IllegalArgumentException if the supplied {@code Iterable} contains no
     *                                  elements or contains elements of different
     *                                  concrete types.
     */
    @SuppressWarnings("unchecked")
    public static <E> E[] arrayFrom(Iterable<E> elements) {
        return new ArrayBuilder<E>().with(elements).build();
    }

    /**
     * Returns an array instance over the type {@code E} containing all elements
     * from the supplied {@code Iterable}. The order of the elements in the resulting
     * array is determined by the order in which they are yielded from the
     * {@code Iterable}.
     *
     * <p>Unlike {@link #arrayFrom(Iterable)}, this variant accepts empty
     * {@code Iterable}s and {@code Iterable}s containing instances of different
     * concrete types and so should be used in preference of {@link #arrayFrom(Iterable)}
     * if such {@code Iterable}s are expected.</p>
     *
     * <h4>Example Usage:</h4>
     * Assume that we have the following instances:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *   Iterable&lt;Employee&gt; employees = iterableWith(partTimeEmployee, fullTimeEmployee, hourlyEmployee);
     * </pre>
     * </blockquote>
     * If we attempt to construct an array directly from the {@code Iterable}, an
     * {@code IllegalArgumentException} will be thrown:
     * <blockquote>
     * <pre>
     *   Employee[] employeeArray = Literals.arrayFrom(employees); // => IllegalArgumentException
     * </pre>
     * </blockquote>
     * However using this variant, we obtain an array of {@code Employee} instances. The following
     * two arrays are equivalent:
     * <blockquote>
     * <pre>
     *   Employee[] employeeArray = Literals.arrayFrom(employees, Employee.class);
     *   Employee[] employeeArray = new Employee[]{partTimeEmployee, fullTimeEmployee, hourlyEmployee};
     * </pre>
     * </blockquote>
     *
     * @param elements     An {@code Iterable} of elements from which an array should be
     *                     constructed.
     * @param elementClass A {@code Class} representing the required type {@code E} of
     *                     the resultant array.
     * @param <E>          The type of the elements to be contained in the returned array.
     * @return An array over the type {@code E} containing all elements from the
     *         supplied {@code Iterable} in the order they are yielded.
     */
    public static <E> E[] arrayFrom(Iterable<? extends E> elements, Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass).with(elements).build();
    }

    /**
     * Returns an array instance over the type {@code E} containing all elements
     * from the supplied array. The order of the elements in the resulting
     * array is the same as the order of elements in the supplied array.
     *
     * <p>The supplied array must contain at least one element so that the type E
     * can be correctly inferred when constructing the array to return. In the
     * case that the array is empty, an {@code IllegalArgumentException}
     * will be thrown.</p>
     *
     * <p>The elements in the supplied array must all be of the same
     * concrete type so that the type E can be inferred deterministically when
     * constructing the array. In the case that the array contains
     * elements of different concrete types, an {@code IllegalArgumentException}
     * will be thrown. If an array containing multiple concrete types of some
     * supertype is required, use the {@link #arrayFrom(Object[], Class)}
     * variant.</p>
     *
     * @param elementArray An array of elements from which an array should be
     *                     constructed.
     * @param <E>          The type of the elements to be contained in the returned array.
     * @return An array over the type {@code E} containing all elements from the
     *         supplied array in the same order as the supplied array.
     * @throws IllegalArgumentException if the supplied {@code Iterable} contains no
     *                                  elements or contains elements of different
     *                                  concrete types.
     */
    public static <E> E[] arrayFrom(E[] elementArray) {
        return new ArrayBuilder<E>().with(elementArray).build();
    }

    /**
     * Returns an array instance over the type {@code E} containing all elements
     * from the supplied array. The order of the elements in the resulting
     * array is the same as the order of elements in the supplied array.
     *
     * <p>Unlike {@link #arrayFrom(Object[])}, this variant accepts empty
     * arrays and arrays containing instances of different concrete types and
     * so should be used in preference of {@link #arrayFrom(Object[])}
     * if such arrays are expected.</p>
     *
     * <h4>Example Usage:</h4>
     * Assume that we have the following instances:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *   Employee[] employees = new Employee[]{partTimeEmployee, fullTimeEmployee, hourlyEmployee};
     * </pre>
     * </blockquote>
     * If we attempt to construct an array directly from the array, an
     * {@code IllegalArgumentException} will be thrown:
     * <blockquote>
     * <pre>
     *   Employee[] employeeArray = Literals.arrayFrom(employees); // => IllegalArgumentException
     * </pre>
     * </blockquote>
     * However using this variant, we obtain an array of {@code Employee} instances. The following
     * two arrays are equivalent:
     * <blockquote>
     * <pre>
     *   Employee[] employeeArray = Literals.arrayFrom(employees, Employee.class);
     *   Employee[] employeeArray = new Employee[]{partTimeEmployee, fullTimeEmployee, hourlyEmployee};
     * </pre>
     * </blockquote>
     *
     * @param elementArray An array of elements from which an array should be constructed.
     * @param elementClass A {@code Class} representing the required type {@code E} of
     *                     the resultant array.
     * @param <E>          The type of the elements to be contained in the returned array.
     * @return An array over the type {@code E} containing all elements from the
     *         supplied array in the same order.
     */
    public static <E> E[] arrayFrom(E[] elementArray, Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass).with(elementArray).build();
    }

    /**
     * Returns an array over the type {@code E} containing the supplied element.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e   An element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e) {
        return arrayFrom(iterableWith(e));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2) {
        return arrayFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3) {
        return arrayFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4) {
        return arrayFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param e6  The sixth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param e6  The sixth element from which to construct an array.
     * @param e7  The seventh element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param e6  The sixth element from which to construct an array.
     * @param e7  The seventh element from which to construct an array.
     * @param e8  The eighth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param e6  The sixth element from which to construct an array.
     * @param e7  The seventh element from which to construct an array.
     * @param e8  The eighth element from which to construct an array.
     * @param e9  The ninth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param e6  The sixth element from which to construct an array.
     * @param e7  The seventh element from which to construct an array.
     * @param e8  The eighth element from which to construct an array.
     * @param e9  The ninth element from which to construct an array.
     * @param e10 The tenth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1    The first element from which to construct an array.
     * @param e2    The second element from which to construct an array.
     * @param e3    The third element from which to construct an array.
     * @param e4    The fourth element from which to construct an array.
     * @param e5    The fifth element from which to construct an array.
     * @param e6    The sixth element from which to construct an array.
     * @param e7    The seventh element from which to construct an array.
     * @param e8    The eighth element from which to construct an array.
     * @param e9    The ninth element from which to construct an array.
     * @param e10   The tenth element from which to construct an array.
     * @param e11on The remaining elements from which to construct an array.
     * @param <E>   The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return arrayFrom(iterableBuilderWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10).and(e11on).build());
    }

    /**
     * Returns an {@code ArrayBuilder} containing no elements. When asked to
     * build an array, the element class will be inferred from the added elements
     * which means empty arrays and mixed concrete type arrays cannot be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array as follows:
     * <blockquote>
     * <pre>
     *   Integer[] array = Literals.&lt;Integer&gt;arrayBuilder()
     *           .with(1, 2, 3)
     *           .and(4, 5, 6)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6}
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param <E> The type of the elements contained in the {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing no elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilder() {
        return new ArrayBuilder<E>();
    }

    /**
     * Returns an {@code ArrayBuilder} over the type of the supplied {@code Class}
     * containing no elements. When asked to build an array, the supplied element
     * class will be used allowing empty arrays and mixed concrete type arrays to
     * be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array as follows:
     * <blockquote>
     * <pre>
     *   Integer[] array = arrayBuilderOf(Integer.class)
     *           .with(1, 2, 3)
     *           .and(4, 5, 6)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6}
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param <E>          The type of the elements contained in the {@code ArrayBuilder}.
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code ArrayBuilder} and the type represented
     *                     by the built array.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing no elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderOf(Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass);
    }

    /**
     * Returns an {@code ArrayBuilder} over type {@code E} initialised with the elements
     * contained in the supplied {@code Iterable}. When asked to build an array, the
     * element class will be inferred from the added elements which means empty arrays
     * and mixed concrete type arrays cannot be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array from two existing
     * {@code Collection} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstCollection = Literals.collectionWith(1, 2, 3);
     *   Collection&lt;Integer&gt; secondCollection = Literals.collectionWith(3, 4, 5);
     *   Integer[] array = arrayBuilderFrom(firstCollection)
     *           .with(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 3, 4, 5};
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param elements An {@code Iterable} containing elements with which the
     *                 {@code ArrayBuilder} should be initialised.
     * @param <E>      The type of the elements contained in the {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> ArrayBuilder<E> arrayBuilderFrom(Iterable<E> elements) {
        return new ArrayBuilder<E>().with(elements);
    }

    /**
     * Returns an {@code ArrayBuilder} over the type of the supplied {@code Class}
     * initialised with the elements contained in the supplied {@code Iterable}.
     * When asked to build an array, the supplied element class will be used allowing
     * empty arrays and mixed concrete type arrays to be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array from two existing
     * {@code Collection} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstCollection = Literals.collectionWith(1, 2, 3);
     *   Collection&lt;Integer&gt; secondCollection = Literals.collectionWith(3, 4, 5);
     *   Integer[] array = arrayBuilderFrom(firstCollection, Integer.class)
     *           .with(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 3, 4, 5};
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param elements     An {@code Iterable} containing elements with which the
     *                     {@code ArrayBuilder} should be initialised.
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code ArrayBuilder} and the type represented
     *                     by the built array.
     * @param <E>          The type of the elements contained in the {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> ArrayBuilder<E> arrayBuilderFrom(Iterable<? extends E> elements, Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass).with(elements);
    }

    /**
     * Returns an {@code ArrayBuilder} over type {@code E} initialised with the elements
     * contained in the supplied array. When asked to build an array, the element class
     * will be inferred from the added elements which means empty arrays and mixed
     * concrete type arrays cannot be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array from two existing
     * arrays as follows:
     * <blockquote>
     * <pre>
     *   Integer[] firstArray = new Integer[]{1, 2, 3};
     *   Integer[] secondArray = new Integer[]{3, 4, 5};
     *   Integer[] array = arrayBuilderFrom(firstArray)
     *           .with(secondArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 3, 4, 5};
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param elementArray An array containing elements with which the {@code ArrayBuilder}
     *                     should be initialised.
     * @param <E>          The type of the elements contained in the {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing
     *         the elements from the supplied array.
     */
    public static <E> ArrayBuilder<E> arrayBuilderFrom(E[] elementArray) {
        return new ArrayBuilder<E>().with(elementArray);
    }

    /**
     * Returns an {@code ArrayBuilder} over the type of the supplied {@code Class}
     * initialised with the elements contained in the supplied array. When asked to
     * build an array, the supplied element class will be used allowing empty arrays
     * and mixed concrete type arrays to be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array from two existing
     * arrays as follows:
     * <blockquote>
     * <pre>
     *   Integer[] firstArray = new Integer[]{1, 2, 3};
     *   Integer[] secondArray = new Integer[]{3, 4, 5};
     *   Integer[] array = arrayBuilderFrom(firstArray, Integer.class)
     *           .with(secondArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 3, 4, 5};
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param elementArray An array containing elements with which the
     *                     {@code ArrayBuilder} should be initialised.
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code ArrayBuilder} and the type represented
     *                     by the built array.
     * @param <E>          The type of the elements contained in the {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing
     *         the elements from the supplied array.
     */
    public static <E> ArrayBuilder<E> arrayBuilderFrom(E[] elementArray, Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass).with(elementArray);
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the
     * supplied element.
     *
     * @param e   The element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         element.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e) {
        return arrayBuilderFrom(iterableWith(e));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2) {
        return arrayBuilderFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6  The sixth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6  The sixth element to be added to the {@code ArrayBuilder}.
     * @param e7  The seventh element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6  The sixth element to be added to the {@code ArrayBuilder}.
     * @param e7  The seventh element to be added to the {@code ArrayBuilder}.
     * @param e8  The eighth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6  The sixth element to be added to the {@code ArrayBuilder}.
     * @param e7  The seventh element to be added to the {@code ArrayBuilder}.
     * @param e8  The eighth element to be added to the {@code ArrayBuilder}.
     * @param e9  The ninth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6  The sixth element to be added to the {@code ArrayBuilder}.
     * @param e7  The seventh element to be added to the {@code ArrayBuilder}.
     * @param e8  The eighth element to be added to the {@code ArrayBuilder}.
     * @param e9  The ninth element to be added to the {@code ArrayBuilder}.
     * @param e10 The tenth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1    The first element to be added to the {@code ArrayBuilder}.
     * @param e2    The second element to be added to the {@code ArrayBuilder}.
     * @param e3    The third element to be added to the {@code ArrayBuilder}.
     * @param e4    The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5    The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6    The sixth element to be added to the {@code ArrayBuilder}.
     * @param e7    The seventh element to be added to the {@code ArrayBuilder}.
     * @param e8    The eighth element to be added to the {@code ArrayBuilder}.
     * @param e9    The ninth element to be added to the {@code ArrayBuilder}.
     * @param e10   The tenth element to be added to the {@code ArrayBuilder}.
     * @param e11on The remaining elements to be added to the {@code ArrayBuilder}. The elements
     *              will be added to the {@code ArrayBuilder} in the order they are defined in the
     *              variadic argument.
     * @param <E>   The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return arrayBuilderFrom(iterableBuilderWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10).and(e11on).build());
    }

    /**
     * Returns a {@code Single} over the specified value.
     *
     * @param first The value to be used as the first in the returned {@code Single}.
     * @param <R>   The type of the first in the returned {@code Single}.
     * @return A {@code Single} over the type and value of the supplied argument.
     */
    public static <R> Single<R> tuple(R first) {
        return single(first);
    }

    /**
     * Returns a {@code Pair} over the specified values.
     *
     * @param first  The value to be used as the first in the returned {@code Pair}.
     * @param second The value to be used as the second in the returned {@code Pair}.
     * @param <R>    The type of the first in the returned {@code Pair}.
     * @param <S>    The type of the second in the returned {@code Pair}.
     * @return A {@code Pair} over the types and values of the supplied arguments.
     */
    public static <R, S> Pair<R, S> tuple(R first, S second) {
        return pair(first, second);
    }

    /**
     * Returns a {@code Triple} over the specified values.
     *
     * @param first  The value to be used as the first in the returned {@code Triple}.
     * @param second The value to be used as the second in the returned {@code Triple}.
     * @param third  The value to be used as the third in the returned {@code Triple}.
     * @param <R>    The type of the first in the returned {@code Triple}.
     * @param <S>    The type of the second in the returned {@code Triple}.
     * @param <T>    The type of the third in the returned {@code Triple}.
     * @return A {@code Triple} over the types and values of the supplied arguments.
     */
    public static <R, S, T> Triple<R, S, T> tuple(R first, S second, T third) {
        return triple(first, second, third);
    }

    /**
     * Returns a {@code Quadruple} over the specified values.
     *
     * @param first  The value to be used as the first in the returned {@code Quadruple}.
     * @param second The value to be used as the second in the returned {@code Quadruple}.
     * @param third  The value to be used as the third in the returned {@code Quadruple}.
     * @param fourth The value to be used as the fourth in the returned {@code Quadruple}.
     * @param <R>    The type of the first in the returned {@code Quadruple}.
     * @param <S>    The type of the second in the returned {@code Quadruple}.
     * @param <T>    The type of the third in the returned {@code Quadruple}.
     * @param <U>    The type of the fourth in the returned {@code Quadruple}.
     * @return A {@code Quadruple} over the types and values of the supplied arguments.
     */
    public static <R, S, T, U> Quadruple<R, S, T, U> tuple(R first, S second, T third, U fourth) {
        return quadruple(first, second, third, fourth);
    }

    /**
     * Returns a {@code Quintuple} over the specified values.
     *
     * @param first  The value to be used as the first in the returned {@code Quintuple}.
     * @param second The value to be used as the second in the returned {@code Quintuple}.
     * @param third  The value to be used as the third in the returned {@code Quintuple}.
     * @param fourth The value to be used as the fourth in the returned {@code Quintuple}.
     * @param fifth  The value to be used as the fifth in the returned {@code Quintuple}.
     * @param <R>    The type of the first in the returned {@code Quintuple}.
     * @param <S>    The type of the second in the returned {@code Quintuple}.
     * @param <T>    The type of the third in the returned {@code Quintuple}.
     * @param <U>    The type of the fourth in the returned {@code Quintuple}.
     * @param <V>    The type of the fifth in the returned {@code Quintuple}.
     * @return A {@code Quintuple} over the types and values of the supplied arguments.
     */
    public static <R, S, T, U, V> Quintuple<R, S, T, U, V> tuple(R first, S second, T third, U fourth, V fifth) {
        return quintuple(first, second, third, fourth, fifth);
    }

    /**
     * Returns a {@code Sextuple} over the specified values.
     *
     * @param first  The value to be used as the first in the returned {@code Sextuple}.
     * @param second The value to be used as the second in the returned {@code Sextuple}.
     * @param third  The value to be used as the third in the returned {@code Sextuple}.
     * @param fourth The value to be used as the fourth in the returned {@code Sextuple}.
     * @param fifth  The value to be used as the fifth in the returned {@code Sextuple}.
     * @param sixth  The value to be used as the sixth in the returned {@code Sextuple}.
     * @param <R>    The type of the first in the returned {@code Sextuple}.
     * @param <S>    The type of the second in the returned {@code Sextuple}.
     * @param <T>    The type of the third in the returned {@code Sextuple}.
     * @param <U>    The type of the fourth in the returned {@code Sextuple}.
     * @param <V>    The type of the fifth in the returned {@code Sextuple}.
     * @param <W>    The type of the sixth in the returned {@code Sextuple}.
     * @return A {@code Sextuple} over the types and values of the supplied arguments.
     */
    public static <R, S, T, U, V, W> Sextuple<R, S, T, U, V, W> tuple(R first, S second, T third, U fourth, V fifth, W sixth) {
        return sextuple(first, second, third, fourth, fifth, sixth);
    }

    /**
     * Returns a {@code Septuple} over the specified values.
     *
     * @param first   The value to be used as the first in the returned {@code Septuple}.
     * @param second  The value to be used as the second in the returned {@code Septuple}.
     * @param third   The value to be used as the third in the returned {@code Septuple}.
     * @param fourth  The value to be used as the fourth in the returned {@code Septuple}.
     * @param fifth   The value to be used as the fifth in the returned {@code Septuple}.
     * @param sixth   The value to be used as the sixth in the returned {@code Septuple}.
     * @param seventh The value to be used as the seventh in the returned {@code Septuple}.
     * @param <R>     The type of the first in the returned {@code Septuple}.
     * @param <S>     The type of the second in the returned {@code Septuple}.
     * @param <T>     The type of the third in the returned {@code Septuple}.
     * @param <U>     The type of the fourth in the returned {@code Septuple}.
     * @param <V>     The type of the fifth in the returned {@code Septuple}.
     * @param <W>     The type of the sixth in the returned {@code Septuple}.
     * @param <X>     The type of the seventh in the returned {@code Septuple}.
     * @return A {@code Septuple} over the types and values of the supplied arguments.
     */
    public static <R, S, T, U, V, W, X> Septuple<R, S, T, U, V, W, X> tuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh) {
        return septuple(first, second, third, fourth, fifth, sixth, seventh);
    }

    /**
     * Returns a {@code Octuple} over the specified values.
     *
     * @param first   The value to be used as the first in the returned {@code Octuple}.
     * @param second  The value to be used as the second in the returned {@code Octuple}.
     * @param third   The value to be used as the third in the returned {@code Octuple}.
     * @param fourth  The value to be used as the fourth in the returned {@code Octuple}.
     * @param fifth   The value to be used as the fifth in the returned {@code Octuple}.
     * @param sixth   The value to be used as the sixth in the returned {@code Octuple}.
     * @param seventh The value to be used as the seventh in the returned {@code Octuple}.
     * @param eighth  The value to be used as the eighth in the returned {@code Octuple}.
     * @param <R>     The type of the first in the returned {@code Octuple}.
     * @param <S>     The type of the second in the returned {@code Octuple}.
     * @param <T>     The type of the third in the returned {@code Octuple}.
     * @param <U>     The type of the fourth in the returned {@code Octuple}.
     * @param <V>     The type of the fifth in the returned {@code Octuple}.
     * @param <W>     The type of the sixth in the returned {@code Octuple}.
     * @param <X>     The type of the seventh in the returned {@code Octuple}.
     * @param <Y>     The type of the eighth in the returned {@code Octuple}.
     * @return A {@code Octuple} over the types and values of the supplied arguments.
     */
    public static <R, S, T, U, V, W, X, Y> Octuple<R, S, T, U, V, W, X, Y> tuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth) {
        return octuple(first, second, third, fourth, fifth, sixth, seventh, eighth);
    }

    /**
     * Returns a {@code Nonuple} over the specified values.
     *
     * @param first   The value to be used as the first in the returned {@code Nonuple}.
     * @param second  The value to be used as the second in the returned {@code Nonuple}.
     * @param third   The value to be used as the third in the returned {@code Nonuple}.
     * @param fourth  The value to be used as the fourth in the returned {@code Nonuple}.
     * @param fifth   The value to be used as the fifth in the returned {@code Nonuple}.
     * @param sixth   The value to be used as the sixth in the returned {@code Nonuple}.
     * @param seventh The value to be used as the seventh in the returned {@code Nonuple}.
     * @param eighth  The value to be used as the eighth in the returned {@code Nonuple}.
     * @param ninth   The value to be used as the ninth in the returned {@code Nonuple}.
     * @param <R>     The type of the first in the returned {@code Nonuple}.
     * @param <S>     The type of the second in the returned {@code Nonuple}.
     * @param <T>     The type of the third in the returned {@code Nonuple}.
     * @param <U>     The type of the fourth in the returned {@code Nonuple}.
     * @param <V>     The type of the fifth in the returned {@code Nonuple}.
     * @param <W>     The type of the sixth in the returned {@code Nonuple}.
     * @param <X>     The type of the seventh in the returned {@code Nonuple}.
     * @param <Y>     The type of the eighth in the returned {@code Nonuple}.
     * @param <Z>     The type of the ninth in the returned {@code Nonuple}.
     * @return A {@code Nonuple} over the types and values of the supplied arguments.
     */
    public static <R, S, T, U, V, W, X, Y, Z> Nonuple<R, S, T, U, V, W, X, Y, Z> tuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth, Z ninth) {
        return nonuple(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);
    }
}
